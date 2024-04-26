package com.wiki.helpers;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
  /**
   * Generates a new AES SecretKey of 128 bits.
   *
   * @return The generated SecretKey.
   * @throws NoSuchAlgorithmException If the requested cryptographic algorithm is
   *                                  not available.
   */
  public static SecretKey generateKey() throws NoSuchAlgorithmException {
    KeyGenerator key = KeyGenerator.getInstance("AES");
    key.init(128);
    return key.generateKey();
  }

  /**
   * Generates a string representation of a SecretKey using Base64 encoding.
   *
   * @return The string representation of the SecretKey.
   * @throws NoSuchAlgorithmException If the requested cryptographic algorithm is
   *                                  not available.
   */
  public static String generateString() throws NoSuchAlgorithmException {
    SecretKey key = generateKey();
    return convertKeyToString(key);
  }

  /**
   * Encrypts a string using AES encryption algorithm and a given SecretKey.
   *
   * @param str The string to be encrypted.
   * @param k   The SecretKey used for encryption.
   * @return The Base64-encoded encrypted string.
   * @throws Exception If an error occurs during encryption.
   */
  public static String encrypt(String str, SecretKey k) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, k);
    byte[] encryptedBytes = cipher.doFinal(str.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  /**
   * Decrypts a Base64-encoded string using AES decryption algorithm and a given
   * SecretKey.
   *
   * @param str The Base64-encoded string to be decrypted.
   * @param k   The SecretKey used for decryption.
   * @return The decrypted original string.
   * @throws Exception If an error occurs during decryption.
   */
  public static String decrypt(String str, SecretKey k) throws Exception {
    byte[] encryptedBytes = Base64.getDecoder().decode(str);
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, k);
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedBytes);
  }

  /**
   * Converts a SecretKey to its string representation using Base64 encoding.
   *
   * @param k The SecretKey to be converted.
   * @return The string representation of the SecretKey.
   * @throws NoSuchAlgorithmException If the requested cryptographic algorithm is
   *                                  not available.
   */
  public static String convertKeyToString(SecretKey k) throws NoSuchAlgorithmException {
    byte[] rawData = k.getEncoded();
    String encodedKey = Base64.getEncoder().encodeToString(rawData);
    return encodedKey;
  }

  /**
   * Converts a string representation of a SecretKey (Base64-encoded) back to a
   * SecretKey object.
   *
   * @param str The string representation of the SecretKey.
   * @return The SecretKey object.
   * @throws NoSuchAlgorithmException If the requested cryptographic algorithm is
   *                                  not available.
   */
  public static SecretKey convertStringToKey(String str) throws NoSuchAlgorithmException {
    byte[] decodedKey = Base64.getDecoder().decode(str);
    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    return originalKey;
  }
}
