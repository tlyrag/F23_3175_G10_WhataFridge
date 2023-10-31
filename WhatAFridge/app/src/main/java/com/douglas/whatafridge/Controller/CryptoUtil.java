package com.douglas.whatafridge.Controller;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class CryptoUtil {
	final public String SECRET_KEY = "I/uXc3/0SGX94r4AcAg6faeHiYol+yRdIemiAsSBIl9Zh0S45tePxIWFr4";

	public String encryptAES256toHex(String src) throws Exception {
		return encryptAES256toHex(src, SECRET_KEY);
	}

	public String encryptAES256toHex(String src, final String secretKey) throws Exception {
		if(src != null) {
			byte[] key256 = secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8);
			byte[] key128 = secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);
				
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key256, "AES"), new IvParameterSpec(key128));
			
		    byte[] encoded = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
		    return byteArrayToHex(encoded);
		} else {
			return null;
		}
	}

	public String decryptAES256fromHex(String src) throws Exception {
		return decryptAES256fromHex(src, SECRET_KEY);
	}
		
	public String decryptAES256fromHex(String src, final String secretKey) throws Exception {
		if(src != null) {
			byte[] key256 = secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8);
			byte[] key128 = secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);
				
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key256, "AES"), new IvParameterSpec(key128));  
			
			byte[] decoded = hexToByteArray(src);
			return new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
		} else {
			return null;
		}
	}
	
	private String byteArrayToHex(byte[] ba) {

		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;

		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}

		return sb.toString();
	}
	
	private byte[] hexToByteArray(String hex) {

		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];

		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return ba;
	}
}
