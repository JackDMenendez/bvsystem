#include "vsamdb.h"
#include "simulator/CreateCluster.h"
#include <exception>
#include <string>
using std::string;
using std::exception;
using simulator::Cluster;

/**
* Simulate creating a cluster on a unix or windows file system.
*/
int db_create(const char* dsname, const db_create_options* options) {
	int rc = 0;
	try {
		string type = "json";
		switch (options->format) {
		case JSON_DB:
			type = "json";
			break;
		case BSON_DB:
			type = "bson";
			break;
		}
		Cluster cluster;
		string pkey("");
		if (options->primary_key != NULL) {
			pkey = options->primary_key;
		} 
		string fileName(dsname);
		cluster.create(type,dsname,pkey);
	}
	catch (exception e) {

	}
	return rc;
}