package arrow.mems.util;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import arrow.mems.constant.CommonConstants;

import com.google.common.io.Files;

public class ResourceUtils {
  public static final String PARAM_KIND = "kind";
  public static final String PARAM_CAT = "cat";
  public static final String PARAM_CODE = "code";
  public static final String PARAM_TYPE = "type";
  public static final String PARAM_MODE = "mode";
  public static final String PARAM_FILENAME = "filename";


  public static String getMasterDevicePicturerResourceUri(String mdevCode, boolean isThumbnail) {
    return "mResources?" + ResourceUtils.PARAM_KIND + "=img&" + ResourceUtils.PARAM_CAT + "=mdev&" + ResourceUtils.PARAM_CODE + "=" + mdevCode + (isThumbnail ? "&" + ResourceUtils.PARAM_MODE + "=thumb"
        : "");
  }

  public static String getDeviceImageResourceUri(String devCode, boolean isThumbnail) {
    return "mResources?" + ResourceUtils.PARAM_KIND + "=img&" + ResourceUtils.PARAM_CAT + "=dev&" + ResourceUtils.PARAM_CODE + "=" + devCode + "&" + ResourceUtils.PARAM_TYPE + "=image" + (isThumbnail ? "&" + ResourceUtils.PARAM_MODE + "=thumb"
        : "");
  }

  public static String getDeviceLocationResourceUri(String devCode, boolean isThumbnail) {
    return "mResources?" + ResourceUtils.PARAM_KIND + "=img&" + ResourceUtils.PARAM_CAT + "=dev&" + ResourceUtils.PARAM_CODE + "=" + devCode + "&" + ResourceUtils.PARAM_TYPE + "=location" + (isThumbnail ? "&" + ResourceUtils.PARAM_MODE + "=thumb"
        : "");
  }

  public static String getDocumentResourceUri(String docCode, String fileName) {
    return "mResources?" + ResourceUtils.PARAM_KIND + "=doc&" + ResourceUtils.PARAM_CODE + "=" + docCode + "&" + ResourceUtils.PARAM_FILENAME + "=" + fileName;
  }

  public static String generateResourceUrl(String kind, String docId, String fileName) {
    return Faces.getRequestBaseURL() + "mResources?kind=" + kind + "&code=" + docId + "&fileName=" + fileName;
  }

  public static String getMimeType(String basePath, String filePath) throws IOException {
    final File file = new File(basePath, URLDecoder.decode(filePath, CommonConstants.DEFAULT_ENCODING));
    if (file.exists()) {
      final String contentType = URLConnection.guessContentTypeFromName(Files.getNameWithoutExtension(file.getName()));
      if (StringUtils.isEmpty(contentType) && file.getName().endsWith(CommonConstants.FileExtensions.FLV))
        return CommonConstants.MimeTypes.VIDEO_FLV;
      else if (StringUtils.isEmpty(contentType) && file.getName().endsWith(CommonConstants.FileExtensions.AVI))
        return CommonConstants.MimeTypes.VIDEO_AVI;
      else if (StringUtils.isEmpty(contentType) && file.getName().endsWith(CommonConstants.FileExtensions.MP4))
        return CommonConstants.MimeTypes.VIDEO_MP4;
      else if (StringUtils.isEmpty(contentType) && file.getName().endsWith(CommonConstants.FileExtensions.WMV))
        return CommonConstants.MimeTypes.VIDEO_AVI;
      else
        return contentType;
    }
    throw new IOException("file not exists");
  }

}
