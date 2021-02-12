package org.bv.com.system;

import java.io.Console;
import java.io.IOException;

import org.bv.com.system.vt100.Frmt;

public class JConsoleReader extends ConsoleReader {

	private final Console console_;

	public JConsoleReader(final Console console) {
		console_ = console;
		//BVNative.get();
	}

	@Override
	protected boolean ready() {
		try {
			return console_.reader().ready();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private final byte[] inBuff = new byte[2];

	@Override
	protected byte readChar() {
		try {
			final var count = System.in.readNBytes(inBuff, 0, 1);
			if (count > 0) {
				return inBuff[0];
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return Frmt.NUL;
	}

	@Override
	protected int read(final char[] buffer, final int offsetInBuffer, final int len) {
		System.err.print("Reading\n");
		//try {
			// (BVNative.get().peekConsole() > 0) {
			//	return console_.reader().read(buffer, offsetInBuffer, len);
			//}
		//} catch (final IOException e) {
		//	e.printStackTrace();
		//}
		return 0;
	}

	public static Event<EquipmentInfo> EquipmentInfoReceived = new Event<>();

	protected void OnEquipmentInfoReceived(final byte[] info) {
		EquipmentInfoReceived.invoke(this, new EquipmentInfo(info));
	}

	public static final byte[] EQUIPMENT_VT101 = { Frmt.ESC, Frmt.OPEN_BRACKET, Frmt.QUESTION_MARK, Frmt.one,
			Frmt.SEMICOLON, Frmt.zero, Frmt.c };
	public static final byte[] EQUIPMENT_VT101MS = { Frmt.ESC, Frmt.OPEN_BRACKET, Frmt.OPEN_BRACKET, Frmt.QUESTION_MARK,
			Frmt.one, Frmt.SEMICOLON, Frmt.zero, Frmt.c };

	public static boolean Compare(final byte[] left, final byte[] right, final int start, final int length) {

		if (left.length < length + start || right.length < length + start) {
			return false;
		}

		for (var idx = 0; idx < length; idx++) {

			if (left[idx + start] != right[idx + start]) {
				return false;
			}
		}
		return true;
	}

	public static byte[] escapedString(final byte[] data, final int start, final int len) {
		assert data.length >= start + len;
		final var a = new byte[len];
		for (var idx = start; idx < start + len; idx++) {
			a[idx - start] = data[idx];
		}
		return a;
	}

	/**
	 * Parse escaped Input sequences from VT10n
	 *
	 * State Machines Sequence | Description -----------|--------------------
	 * \x1b[?1;0c | Query Equipment
	 */
	@Override
	public void EscapedInput(final byte[] data) {
		assert 0 < data.length;
		assert Frmt.ESC == data[0];
		if (data.length == EQUIPMENT_VT101.length) {
			if (Compare(EQUIPMENT_VT101, data, 0, EQUIPMENT_VT101.length)) {
				final var hardware = "VT101";
				OnEquipmentInfoReceived(hardware.getBytes());
			}
		} else if (data.length == EQUIPMENT_VT101MS.length) { // Microsoft bug?
			if (Compare(EQUIPMENT_VT101MS, data, 0, EQUIPMENT_VT101MS.length)) {
				final var hardware = "VT101";
				OnEquipmentInfoReceived(hardware.getBytes());
			}
		} else {
			try {
				System.out.write(data);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
