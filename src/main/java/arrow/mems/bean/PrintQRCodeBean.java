/**
 *
 */
package arrow.mems.bean;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.config.AppConfig;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.repository.CountRecordRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.service.PrintQRCodeService;

import com.google.zxing.WriterException;


@Named
@ViewScoped
public class PrintQRCodeBean extends AbstractBean {
  @Inject
  PrintQRCodeService printQRCodeService;

  @Inject
  UserCredential userCredential;
  @Inject
  CountRecordRepository countRecordRepo;
  @Inject
  MdevChecklistRepository mdeChecklistRepo;
  @Inject
  DeviceRepository deviceRepo;

  DeviceDto currentDevice;
  private String url;
  private String deviceCode;
  public static int QRCODE_IMAGE_HEIGHT = 300;
  public static int QRCODE_IMAGE_WIDTH = 300;

  private boolean isDisplay = false;
  private String linkToImage;

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    this.currentDevice = new DeviceDto();
    final Device device = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
    BeanCopier.copy(device, this.currentDevice);
    this.isDisplay = false;
  }

  public void generateQRCode() throws WriterException, IOException {
    final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    final String QRCodeFolder = servletContext.getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME) + "/" + AppConfig.QRCODE_FOLDER;
    final File targetPath = new File(QRCodeFolder);
    if (!targetPath.exists()) {
      targetPath.mkdirs();
    }
    final String filePath = QRCodeFolder + "/" + this.deviceCode + AppConfig.QRCODE_EXTENSION;
    final String qrCodeData = this.deviceCode;
    this.printQRCodeService.createQRCode(qrCodeData, filePath, PrintQRCodeBean.QRCODE_IMAGE_HEIGHT, PrintQRCodeBean.QRCODE_IMAGE_WIDTH);
    this.isDisplay = true;
  }

  public String getImageContent() {
    if (this.deviceCode != null) {
      this.linkToImage = "/mResources?kind=qrCode&code=" + this.deviceCode;
      return this.linkToImage;
    } else
      return null;
  }

  public DeviceDto getCurrentDevice() {
    return this.currentDevice;
  }

  public String getLinkToImage() {
    return this.linkToImage;
  }

  public void setLinkToImage(String pLinkToImage) {
    this.linkToImage = pLinkToImage;
  }

  public void setCurrentDevice(DeviceDto pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
  }

  public void closeCountRecord(ActionEvent ae) {
    // reset data
    this.currentDevice = new DeviceDto();

    // reset stage
    this.resetStage(ae);
  }

  public boolean isDisplay() {
    return this.isDisplay;
  }

  public void setDisplay(boolean pIsDisplay) {
    this.isDisplay = pIsDisplay;
  }

  public void cleanStage() {
    this.currentDevice = new DeviceDto();
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String pUrl) {
    this.url = pUrl;
  }


}
