package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.mems.persistence.entity.OperationLog;
import arrow.mems.persistence.repository.OperationLogRepository;

public class OperationLogServiceTest extends Arquillian {

  @Inject
  OperationLogService logService;
  @Inject
  OperationLogRepository logRepository;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testAddOperationLog() throws Exception {
    List<OperationLog> log = this.logRepository.findAll();
    Assert.assertNotNull(log);
    Assert.assertEquals(log.size(), 8);
    this.logService.addOperationLog("arrow.mems.persistence.entity.Address", 1, 3, "update address");
    log = this.logRepository.findAll();
    Assert.assertNotNull(log);
    Assert.assertEquals(log.size(), 9);
  }
}
