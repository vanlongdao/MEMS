/**
 *
 */
package arrow.mems.service;

import java.time.LocalDateTime;

import javax.inject.Inject;

import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.OperationLog;
import arrow.mems.persistence.repository.OperationLogRepository;

/**
 * @author tainguyen
 *
 */
public class OperationLogService extends AbstractService {
  @Inject
  UserCredential userCredential;
  @Inject
  OperationLogRepository logRepository;

  public void addOperationLog(String targetTable, int oldId, int newId, String desc) {
    final OperationLog log = new OperationLog();
    log.setTargetTable(targetTable);
    log.setApprovedBy(this.userCredential.getUserId());
    log.setOperatedBy(this.userCredential.getUserId());
    log.setOperatedAt(LocalDateTime.now());
    log.setDescription(desc);
    log.setOldId(oldId);
    log.setNewId(newId);
    this.logRepository.saveAndFlush(log);
  }
}
