package org.bv.com.system.vt100;

import java.io.IOException;
import java.io.OutputStream;

///////////////////////////////////////////////////////////////////////////////
/**
 * Command sequence for VT100 virtual terminal
 * 
 * In all of the following descriptions, ESC is always the hexadecimal value
 * 0x1B. No spaces are to be included in terminal sequences.
 * 
 * @author jackd
 *
 */
public class Frmt {
	/**
	 * An ASCII character used as a terminator in many command sequences
	 */
	public static byte B = 0x42;
	public static byte BACK_SPACE = 0x08;
	public static byte BEL = 0x07;
	public static byte c = 'c';
	public static byte CLOSE_BRACKET = 0x5d;
	public static byte DEL = 0x7f;
	public static byte eight = '8';
	public static byte EOT = 0x03; // End of text
	public static byte ESC = 0x1b;
	public static byte five = 0x35;
	public static byte four = '4';
	public static byte h = 0x68;
	public static byte I = 'I';
	public static byte J = 0x4a;
	public static byte LEFT_PAREN = 0x28;
	public static byte nine = '9';
	public static byte NUL = 0x00;
	public static byte one = '1';
	public static byte OPEN_BRACKET = 0x5B;
	public static byte P = 0x50;
	public static byte q = 0x71;
	public static byte QUESTION_MARK = 0x3f;
	public static byte RIGHT_PAREN = 0x29;
	public static byte S = 0x53;
	public static byte SEMICOLON = 0x3b;
	public static byte seven = '7';
	public static byte six = '6';
	public static byte SOH = 0x01; // Start of Heading
	public static byte SOT = 0x02; // Start of text.
	public static byte three = '3';
	public static byte two = '2';
	public static byte zero = '0';

	/**
	 * Control Sequence Introduce
	 * 
	 * Control Sequence Introducer (CSI) type sequences ie, a control sequence that
	 * uses 8-bit characters.. All CSI sequences start with ESC (0x1B) followed by [
	 * (left bracket, 0x5B)
	 */
	public static byte[] CSI = { ESC, OPEN_BRACKET };
	/**
	 * Device Control String
	 * 
	 * Introduces a device control string that uses 8-bit characters. A DCS control
	 * string is used for loading function keys or a soft character set.
	 */
	public static byte[] DCS = { ESC, P };
	/**
	 * Operating System Command OSC
	 * 
	 * Introduces an operating system command. The VT510 ignores all following
	 * characters until it receives a SUB, ST, or any other C1 control character.
	 */
	public static byte[] OSC = { ESC, CLOSE_BRACKET };

	public static byte HLineChar = q;
	public static byte[] SU1 = { CSI[0], CSI[1], SOH, S };

