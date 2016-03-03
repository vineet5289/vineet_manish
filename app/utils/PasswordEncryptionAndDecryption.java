package utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptionAndDecryption {
	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		return new BigInteger(350, random).toString(32);
	}

	public String getSalt() {
		return new BigInteger(150, random).toString(32);
	}

	public String getBCryptPassword(String password) {
		String cryptPassword = BCrypt.hashpw(password, BCrypt.gensalt(10, new SecureRandom()));
		return cryptPassword;
	}

	public boolean checkPasswordMatch(String dbPassword, String hashPassword) throws NoSuchAlgorithmException {
		boolean isPasswordMatch = BCrypt.checkpw(hashPassword, dbPassword);
		return isPasswordMatch;
	}
}
