package arrow.mems.service;

import java.time.LocalDate;
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
import arrow.mems.generator.HospitalIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.HospitalDto;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class HospitalManagementService extends AbstractService {
  @Inject
  HospitalRepository hospitalRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogService logService;
  @Inject
  HospitalDeptRepository hospDeptRepo;


  public ServiceResult<Hospital> createHospital(HospitalDto currentHospital, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<Hospital> result = null;
    final Hospital newHospital = new Hospital();
    BeanCopier.copy(currentHospital, newHospital);
    if (currentHospital.getCorporate() != null) {
      newHospital.setCorpCode(currentHospital.getCorporate().getCorpCode());
    }
    newHospital.setOfficeCode(currentHospital.getOffice().getOfficeCode());
    newHospital.setIsDeleted(CommonConstants.ACTIVE);
    newHospital.setCheckedAt(checkedDate);
    newHospital.setCheckedBy(checkedBy);
    newHospital.setCreatedAt(LocalDateTime.now());
    newHospital.setCreatedBy(this.userCredential.getUserId());
    this.hospitalRepo.saveAndFlush(newHospital);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newHospital, message);
    return result;
  }

  public ServiceResult<Hospital> deleteHospital(Hospital selectedHospital) {
    ServiceResult<Hospital> result = null;
    final Hospital deleteHospital = this.hospitalRepo.findBy(selectedHospital.getHospId());
    deleteHospital.setIsDeleted(CommonConstants.DELETE);
    this.hospitalRepo.saveAndFlush(deleteHospital);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  public List<Hospital> getListHospital() {

    final ArrowQuery<Hospital> query = new ArrowQuery<>(super.em);
    query.select("e").from("Hospital e ");
    query.where(" e.isDeleted = 0");

    query.addFilterAndSorterForString("hospCode", "e.hospCode");
    query.addFilterAndSorterForString("hospName", "e.name");
    query.addFilterAndSorterForString("corporate", "e.corporate.name");


    query.orderBy("hospCode");

    return query.getResultList();
  }

  public ServiceResult<HospitalDto> prepareEditHospital(Hospital hospital) {
    ServiceResult<HospitalDto> result = null;
    Hospital getHospital = new Hospital();
    if (hospital.isSelected()) {
      getHospital = this.hospitalRepo.findBy(hospital.getHospId());
      final HospitalDto hospitalDTO = new HospitalDto();
      BeanCopier.copy(getHospital, hospitalDTO);
      result = new ServiceResult<HospitalDto>(true, hospitalDTO);
    }
    return result;
  }

  public ServiceResult<Hospital> saveHospital(HospitalDto currentHospital) {
    ServiceResult<Hospital> result = null;
    if (currentHospital.getHospId() == 0) {
      final HospitalIdGenerator generator = new HospitalIdGenerator(currentHospital.getOffice().getOfficeCode(), LocalDate.now().getYear());
      final String nextHospitalCode = generator.getNext();
      currentHospital.setHospCode(nextHospitalCode);

      result = this.createHospital(currentHospital, null, null);
    } else {
      final Hospital editHospital = this.hospitalRepo.findBy(currentHospital.getHospId());
      if (editHospital.isPassedApprovalProcess()) {
        editHospital.setIsDeleted(CommonConstants.DELETE);
        this.hospitalRepo.saveAndFlush(editHospital);
        result = this.createHospital(currentHospital, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editHospital.setIsDeleted(1);
        this.hospitalRepo.saveAndFlush(editHospital);
        result = this.createHospital(currentHospital, null, null);
      }
      // add operation log after edit Hospital
      this.logService.addOperationLog(Hospital.class.getName(), currentHospital.getHospId(), result.getData().getHospId(), "update approve");
    }
    return result;
  }

  public List<HospitalDept> getListHosptialDeptByHospCode(String hospCode) {
    return this.hospDeptRepo.findHospitalDeptByHospCode(hospCode).getResultList();
  }
}
