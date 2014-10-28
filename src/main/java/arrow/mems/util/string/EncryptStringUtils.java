package arrow.mems.util.string;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;

import arrow.framework.logging.ArrowLogger;

/**
 * The Class EncryptString to use encrypt string into MD5 . And decrypt string .
 */
public class EncryptStringUtils {
  private static final String AES_ALGORITHM_SCHEMA = "AES";
  private static final String UNICODE_FORMAT = "UTF8";
  @Inject
  private static ArrowLogger log;

  public static String encrypt(String encryptText, String privateKey) {
    encryptText = EncryptStringUtils.reverseString(encryptText);
    byte[] keyBytes;
    byte[] encrypt = null;
    String resultEncrypted = "";
    try {
      keyBytes = privateKey.getBytes(EncryptStringUtils.UNICODE_FORMAT);
      final Key key = new SecretKeySpec(keyBytes, EncryptStringUtils.AES_ALGORITHM_SCHEMA);
      final Cipher c = Cipher.getInstance(EncryptStringUtils.AES_ALGORITHM_SCHEMA);
      c.init(Cipher.ENCRYPT_MODE, key);
      encrypt = c.doFinal(encryptText.getBytes(EncryptStringUtils.UNICODE_FORMAT));
      resultEncrypted = new String(Base64.encodeBase64(encrypt), EncryptStringUtils.UNICODE_FORMAT);
    } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException
        | BadPaddingException e) {
      EncryptStringUtils.log.debug("EXCEPTION WHEN ENCRYPT STRING", e);
    }
    return resultEncrypted;
  }

  public static String decrypt(final String encryptedString, String privateKey) {
    byte[] keyBytes;
    try {
      keyBytes = privateKey.getBytes(EncryptStringUtils.UNICODE_FORMAT);
      final Key key = new SecretKeySpec(keyBytes, EncryptStringUtils.AES_ALGORITHM_SCHEMA);
      final Cipher c = Cipher.getInstance(EncryptStringUtils.AES_ALGORITHM_SCHEMA);
      c.init(Cipher.DECRYPT_MODE, key);
      final byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes(EncryptStringUtils.UNICODE_FORMAT));
      final byte[] decValue = c.doFinal(encryptedText);
      return EncryptStringUtils.reverseString(new String(decValue, EncryptStringUtils.UNICODE_FORMAT));
    } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException
        | BadPaddingException e) {
      EncryptStringUtils.log.debug("EXCEPTION WHEN DECRYPT STRING", e);
    }

    return null;
  }

  public static String reverseString(final String strReverse) {
    final StringBuilder strOrigin = new StringBuilder(strReverse);
    return strOrigin.reverse().toString();
  }

  public static String[] encryptPassword(String plainPassword) {
    final String salt = EncryptStringUtils.generateSalt();
    final String encryptPassword = EncryptStringUtils.encrypt(plainPassword, salt);
    return new String[] {salt, encryptPassword};
  }

  public static String generateSalt() {
    return EncryptStringUtils.autoGenerateKey(ArrowStrUtils.SALT_KEY_LENGTH);
  }

  /**
   * Auto generate key.
   *
   * @param len the length of output string
   * @return the string
   */
  private static String autoGenerateKey(int len) {
    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final Random rnd = new Random();
    final StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }
    return sb.toString();
  }
}
