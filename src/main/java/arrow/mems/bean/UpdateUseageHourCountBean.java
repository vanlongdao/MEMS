/**
 *
 */
package arrow.mems.bean;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.dto.CountRecordDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.CountRecord;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.repository.CountRecordRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.service.CountRecordService;


@Named
@ViewScoped
public class UpdateUseageHourCountBean extends AbstractApprovalBean {
  @Inject
  CountRecordService countRecordService;

  @Inject
  UserCredential userCredential;
  @Inject
  CountRecordRepository countRecordRepo;
  @Inject
  MdevChecklistRepository mdeChecklistRepo;
  @Inject
  DeviceRepository deviceRepo;

  private String deviceCode;
  private CountRecordDto currentCountRecord;
  private String comment;
  private List<MdevChecklist> listAllActiveMdevChecklist;
  private MdevChecklist mdevChecklist;
  private String serialNo;

  public void addCountRecord() {
    this.currentCountRecord = new CountRecordDto();
  }


  public void saveCountRecord() {
    if (this.mdevChecklist != null) {
      this.currentCountRecord.setCklistCode(this.mdevChecklist.getCklistCode());
    }
    this.currentCountRecord.setDevCode(this.deviceCode);
    final ServiceResult<CountRecord> result = this.countRecordService.saveCountRecord(this.currentCountRecord);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    if (StringUtils.isEmpty(this.deviceCode)) {
      this.currentCountRecord = new CountRecordDto();
    } else {
      final Device device = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
      this.currentCountRecord = new CountRecordDto();
      if (device != null) {
        this.currentCountRecord.setDevCode(device.getDevCode());
        this.serialNo = device.getSerialNo();
      }
    }
  }

  public List<MdevChecklist> filterMdevChecklist(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveMdevChecklist();

    return CollectionUtils.filter(this.getListAllActiveMdevChecklist(), arg0 -> {
      final MdevChecklist item = (MdevChecklist) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getMdevCode()).contains(query);
    });
  }

  public List<MdevChecklist> getListAllActiveMdevChecklist() {
    final Device device = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
    final String mDevicCode = device.getMdevCode();
    if (this.listAllActiveMdevChecklist == null) {
      this.listAllActiveMdevChecklist = this.mdeChecklistRepo.findActiveMevChecklistByMdevcode(mDevicCode).getResultList();
    }
    return this.listAllActiveMdevChecklist;
  }

  public void closeCountRecord(ActionEvent ae) {
    // reset data
    this.currentCountRecord = new CountRecordDto();

    // reset stage
    this.resetStage(ae);
  }

  public void cleanStage() {
    this.currentCountRecord = new CountRecordDto();
  }

  public String getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(String pSerialNo) {
    this.serialNo = pSerialNo;
  }

  @NotNull(message = "{" + MessageCode.MPM00019 + "}")
  public MdevChecklist getMdevChecklist() {
    return this.mdevChecklist;
  }

  public void setMdevChecklist(MdevChecklist pMdevChecklist) {
    this.mdevChecklist = pMdevChecklist;
  }

  public void requestApprove() {}

  public CountRecordDto getCurrentCountRecord() {
    return this.currentCountRecord;
  }

  public void setCurrentCountRecord(CountRecordDto pCurrentCountRecord) {
    this.currentCountRecord = pCurrentCountRecord;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  @Override
  public Object getEntityId() {
    return this.currentCountRecord.getPk();
  }

  @Override
  public String getDataType() {
    return MemsDataType.COUNT_RECORD;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    // TODO Must Implement
    return true;
  }


  @Override
  public String getItemLabel() {
    // TODO Auto-generated method stub
    return null;
  }

}
