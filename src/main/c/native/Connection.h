#include "../pch.h"
#include "../vsamdb.h"
#include <thread>
namespace native {
	/**
	* A connection to the VSAM database
	*/
	class Connection {
	private:
		int cid;
		bool openFlag;
		db_connection_t backend;
		std::thread::id tid; // unsigned int
		
	public:
		Connection(int id) {
			cid = id;
			openFlag = false;
			backend = nullptr;
		}
	};
}
