package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.HospitalDeptIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.HospitalDeptDto;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class HospitalDeptManagementService extends AbstractService {
  @Inject
  HospitalDeptRepository hospitalDeptRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogService logService;

  public ServiceResult<HospitalDept> createHospitalDept(HospitalDeptDto currentHospitalDept, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<HospitalDept> result = null;
    final HospitalDept newHospitalDept = new HospitalDept();
    BeanCopier.copy(currentHospitalDept, newHospitalDept);
    newHospitalDept.setHospCode(currentHospitalDept.getHospital().getHospCode());
    newHospitalDept.setIsDeleted(CommonConstants.ACTIVE);
    newHospitalDept.setCheckedAt(checkedDate);
    newHospitalDept.setCheckedBy(checkedBy);
    newHospitalDept.setCreatedAt(LocalDateTime.now());
    newHospitalDept.setCreatedBy(this.userCredential.getUserId());
    this.hospitalDeptRepo.saveAndFlush(newHospitalDept);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newHospitalDept, message);
    return result;
  }

  public ServiceResult<HospitalDept> deleteHospitalDept(HospitalDept selectedHospitalDept) {
    ServiceResult<HospitalDept> result = null;
    final HospitalDept deleteHospitalDept = this.hospitalDeptRepo.findBy(selectedHospitalDept.getDeptId());
    deleteHospitalDept.setIsDeleted(CommonConstants.DELETE);
    this.hospitalDeptRepo.saveAndFlush(deleteHospitalDept);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  public List<HospitalDept> getListHospitalDept(String hospCode) {

    final ArrowQuery<HospitalDept> query = new ArrowQuery<>(super.em);
    query.select("e").from("HospitalDept e ");
    query.where(" e.isDeleted = 0");
    if (StringUtils.isNotEmpty(hospCode)) {
      query.where(" e.hospCode = ?", hospCode);
    }

    query.addFilterAndSorterForString("hospDeptCode", "e.deptCode");
    query.addFilterAndSorterForString("hospDeptName", "e.name");
    query.addFilterAndSorterForString("hospName", "e.hospital.name");
    query.orderBy("hospCode");

    return query.getResultList();
  }

  public ServiceResult<HospitalDeptDto> prepareEditHospitalDept(HospitalDept hospital) {
    ServiceResult<HospitalDeptDto> result = null;
    HospitalDept getHospitalDept = new HospitalDept();
    if (hospital.isSelected()) {
      getHospitalDept = this.hospitalDeptRepo.findBy(hospital.getDeptId());
      final HospitalDeptDto hospitalDTO = new HospitalDeptDto();
      BeanCopier.copy(getHospitalDept, hospitalDTO);
      result = new ServiceResult<HospitalDeptDto>(true, hospitalDTO);
    }
    return result;
  }

  public ServiceResult<HospitalDept> saveHospitalDept(HospitalDeptDto currentHospitalDept) {
    ServiceResult<HospitalDept> result = null;
    if (currentHospitalDept.getDeptId() == 0) {
      final HospitalDeptIdGenerator generator =
          new HospitalDeptIdGenerator(currentHospitalDept.getHospital().getOfficeCode(), LocalDate.now().getYear());
      final String nextHospitalDeptCode = generator.getNext();
      currentHospitalDept.setDeptCode(nextHospitalDeptCode);

      result = this.createHospitalDept(currentHospitalDept, null, null);
    } else {
      final HospitalDept editHospitalDept = this.hospitalDeptRepo.findBy(currentHospitalDept.getDeptId());
      if (editHospitalDept.isPassedApprovalProcess()) {
        editHospitalDept.setIsDeleted(CommonConstants.DELETE);
        this.hospitalDeptRepo.saveAndFlush(editHospitalDept);
        result = this.createHospitalDept(currentHospitalDept, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editHospitalDept.setIsDeleted(CommonConstants.DELETE);
        this.hospitalDeptRepo.saveAndFlush(editHospitalDept);
        result = this.createHospitalDept(currentHospitalDept, null, null);
      }
      // add operation log after edit HospitalDept
      this.logService.addOperationLog(HospitalDept.class.getName(), currentHospitalDept.getDeptId(), result.getData().getDeptId(), "update approve");
    }
    return result;
  }

  public ServiceResult<List<HospitalDept>> getAllDepartmentsByOfficeForRestful(String pOfficeCode) {
    final List<HospitalDept> result = this.hospitalDeptRepo.findAllActiveHospitalDeptInOneOfficce(pOfficeCode);
    return new ServiceResult<List<HospitalDept>>(true, result);
  }

  public ServiceResult<List<HospitalDept>> findDepartmentInOffice(String pOfficeCode, String pQuery) {
    final List<HospitalDept> result = this.hospitalDeptRepo.findDepartmentByOfficeCodeAndQuery(pOfficeCode, pQuery);
    return new ServiceResult<List<HospitalDept>>(true, result);
  }
}
