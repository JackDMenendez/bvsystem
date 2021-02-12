#pragma once
#include "../pch.h"
#include <string>
#include "../vsamdb.h"
using std::string;
///////////////////////////////////////////////////////////////////////////////
namespace simulator {
	/**
	* A cluster is a set of directories that will hold data plus a meta
	* file.
	*/
	class Cluster
	{
	public:
		enum createRC {
			ok,
			clusterExists,
			invalidName,
			unknown
		};
		/**
		* Different cluster types define how they can be queried
		* and addressed.
		*/
		enum clusterType { 
			/**
			* The cluster type is considered binary data
			*/
			bin,
			/**
			* The cluster type is json format data that
			* can be queried with json queries.
			*/
			json, 
			/**
			* The cluster type is bson
			*/
			bson, 
			/**
			* The cluster type is xml and may be queried
			* with XML queries
			*/
			xml, 
			/**
			* The cluster type is html
			*/
			html, 
			/**
			* The cluster type is plane text
			*/
			text };
		/**
		* create a cluster
		* 
		* \param type The type of data stored in the cluster
		* \param name A name that matches IBM standard for cluster names
		* \param pkey A name of the index of the cluster.
		* \return 
		*/
		virtual createRC create(const string type, const string name, 
			const string pkey) {
			
			return ok;
		}
	};
}

