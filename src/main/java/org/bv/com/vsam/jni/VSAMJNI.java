package org.bv.com.vsam.jni;
///////////////////////////////////////////////////////////////////////////////
/*
 *	 XXX XXX  XXXXX    XX    XX   XX   XXXX  XX  XXX  XXXXX
 *	  X   X  X     X    X     X   X      X    X   X     X
 *	  X   X  X          X     XX XX      X    XX  X     X
 *	  X   X  X         X X    XX XX      X    XX  X     X
 *	   X X    XXXXX    X X    X X X      X    X X X     X
 *	   X X         X  X   X   X X X      X    X  XX     X
 *	   X X         X  XXXXX   X   X  X   X    X  XX     X
 *	    X    X     X  X   X   X   X  X   X    X   X     X
 *	    X     XXXXX  XXX XXX XXX XXX  XXX    XXX  X   XXXXX
 *
 *-----------------------------------------------------------------------------
 *  Jack Menendez          | Comment formatted for Doxygen
 *  Version  0.0.1         | Since JRE 15.0.1
 *  01/26/2021             | JNI org_bv_com_VSAM_jni_VSAMJNI.h
 *-----------------------------------------------------------------------------
*/
///////////////////////////////////////////////////////////////////////////////
/**
 * VSAM JNI API
 *
 * These are not boundary functions other than to cross over to native. This
 * API assumes boundary checking is done in front of it.
 *
 * Asynchronous Calls
 * ------------------
 * There is a synchronous and asynchronous version of each function like
 * myFunction() and myFunctionAsync(). The asynchronous version of each
 * function has additional parameters for call backs.
 *
 * Error Handling
 * --------------
 * Will provide JNI exception handling in the C++ side. There will be changes
 * to the code in this file to implement exceptions.
 *
 * \TODO: error handling C++ and java changes here.
 *
 * Discussion
 * ==========
 *
 * Management Functions
 * --------------------
 *
 * The design of this application supports users deploying applications in a
 * cloud architecture. This means that application management is done by the
 * implementer of the application who is just a kind of special user.
 *
 * Security *
 *
 * JAAS can prevent unauthorized use of this module by another application.
 * OpenID will be used to authorize users. That does not help providing
 * security for management functions.
 *
 * The challenge is to prevent access to management functions by users without
 * some kind of authorization. Normally, EIM provides this kind of security
 * for a database connection, which would be coded into the database driver.
 * Well this code IS effectively the database driver!
 *
 * Not sure what to do about that part yet.  One thought is to separate out
 * management into a database facade in front of the VSAMJNI. This way two
 * different Jars can be managed securely with JAAS and two different web
 * services provide a management interface and a production interface.
 * One conclusion though is that the particular class will become a singleton
 * with no internal state.
 *
 * \TODO break up this JNI into two a management and non-management API
 * where the management API is a superset of non-management API calls.
 *
 * Reporting *
 *
 * Cloud applications need to provide reporting to their owners. There is
 * nothing here so far the lends itself to this. Need to think about the
 * different ways a provider might want to monetize this. This info might
 * require yet a third web service interface.
 *
 * \TODO Discussion with IBM
 *
 * Character Set *
 *
 * Everything is in UTF-8 unless otherwise noted. Will provide a class for
 * producing EBCDIC from UTF-8 and vice versa.
 *
 * \TODO write EBCDIC class for translation of buffers.
 *
 * Buffer Types *
 *
 * Can we have char[] instead of byte[]?
 *
 * \TODO experiment with different jni buffer types in C++
 *
 * Connection Count *
 *
 * Will assume maximum number of connections is between 1000 and 2000
 *
 * \TODO implement connection pooling on java side.
 *
 * Notes on JNI *
 *
 * - Overloading native methods is not allowed.
 * - Ignores final keyword on parameters
 *
 * @author jackmenendez
 * @version 0.0.2
 * @see Connection
 * @see CreateDatabaseOptions
 * @see DocumentId
 * @see OpenOptions
 * @see ResultSet
 * @see Transaction
 */
public final class VSAMJNI {

	private static VSAMJNI vsamJNI = null;
	private VSAMJNI() {
		initializeVSAMJNI();
	}

