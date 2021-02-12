/**
 * 
 */
package org.bv.com.system.test;

import org.bv.com.system.vt100.Frmt;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author jackd
 *
 */
class VT100FrmtTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testESC() {
		byte artifact = Frmt.ESC;
		Assertions.assertEquals(0x1b, artifact);
	}

	@Test
	void testBEL() {
		byte artifact = Frmt.BEL;
		Assertions.assertEquals(7, artifact);
	}

	@Test
	void testCSI() {
		byte[] artifact = Frmt.CSI;
		Assertions.assertEquals(2, artifact.length);
		Assertions.assertEquals(Frmt.ESC, artifact[0]);
		Assertions.assertEquals('[', artifact[1]);
	}

	@Test
	void testOSC() {
		byte[] artifact = Frmt.OSC;
		Assertions.assertEquals(2, artifact.length);
		Assertions.assertEquals(Frmt.ESC, artifact[0]);
		Assertions.assertEquals(']', artifact[1]);
	}

	@Test
	void testASCII() {
		byte[] artifact = Frmt.ASCII;
		Assertions.assertEquals(3, artifact.length);
		Assertions.assertEquals(Frmt.ESC, artifact[0]);
		Assertions.assertEquals('(', artifact[1]);
		Assertions.assertEquals('B', artifact[2]);
	}

	@Test
	void testDECCOLM() {
		byte[] artifact = Frmt.DECCOLM;
		Assertions.assertEquals(5, artifact.length);
		Assertions.assertEquals(Frmt.CSI[0], artifact[0]);
		Assertions.assertEquals(Frmt.CSI[1], artifact[1]);
		Assertions.assertEquals('?', artifact[2]);
		Assertions.assertEquals('3', artifact[3]);
		Assertions.assertEquals('h', artifact[4]);
	}

	/**
	 * Test method for
	 * {@link org.bv.com.system.vt100.Frmt#WindowTitle(java.lang.String)}.
	 */
	@Test
	void testWindowTitle() {
		byte[] testTitle = Frmt.WindowTitle("TEST");
		Assertions.assertEquals(9, testTitle.length);
		Assertions.assertEquals(Frmt.OSC[0], testTitle[0]);
		Assertions.assertEquals(Frmt.OSC[1], testTitle[1]);
		Assertions.assertEquals('2', testTitle[2]);
		Assertions.assertEquals(';', testTitle[3]);
		Assertions.assertEquals('T', testTitle[4]);
		Assertions.assertEquals('E', testTitle[5]);
		Assertions.assertEquals('S', testTitle[6]);
		Assertions.assertEquals('T', testTitle[7]);
		Assertions.assertEquals(Frmt.BEL, testTitle[8]);
	}

}
