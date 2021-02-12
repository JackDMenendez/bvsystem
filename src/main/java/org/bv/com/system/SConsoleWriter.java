package org.bv.com.system;

import java.io.IOException;
import java.io.OutputStream;

public class SConsoleWriter extends ConsoleWriter {
	private final OutputStream ostrm_;

	public SConsoleWriter(OutputStream ostrm) {
		ostrm_ = ostrm;
	}

	@Override
	protected void write(String msg) {
		if (msg == null)
			return;

		try {
			ostrm_.write(msg.getBytes());
			ostrm_.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
