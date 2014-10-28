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
import arrow.mems.persistence.dto.HumanResourceDto;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.OperationLog;
import arrow.mems.persistence.repository.HumanResourceRepository;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class HumanResourceManagementService extends AbstractService {
  @Inject
  HumanResourceRepository humanRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepository;
  @Inject
  UserService userService;
  @Inject
  OperationLogRepository logRepo;


  public List<HumanResource> getListHumanResource(String officeCode) {
    final List<Integer> listCreatedByUserId = this.userService.findUserInOneOffice(officeCode);
    final ArrowQuery<HumanResource> query = new ArrowQuery<>(super.em);
    query.select("e").from("HumanResource e ");
    query.where(" e.isDeleted = 0");
    query.where(" e.createdBy in (?) ", listCreatedByUserId);
    // add filter and sort
    query.addFilterAndSorterForString("hospCode", "e.hospital.hospCode");
    query.addFilterAndSorterForString("hospName", "e.hospital.name");
    query.addFilterAndSorterForString("hospDeptCode", "e.hospitalDept.deptCode");
    query.addFilterAndSorterForString("hospDeptName", "e.hospitalDept.name");
    query.addFilterAndSorterForString("personCode", "e.person.psnCode");
    query.addFilterAndSorterForString("personName", "e.person.name");
    query.addFilterAndSorterForString("classCode", "e.personClass.classCode");
    query.addFilterAndSorterForString("country", "e.personClass.mtCountry.name");
    query.orderBy("hospCode");

    return query.getResultList();
  }

  public ServiceResult<HumanResourceDto> prepareEditHumanResource(HumanResource humanResource) {
    ServiceResult<HumanResourceDto> result = null;
    HumanResource getHumanResource = new HumanResource();
    if (humanResource.isSelected()) {
      getHumanResource = this.humanRepository.findBy(humanResource.getHrId());
      final HumanResourceDto humanResourceDTO = new HumanResourceDto();
      BeanCopier.copy(getHumanResource, humanResourceDTO);
      result = new ServiceResult<HumanResourceDto>(true, humanResourceDTO);
    }
    return result;
  }


  public ServiceResult<HumanResource> saveHumanResource(HumanResourceDto currentHumanResource) {
    ServiceResult<HumanResource> result = null;
    if (currentHumanResource.getHrId() == 0) {
      result = this.createHumanResource(currentHumanResource, null, null);
    } else {
      final HumanResource editHumanResource = this.humanRepository.findBy(currentHumanResource.getHrId());
      if (editHumanResource.isPassedApprovalProcess()) {
        editHumanResource.setIsDeleted(CommonConstants.DELETE);
        this.humanRepository.saveAndFlush(editHumanResource);
        result = this.createHumanResource(currentHumanResource, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editHumanResource.setIsDeleted(CommonConstants.DELETE);
        this.humanRepository.saveAndFlush(editHumanResource);
        result = this.createHumanResource(currentHumanResource, null, null);
      }
      // add operation log after edit HumanResource
      // TODO: use common method.
      final OperationLog log = new OperationLog();
      log.setTargetTable(HumanResource.class.getName());
      log.setApprovedBy(this.userCredential.getUserId());
      log.setOperatedBy(this.userCredential.getUserId());
      log.setOperatedAt(LocalDateTime.now());
      log.setDescription("update_data_approved");
      log.setOldId(currentHumanResource.getHrId());
      log.setNewId(result.getData().getHrId());
      this.logRepo.saveAndFlush(log);
    }
    return result;
  }

  public ServiceResult<HumanResource> createHumanResource(HumanResourceDto currentHumanResource, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<HumanResource> result = null;
    final HumanResource newHumanResource = new HumanResource();
    BeanCopier.copy(currentHumanResource, newHumanResource);
    newHumanResource.setHospCode(currentHumanResource.getHospital().getHospCode());
    newHumanResource.setHospDeptCode(currentHumanResource.getHospitalDept().getDeptCode());
    newHumanResource.setPsnCode(currentHumanResource.getPerson().getPsnCode());
    if (currentHumanResource.getPersonClass() != null) {
      newHumanResource.setClassCode(currentHumanResource.getPersonClass().getClassCode());
    }
    newHumanResource.setIsDeleted(CommonConstants.ACTIVE);
    newHumanResource.setCheckedAt(checkedDate);
    newHumanResource.setCheckedBy(checkedBy);
    newHumanResource.setCreatedAt(LocalDateTime.now());
    newHumanResource.setCreatedBy(this.userCredential.getUserId());
    this.humanRepository.saveAndFlush(newHumanResource);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newHumanResource, message);
    return result;
  }

  public ServiceResult<HumanResource> deleteHumanResource(HumanResource selectedHumanResource) {
    ServiceResult<HumanResource> result = null;
    final HumanResource deleteHumanResource = this.humanRepository.findBy(selectedHumanResource.getHrId());
    deleteHumanResource.setIsDeleted(CommonConstants.DELETE);
    this.humanRepository.saveAndFlush(deleteHumanResource);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  public boolean checkExistingHumanResource(String hospCode, String hospDeptCode, String personCode) {
    boolean isExistingResult = true;
    final HumanResource humanResource = this.humanRepository.getHumanResourcesToCheckUnique(hospCode, hospDeptCode, personCode).getAnyResult();
    if (humanResource != null) {
      isExistingResult = true;
    } else {
      isExistingResult = false;
    }
    return isExistingResult;
  }

  public boolean checkExistingHumanResourceWhenEdit(String hospCode, String hospDeptCode, String personCode, int hrId) {
    boolean isExistingResult = true;
    final HumanResource humanResource =
        this.humanRepository.getHumanResourcesToCheckUniqueWhenEdit(hospCode, hospDeptCode, personCode, hrId).getAnyResult();
    if (humanResource != null) {
      isExistingResult = true;
    } else {
      isExistingResult = false;
    }
    return isExistingResult;
  }
}
