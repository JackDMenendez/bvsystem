package org.bv.com.system;

import java.io.IOException;
import java.io.InputStream;

import org.bv.com.system.vt100.Frmt;

public class SConsoleReader extends ConsoleReader {
	private final InputStream istrm_;

	public SConsoleReader(InputStream istrm) {
		istrm_ = istrm;
	}

	@Override
	public boolean ready() {
		try {
			return istrm_.available() > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public byte readChar() {
		try {
			return (byte) istrm_.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Frmt.NUL;
	}

	@Override
	public int read(char[] buffer, int offsetInBuffer, int len) {
		byte[] byteBuffer = new byte[buffer.length - offsetInBuffer];
		int readCount;
		try {
			readCount = istrm_.read(byteBuffer, 0, len);
			for (int byteIndex = 0; byteIndex < len; byteIndex++) {
				buffer[offsetInBuffer + byteIndex] = (char) byteBuffer[byteIndex];
			}
			return readCount;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}