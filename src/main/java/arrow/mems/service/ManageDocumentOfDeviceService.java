package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.DocumentDto;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.repository.DocumentRepository;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.persistence.repository.UsersRepository;


public class ManageDocumentOfDeviceService extends AbstractService {
  @Inject
  DocumentRepository documentRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogRepository logRepo;

  public List<Document> getListDocument(String masterDeviceCode) {
    final ArrowQuery<Document> query = new ArrowQuery<>(super.em);
    query.select("e").from("Document e ");
    query.where(" e.isDeleted = 0");
    query.where(" e.mdevCode = ?", masterDeviceCode);
    query.orderBy("softwareRev");

    return query.getResultList();
  }

  public ServiceResult<DocumentDto> prepareEditDocument(Document document) {
    ServiceResult<DocumentDto> result = null;
    Document getDocument = new Document();
    if (document.isSelected()) {
      getDocument = this.documentRepo.findBy(document.getDocId());
      final DocumentDto documentDTO = new DocumentDto();
      BeanCopier.copy(getDocument, documentDTO);
      result = new ServiceResult<DocumentDto>(true, documentDTO);
    }
    return result;
  }

  public ServiceResult<Document> saveDocument(DocumentDto currentDocument) {
    ServiceResult<Document> result = null;
    if (currentDocument.getDocId() == 0) {
      result = this.createDocument(currentDocument, null, null);
    } else {
      final Document editDocument = this.documentRepo.findBy(currentDocument.getDocId());
      if (editDocument.isPassedApprovalProcess()) {
        editDocument.setIsDeleted(CommonConstants.DELETE);
        this.documentRepo.saveAndFlush(editDocument);
        result = this.createDocument(currentDocument, this.userCredential.getUserId(), LocalDateTime.now());
      } else {
        editDocument.setIsDeleted(1);
        editDocument.setCheckedAt(LocalDateTime.now());
        editDocument.setCheckedBy(this.userCredential.getUserId());
        this.documentRepo.saveAndFlush(editDocument);
        result = this.createDocument(currentDocument, null, null);
      }
    }
    return result;
  }

  public ServiceResult<Document> createDocument(DocumentDto currentDocument, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<Document> result = null;
    final Document newDocument = new Document();
    BeanCopier.copy(currentDocument, newDocument);
    newDocument.setIsDeleted(CommonConstants.ACTIVE);
    newDocument.setCheckedAt(checkedDate);
    newDocument.setCheckedBy(checkedBy);
    newDocument.setCreatedAt(LocalDateTime.now());
    newDocument.setCreatedBy(this.userCredential.getUserId());
    this.documentRepo.saveAndFlush(newDocument);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newDocument, message);
    return result;
  }

  public ServiceResult<Document> deleteDocument(Document selectedDocument) {
    ServiceResult<Document> result = null;
    final Document deleteDocument = this.documentRepo.findBy(selectedDocument.getDocId());
    deleteDocument.setIsDeleted(CommonConstants.DELETE);
    this.documentRepo.saveAndFlush(deleteDocument);

    // delete files

    result = new ServiceResult<>(true, null, null);
    return result;
  }
}
