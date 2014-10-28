package arrow.mems.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import arrow.framework.faces.messages.Message;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.config.AppConfig;
import arrow.mems.helper.UploadHelper;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.DocumentDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Document;
import arrow.mems.service.ManageDocumentOfDeviceService;
import arrow.mems.service.UserService;
import arrow.mems.util.file.FileUtils;

@Named
@ViewScoped
public class ManagementDocumentOfMasterDeviceBean extends AbstractApprovalBean {

  private DocumentDto currentDocument;
  private Document selectedDocument;

  private List<Document> listDocumentSets;

  private List<Document> listDeletingDocuments = new ArrayList<Document>();
  private List<Document> listModifiedDocuments = new ArrayList<Document>();

  public List<Document> getListModifiedDocuments() {
    return this.listModifiedDocuments;
  }

  public void setListModifiedDocuments(List<Document> pListModifiedDocuments) {
    this.listModifiedDocuments = pListModifiedDocuments;
  }

  private List<Document> listNewDocuments = new ArrayList<Document>();

  public List<Document> getListNewDocuments() {
    return this.listNewDocuments;
  }

  public void setListNewDocuments(List<Document> pListNewDocuments) {
    this.listNewDocuments = pListNewDocuments;
  }

  private String comment;
  private String masterDeviceCode;

  // init upload document
  private UploadedFile fileUserManual;
  private UploadedFile fileServiceManual;
  private UploadedFile fileServiceGuide;
  private UploadedFile fileSafetyTest;
  private UploadedFile filePerformanceTest;

  // init upload video
  private UploadedFile fileUserManualVideo;
  private UploadedFile fileServiceManualVideo;
  private UploadedFile fileSafetyTestVideo;
  private UploadedFile filePerformanceTestVideo;
  String linkToDocument;
  @Inject
  ManageDocumentOfDeviceService documentService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;
  List<Message> listMessage = new ArrayList<Message>();
  boolean isDisplay = false;

