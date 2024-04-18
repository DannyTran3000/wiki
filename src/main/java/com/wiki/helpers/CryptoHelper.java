package com.wiki.helpers;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
  public static SecretKey generateKey() throws NoSuchAlgorithmException {
    KeyGenerator key = KeyGenerator.getInstance("AES");
    key.init(128);
    return key.generateKey();
  }

  public static String generateString() throws NoSuchAlgorithmException {
    SecretKey key = generateKey();
    return convertKeyToString(key);
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

  public static String convertKeyToString(SecretKey k) throws NoSuchAlgorithmException {
    byte[] rawData = k.getEncoded();
    String encodedKey = Base64.getEncoder().encodeToString(rawData);
    return encodedKey;
  }

  public static SecretKey convertStringToKey(String str) throws NoSuchAlgorithmException {
    byte[] decodedKey = Base64.getDecoder().decode(str);
    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    return originalKey;
  }
}