	/**
	 * Factory that produces an instance of the VSAMJNI class
	 *
	 * @return a singleton
	 */
	public static VSAMJNI get() {
		if (vsamJNI == null) {
			vsamJNI = new VSAMJNI();
		}
		return vsamJNI;
	}
	/**
	 * Initializes backend framework, enabling it to accept requests
	 */
	public native int initializeVSAMJNI();
	/**
	 * Shutdown the backend
	 */
	public native int shutdownVSAMJNI();
	///////////////////////////////////////////////////////////////////////////
	//                    Useful Generic Constants
	public static final int TRUE = 1;
	public static final int FALSE = 0;

	///////////////////////////////////////////////////////////////////////////
	//                   Return code definitions
	/**
	 * The call to JNI method completed successfully
	 */
	public static final int RC_OK = 0;
	/**
	 * The backend must be initialized before any other calls are made.
	 */
	public static final int RC_BACKEND_NOT_INITIALIZED = 2;
	/**
	 * The connection is already open but a call to open is taking place
	 */
	public static final int RC_CONNECTION_ALREADY_OPEN = 4;
	/**
	 * The connection must be previously open and not closed
	 */
	public static final int RC_CONNECTION_NOT_OPENED = 3;
	/**
	 * The settings in the Connection object are not consistent.
	 */
	public static final int RC_INVALID_CONNETION = 5;
	/**
	 * An asynchronous call completed without waiting
	 *
	 * An asynch call back to place, but there is no reason to wait.
	 */
	public static final int RC_COMPLETE = 1;
	/**
	 * A timeout occurred during a call.
	 *
	 * The system returned because it was interrupted by a timeout. Completion
	 * status is provided through an error callback or exception.
	 */
	public static final int RC_TIMEOUT = -1;
	///////////////////////////////METHOD//////////////////////////////////////
	/**
	 * Obtain a connection
	 *
	 * A connection represents an Open VSAM ACB on the back end
	 * A transaction issued on this connection effects all
	 * requests on the connection.  For example a commit causes
	 * all requests to commit.
	 *
	 * Restrictions *
	 *
	 * - Connection.state must be CLOSED
	 * - Connection.id must be NO_CONNECTION
	 * - Backend must be initialized with call to initializeVSAMJNI()
	 *
	 * Successful Completion *
	 *
	 * - Connection.state = OPEN
	 * - Connection.id > 0
	 * - threadId > 0
	 * - context > 0
	 *
	 * VSAM ACB and TCB *
	 *
	 * We will assume that all thread management is take care of on the the
	 * other side of this API.
	 *
	 * \TODO Document Exception handling
	 *
	 * @param connection returned object that contains context
	 * @param timeout in milliseconds
	 * @param options parameters for open
	 * @return The following return codes are defined.
	 *
	 * |            Code            |           Description
	 * | -------------------------: | :-----------------------------:
	 * | RC_OK                      | The function completed normally
	 * | RC_TIMEOUT                 | A timeout occurred
	 * | RC_BACKEND_NOT_INITIALIZED | The call cannot be handled because the backend was not initialized
	 * | RC_CONNECTION_ALREADY_OPEN | The connection is already in the open state.
	 *
	 * @see Connection
	 * @see Connection.CLOSED
	 * @see Connection.OPEN
	 * @see OpenOptions
	 */
	public native int open(
			Connection connection,
			long timeout,
			OpenOptions options);
	///////////////////////////////METHOD//////////////////////////////////////
	/**
	 * Obtain a connection asynchronously
	 *
	 * A connection represents an Open VSAM ACB on the back end
	 * A transaction issued on this connection effects all
	 * requests on the connection.  For example a commit causes
	 * all requests to commit.
	 *
	 * Restrictions *
	 *
	 * - Connection.state must be CLOSED
	 * - Connection.id must be NO_CONNECTION
	 *
	 * Successful Completion *
	 *
	 * - Connection.state = OPEN
	 * - Connection.id > 0
	 * - threadId > 0
	 * - context > 0
	 *
	 * VSAM ACB and TCB *
	 *
	 * We will assume that all thread management is take care of on the the
	 * other side of this API.
	 *
	 * \TODO Document Exception handling
	 *
	 * @param connection returned object that contains context
	 * @param timeout in milliseconds
	 * @param options parameters for open
	 * @param context The object with asynch methods to call back
	 * on.
	 * @param methodComplete The name of the completion method
	 * @param methodError The name of the error handling method.
	 * @return The following return codes are defined.
	 * |    Code      |           Description
	 * | -----------: | :----------------------------------:
	 * | RC_OK        | The asynchronous call was accepted
	 * | RC_COMPLETE  | The asynchronous call completed without waiting.
	 * @see Connection
	 * @see Connection.CLOSED
	 * @see Connection.OPEN
	 * @see OpenOptions
	 */
	public native int openAsync(
			Connection connection,
			long timeout,
			OpenOptions options,
			Object context,
			final String methodComplete,
			final String methodError);
	///////////////////////////////METHOD//////////////////////////////////////
	/**
	 * Closes a connection context
	 *
	 * Restrictions *
	 *
	 * - The Connection.pooledFlag field in Connection set to NOT_POOLED
	 * - The Connection.threadId must be greater than zero.
	 * - The Connection.id must not be negative.
	 * - The Connection.context must be greater than zero.
	 * - The Connection.state must be open.
	 *
	 * @param connection the context to close
	 * @return RC_OK, a non-zero value indicates an error
	 * @see Connection
	 */
	public native int close(
			Connection connection);
	///////////////////////////////METHOD//////////////////////////////////////
	/**
	 * Closes a connection context
	 *
	 * Restrictions *
	 *
	 * - The Connection.pooledFlag field in Connection set to NOT_POOLED
	 * - The Connection.threadId must be greater than zero.
	 * - The Connection.id must not be negative.
	 * - The Connection.context must be greater than zero.
	 * - The Connection.state must be open.
	 *
	 * @param connection the context to close
	 * @param context The object with asynch methods to call back
	 * on.
	 * @param methodComplete The name of the completion method
	 * @param methodError The name of the error handling method.
	 * @return The following return codes are defined.
	 * |    Code      |           Description
	 * | -----------: | :----------------------------------:
	 * | RC_OK        | The asynchronous call was accepted
	 * | RC_COMPLETE  | The asynchronous call completed without waiting.
	 * @see Connection
	 */
	public native int closeAsync(
			Connection connection,
			Object context,
			final String methodComplete,
			final String methodError);
	///////////////////////////////METHOD//////////////////////////////////////
	/**
	 * Management call to create a VSAM database
	 *
	 * @param connection previously opened
	 * @param lengthOfDatabaseName less 44 characters
	 * @param databaseName Is an IBM dataset name with following rules:
	 * Data set names must not exceed 44 characters, including all name segments
	 * and periods. See Naming a Cluster and Naming an Alternate Index for
	 * examples of naming a VSAM data set. Restriction: The use of name segments
	 * longer than 8 characters would produce unpredictable results.
	 * <https://www.ibm.com/support/knowledgecenter/en/SSLTBW_2.3.0/com.ibm.zos.v2r3.idad400/name.htm>
	 *
	 * You specify a name for the cluster when defining it.
	 * A cluster name that contains more than 8 characters must be segmented
	 * by periods; 1 to 8 characters can be
	 * specified between periods. A name with a single segment is called an
	 * unqualified name. A name with more than 1 segment is called a qualified
	 * name. Each segment of a qualified name is called a qualifier.
	 * <https://www.ibm.com/support/knowledgecenter/SSLTBW_2.2.0/com.ibm.zos.v2r2.idad400/clname.htm#clname>
	 * @param options Optional parameters for create including primary
	 * index specification.
	 * @return RC_OK, a non-zero value indicates an error
	 * TODO Other errors
	 */
	public native int createDatabase(
			Connection connection,
			int lengthOfDatabaseName,
			byte[] databaseName,
			CreateDatabaseOptions options);
	public native int createDatabaseAsync(
			Connection connection,
			int lengthOfDatabaseName,
			byte[] databaseName,
			CreateDatabaseOptions options,
			Object context,
			final String methodComplete,
			final String methodError);
	/**
	 * Drop a VSAM database
	 * @param connection
	 * @param database
	 * @return RC_OK, a non-zero value indicates an error
	 */
	public native int dropDatabase(
			Connection connection,
			int lengthOfDatabaseName,
			byte[] databaseName);
	public native int dropDatabaseAsync(
			Connection connection,
			int lengthOfDatabaseName,
			byte[] databaseName,
			Object context,
			final String methodComplete,
			final String methodError);
	/**
	 * Create an additional index
	 *
	 * @param connection
	 * @param LengthOfIndexName
	 * @param index
	 * @param options
	 * @return
	 * @see CreateIndexOptions
	 * @see Connection
	 */
	public native int createIndex(Connection connection, int LengthOfIndexName,
			byte[] index, CreateIndexOptions options);
	public native int createIndexAsync(Connection connection, int LengthOfIndexName,
			byte[] index, CreateIndexOptions options,
			Object context,
			final String methodComplete,
			final String methodError);
	/**
	 * Destroy index
	 *
	 * @param connection
	 * @param lengthOfIndexName
	 * @param index
	 * @return
	 *
	 * Questions:
	 * - Can this be used against the primary index? Do I need to protect
	 *   against it?
	 */
	public native int dropIndex(
			Connection connection,
			int lengthOfIndexName,
			byte[] index);
	public native int dropIndexAsync(
			Connection connection,
			int lengthOfIndexName,
			byte[] index,
			Object context,
			final String methodComplete,
			final String methodError);
	/**
	 * Turn auto commit on and off
	 * @param connection
	 * @param autoCommitState
	 * @return
	 */
	public native int autoCommit(Connection connection, boolean autoCommitState);
	public native int autoCommitAsync(Connection connection,
			boolean autoCommitState,
			Object context,
			final String methodComplete,
			final String methodError);
	/**
	 * Read a single document from the database
	 *
	 * @param connection
	 * @param lengthToCopy the maximum length that can be copied into the buffer
	 * starting at startPoisition.
	 * @param startPosition position in the buffer to start copying
	 * @param lengthCopied feedback of length actually copied
	 * @param lengthLeft feedback of length of data not yet copied
	 * @param buffer
	 * @param lengthOfIndexName
	 * @param indexName
	 * @param lengthOfKeyValue
	 * @param keyValue
	 * @param flags
	 * @return RC_OK, a non-zero value indicates an error
	 *
	 * Questions:
	 * - What is the right name to use here, readSingleDocument, readSingle,
	 *   readDocument?
	 */
	public native int querySingle(Connection connection, int lengthToCopy,
			int startPosition, Integer lengthCopied, Integer lengthLeft,
			byte[] buffer, int lengthOfIndexName, byte[] indexName,
			int lengthOfKeyValue, byte[] keyValue,
			int flags);

