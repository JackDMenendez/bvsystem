package org.bv.com.system;

public interface CReader extends ServiceRequestI {

	String read();

	void EscapedInput(byte[] data);

}