package arrow.mems.bean;

import java.io.IOException;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import arrow.framework.util.BeanCopier;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.config.AppConfig;
import arrow.mems.persistence.dto.DocumentDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.DocumentRepository;
import arrow.mems.service.ManageDocumentOfDeviceService;
import arrow.mems.util.ResourceUtils;

@Named
@ViewScoped
public class ViewManualDocumentBean extends AbstractApprovalBean {
  private DocumentDto currentDocument;
  String linkToDocument;
  @Inject
  ManageDocumentOfDeviceService documentService;
  @Inject
  ViewDetailQRCodeBean viewQRCodeBean;
  @Inject
  DeviceRepository deviceRepo;
  @Inject
  DocumentRepository documentRepo;

  String deviceCode;
  boolean isDisplay = false;

  public String getDocumentData(String docId, String fileName) {
    this.linkToDocument = ResourceUtils.generateResourceUrl("doc", docId, fileName);
    this.isDisplay = true;
    return this.linkToDocument;
  }

  public String getDocumentVideoData(String docId, String fileName) {
    this.linkToDocument = ResourceUtils.generateResourceUrl("video", docId, fileName);
    this.isDisplay = true;
    return this.linkToDocument;
  }

  public String getMimeType(Document document, String fileName) throws IOException {
    final String basePath = Faces.getInitParameter(AppConfig.UPLOAD_DIR_PARAM_NAME);
    final String filePath = document.getMdevCode() + "/" + document.getSoftwareRev() + "/" + fileName;
    return ResourceUtils.getMimeType(basePath, filePath);
  }

  public void getDocumentOfMDevice() {
    this.currentDocument = new DocumentDto();
    final Device device = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
    final String mdevCode = device.getMdevCode();
    final String softwareRevison = device.getSoftwareRev();
    if (StringUtils.isNotEmpty(mdevCode)) {
      final Document document = this.documentRepo.findDocumentByDeviceCodeAndIsDeleted(mdevCode, softwareRevison).getAnyResult();
      if (document != null) {
        BeanCopier.copy(document, this.currentDocument);
      }
    }
  }

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    if (StringUtils.isNotEmpty(this.deviceCode)) {
      this.getDocumentOfMDevice();
    }
  }

  public boolean isDisplay() {
    return this.isDisplay;
  }

  public void setDisplay(boolean pIsDisplay) {
    this.isDisplay = pIsDisplay;
  }

  public String getLinkToDocument() {
    return this.linkToDocument;
  }

  public void setLinkToDocument(String pFullNameDir) {
    this.linkToDocument = pFullNameDir;
  }

  public DocumentDto getCurrentDocument() {
    return this.currentDocument;
  }

  public void setCurrentDocument(DocumentDto pCurrentDocument) {
    this.currentDocument = pCurrentDocument;
  }

  public void discardChange(ActionEvent ae) {
    this.resetStage(ae);
    this.cleanStage();
  }

  public void reset() {
    if (this.currentDocument == null) {
      this.currentDocument = new DocumentDto();
    }
  }

  public void cleanStage() {
    this.currentDocument = null;
  }

  @Override
  public Object getEntityId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDataType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getItemLabel() {
    // TODO Auto-generated method stub
    return null;
  }

}
