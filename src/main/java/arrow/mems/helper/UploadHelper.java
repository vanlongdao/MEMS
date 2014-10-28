package arrow.mems.helper;

import org.primefaces.model.UploadedFile;

import arrow.mems.util.string.ArrowStrUtils;

public class UploadHelper {
  // $UPLOAD_DIR/<mdevcode>/<rev>/<filename>
  public static String getUploadPath(String uploadDir, String mdevCode, String softwareRev, UploadedFile uploadedFile) {
    return String.format("%s/%s/%s/%s", uploadDir, mdevCode, ArrowStrUtils.escapeFilename(softwareRev), uploadedFile.getFileName());
  }

  // $UPLOAD_TEMP_DIR/<uploadFolderName>
  // uploadFolderName will be an UUID
  public static String getUploadTempPath(String uploadTempDir, String uploadFolderName) {
    return String.format("%s/%s", uploadTempDir, uploadFolderName);
  }

  public static String getUploadTempDir(String uploadTempDir, int pUserId, String pModuleMasterDevice) {
    return String.format("%s/%s/%s", uploadTempDir, String.valueOf(pUserId), pModuleMasterDevice);
  }

  public static String getUploadDir(String uploadDir, String mdevCode, String softwareRev) {
    return String.format("%s/%s/%s", uploadDir, mdevCode, softwareRev);
  }

}
