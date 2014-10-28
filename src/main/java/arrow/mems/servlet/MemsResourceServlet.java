package arrow.mems.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arrow.mems.bean.ImageStreamBean;
import arrow.mems.config.AppConfig;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.repository.DocumentRepository;
import arrow.mems.service.DeviceManagementService;
import arrow.mems.service.DevicesManagementService;

/**
 * mResources?kind=doc&code=<docCode>&filename=<filename>&mode=thumb
 * mResources?kind=img&cat=dev&code=<devCode>&type=location&mode=thumb
 * mResources?kind=img&cat=dev&code=<devCode>&type=image&mode=thumb
 * mResources?kind=img&cat=mdev&code=<mdevcode>&mode=thumb
 *
 * @author michael
 *
 */

@WebServlet(name = "MemsResourceServlet", urlPatterns = {"/mResources/*"})
public class MemsResourceServlet extends HttpServlet {

  @Inject
  DevicesManagementService masterDeviceService;

  @Inject
  ImageStreamBean imageStreamBean;

  @Inject
  DeviceManagementService deviceService;
  @Inject
  DocumentRepository documentRepo;
  @Inject
  AppConfig appConfig;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: test: try to return image as stream then check how primefaces's lightbox work.
    final String kind = request.getParameter("kind");
    switch (kind) {
      case "img":
        this.doGetImg(request, response);
        break;
      case "qrCode":
        this.doGetQRCode(request, response);
        break;
      case "video":
        this.doVideo(request, response);
        break;
      default:
        this.doGetDoc(request, response);
    }
  }

  private static final int DEFAULT_BUFFER_SIZE = 10240;

  private void doVideo(HttpServletRequest pRequest, HttpServletResponse response) throws IOException {
    final String docId = pRequest.getParameter("code");
    final String fileName = pRequest.getParameter("fileName");
    final Document document = this.documentRepo.findBy(Integer.parseInt(docId));
    final String basePath = pRequest.getServletContext().getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME);
    final String filePath = document.getMdevCode() + "/" + document.getSoftwareRev() + "/" + fileName;

    // URL-decode the file name (might contain spaces and on) and prepare file object.
    final File file = new File(basePath, URLDecoder.decode(filePath, CommonConstants.DEFAULT_ENCODING));

    // Check if file actually exists in filesystem.
    if (!file.exists()) {
      // Do your thing if the file appears to be non-existing.
      // Throw an exception, or send 404, or show default/warning page, or just ignore it.
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    // Get content type by filename.
    String contentType = this.getServletContext().getMimeType(file.getName());

    // If content type is unknown, then set the default value.
    // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
    // To add new content types, add new mime-mapping entry in web.xml.
    if (contentType == null) {
      contentType = "application/octet-stream";
    }


    // Init servlet response.
    response.reset();
    response.setBufferSize(10240);
    response.setContentType(contentType);
    response.setHeader("Content-Length", String.valueOf(file.length()));
    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

    // Prepare streams.
    BufferedInputStream input = null;
    BufferedOutputStream output = null;

    try {
      // Open streams.
      input = new BufferedInputStream(new FileInputStream(file), MemsResourceServlet.DEFAULT_BUFFER_SIZE);
      output = new BufferedOutputStream(response.getOutputStream(), MemsResourceServlet.DEFAULT_BUFFER_SIZE);

      // Write file contents to response.
      final byte[] buffer = new byte[MemsResourceServlet.DEFAULT_BUFFER_SIZE];
      int length;
      while ((length = input.read(buffer)) > 0) {
        output.write(buffer, 0, length);
      }
    } finally {
      // Gently close streams.
      this.close(output);
      this.close(input);
    }

  }

  // Helpers (can be refactored to public utility class) ----------------------------------------

  private void close(Closeable resource) {
    if (resource != null) {
      try {
        resource.close();
      } catch (final IOException e) {
        // Do your thing with the exception. Print it, log it or mail it.
        e.printStackTrace();
      }
    }
  }


  public void doGetQRCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    final String deviceCode = request.getParameter("code");
    final String pathName =
        request.getServletContext().getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME) + "/" + AppConfig.QRCODE_FOLDER + "/" + deviceCode + AppConfig.QRCODE_EXTENSION;
    final ServletOutputStream out = response.getOutputStream();
    InputStream in = new FileInputStream(new File(pathName));
    in = new FileInputStream(new File(pathName));
    final byte[] fileContent = new byte[1024];
    int bytesRead;

    while ((bytesRead = in.read(fileContent)) != -1) {
      out.write(fileContent, 0, bytesRead);
    }

    in.close();
    out.close();
  }

  private void doGetDoc(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
    final String docId = pRequest.getParameter("code");
    final String fileName = pRequest.getParameter("fileName");
    final Document document = this.documentRepo.findBy(Integer.parseInt(docId));
    final String pathName =
        pRequest.getServletContext().getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME) + "/" + document.getMdevCode() + "/" + document
            .getSoftwareRev() + "/" + fileName;

    final ServletOutputStream out = pResponse.getOutputStream();
    InputStream in = new FileInputStream(new File(pathName));
    in = new FileInputStream(new File(pathName));
    final String mimeType = "application/pdf";
    final byte[] fileContent = new byte[4096];
    int bytesRead;

    pResponse.setContentType(mimeType);

    while ((bytesRead = in.read(fileContent)) != -1) {
      out.write(fileContent, 0, bytesRead);

    }

    in.close();
    out.close();
  }

  private void doGetImg(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
    final String cat = pRequest.getParameter("cat");
    switch (cat) {
      case "mdev":
        this.doGetMasterDevice(pRequest, pResponse);
        break;
      case "dev":
        this.doGetDevice(pRequest, pResponse);
      case "rr":
        this.doGetRepairRequest(pRequest, pResponse);
      default:
        break;
    }
  }

  private void doGetRepairRequest(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {


  }

  private void doGetDevice(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
    final String devCode = pRequest.getParameter("code");
    final String type = pRequest.getParameter("type");
    byte[] fileContent = null;
    if (fileContent == null) {
      if (type.equals("location")) {
        fileContent = this.imageStreamBean.getDeviceLocationImage(devCode);
      } else {
        fileContent = this.imageStreamBean.getDeviceImage(devCode);
      }
    }

    if (fileContent == null) {
      final Device device = this.deviceService.loadDeviceByDeviceCode(devCode);
      if (device != null) {
        if (type.equals("location") && (device.getLocationImage() != null)) {
          fileContent = device.getLocationImage();
          this.imageStreamBean.uploadDeviceLocationImageTemporary(devCode, fileContent);
        } else if (device.getImageFile() != null) {
          fileContent = device.getImageFile();
          this.imageStreamBean.uploadDeviceImageTemporary(devCode, fileContent);
        }
      }
    }



    if (fileContent != null) {
      this.writeFileContent(pResponse, fileContent, "image/png");
    }

  }

  private void doGetMasterDevice(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
    final String mdevCode = pRequest.getParameter("code");
    final MDevice masterDev = this.masterDeviceService.getDeviceFromMdevCode(mdevCode, CommonConstants.ACTIVE);
    if (masterDev != null) {
      pResponse.reset();
      pResponse.setContentType("image/jpeg");

      // TODO: check mode=thumb case
      pResponse.getOutputStream().write(masterDev.getPicture(), 0, masterDev.getPicture().length);
    } else {
      // not found in database, maybe in memory.
      // this.imageStreamBean.getMasterDeviceImage(mdevCode);
    }

  }

  private void writeFileContent(HttpServletResponse pResponse, byte[] fileContent, String contentType) throws IOException {
    pResponse.reset();
    pResponse.setContentType(contentType);

    // TODO: check mode=thumb case
    pResponse.getOutputStream().write(fileContent, 0, fileContent.length);
  }
}
