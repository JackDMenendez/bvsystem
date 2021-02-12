#ifndef VSAMDB_H_
#define VSAMDB_H_
#include <stddef.h>
/* TODO:
* Other topics for discussion: collections * key retrieval
*/
/*
* requirements for define:
*
* required: name, max_space, storclass, keyname
* optional: log
*
* defaults:
* shareoptions(2,3)
* volcnt=59 (number of volumes)
* log(none/undo/all)
*
* required for add_index required: altkeys, uniqueness
* 09/27/20 Notes:
* Expiration??? ACS routines may set management class. Added to create * options
* Future question - EBCDIC v UTF-8? should mixed be allowed... * - more options: all UTF-8? or all textual elements the configured compiler setting */
enum db_format {
JSON_DB = 0, // <- Default?
BSON_DB
};
enum db_log_options {
LOG_NONE = 0,
LOG_UNDO,
LOG_ALL
};
/*
* Options for create. All are optional.
*/
struct db_create_options {
int version;
enum db_format format;
int max_space;
int avg_doc_size;
enum db_log_options log_options;
char *primary_key;
char *storclas;
char *mgmtclas;
char *dataclas;
char *logstream_id;
};
/*
* Options for add_index. All are optional.
*/
struct db_add_index_options {
int version;
unsigned int flags;
};
typedef void * db_connection_t;
typedef void * db_result_set_t;
typedef void * db_transaction_t;
#define DB_INDEX_FLAG_ALLOW_DUPLICATES (1 << 0)
#define DB_INDEX_FLAG_ASYNC (1 << 1)
#ifdef __CPP__
extern "C" {
#endif
/************************************************************ * Database managment functions.
************************************************************/ /**
*
* @param dsname Name of the VSAM cluster. Must not be null.
* @param options pointer to options which describe the VSAMDB cluster. If NULL, * defaults will be used.
* @returns
* 0 - successful
* VSAMDB_ERR_EXISTS - cluster already exists
* VSAMDB_ERR_BAD_DSNAME -
* VSAMDB_ERR_NO_SPACE
* VSAMDB_ERR_PERM - Not authorized
* ... etc parm validation errors
*/
/*
* Implementation notes:
* Invokes AMS/catalog/dynamic allocation under the covers to allocate * cluster
* TODO: parm validation? yes/no/how rigorous?
* TODO: Should create prime the free space on the cluster? involves creating a dummy doc
* Any concerns about user catalog?
*/
int db_create(const char *dsname, const struct db_create_options *options); /**
* Call after receiving a failing return code to get detailed failure * information.
*
* @param buflen - pointer to the size of the buffer. If the buffer is too  * small, returns the required size to hold the diagnostic
* information.
* @param buffer - pointer to an area to store diagnostic information. *
* @returns
* 0 - successful
* VSAMDB_ERR_BUFFER_TOO_SMALL -
*/
int db_last_error(size_t *buflen, char *buffer); // TBD
/**
* @param con the database connection.
* @param altkey the alternate key to index
* @param options pointer to options which describe the alternate index. Defaults * will be used if NULL.
* @returns
* 0 - successful
* VSAMDB_ERR_DUPLICATE_INDEX - index already exists * VSAMDB_ERR_BAD_KEY - specified key cannot be parsed * VSAMDB_ERR_PERM - Not authorized
*/
int db_add_index(db_connection_t con, const char *altkey,
const struct db_create_options *options);
//TODO: Define
int db_drop_index(db_connection_t con, ...);
/**
* @param dsname the name of the VSAMDB cluster to destroy. * @returns
* 0 - successful
* VSAMDB_ERR_DB_DOES_NOT_EXIST - the cluster does not exist * VSAMDB_ERR_IN_USE - the cluster is still in use
* VSAMDB_ERR_PERM - Not authorized
* VSAMDB_ERR_ENVIRONMENTAL - misc environmental error.
*/
/*
* implementation notes:
* Invokes dynamic allocation to delete the data set
*/
int db_destroy(const char *dsname);
/******************************************************************* * Connection functions
*******************************************************************/ enum db_integrity {
NRI=0,
CR,
CRE
};
enum db_boolean {
DB_DEFAULT = 0,
DB_TRUE,
DB_FALSE
};
struct db_open_options {
int version;
enum db_integrity integrity;
unsigned int timeout;
enum db_boolean autocommit; // proposed
};
//TODO: question - max number of connections?
#define VSAMDB_OPEN_FLAG_READONLY (1 << 0)
#define VSAMDB_OPEN_FLAG_FORCE_UPDATE (1 << 1)
/**
* @param con A pointer to the returned VSAMDB connection
* @param dsname The name of the VSAMDB cluster to open
* @param flags Option flags for the new connection
* @param options An optional pointer to the open options.
* May be NULL.
*/
/*
* Implementation notes:
* Performs the dynalloc; no ddname required
* Should open open the whole sphere?
*
* integrity levels:
* READ_UNCOMMITTED ~ NRI
* READ_COMMITTED ~ (minimally) CR (with waits)
* READ_REPEATABLE ~ CRE (with waits)
* READ_SERIALIZABLE ~ not implemented
*
* transactions
* A - updates
* B - NRI reads
*
* R is not updated, R' is updated
* What does B see?
* A has not updated yet - R
* A has updated but not committed (NRI)- R'
* A has updated but not committed (CR/CRE)- wait for the commit/backout * A has committed - R'
* A has backed out - R
*/
int db_open(db_connection_t *con, const char *dsname, unsigned int flags, const struct db_open_options *options);
/**
* Closes the VSAMDB connection.
*/
int db_close(db_connection_t con);
/**
* Sets the autocommit property. If set to true and there is an outstanding
* transaction, the transaction is committed.
*/
int db_set_autocommit(db_connection_t con, unsigned int autocommit);
/****************************************************************************** * Read functions
******************************************************************************/ typedef void * db_result_set_t;
typedef void * db_result_t;
typedef char doc_id_t[128];
#define VSAMDB_REQUEST_ASYNC (1 << 0)
/**
* Reads a single a single document from the database.
*
* @param con The database conneciton
* @param buf the buffer to fill
* @param buf_len A pointer to a length field. Provides the size of the buffer * on input, and is updated with the length written on output
* @param key The document key to be searched
* @param key_value The value to search for. If NULL and the key is indexed, * the result will point to the first entry indexed entry for
* the key.
* If the DB is BSON, key_value points to a well-formed BSON * value.
* TODO: comment - if key is not indexed, still is desirable to search through * entries without caller code modification
* batch read... flesh out more
*/
int db_read(db_connection_t con, const char *buf, size_t *buf_len, const char *key, const char *key_value, unsigned int flags);
int db_read_by_id(db_connection_t con, const char *buf, size_t *buf_len, const doc_id_t *id, unsigned int flags);
/**
* Reads multiple documents from the database. If CRE read integrity, then * any read result in the returned result set cannot be updated or deleted * until the result set is closed.
*
* @param con The database conneciton
* @param results The return results context
* @param key The document key to be searched
* @param key_value The value to search for. If NULL and the key is indexed, * the result will point to the first entry indexed entry for
* the key.
* If the DB is BSON, key_value points to a well-formed BSON * value.
* TODO:
* flags should include option for reading for update
*
* direct read for no update (probably better suited for db_read)
* direct read for update (enables update and erases; requires exclusive lock) * sequential read for no update
* sequential read for update
*
* read integrity option on read api?
* option for read generic? would require for semantic ordering of BSON values
*
* key value <= 251 bytes, return the key?
* key value > 251 bytes need to parse the document
*
* treat primary key w/o alternate index as if key was never defined. */
#define VSAMDB_READ_DESC (1 << 0)
enum db_search_order {
EQUAL = 0,
GREATER_THAN,
LESS_THAN,
GENERIC_ASC,
GENERIC_DSC
};
int db_position(db_connection_t con, db_result_set_t *results, const char *key, const char *key_value, enum db_search_order search_order, unsigned int flags);
/**
* Navigates the cursor to the next readable result in the result set and  * returns the document corresponding to the result.
*
* @param con The database conneciton
* @param buf the buffer to fill
* @param buf_len A pointer to a length field. Provides the size of the buffer * on input, and is updated with the length written on output
*/
int db_next_result(db_result_set_t results, const char *buf, size_t *buf_len, doc_id_t *id, unsigned int flags);
int db_next_key(db_result_set_t results, const char *key, size_t *key_len,  doc_id_t *id,
unsigned int flags);
/**
* Updates the current result with the specified document.
*
* TODO: How does this interact with CRE (ans: update integrity ignores CRE) */
int db_update_result(db_result_set_t result, const char *buf, size_t *buf_len, unsigned int flags);
/**
* Deletes the current result from the database
*/
int db_delete_result(db_result_set_t result);
/**
* Deletes a specific result from the database
*/
int db_delete(db_connection_t, const char *key, const char *value);
/**
* Closes the result set and returns all utilized resources.
*/
int db_close_result(db_result_set_t *result);
/****************************************************************************** * Write functions
******************************************************************************/ /**
* Writes the document to the database. If autocommit is enabled, the
* transaction is also committed. Otherwise, db_commit needs to be explicitly * invoked to end the transaction.
*
* Implementation notes:
* Should length be checked against the size of the document??
*/
int db_write(db_connection_t con, const char *buf, size_t buff_len,
unsigned int flags);
/**
* Commits the current transaction. Does nothing if autocommit is enabled */
int db_commit(db_connection_t con);
/**
* Aborts the current transaction. Does nothing if autocommit is enabled
*/
int db_abort(db_connection_t con);
// TODO: on deck - db_info/query/whatever to get information about the db. // Possible metadata:
// # of records
// size of largest inserted record - no dice.
//
#ifdef __CPP__
} /* extern "C" */
#endif
#endif /* VSAMDB_H_ */
