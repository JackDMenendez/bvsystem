package org.bv.com.vsam.jni;

/**
 * Used with open
 *
 * @author jackd
 *
 */
public class OpenOptions {

	/**
	 * Dirty Reads
	 *
	 * The database will not obtain a record lock when it
	 * is read. It is possible to see uncommitted changes.
	 * It is possible that the database might obtain a shared
	 * record lock anyway when it detects inconsistencies.
	 *
	 * VSAM
	 * This is equivalent to NRI.
	 */
	public static final int DIRTY_READS = 0;
	/**
	 * Consistent Reads
	 *
	 * Obtain a shared lock when reading. The reader will
	 * never see uncommitted data. A read will wait for a
	 * commit or release of an exclusive lock before proceeding.
	 *
	 * VSAM
	 * This is equivalent to CR.
	 */
	public static final int CONSISTENT_READS = 1;
	/**
	 * Repeatable Read
	 *
	 * No updates can take place to data during the transaction
	 *
	 * VSAM
	 * This is equivalent to CRE.
	 */
	public static final int EXCLUSIVE_READS = 2;

	public final int integrity;
	public final int autoCommit;
	private OpenOptions() {
		integrity = CONSISTENT_READS;
		autoCommit = VSAMJNI.FALSE;
	}
	private static final OpenOptions defaultSingleton = new OpenOptions();
	public OpenOptions getDefault() { return defaultSingleton; }
	public OpenOptions(final int integrity, final int autoCommit) {
		assert integrity == EXCLUSIVE_READS || integrity == CONSISTENT_READS || integrity == DIRTY_READS;
		assert autoCommit == VSAMJNI.TRUE || autoCommit == VSAMJNI.FALSE;
		this.integrity = integrity;
		this.autoCommit = autoCommit;
	}
}