package com.wiki.helpers;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.*;

public class CryptoHelper {
  public static SecretKey generate() throws NoSuchAlgorithmException {
    KeyGenerator key = KeyGenerator.getInstance("AES");
    key.init(256);
    return key.generateKey();
  }

  public static String encrypt(String str, SecretKey k) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, k);
    byte[] encryptedBytes = cipher.doFinal(str.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String str, SecretKey k) throws Exception {
    byte[] encryptedBytes = Base64.getDecoder().decode(str);
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, k);
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedBytes);
  }
}
