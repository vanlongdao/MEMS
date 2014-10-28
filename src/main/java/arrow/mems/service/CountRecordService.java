/**
 *
 */
package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.CountRecordIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.CountRecordDto;
import arrow.mems.persistence.entity.CountRecord;
import arrow.mems.persistence.repository.CountRecordRepository;

public class CountRecordService extends AbstractService {
  @Inject
  CountRecordRepository countRecordRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  CountryService countryService;
  @Inject
  UserService userService;
  @Inject
  OperationLogService logService;

  public CountRecord findCountRecordByDeviceCode(String deviceCode) {
    return this.countRecordRepo.getCountRecordByDeviceCode(deviceCode).getAnyResult();
  }

  public ServiceResult<CountRecord> saveCountRecord(CountRecordDto currentCountRecord) {
    ServiceResult<CountRecord> result = null;
    final Integer userIdChecked = null;
    final LocalDateTime checkedAt = null;

    if (currentCountRecord.getCountRecCode() == null) {
      // Generate new countRecord code
      final CountRecordIdGenerator generator =
          new CountRecordIdGenerator(this.userCredential.getUserInfo().getOfficeCode().toString(), LocalDate.now().getYear());
      final String countRecordCode = generator.getNext();
      currentCountRecord.setCountRecCode(countRecordCode);
      result = this.saveNewCountRecord(currentCountRecord, userIdChecked, checkedAt);
    }
    return result;
  }

  // Create new countRecord
  public ServiceResult<CountRecord> saveNewCountRecord(CountRecord currentCountRecord, Integer checkedBy, LocalDateTime checkedAt) {
    final List<Message> messages = new ArrayList<>();
    final CountRecord newCountRecord = new CountRecord();
    BeanCopier.copy(currentCountRecord, newCountRecord);

    newCountRecord.setIsDeleted(CommonConstants.ACTIVE);
    newCountRecord.setCheckedBy(checkedBy);
    newCountRecord.setCheckedAt(checkedAt);
    this.countRecordRepo.saveAndFlush(newCountRecord);

    messages.add(MMIMessages.MMI00006());
    return new ServiceResult<CountRecord>(true, newCountRecord, messages);

  }


}