	public native int querySingleAsync(Connection connection,
			int lengthToCopy,
			int startPosition,
			Integer lengthCopied,
			Integer lengthLeft,
			byte[] buffer,
			int lengthOfIndexName,
			byte[] indexName,
			int lengthOfKeyValue,
			byte[] keyValue,
			int flags,
			Object myClass,
			String methodComplete,
			String methodError);
   /**
    * Get a result set using a known DocumentId.
    *
    * @param connection
    * @param lengthToCopy
    * @param startPosition
    * @param lengthCopied
    * @param lengthLeft
    * @param buffer
    * @param documentId
    * @param flags
    * @return
    */
	public native int queryByDocumentId(Connection connection, int lengthToCopy,
			int startPosition, Integer lengthCopied, Integer lengthLeft,
			byte[] buffer, DocumentId documentId,
			int flags);
	public native int queryByDocumentIdAsync(Connection connection, int lengthToCopy,
			int startPosition, Integer lengthCopied, Integer lengthLeft,
			byte[] buffer, DocumentId documentId,
			int flags,
			Object myClass,
			String methodComplete,
			String methodError);

	/**
	 * Query Search order flag equal
	 */
	public static final int QS_EQ = 0;
	/**
	 * Query Search order flag greater than
	 */
	public static final int QS_GT = 1;
	/**
	 * Query Search order flag less than
	 */
	public static final int QS_LT = 2;
	/**
	 * Query Search order flag ascending
	 */
	public static final int QS_ASC = 3;
	/**
	 * Query Search order flag descending
	 */
	public static final int QS_DSC = 4;

