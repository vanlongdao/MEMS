package arrow.mems.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class FileUtils {

  private static final String PATH_UPLOAD = "/home/vanlongdao/Documents/upload/";

  /**
   * Download file from filePath by servlet.
   *
   * @param filePath the file path to open
   * @param request the request from servlet
   * @param response the response from servlet
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
    // reads input file from an absolute path
    final File downloadFile = new File(filePath);
    final FileInputStream inStream = new FileInputStream(downloadFile);

    // if you want to use a relative path to context root:
    final String relativePath = request.getServletContext().getRealPath("");
    System.out.println("relativePath = " + relativePath);

    // obtains ServletContext
    final ServletContext context = request.getServletContext();

    // gets MIME type of the file
    String mimeType = context.getMimeType(filePath);
    if (mimeType == null) {
      // set to binary type if MIME mapping not found
      mimeType = "application/octet-stream";
    }
    System.out.println("MIME type: " + mimeType);

    response.setContentType(mimeType);
    response.setContentLength((int) downloadFile.length());

    // forces download
    final String headerKey = "Content-Disposition";
    final String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
    response.setHeader(headerKey, headerValue);

    // obtains response's output stream
    final OutputStream outStream = response.getOutputStream();

    final byte[] buffer = new byte[4096];
    int bytesRead = -1;

    while ((bytesRead = inStream.read(buffer)) != -1) {
      outStream.write(buffer, 0, bytesRead);
    }

    inStream.close();
    outStream.close();
  }

  /**
   * Open file from file path by servlet.
   *
   * @param filePath the file path
   * @param request the request
   * @param response the response
   * @throws IOException
   */
  public static void open(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
    final ServletOutputStream out = response.getOutputStream();
    final File file = new File(filePath);
    final InputStream inStream = new FileInputStream(file);

    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      // Use Buffered Stream for reading/writing.
      bis = new BufferedInputStream(inStream);
      bos = new BufferedOutputStream(out);
      final byte[] buff = new byte[2048];
      int bytesRead;
      // Simple read/write loop.
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, bytesRead);
      }
    } catch (final IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      throw e;
    } finally {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
    }
  }

  public static String upload(InputStream is, String fileName) {
    try {
      final File targetPath = new File(FileUtils.PATH_UPLOAD);
      if (!targetPath.exists()) {
        targetPath.mkdir();
      }
      final OutputStream out = new FileOutputStream(new File(targetPath, fileName));
      int read = 0;
      final byte[] bytes = new byte[1024];
      while ((read = is.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      is.close();
      out.flush();
      out.close();
    } catch (final IOException e) {
      e.printStackTrace();
      return "Unsuccessful";
    }
    return FileUtils.PATH_UPLOAD + fileName;
  }

  public static String uploadFile(InputStream is, String fileName, String path) {
    try {
      final File targetPath = new File(path);
      if (!targetPath.exists()) {
        targetPath.mkdirs();
      }
      final OutputStream out = new FileOutputStream(new File(targetPath, fileName));
      int read = 0;
      final byte[] bytes = new byte[1024];
      while ((read = is.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      is.close();
      out.flush();
      out.close();
    } catch (final IOException e) {
      e.printStackTrace();
      return "Unsuccessful";
    }
    return path + fileName;
  }

  public static byte[] getContentFile(InputStream is) throws IOException {
    return IOUtils.toByteArray(is);
  }

  public static void rmdir(String pUploadTempDir) throws IOException {
    org.apache.commons.io.FileUtils.deleteDirectory(new File(pUploadTempDir));
  }

  public static void copyDir(String srcDir, String desDir) throws IOException {
    final File targetPath = new File(desDir);
    if (!targetPath.exists()) {
      targetPath.mkdirs();
    }
    org.apache.commons.io.FileUtils.copyDirectory(new File(srcDir), targetPath);
  }

  public static void renameDir(String srcDir, String desDir) throws IOException {
    org.apache.commons.io.FileUtils.moveDirectory(new File(srcDir), new File(desDir));
  }
}