	public static OutputStream ASCII(OutputStream out) {
		try {
			out.write(ESC);
			out.write(LEFT_PAREN);
			out.write(B);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	public static byte[] ASCII = { ESC, LEFT_PAREN, B };
	/**
	 * Restore Cursor Position from Memory**
	 */
	public static byte[] DECSR = { ESC, BACK_SPACE };

	/**
	 * ESC 7 Save Cursor Position in Memory**
	 */
	public static byte[] DECSC = { ESC, BEL };
	// public static String SD1 = String.format("%s%s", CSI, "1T");
	/// <summary>
	/// Report Cursor Position
	/// </summary>
	/// <remarks>
	/// Emit the cursor position as: ESC [ r ; c R
	/// Where r = cursor row and c = cursor column
	/// </remarks>
	// public static String DECXCPR = String.format("%s%s", CSI, "6n");
	/// <summary>
	/// Default Returns all attributes to the default state prior to
	/// modification
	/// </summary>
	// public static int GRADefault = 0;
	/// <summary>
	/// Bold/Bright Applies brightness/intensity flag to foreground color
	/// </summary>
	// public static int GRABoldBright = 1;
	/// <summary>
	/// Adds underline
	/// </summary>
	// public static int GRAUnderline = 4;
	/// <summary>
	/// Removes underline
	/// </summary>
	// public static int GRANoUnderline = 24;
	/// <summary>
	/// Swaps foreground and background colors
	/// </summary>
	// public static int GRASwapForgroundBackground = 7;
	/// <summary>
	/// Return foreground background to normal
	/// </summary>
	// public static int GRAUnSwapForegroundBackground = 27;
	/// <summary>
	/// Applies non-bold/bright black to foreground
	/// </summary>
	// public static int GRAForegroundBlack = 30;
	/// <summary>
	/// Applies non-bold/bright red to foreground
	/// </summary>
	// public static int GRAForegroundRed = 31;
	/// <summary>
	/// Applies non-bold/bright green to foreground
	/// </summary>
	// public static int GRAForegroundGreen = 32;
	/// <summary>
	/// Applies non-bold/bright yellow to foreground
	/// </summary>
	// public static int GRAForegroundYellow = 33;
	/// <summary>
	/// Applies non-bold/bright blue to foreground
	/// </summary>
	// public static int GRAForegroundBlue = 34;
	/// <summary>
	/// Applies non-bold/bright magenta to foregound
	/// </summary>
	// public static int GRAForegroundMagenta = 35;
	// 36 Foreground Cyan Applies non-bold/bright cyan to foreground
	// 37 Foreground White Applies non-bold/bright white to foreground
	// 38 Foreground Extended Applies extended color value to the foreground(see
	// details below)
	// 39 Foreground Default Applies only the foreground portion of the defaults(see
	// 0)
	// 40 Background Black Applies non-bold/bright black to background
	// 41 Background Red Applies non-bold/bright red to background
	// 42 Background Green Applies non-bold/bright green to background
	// 43 Background Yellow Applies non-bold/bright yellow to background
	// 44 Background Blue Applies non-bold/bright blue to background
	// 45 Background Magenta Applies non-bold/bright magenta to background
	// 46 Background Cyan Applies non-bold/bright cyan to background
	// 47 Background White Applies non-bold/bright white to background
	// 48 Background Extended Applies extended color value to the background(see
	// details below)
	// 49 Background Default Applies only the background portion of the defaults(see
	// 0)
	// 90 Bright Foreground Black Applies bold/bright black to foreground
	/// <summary>
	/// Applies bold/brigt red to foreground
	/// </summary>
	// public static int GRABrightForegroundRed = 91;

//92	Bright Foreground Green Applies bold/bright green to foreground
//93	Bright Foreground Yellow Applies bold/bright yellow to foreground
//94	Bright Foreground Blue Applies bold/bright blue to foreground
//95	Bright Foreground Magenta Applies bold/bright magenta to foreground
//96	Bright Foreground Cyan Applies bold/bright cyan to foreground
//97	Bright Foreground White Applies bold/bright white to foreground
//100	Bright Background Black Applies bold/bright black to background
//101	Bright Background Red Applies bold/bright red to background
//102	Bright Background Green Applies bold/bright green to background
//103	Bright Background Yellow Applies bold/bright yellow to background
//104	Bright Background Blue Applies bold/bright blue to background
//105	Bright Background Magenta Applies bold/bright magenta to background
//106	Bright Background Cyan Applies bold/bright cyan to background
//107	Bright Background White Applies bold/bright white to background
	/// <summary>
	/// Set Graphics Rendition
	/// </summary>
	/// <param name="grphcsAttribute"> Set the format of the screen and text as
	/// specified
	/// </param>
	/// <returns></returns>
	// public static String SGR(int grphcsAttribute) {
	// return String.format("%s%d%s", CSI, grphcsAttribute, "m");
	// }

	/// <summary>
	/// Erase one character
	/// </summary>
	/// <remarks>
	/// Erase 1 characters from the current cursor position by
	/// overwriting them with a space character.
	/// </remarks>
	// public static String ECH1 = String.format("%s%s", CSI, "1X");
	/// <summary>
	/// Delete 1 character at cursor
	/// </summary>
	/// <remarks>
	/// Delete 1 characters at the current cursor position, shifting
	/// in space characters from the right edge of the screen.
	/// </remarks>
	// public static String DCH1 = String.format("%s%s", CSI, "1P");
	/// <summary>
	/// Backup the cursor 1 space.
	/// </summary>
	/// <remarks>
	/// Cursor backward (Left) by 1.
	/// </remarks>
	// public static String CUB1 = String.format("%s%s", CSI, "1D");
	/// <summary>
	/// Forward cursor right by 1 character
	/// </summary>
	// public static String CUF1 = String.format("%s%s", CSI, "1C");

	// public static String CUP(int col, int line) {
	// return String.format("%s%d;%dH", CSI, line, col);
	// }

	// public static String IL(String text) {
	// return String.format("%s L%s", CSI, text);
	// }

	/// <Summary>Insert 1 space</Summary>
	/// <remarks>
	/// insert at the current cursor position, shifting
	/// all existing text to the right. Text exiting the screen
	/// to the right is removed.
	/// </remarks>i
	// public static String ICH1 = String.format("%d%s", CSI, "1@");

	/// <summary>
	/// Set Scrolling Region
	/// </summary>
	/// <remarks>
	/// Sets the VT scrolling margins of the viewport.
	/// </remarks>
	// public static String DECSTBM(int begin, int end) {
	// return String.format("%s%d;%dr", CSI, begin, end);
	// }

	public static byte[] WindowTitle(String title) {
		byte[] titleBytes = title.getBytes();
		int len = titleBytes.length + 5;
		byte[] terminalCommand = new byte[len];
		int tcidx = 0;
		terminalCommand[tcidx++] = OSC[0];
		terminalCommand[tcidx++] = OSC[1];
		terminalCommand[tcidx++] = two;
		terminalCommand[tcidx++] = SEMICOLON;
		for (int bidx = 0; bidx < titleBytes.length; bidx++) {
			terminalCommand[tcidx++] = titleBytes[bidx];
		}
		terminalCommand[tcidx] = BEL;
		return terminalCommand;
	}

	public static OutputStream UseAlternateStreamBuffer(OutputStream out) {
		try {
			out.write(CSI);
			out.write(QUESTION_MARK);
			out.write(one);
			out.write(zero);
			out.write(four);
			out.write(nine);
			out.write(h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	public static OutputStream UseMainStreamBuffer(OutputStream out) {
		try {
			out.write(CSI);
			out.write(QUESTION_MARK);
			out.write(one);
			out.write(zero);
			out.write(four);
			out.write(nine);
			out.write(I);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	// ESC [ 0 c
	public static OutputStream QueryTerminalCharacteristics(OutputStream out) {
		try {
			out.write(CSI);
			out.write(zero);
			out.write(c);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	public static OutputStream WindowTitle(String title, OutputStream out) {
		byte[] titleBytes = title.getBytes();
		try {
			out.write(OSC);
			out.write(two);
			out.write(SEMICOLON);
			out.write(titleBytes);
			out.write(BEL);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	public static OutputStream ClearScreen(OutputStream out) {
		try {
			out.write(CSI);
			out.write(two);
			out.write(J);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	/**
	 * Set the column width to 132
	 */
	public static byte[] DECCOLM = { CSI[0], CSI[1], QUESTION_MARK, '3', h };

	public static OutputStream DECCOLM(OutputStream out) {
		try {
			out.write(CSI);
			out.write(QUESTION_MARK);
			out.write(three);
			out.write(h);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	// public static String WindowIcon(String path) {
	// return String.format("%c%c%d;%s%c", ESC, OSCDelim, 0, path, BEL);
	// }

	// public static String SetToDECCharactersForDrawing = String.format("%c(0",
	// ESC);

	// public static String SetToAsciiCharactersForVT100 = String.format("%c(B",
	// ESC);

	// public static String EL = String.format("%s%dK", CSI, 0);

}