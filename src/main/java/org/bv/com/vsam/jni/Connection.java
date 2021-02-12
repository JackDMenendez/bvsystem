package org.bv.com.vsam.jni;

///////////////////////////////////////////////////////////////////////////
/**
 * Used with each call to VSAMJNI methods
 *
 * VSAM must have a commit on the same mainframe
 * task (TCB) that opened the connection (ACB).
 * So we track the thread here such that it opens,
 * commits, and closes on the same C++ thread, which
 * is associated a mainframe task (TCB).
 *
 * @author jackmenendez
 */
public class Connection {
	/**
	 * This is a pooled connection
	 */
	public static final int POOLED = 1;
	/**
	 * The connection is not pooled.
	 */
	public static final int NOT_POOLED = 0;
	/**
	 * The connection is closed
	 */
	public static final int CLOSED = -1;
	/**
	 * The connection is open
	 */
	public static final int OPEN = 1;
	/**
	 * indicates whether the connection is open
	 */
	public int state = CLOSED;
	/**
	 * Initial state before open.
	 */
	public final static int NO_CONNECTION = -1;
	/**
	 * Feedback identifies connection
	 */
	public int id = NO_CONNECTION;
	/**
	 * Feedback C++ thread that opened the connection.
	 */
	public int threadId = -1;
	/**
	 * Feedback returned from open this is pointer for
	 * VSAM to find its way back.
	 */
	public long context = 0;
	/**
	 * Flag that indicates whether a connection is pooled
	 *
	 * When the flag is set to POOLED then the connection is
	 * not closed but put back into the connection pool for
	 * reuse.
	 */
	public int pooledFlag = NOT_POOLED;
}