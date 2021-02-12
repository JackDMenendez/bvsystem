package org.bv.com.windows.jni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * JNI calls
 *
 * JNI is a MPITA.  Really people!
 *
 * It is unknowns if all of this will this work outside of
 * unit test where classes and resources are not packaged.
 *
 * @author jackd
 *
 */
public class BVNativeJNI {


	private static Path getEnclosingDirectory() {
	    return Paths.get(BVNativeJNI.class.getProtectionDomain().getPermissions()
	            .elements().nextElement().getName()).getParent();
	}

	private static String getPathSeparator() {
		return switch (detectOS()) {
		case windows10, windows -> "\\";
		default -> "/";
		};
	}
	private static InputStream getResourceStream(final String nativeLibraryFilePath) throws FileNotFoundException {
		var reader = BVNativeJNI.class.getResourceAsStream(nativeLibraryFilePath);
		if (reader == null) {
			reader = new FileInputStream(nativeLibraryFilePath);
		}
		return reader;
	}

	/**
	 * Extract the custom .dll from the jar and make it available
	 * for JNI calls.
	 *
	 * @apiNote Java will grab hold of an old library when starts
	 *   such that we can't delete it with a new library.  Therefore
	 *   it might be a good idea to manually clear the TmpDir before
	 *   trying to load anything.
	 * @param path
	 * @param libraryFileName
	 * @param targetFolder
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InterruptedException
	 */
	private static boolean extractAndLoadLibraryFile(
			final Path path,
			final String libraryFileName,
			final String targetFolder) throws IOException, NoSuchAlgorithmException, InterruptedException {

		final String nativeLibraryFilePath;

		if (path == null) {
			nativeLibraryFilePath = libraryFileName;
		} else {
			nativeLibraryFilePath = path  + getPathSeparator() + libraryFileName;
		}


		final var extractedLibFileName = libraryFileName;
		final var extractedLibFile = new File(targetFolder, extractedLibFileName);

			// If it exists, check to see if it is the same one we have
			// and keep it if it is the same.
			if (extractedLibFile.exists()) {
				// test md5sum value
				final var md5sum1 = md5sum(getResourceStream(nativeLibraryFilePath));
				final var md5sum2 = md5sum(new FileInputStream(extractedLibFile));

				if (md5sum1.equals(md5sum2)) {
					return loadNativeLibrary(targetFolder, extractedLibFileName);
				} else {
					// remove old native library file
					final var deletionSucceeded = extractedLibFile.delete();
					if (!deletionSucceeded) {
						throw new IOException(
								"failed to remove existing native library file: " + extractedLibFile.getAbsolutePath());
					}
				}
			}

			// Extract file into the current directory
			final var reader = getResourceStream(nativeLibraryFilePath);
			final var writer = new FileOutputStream(extractedLibFile);
			final var buffer = new byte[1024];
			var bytesRead = 0;
			while ((bytesRead = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, bytesRead);
			}

			writer.close();
			reader.close();

			if (!System.getProperty("os.name").contains("Windows")) {
					Runtime.getRuntime().exec(new String[] { "chmod", "755", extractedLibFile.getAbsolutePath() })
							.waitFor();
			}

			return loadNativeLibrary(targetFolder, extractedLibFileName);
	}

	private static String md5sum(final InputStream resourceAsStream) throws NoSuchAlgorithmException, IOException {
		MessageDigest messageDigest;

		messageDigest = MessageDigest.getInstance("SHA1");
		final var dataBytes = new byte[1024];
		var bytesRead = 0;
		while((bytesRead = resourceAsStream.read(dataBytes)) != -1) {
			messageDigest.update(dataBytes,0,bytesRead);
		}
		final var digestBytes = messageDigest.digest();
		final var sb = new StringBuilder("");
		for (final byte digestByte : digestBytes) {
			sb.append(Integer.toString((digestByte & 0xff)+0x100, 16).substring(1));
		}
		return sb.toString();
	}

    private static String getTempDir() {
    	return System.getProperty("java.io.tmpdir");
    }

	private static synchronized boolean loadNativeLibrary(final String path, final String name) {
		final var libPath = new File(path, name);
		if (libPath.exists()) {
			try {
				System.load(new File(path, name).getAbsolutePath());
				return true;
			} catch (final UnsatisfiedLinkError e) {

				System.err.println(e);
				return false;
			}

		} else {
			return false;
		}
	}

    private enum OS { windows10, windows, linux, mac, unknown }
    private static final String windows_10 = "Windows 10";
    private static OS detectOS() {
    	final var osName = System.getProperty("os.name");
    	System.out.println("Detected OS: " + osName);
    	if (osName.toLowerCase().contains("windows")) {
			return OS.windows;
		}
    	return OS.unknown;
    }

    /**
     * Create a set of file names of native libraries to load
     *
     * Uses the detectOS function to determine what list to load.
     *
     * @return array of executable libraries
     */
    private static String[] getFileNames() {
		return switch (detectOS()) {
		case windows10, windows -> new String[] {"windows.dll"};
		default -> new String[] {};
		};
	}

    /**
     * Load native libraries for the detected OS
     */
    private static void loadNativeLibraries() {
		System.out.println("Loading native libraries");
		final var filenames = getFileNames();
		final var enclosingDirectory = getEnclosingDirectory();
		final var tmpDir = getTempDir();
		for (final String file : filenames) {
			System.out.println("Extract " + file + " from " + enclosingDirectory + " into " + tmpDir );
			try {
				extractAndLoadLibraryFile(enclosingDirectory, file, tmpDir);
			} catch (final NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    // static initialization loads the native libraries for the
    // local operating system.
	static {
		loadNativeLibraries();
	}

	/**
	 * Protected for use by the get() function only
	 */
	protected BVNativeJNI() {}

	/**
	 * Field to hold the singleton
	 */
	private static BVNativeJNI get_ = null;

	/**
	 * Factory, exclusive wait to get instance of BVNativeJNI
	 *
	 * @return
	 */
    public static BVNativeJNI get() {
    	if (get_ == null) {
			get_ = new BVNativeJNI();
		}
    	return get_;
    }


	/**
	 * Get a 128 bit timer count
	 *
	 * @return an array of two 64 bit unsigned integers. Where index 0
	 * is the high order word and index 1 is the low order word.
	 */
	public native long[] ticks();
}
