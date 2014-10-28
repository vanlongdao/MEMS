package arrow.mems.util.string;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

public class ArrowStrUtils {
  private static final double BIG = 1e30; // Double.MAX_VALUE/MIN_VALUE would cause SQL 302 error
  private static final double SMALL = 1e-10; // small positive value. to cater for the case where
  // user do not
  public static final String EMPTY_STRING = org.apache.commons.lang3.StringUtils.EMPTY;
  public static final int SALT_KEY_LENGTH = 24;

  public static final String SEPARATOR = "|";

  public static String joinExtentions(String[] extendtions) {
    return Joiner.on(ArrowStrUtils.SEPARATOR).join(extendtions);
  }

  public static String buildExpresion(String[] extentions) {
    return "/(\\.|\\/)(" + ArrowStrUtils.joinExtentions(extentions) + ")$/";
  }

  public static boolean areEquals(String val1, String val2) {
    return ((val1 == null) && (val2 == null)) || val1.equals(val2);
  }

  public static String getLastToken(final String originStr, final String delimiter) {
    final String[] tokens = originStr.split("\\.");
    return tokens[tokens.length - 1];
  }

  public static String nullTrim(final String s) {
    return s == null ? "" : s.trim();
  }

  public static String idTrim(final String s) {
    return (org.apache.commons.lang3.StringUtils.isEmpty(s)) ? null : org.apache.commons.lang3.StringUtils.trim(s).toUpperCase();
  }

  public static String likePattern(final String s) {
    return "%" + ArrowStrUtils.nullTrim(s).toUpperCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
  }

  public static String likePatternExactCase(final String s) {
    return "%" + ArrowStrUtils.nullTrim(s).replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
  }

  /**
   * parse string to a pair of integers for range filter s = 123 -> return {123, 123} s = > 123 ->
   * return {123, Integer.MAX_INTEGER} s = < 123 ->
   * return {Integer.MIN_INTEGER, 123} s = 123 < 456 return {123, 456}
   *
   * @param s
   * @return
   */
  public static Double[] parseNumberRange(String s) {
    // enter a range

    try {
      if (StringUtils.isEmpty(s))
        return null;

      final Double[] range = new Double[2];
      s = s.trim();

      if (s.startsWith(">")) {
        range[0] = Double.parseDouble(s.substring(1).trim());
        range[1] = ArrowStrUtils.BIG;
      }

      else if (s.startsWith("<")) {
        range[0] = -ArrowStrUtils.BIG;
        range[1] = Double.parseDouble(s.substring(1).trim());
      }

      else if (s.contains("<")) {
        final String[] splits = s.split("<");
        range[0] = Double.parseDouble(splits[0].trim());
        range[1] = Double.parseDouble(splits[1].trim());
      }

      else {
        final double d = Double.parseDouble(s);
        range[0] = d - ArrowStrUtils.SMALL;
        range[1] = d + ArrowStrUtils.SMALL;
      }

      return range;
    }

    catch (final NumberFormatException e) {
      return null;
    }
  }



  public static boolean isEmpty(final String str) {
    return (str == null) || "".equals(str.trim());
  }

  public static boolean isNotEmpty(final String str) {
    return !StringUtils.isEmpty(str);
  }

  public static int convertStringToInt(String strInput) {
    return Integer.parseInt(strInput);
  }

  public static String convertIntToString(int input) {
    return String.valueOf(input);
  }

  public static String convertByteToString(byte[] input) {
    return new String(input);
  }

  public static byte[] convertStringToByte(String input) {
    return input.getBytes();
  }

  public static InputStream convertStringToInputStream(String input) {
    return new ByteArrayInputStream(ArrowStrUtils.convertStringToByte(input));
  }

  /**
   * Check that the given CharSequence is neither {@code null} nor of length 0.
   * Note: Will return {@code true} for a CharSequence that purely consists of whitespace.
   * <p>
   *
   * <pre class="code">
   * StringUtils.hasLength(null) = false
   * StringUtils.hasLength("") = false
   * StringUtils.hasLength(" ") = true
   * StringUtils.hasLength("Hello") = true
   * </pre>
   *
   * @param str the CharSequence to check (may be {@code null})
   * @return {@code true} if the CharSequence is not null and has length
   * @see #hasText(String)
   */
  public static boolean hasLength(CharSequence str) {
    return ((str != null) && (str.length() > 0));
  }

  /**
   * Check that the given String is neither {@code null} nor of length 0.
   * Note: Will return {@code true} for a String that purely consists of whitespace.
   *
   * @param str the String to check (may be {@code null})
   * @return {@code true} if the String is not null and has length
   * @see #hasLength(CharSequence)
   */
  public static boolean hasLength(String str) {
    return ArrowStrUtils.hasLength((CharSequence) str);
  }

