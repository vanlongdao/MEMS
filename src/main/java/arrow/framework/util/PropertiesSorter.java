/*
 * package: arrow.framework.util
 * file: PropertiesSorter.java
 * time: Jun 27, 2014
 *
 * @author michael
 */
package arrow.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PropertiesSorter {
  public static final String PATH_PREFIX = "src/main/resources";
  public static final String PROPERTY_FILE_SUFFIX = ".properties";
  public static final String UTF8_BOM = "\uFEFF";
  public static final String UTF8_ENCODING = "UTF-8";
  private static List<String> writtenPropertyKeys;

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws PropertyFileException the property file exception
   */
  public static void main(final String... args) throws IOException, PropertyFileException {
    final List<String> listBundlePrefix = new ArrayList<>();
    if (args.length == 0) {

      // set priority for ValidationMessages higher
      listBundlePrefix.add("ValidationMessages");
    } else {
      listBundlePrefix.addAll(Arrays.stream(args).collect(Collectors.toList()));
    }
    // listBundlePrefix.add("error-messages");
    // listBundlePrefix.add("database_labels");

    // Have to put msg-keys at last.
    // listBundlePrefix.add("msg-keys");
    PropertiesSorter.writtenPropertyKeys = new ArrayList<String>();
    for (final String bundle : listBundlePrefix) {
      PropertiesSorter.sortResouces(bundle);
    }

    System.out.println("Finish!");
  }

  private static void sortResouces(final String resourcePrefix) throws IOException, PropertyFileException {
    System.out.println("Start for " + resourcePrefix + " !");
    final Map<String, Properties> mapOfNameAndProperties = new HashMap<String, Properties>();
    final Map<String, PrintWriter> mapOfNameAndWriters = new HashMap<String, PrintWriter>();

    PropertiesSorter.readFilesToProperties(resourcePrefix, mapOfNameAndProperties, mapOfNameAndWriters);
    PropertiesSorter.writePropertiesToFiles(resourcePrefix, mapOfNameAndProperties, mapOfNameAndWriters);
    System.out.println("Done for " + resourcePrefix + " !");
  }

  private static void readFilesToProperties(final String resourcePrefix, final Map<String, Properties> mapOfNameAndProperties,
      final Map<String, PrintWriter> mapOfNameAndWriters) throws IOException, PropertyFileException {

    System.out.println("Read properties from :" + resourcePrefix);
    final File rootResourceFolder = new File(PropertiesSorter.PATH_PREFIX);
    for (final File resourceFile : rootResourceFolder.listFiles()) {
      if (resourceFile.getName().endsWith(PropertiesSorter.PROPERTY_FILE_SUFFIX) && resourceFile.getName().startsWith(resourcePrefix)) {
        PropertiesSorter.validatePropertyFile(resourceFile);

        final Properties prop = new Properties();

        try (BufferedReader bufferReader =
            new BufferedReader(new InputStreamReader(new FileInputStream(resourceFile), PropertiesSorter.UTF8_ENCODING))) {
          // Remove the UTF8_BOM chacters and put back to the properties list
          String firstLine = bufferReader.readLine();
          if (firstLine != null) {
            final boolean isUTF8File = firstLine.contains(PropertiesSorter.UTF8_BOM);
            if (isUTF8File) {
              firstLine = firstLine.replace(PropertiesSorter.UTF8_BOM, "");
            }
            if (firstLine.split("=").length == 1) {
              prop.put(firstLine.split("=")[0], "");
            } else {
              prop.put(firstLine.split("=")[0], firstLine.split("=")[1]);
            }

            prop.load(bufferReader);
            bufferReader.close();

            final FileOutputStream fos = new FileOutputStream(resourceFile);
            // Write some prefix by to indicate that our file is UTF-8 with BOM
            if (isUTF8File) {
              fos.write(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            }
            fos.close();

            final PrintWriter writer =
                new PrintWriter(new OutputStreamWriter(new FileOutputStream(resourceFile), PropertiesSorter.UTF8_ENCODING), true);
            mapOfNameAndProperties.put(resourceFile.getName(), prop);
            mapOfNameAndWriters.put(resourceFile.getName(), writer);
          }
        }
      }
    }

  }

  private static void writePropertiesToFiles(final String resourcePrefix, final Map<String, Properties> mapOfNameAndProperties,
      final Map<String, PrintWriter> mapOfNameAndWriters) {

    System.out.println("Start write properties:" + resourcePrefix);
    Properties standardProperties = null;
    PrintWriter standardWriter = null;
    for (final Entry<String, Properties> item : mapOfNameAndProperties.entrySet()) {
      if (PropertiesSorter.isStandardPropertyFile(item.getKey(), resourcePrefix)) {
        standardProperties = item.getValue();
        standardWriter = mapOfNameAndWriters.get(item.getKey());
      }
    }

    final Set<String> keys = new TreeSet<String>();
    keys.addAll(standardProperties.stringPropertyNames());
    for (final String key : keys) {

      // exclude msg-keys as it is not normal resource bundle.
      if (!resourcePrefix.equalsIgnoreCase("msg-keys")) {
        if (PropertiesSorter.writtenPropertyKeys.contains(key)) {
          // Duplicate key, keep the first version
          continue;
        } else {
          PropertiesSorter.writtenPropertyKeys.add(key);
        }
      }

      PropertiesSorter.printPropertyEntry(key, standardProperties.getProperty(key), standardWriter);
      for (final Entry<String, Properties> entry : mapOfNameAndProperties.entrySet()) {
        if (PropertiesSorter.isStandardPropertyFile(entry.getKey(), resourcePrefix) == false) {

          System.out.println("Write to :" + entry.getKey());
          final Properties prop = entry.getValue();
          final PrintWriter writer = mapOfNameAndWriters.get(entry.getKey());
          if (prop.containsKey(key)) {

            System.out.println("Write " + key + ": " + prop.getProperty(key) + " to " + entry.getKey());
            PropertiesSorter.printPropertyEntry(key, prop.getProperty(key), writer);
          } else {
            System.out.println("Write " + key + ": " + prop.getProperty(key));
            PropertiesSorter.printPropertyEntry(key, standardProperties.getProperty(key), writer);
          }
        }
      }
    }

    // Close the writer
    for (final Entry<String, PrintWriter> entry : mapOfNameAndWriters.entrySet()) {
      final PrintWriter writer = entry.getValue();
      System.out.println("Flush and close :" + entry.getKey());
      writer.flush();
      writer.close();
    }
    standardWriter.close();

  }

  private static void printPropertyEntry(final String key, final String value, final PrintWriter writer) {
    writer.println(key + "=" + value);
  }

  private static final String[] ERROR_MARKS = {"====", "<<<<<", ">>>>>"};

  private static String readFile(final String filePath) throws IOException {
    final StringBuffer fileData = new StringBuffer(1000);
    final BufferedReader reader = new BufferedReader(new FileReader(filePath));
    char[] buf = new char[1024];
    int numRead = 0;
    while ((numRead = reader.read(buf)) != -1) {
      final String readData = String.valueOf(buf, 0, numRead);
      fileData.append(readData);
      buf = new char[1024];
    }
    reader.close();
    return fileData.toString();
  }

  private static void validatePropertyFile(final File file) throws IOException, PropertyFileException {
    final String fileContentStr = PropertiesSorter.readFile(file.getPath());
    for (final String errorMark : PropertiesSorter.ERROR_MARKS) {
      if (fileContentStr.contains(errorMark))
        throw new PropertyFileException("Found string " + errorMark + " on file " + file.getName());
    }
  }

  private static boolean isStandardPropertyFile(final String fileName, final String resourcePrefix) {
    return fileName.equalsIgnoreCase(resourcePrefix + PropertiesSorter.PROPERTY_FILE_SUFFIX);
  }

  static class PropertyFileException extends Exception {

    public PropertyFileException(final String message) {
      super(message + ". Resolve and run PropertySorter again!");
    }
  }
}