  public String goToViewDocument(String docId, String fileName) {
    this.linkToDocument = "/mResources?kind=doc&code=" + docId + "&fileName=" + fileName;
    this.isDisplay = true;
    return this.linkToDocument;
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

  public UploadedFile getFileUserManual() {
    return this.fileUserManual;
  }

  public void setFileUserManual(UploadedFile pFileUserManual) {
    this.fileUserManual = pFileUserManual;
  }

  public UploadedFile getFileServiceManual() {
    return this.fileServiceManual;
  }

  public void setFileServiceManual(UploadedFile pFileServiceManual) {
    this.fileServiceManual = pFileServiceManual;
  }

  public UploadedFile getFileServiceGuide() {
    return this.fileServiceGuide;
  }

  public void setFileServiceGuide(UploadedFile pFileServiceGuide) {
    this.fileServiceGuide = pFileServiceGuide;
  }

  public UploadedFile getFileSafetyTest() {
    return this.fileSafetyTest;
  }

  public void setFileSafetyTest(UploadedFile pFileSafetyTest) {
    this.fileSafetyTest = pFileSafetyTest;
  }

  public UploadedFile getFilePerformanceTest() {
    return this.filePerformanceTest;
  }

  public void setFilePerformanceTest(UploadedFile pFilePerformanceTest) {
    this.filePerformanceTest = pFilePerformanceTest;
  }

  public UploadedFile getFileUserManualVideo() {
    return this.fileUserManualVideo;
  }

  public void setFileUserManualVideo(UploadedFile pFileUserManualVideo) {
    this.fileUserManualVideo = pFileUserManualVideo;
  }

  public UploadedFile getFileServiceManualVideo() {
    return this.fileServiceManualVideo;
  }

  public void setFileServiceManualVideo(UploadedFile pFileServiceManualVideo) {
    this.fileServiceManualVideo = pFileServiceManualVideo;
  }

  public UploadedFile getFileSafetyTestVideo() {
    return this.fileSafetyTestVideo;
  }

  public void setFileSafetyTestVideo(UploadedFile pFileSafetyTestVideo) {
    this.fileSafetyTestVideo = pFileSafetyTestVideo;
  }

  public UploadedFile getFilePerformanceTestVideo() {
    return this.filePerformanceTestVideo;
  }

  public void setFilePerformanceTestVideo(UploadedFile pFilePerformanceTestVideo) {
    this.filePerformanceTestVideo = pFilePerformanceTestVideo;
  }

  public boolean isEnableEdit() {
    return this.selectedDocument != null;
  }

  public boolean isEnableDelete() {
    return (this.selectedDocument != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public Document getSelectedDocument() {
    return this.selectedDocument;
  }

  public void setSelectedDocument(Document pSelectedDocument) {
    this.selectedDocument = pSelectedDocument;
  }

  public DocumentDto getCurrentDocument() {
    return this.currentDocument;
  }

  public void setCurrentDocument(DocumentDto pCurrentDocument) {
    this.currentDocument = pCurrentDocument;
  }

  public String getMasterDeviceCode() {
    return this.masterDeviceCode;
  }

  public void setMasterDeviceCode(String pMasterDeviceCode) {
    this.masterDeviceCode = pMasterDeviceCode;
    this.listDocumentSets = null;
  }

  public List<Document> getDeletingDocuments() {
    return this.listDeletingDocuments;
  }

  public void setListDeletingDocuments(List<Document> pListDocumentDelete) {
    this.listDeletingDocuments = pListDocumentDelete;
  }

  public void toggleSelection(final Document document) {
    if (document.isSelected()) {
      if (this.selectedDocument != null) {
        this.selectedDocument.setSelected(false);
      }
      this.selectedDocument = document;
      this.currentDocument = this.createNewDocument();
      BeanCopier.copy(this.selectedDocument, this.currentDocument);
    } else {
      this.selectedDocument = null;
    }
  }

  public List<Document> getListDocument() {
    if (this.userCredential.isLoggedIn() && (this.listDocumentSets == null)) {
      this.listDocumentSets = new ArrayList<Document>();
      if (this.masterDeviceCode != null) {
        this.listDocumentSets.addAll(this.documentService.getListDocument(this.masterDeviceCode));
      }
    }
    return this.listDocumentSets;
  }

  public void setListDocument(List<Document> listDocument) {
    this.listDocumentSets = listDocument;
  }

  public void addNewDocument(ActionEvent ae) {
    this.resetStage(ae);
    this.cleanStage();
    this.currentDocument = this.createNewDocument();
  }

  private DocumentDto createNewDocument() {
    final DocumentDto newDoc = new DocumentDto();
    newDoc.setUploadFolderName(UUID.randomUUID().toString());
    return newDoc;
  }

  public void editDocument() {
    this.currentDocument = this.createNewDocument();
    BeanCopier.copy(this.selectedDocument, this.currentDocument);
  }

  public void deleteDocument() {
    if (this.selectedDocument.isSelected()) {
      this.listDocumentSets.remove(this.selectedDocument);
      if (this.selectedDocument.getDocId() > 0) {
        this.listDeletingDocuments.add(this.selectedDocument);
        if (this.listModifiedDocuments.contains(this.selectedDocument)) {
          this.listModifiedDocuments.remove(this.selectedDocument);
        }
      } else {
        this.listNewDocuments.remove(this.selectedDocument);
      }

      this.cleanStage();
    }
    this.cleanStage();
  }

  public void discardChange(ActionEvent ae) {
    this.resetStage(ae);
    this.listDocumentSets = null;
    this.listDeletingDocuments.clear();
    this.listModifiedDocuments.clear();
    this.listNewDocuments.clear();
    this.cleanStage();
  }

  public void reset() {
    if (this.currentDocument == null) {
      this.currentDocument = new DocumentDto();
    } else {
      this.editDocument();
    }

  }

  public void cleanStage() {
    this.currentDocument = null;
    if (this.selectedDocument != null) {
      this.selectedDocument.setSelected(false);
      this.selectedDocument = null;
    }
  }

  public void saveDocumentTemporary() throws IOException {
    // set data.
    if (this.fileUserManual != null) {
      this.currentDocument.setInst01(this.fileUserManual.getFileName());
    }
    if (this.fileServiceManual != null) {
      this.currentDocument.setService01(this.fileServiceManual.getFileName());
    }
    if (this.fileServiceGuide != null) {
      this.currentDocument.setService02(this.fileServiceGuide.getFileName());
    }
    if (this.fileSafetyTest != null) {
      this.currentDocument.setSafety01(this.fileSafetyTest.getFileName());
    }
    if (this.filePerformanceTest != null) {
      this.currentDocument.setPerformance01(this.filePerformanceTest.getFileName());
    }

    // for video, check if it's video file and then upload
    if (this.fileUserManualVideo != null) {
      this.currentDocument.setInstV(this.fileUserManualVideo.getFileName());
    }
    if (this.fileServiceManualVideo != null) {
      this.currentDocument.setReplacePartV(this.fileServiceManualVideo.getFileName());
    }
    if (this.fileSafetyTestVideo != null) {
      this.currentDocument.setSafetyV(this.fileSafetyTestVideo.getFileName());
    }
    if (this.filePerformanceTestVideo != null) {
      this.currentDocument.setPerformanceV(this.filePerformanceTestVideo.getFileName());
    }

    // operate add and remove document temporary
    this.listDocumentSets.remove(this.selectedDocument);
    this.listDocumentSets.add(this.currentDocument);
    if (this.currentDocument.getDocId() <= 0) {
      this.listNewDocuments.add(this.currentDocument);
    } else {

      this.listModifiedDocuments.add(this.currentDocument);
    }

    this.currentDocument = null;
  }

  private void uploadFileToTempDir(UploadedFile uploadedFile, Document currentDocument) {
    try {
      FileUtils.uploadFile(uploadedFile.getInputstream(), uploadedFile.getFileName(),
          UploadHelper.getUploadTempPath(this.appConfig.getUploadTempDir(), currentDocument.getUploadFolderName()));
    } catch (final IOException e1) {
      this.log.debug("error while upload file", e1);
    }
  }

  // File upload event document
  public void fileUserManualUploadListener(FileUploadEvent e) {
    this.fileUserManual = e.getFile();
    this.setFileUserManual(this.fileUserManual);
    this.currentDocument.setInst01(this.fileUserManual.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void fileServiceManualUploadListener(FileUploadEvent e) {
    this.fileServiceManual = e.getFile();
    this.setFileServiceManual(this.fileServiceManual);
    this.currentDocument.setService01(this.fileServiceManual.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void fileServiceGuideUploadListener(FileUploadEvent e) {
    this.fileServiceGuide = e.getFile();
    this.setFileServiceGuide(this.fileServiceGuide);
    this.currentDocument.setService02(this.fileServiceGuide.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void fileSafetyTestUploadListener(FileUploadEvent e) {
    this.fileSafetyTest = e.getFile();
    this.setFileSafetyTest(this.fileSafetyTest);
    this.currentDocument.setSafety01(this.fileSafetyTest.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void filePerformanceTestUploadListener(FileUploadEvent e) {
    this.filePerformanceTest = e.getFile();
    this.setFilePerformanceTest(this.filePerformanceTest);
    this.currentDocument.setPerformance01(this.filePerformanceTest.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  // File upload event Video
  public void fileManualVideoUploadListener(FileUploadEvent e) {
    this.fileUserManualVideo = e.getFile();
    this.setFileUserManualVideo(this.fileUserManualVideo);
    this.currentDocument.setInstV(this.fileUserManualVideo.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void fileServiceManualVideoUploadListener(FileUploadEvent e) {
    this.fileServiceManualVideo = e.getFile();
    this.setFileServiceManualVideo(this.fileServiceManualVideo);
    this.currentDocument.setReplacePartV(this.fileServiceManualVideo.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void fileSafetyTestVideoUploadListener(FileUploadEvent e) {
    this.fileSafetyTestVideo = e.getFile();
    this.setFileSafetyTestVideo(this.fileSafetyTestVideo);
    this.currentDocument.setSafetyV(this.fileSafetyTestVideo.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public void filePerformanceTestVideoUploadListener(FileUploadEvent e) {
    this.filePerformanceTestVideo = e.getFile();
    this.setFilePerformanceTestVideo(this.filePerformanceTestVideo);
    this.currentDocument.setPerformanceV(this.filePerformanceTestVideo.getFileName());
    this.uploadFileToTempDir(e.getFile(), this.currentDocument);
  }

  public List<Message> validateFileUpload(UploadedFile uploadFile, boolean isVideo) {
    final String expression = isVideo ? AppConfig.VIDEO_FILE_TYPE_ALLOWED : AppConfig.DOCUMENT_FILE_TYPE_ALLOWED;
    final String explain = isVideo ? AppConfig.VIDEO_FILE_TYPE_ALLOWED_EXPLAIN : AppConfig.DOCUMENT_FILE_TYPE_ALLOWED_EXPLAIN;
    final List<Message> errors = new ArrayList<Message>();
    if ((!uploadFile.getFileName().matches(expression))) {
      errors.add(MDMMessages.MDM00011(explain));
    }
    final int sizeLimit = isVideo ? AppConfig.VIDEO_FILE_SIZE_ALLOWED : AppConfig.DOCUMENT_FILE_SIZE_ALLOWED;
    if (uploadFile.getSize() > sizeLimit) {
      errors.add(MDMMessages.MDM00012(sizeLimit));
    }
    return errors;
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

  public void resetAll() {
    this.listDocumentSets = null;
    this.masterDeviceCode = null;
    this.listDeletingDocuments.clear();
    this.listModifiedDocuments.clear();
    this.listNewDocuments.clear();
  }
}
