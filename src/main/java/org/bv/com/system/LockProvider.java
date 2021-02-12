package org.bv.com.system;

public interface LockProvider {
   void get();
   void release();
   Thread getOwner();
}
