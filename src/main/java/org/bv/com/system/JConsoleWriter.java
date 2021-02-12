package org.bv.com.system;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;

import org.bv.com.system.vt100.Frmt;

public class JConsoleWriter extends ConsoleWriter {

	private final Console console_;
	private final OutputStream out;

	public JConsoleWriter(Console console, OutputStream out) {

		this.out = out;
		console_ = console;
		this.width = 132;
		this.height = 50;
		JConsoleReader.EquipmentInfoReceived.Add((s, e) -> {
			write(e.info);
		});
		// Frmt.DECCOLM(Frmt.WindowTitle("BVConsole",
		// Frmt.ASCII(Frmt.UseAlternateStreamBuffer(this.out))));
		Frmt.QueryTerminalCharacteristics(this.out);

	}

	protected void consoleWrite(String text) {
		console_.writer().print(text);
		console_.writer().flush();
	}

	public void write(byte[] msg) {
		if (msg == null)
			return;

		try {
			// for (byte b : msg) {
			// out.write(b);
			// }
			out.write(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(String msg) {

		if (msg == null)
			return;

		console_.writer().write(msg);
		console_.writer().flush();
	}

	@Override
	public void Clear() {

	}
}