  /**
   * Check whether the given CharSequence has actual text.
   * More specifically, returns {@code true} if the string not {@code null},
   * its length is greater than 0, and it contains at least one non-whitespace character.
   * <p>
   *
   * <pre class="code">
   * StringUtils.hasText(null) = false
   * StringUtils.hasText("") = false
   * StringUtils.hasText(" ") = false
   * StringUtils.hasText("12345") = true
   * StringUtils.hasText(" 12345 ") = true
   * </pre>
   *
   * @param str the CharSequence to check (may be {@code null})
   * @return {@code true} if the CharSequence is not {@code null},
   *         its length is greater than 0, and it does not contain whitespace only
   * @see Character#isWhitespace
   */
  public static boolean hasText(CharSequence str) {
    if (!ArrowStrUtils.hasLength(str))
      return false;
    final int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i)))
        return true;
    }
    return false;
  }

  /**
   * Check whether the given String has actual text.
   * More specifically, returns {@code true} if the string not {@code null},
   * its length is greater than 0, and it contains at least one non-whitespace character.
   *
   * @param str the String to check (may be {@code null})
   * @return {@code true} if the String is not {@code null}, its length is
   *         greater than 0, and it does not contain whitespace only
   * @see #hasText(CharSequence)
   */
  public static boolean hasText(String str) {
    return ArrowStrUtils.hasText((CharSequence) str);
  }


  public static String randomString(int len) {
    final String AB = "123456789";
    final Random rnd = new Random();
    final StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }
    return sb.toString();
  }

  // TODO: replace all invalid characters by _
  public static String escapeFilename(String pSoftwareRev) {
    return pSoftwareRev;
  }


  public static String[] commaDelimitedListToStringArray(String str) {
    return ArrowStrUtils.delimitedListToStringArray(str, ",");
  }

  /**
   * Take a String which is a delimited list and convert it to a String array.
   * <p>
   * A single delimiter can consists of more than one character: It will still be considered as
   * single delimiter string, rather than as bunch of potential delimiter characters - in contrast
   * to <code>tokenizeToStringArray</code>.
   *
   * @param str the input String
   * @param delimiter the delimiter between elements (this is a single delimiter,
   *        rather than a bunch individual delimiter characters)
   * @return an array of the tokens in the list
   * @see #tokenizeToStringArray
   */
  public static String[] delimitedListToStringArray(String str, String delimiter) {
    return ArrowStrUtils.delimitedListToStringArray(str, delimiter, null);
  }

  /**
   * Take a String which is a delimited list and convert it to a String array.
   * <p>
   * A single delimiter can consists of more than one character: It will still be considered as
   * single delimiter string, rather than as bunch of potential delimiter characters - in contrast
   * to <code>tokenizeToStringArray</code>.
   *
   * @param str the input String
   * @param delimiter the delimiter between elements (this is a single delimiter,
   *        rather than a bunch individual delimiter characters)
   * @param charsToDelete a set of characters to delete. Useful for deleting unwanted
   *        line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a String.
   * @return an array of the tokens in the list
   * @see #tokenizeToStringArray
   */
  public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
    if (str == null)
      return new String[0];
    if (delimiter == null)
      return new String[] {str};
    final List<String> result = new ArrayList<String>();
    if ("".equals(delimiter)) {
      for (int i = 0; i < str.length(); i++) {
        result.add(ArrowStrUtils.deleteAny(str.substring(i, i + 1), charsToDelete));
      }
    } else {
      int pos = 0;
      int delPos;
      while ((delPos = str.indexOf(delimiter, pos)) != -1) {
        result.add(ArrowStrUtils.deleteAny(str.substring(pos, delPos), charsToDelete));
        pos = delPos + delimiter.length();
      }
      if ((str.length() > 0) && (pos <= str.length())) {
        // Add rest of String, but not in case of empty input.
        result.add(ArrowStrUtils.deleteAny(str.substring(pos), charsToDelete));
      }
    }
    return ArrowStrUtils.toStringArray(result);
  }

  /**
   * Copy the given Collection into a String array.
   * The Collection must contain String elements only.
   * 
   * @param collection the Collection to copy
   * @return the String array (<code>null</code> if the passed-in
   *         Collection was <code>null</code>)
   */
  public static String[] toStringArray(Collection<String> collection) {
    if (collection == null)
      return null;
    return collection.toArray(new String[collection.size()]);
  }

  /**
   * Copy the given Enumeration into a String array.
   * The Enumeration must contain String elements only.
   * 
   * @param enumeration the Enumeration to copy
   * @return the String array (<code>null</code> if the passed-in
   *         Enumeration was <code>null</code>)
   */
  public static String[] toStringArray(Enumeration<String> enumeration) {
    if (enumeration == null)
      return null;
    final List<String> list = Collections.list(enumeration);
    return list.toArray(new String[list.size()]);
  }


  /**
   * Replace all occurences of a substring within a string with
   * another string.
   *
   * @param inString String to examine
   * @param oldPattern String to replace
   * @param newPattern String to insert
   * @return a String with the replacements
   */
  public static String replace(String inString, String oldPattern, String newPattern) {
    if (!ArrowStrUtils.hasLength(inString) || !ArrowStrUtils.hasLength(oldPattern) || (newPattern == null))
      return inString;
    final StringBuilder sb = new StringBuilder();
    int pos = 0; // our position in the old string
    int index = inString.indexOf(oldPattern);
    // the index of an occurrence we've found, or -1
    final int patLen = oldPattern.length();
    while (index >= 0) {
      sb.append(inString.substring(pos, index));
      sb.append(newPattern);
      pos = index + patLen;
      index = inString.indexOf(oldPattern, pos);
    }
    sb.append(inString.substring(pos));
    // remember to append any characters to the right of a match
    return sb.toString();
  }

  /**
   * Delete all occurrences of the given substring.
   *
   * @param inString the original String
   * @param pattern the pattern to delete all occurrences of
   * @return the resulting String
   */
  public static String delete(String inString, String pattern) {
    return ArrowStrUtils.replace(inString, pattern, "");
  }

  /**
   * Delete any character in a given String.
   *
   * @param inString the original String
   * @param charsToDelete a set of characters to delete.
   *        E.g. "az\n" will delete 'a's, 'z's and new lines.
   * @return the resulting String
   */
  public static String deleteAny(String inString, String charsToDelete) {
    if (!ArrowStrUtils.hasLength(inString) || !ArrowStrUtils.hasLength(charsToDelete))
      return inString;
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < inString.length(); i++) {
      final char c = inString.charAt(i);
      if (charsToDelete.indexOf(c) == -1) {
        sb.append(c);
      }
    }
    return sb.toString();
  }


}