	/**
	 * Query to get a result set
	 *
	 * @param connection
	 * @param resultSet returned result
	 * @param indexLength
	 * @param index
	 * @param keyLength
	 * @param key
	 * @param searchOrder
	 * @param flags
	 * @return
	 */
	public native int query(Connection connection,
			ResultSet resultSet, int indexLength, byte[] index,
			int keyLength, byte[] key, int OrderBy, int flags);

	public native int queryAsync(Connection connection,
			ResultSet resultSet, int indexLength, byte[] index,
			int keyLength, byte[] key, int OrderBy, int flags,
			Object myClass,
			String methodComplete,
			String methodError);
	/**
	 * Navigate to the next readable result.
	 *
	 * @param connection
	 * @param resultSet
	 * @param bufferLength
	 * @param startPosition
	 * @param buffer
	 * @param charWritten
	 * @param charsLeft
	 * @param documentId
	 * @return
	 */
    public native int queryNext(Connection connection,
    		ResultSet resultSet,
    		int bufferLength,
    		int startPosition,
    		char[] buffer,
    		Integer charWritten,
    		Integer charsLeft,
    		DocumentId documentId);
    public native int queryNextAsync(Connection connection,
    		ResultSet resultSet,
    		int bufferLength,
    		int startPosition,
    		char[] buffer,
    		Integer charWritten,
    		Integer charsLeft,
    		DocumentId documentId,
			Object myClass,
			String methodComplete,
			String methodError);
    public native int queryNextKey(Connection connection,
    		ResultSet resultSet,
    		int bufferLength,
    		int startPosition,
    		char[] keyBuffer,
    		DocumentId documentId);
    public native int queryNextKeyAsync(Connection connection,
    		ResultSet resultSet,
    		int bufferLength,
    		int startPosition,
    		char[] keyBuffer,
    		DocumentId documentId,
			Object myClass,
			String methodComplete,
			String methodError);
    public native int update(Connection connection,
    		ResultSet resultSet, int dataLength, String data,
    		int flags);
    public native int updateAsync(Connection connection,
    		ResultSet resultSet, int dataLength, String data,
    		int flags,
			Object myClass,
			String methodComplete,
			String methodError);
    /**
     * Deletes the ResultSet out of the database.
     *
     * @param connection
     * @param resultSet
     * @return
     */
    public native int deleteResult(Connection connection, ResultSet resultSet);
    public native int deleteResultAsync(Connection connection,
    		ResultSet resultSet,
			Object myClass,
			String methodComplete,
			String methodError);
    /**
     * Delete a specific record from the database.
     *
     * @param connection
     * @param indexNameLength
     * @param indexName
     * @param keyLength
     * @param key
     * @return
     */
    public native int delete(Connection connection,
    		int indexNameLength, String indexName,
    		int keyLength, String key);
    public native int deleteAsync(Connection connection,
    		int indexNameLength, String indexName,
    		int keyLength, String key,
			Object myClass,
			String methodComplete,
			String methodError);
    /**
     * Closes ResultSet and returns all utilized resources.
     *
     * @param connection
     * @param resultSet
     * @return
     */
    public native int closeResult(Connection connection, ResultSet resultSet);
    public native int closeResultAsync(Connection connection, ResultSet resultSet,
			Object myClass,
			String methodComplete,
			String methodError);
    public native int write(Connection connection, int bufferLength, String buffer, int flags);
    public native int writeAsync(Connection connection, int bufferLength, String buffer, int flags,
			Object myClass,
			String methodComplete,
			String methodError);
	/**
	 * Commit all uncommitted transactions on this connection
	 *
	 * @param connection
	 * @return
	 */
	public native int commit(Connection connection);
	public native int commitAsync(
			Connection connection,
			Object myClass,
			String methodComplete,
			String methodError);

	/**
	 * Rollback all uncommitted transactions on this connection
	 * @param connection
	 * @return
	 */
	public native int rollback(Connection connection);
	public native int rollbackAsync(Connection connection,
			Object myClass,
			String methodComplete,
			String methodError);

}

