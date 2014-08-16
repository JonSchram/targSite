package site.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

public class PasswordHasher {
	public static class HashData {
		private int salt;
		private String hash;

		public HashData(int salt, String hash) {
			this.salt = salt;
			this.hash = hash;
		}

		public String getHash() {
			return hash;
		}

		public int getSalt() {
			return salt;
		}
	}

	final static int ITERATIONS = 1000;

	public static HashData doHash(String password) {
		// create a secure random number generator to make salt
		SecureRandom secureGenerator = new SecureRandom();
		int salt = secureGenerator.nextInt();

		// make an empty byte array to access this variable outside of try block
		byte[] hash = new byte[0];

		try {
			// allows access to hashing functions
			MessageDigest m;
			m = MessageDigest.getInstance("SHA-256");

			// append salt to password, as string
			String preHash = password + salt;
			// get byte array of preHash data in order to repeatedly hash it
			hash = preHash.getBytes();

			// apply hash a thousand times
			for (int i = 0; i < ITERATIONS; i++) {
				hash = m.digest(hash);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return new HashData(salt, Base64.encodeBase64String(hash));
	}

}
