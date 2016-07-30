package utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.mindrot.jbcrypt.BCrypt;

public class RandomGenerator {
	public static String nextSessionId(int length, SecureRandom random) {
		return new BigInteger(length, random).toString(32);
	}

	public static String getSalt(int length, SecureRandom random) {
		return new BigInteger(length, random).toString(32);
	}

	public static String getOTP() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		String otp = new Integer(secureRandom.nextInt()).toString().replace("-", "");
		return otp;
	}

	public static String getBCryptPassword(String password) {
		String cryptPassword = BCrypt.hashpw(password, BCrypt.gensalt(10, new SecureRandom()));
		return cryptPassword;
	}

	public String getReferenceNumber(int length, SecureRandom random) {
		return new BigInteger(length, random).toString(32);
	}

	public static boolean checkPasswordMatch(String dbPassword, String hashPassword) throws NoSuchAlgorithmException {
		boolean isPasswordMatch = BCrypt.checkpw(hashPassword, dbPassword);
		return isPasswordMatch;
	}
}
