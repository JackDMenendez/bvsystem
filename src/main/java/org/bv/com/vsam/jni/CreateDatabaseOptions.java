package org.bv.com.vsam.jni;

/**
 * Not sure what is important here yet
 *
 * @author jackmenendez
 *
 * Questions:
 * - What should defaults be?
 *
 */
public class CreateDatabaseOptions {
	public int maxSpace=0;
	public int avgDocSize=4000;
	public static final int LOG_NONE = 0;
	public static final int LOG_UNDO = 1;
	public static final int LOG_ALL = 2;
	public int logOptions = LOG_NONE;
	public int lengthPrimaryIndex = 0;
	public byte[] primaryKey = new byte[0];
	public int lengthStorageClass=0;
	public byte[] storageClass = new byte[0];
	public int lengthMgmtClass = 0;
	public byte[] mgmtClass = new byte[0];
	public int lengthDataClass = 0;
	public byte[] dataClass = new byte[0];
	public int lengthStreamId = 0;
	public byte[] logStreamId = new byte[0];
}