/**
 *
 */
package arrow.mems.bean;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.ServiceResult;
import arrow.framework.ui.ScreenMonitor;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MAPMessages;
import arrow.mems.messages.MMIXMessages;
import arrow.mems.persistence.dto.AddressDto;
import arrow.mems.persistence.dto.CorporateDto;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.dto.HospitalDeptDto;
import arrow.mems.persistence.dto.HospitalDto;
import arrow.mems.persistence.dto.HumanResourceDto;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.dto.OfficeDto;
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.dto.PresetPhrasesDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.ApprovalConfig;
import arrow.mems.persistence.entity.ApprovalLevel;
import arrow.mems.persistence.entity.ApprovalLevelSupervisor;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.AddressMapped_;
import arrow.mems.persistence.mapped.CorporateMapped_;
import arrow.mems.persistence.mapped.DeviceMapped_;
import arrow.mems.persistence.mapped.HospitalDeptMapped_;
import arrow.mems.persistence.mapped.HospitalMapped_;
import arrow.mems.persistence.mapped.HumanResourceMapped_;
import arrow.mems.persistence.mapped.MDeviceMapped_;
import arrow.mems.persistence.mapped.OfficeMapped_;
import arrow.mems.persistence.mapped.PersonMapped_;
import arrow.mems.persistence.mapped.PresetPhrasesMapped_;
import arrow.mems.service.ApprovalDataService;
import arrow.mems.service.ApprovalService;
import arrow.mems.ui.ScreenCodes;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class ApproveManagementBean extends AbstractBean {

  @Inject
  private ApprovalService approvalService;
  @Inject
  private UserCredential userCredential;
  @Inject
  private ApprovalDataService approvalDataService;
  @Inject
  private ScreenMonitor screenMonitor;

  // Inject all beans for open detail approve
  @Inject
  private DevicesManagementBean masterDeviceManageBean;
  @Inject
  private DeviceManagementBean deviceManageBean;
  @Inject
  private AddressManagementBean addressManageBean;
  @Inject
  private PersonManagementBean personManageBean;
  @Inject
  private OfficeManagementBean officeManageBean;
  @Inject
  private HospitalManagementBean hospitalManageBean;
  @Inject
  private HospitalDeptManagementBean hospitalDeptManageBean;
  @Inject
  private CorporateManagementBean corporateManageBean;
  @Inject
  private HumanResourceManagementBean humanResourceManageBean;
  @Inject
  private ManagePresetPhrasesBean presetPhrasesBean;

  private ApprovalConfig currentApproval;
  private List<ApprovalLevel> listApprovalLevels;
  private List<ApprovalConfig> listApprovalConfigs;
  private List<ApprovalLevelSupervisor> listSupervisors;
  private List<ApprovalSummary> listApprovalSummaries;
  private Users selectedSupervisor;
  private boolean isAddSupervisor;
  private ApprovalLevel currentLevel;

  // For screen Summary
  private HashMap<Integer, String> valueOfApprovalType;


  public void toggleSelection(ApprovalConfig approval) {
    this.approvalService.changeStatusOfApproval(approval);
  }

  public List<ApprovalLevel> getListApprovalLevels() {
    return this.listApprovalLevels = this.approvalService.listAllApprovalLevels(this.getCurrentApproval().getConfigId());
  }

  public void setListApprovalLevels(List<ApprovalLevel> listApprovalLevels) {
    this.listApprovalLevels = listApprovalLevels;
  }

  public List<ApprovalLevelSupervisor> getListSupervisors(ApprovalLevel level) {
    this.listSupervisors = this.approvalService.getAllSupervisorByLevelId(level.getLevelId());
    if (this.isAddSupervisor && (this.currentLevel.getLevelId() == level.getLevelId())) {
      this.listSupervisors.add(new ApprovalLevelSupervisor());
    }
    return this.listSupervisors;
  }

  public void addSupervisor(ApprovalLevel level) {
    this.currentLevel = level;
    this.isAddSupervisor = true;
  }

  public List<ApprovalConfig> getListApprovalConfigs() {
    if (this.listApprovalConfigs == null) {
      this.listApprovalConfigs = this.approvalService.listAllApprovalConfig();
    }
    return this.listApprovalConfigs;
  }

  public void setListApprovalConfigs(List<ApprovalConfig> pListApprovalConfigs) {
    this.listApprovalConfigs = pListApprovalConfigs;
  }

  public String getDataTypeName(ApprovalConfig approval) {
    return MemsDataType.memsDataType().get(approval.getDataType());
  }

  public ApprovalConfig getCurrentApproval() {
    return this.currentApproval;
  }

  public void setCurrentApproval(ApprovalConfig pCurrentApproval) {
    this.currentApproval = pCurrentApproval;
  }

  public boolean checkLatestLevelHaveSupervisor() {
    final int latestLevelId = this.listApprovalLevels.get(this.listApprovalLevels.size() - 1).getLevelId();
    final List<ApprovalLevelSupervisor> supervisorOfLevel = this.approvalService.getAllSupervisorByLevelId(latestLevelId);
    if (supervisorOfLevel.size() != 0)
      return true;
    return false;
  }

  public void addLevel() {
    if ((this.listApprovalLevels.size() == 0) || this.checkLatestLevelHaveSupervisor()) {
      final ServiceResult<ApprovalLevel> result = this.approvalService.addNewLevel(this.currentApproval, this.listApprovalLevels.size() + 1);
      result.showMessages();
    }
  }

  public Users getSupervisor(ApprovalLevelSupervisor supervisor) {
    return this.approvalService.findActiveUserById(supervisor.getSupervisorId());
  }

  public Users getSelectedSupervisor() {
    return this.selectedSupervisor;
  }

  public void setSelectedSupervisor(Users pSelectedSupervisor) {
    this.selectedSupervisor = pSelectedSupervisor;
  }

  public void addNewSupervisor(ApprovalLevel level) {
    final ServiceResult<ApprovalLevelSupervisor> result =
        this.approvalService.addNewSupervisor(level.getLevelId(), this.selectedSupervisor.getUserId());
    result.showMessages();
    if (result.isSuccess()) {
      this.currentLevel = null;
      this.isAddSupervisor = false;
      this.selectedSupervisor = null;
    }
  }

  public void deleteSupervisor(ApprovalLevelSupervisor supervisor) {
    // TODO: Have to check if there are pending approval data of current level.
    final ServiceResult<ApprovalLevelSupervisor> result = this.approvalService.deleteSupervisor(supervisor);
    if (result.isSuccess()) {
      MAPMessages.MAP00004().show();
    }
  }


  public void deleteLevel(ApprovalLevel level) {
    final ServiceResult<ApprovalLevel> result = this.approvalService.deleteLevel(level);
    result.showMessages();
  }

  /*
   * 2014-10-09 :
   * 
   * @author : vanlongdao
   */

  public void approveData() {
    final ServiceResult<ApprovalSummary> result = this.approvalService.approveData(this.listApprovalSummaries, this.userCredential.getUserId());
    if (result.isSuccess()) {
      this.listApprovalSummaries = this.approvalService.getApprovalSummaryBySuppervisor(this.userCredential.getUserId());
      MMIXMessages.MMIX00001().show();
    } else {
      MMIXMessages.MMIX00002().show();
    }
  }

  public ServiceResult<ApprovalSummary> requestApproval(String dataType, String itemCode, int requestor, String itemLabel) {
    return this.approvalService.requestApproval(dataType, itemCode, requestor, itemLabel);
  }

  public List<ApprovalSummary> getListApprovalSummaries() {
    if (this.listApprovalSummaries == null) {
      this.listApprovalSummaries = this.approvalService.getApprovalSummaryBySuppervisor(this.userCredential.getUserId());
    }
    return this.listApprovalSummaries;
  }

  public void openApproveDetail(ApprovalSummary selectedApproval) {
    String dataType = "";
    if (selectedApproval.getApprovalLevel() != null) {
      if (selectedApproval.getApprovalLevel().getApprovalConfig() != null) {
        dataType = selectedApproval.getApprovalLevel().getApprovalConfig().getDataType();
      }
    }
    switch (dataType) {
      case MemsDataType.MDEVICE:
        final MDevice currentMDevice = this.approvalDataService.findEntity(MDevice.class, MDeviceMapped_.mdevCode, selectedApproval.getItemCode());
        final MDeviceDto currentMDeviceDto = new MDeviceDto();
        if (currentMDevice != null) {
          this.screenMonitor.switchScreen(ScreenCodes.MASTER_DEVICES_MANAGEMENT_LIST_SEARCH);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentMDevice, currentMDeviceDto);
        this.masterDeviceManageBean.setCurrentDevice(currentMDeviceDto);
        break;
      case MemsDataType.DEVICE:
        final Device currentDevice = this.approvalDataService.findEntity(Device.class, DeviceMapped_.devCode, selectedApproval.getItemCode());
        final DeviceDto currentDeviceDto = new DeviceDto();
        if (currentDevice != null) {
          this.screenMonitor.switchScreen(ScreenCodes.LIST_SEARCH_DEVICE);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentDevice, currentDeviceDto);
        this.deviceManageBean.setCurrentDevice(currentDeviceDto);
        break;
      case MemsDataType.ADDRESS:
        final Address currentAddr = this.approvalDataService.findEntity(Address.class, AddressMapped_.addrCode, selectedApproval.getItemCode());
        final AddressDto currentAddrDto = new AddressDto();
        if (currentAddr != null) {
          this.screenMonitor.switchScreen(ScreenCodes.ADDRESS_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentAddr, currentAddrDto);
        this.addressManageBean.setCurrentAddress(currentAddrDto);
        break;
      case MemsDataType.PERSON:
        final Person currentPerson = this.approvalDataService.findEntity(Person.class, PersonMapped_.psnCode, selectedApproval.getItemCode());
        final PersonDto currentPersonDto = new PersonDto();
        if (currentPerson != null) {
          this.screenMonitor.switchScreen(ScreenCodes.PERSON_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentPerson, currentPersonDto);
        this.personManageBean.setCurrentPerson(currentPersonDto);
        break;
      case MemsDataType.OFFICE:
        final Office currentOffice = this.approvalDataService.findEntity(Office.class, OfficeMapped_.officeCode, selectedApproval.getItemCode());
        final OfficeDto currentOfficeDto = new OfficeDto();
        if (currentOffice != null) {
          this.screenMonitor.switchScreen(ScreenCodes.OFFICE_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentOffice, currentOfficeDto);
        this.officeManageBean.setCurrentOffice(currentOfficeDto);
        break;
      case MemsDataType.HOSPITAL:
        final Hospital currentHospital =
            this.approvalDataService.findEntity(Hospital.class, HospitalMapped_.hospCode, selectedApproval.getItemCode());
        final HospitalDto currentHospitalDto = new HospitalDto();
        if (currentHospital != null) {
          this.screenMonitor.switchScreen(ScreenCodes.HOSPITAL_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentHospital, currentHospitalDto);
        this.hospitalManageBean.setCurrentHospital(currentHospitalDto);
        break;
      case MemsDataType.HOSPITAL_DEPARTMENT:
        final HospitalDept currentHospDept =
            this.approvalDataService.findEntity(HospitalDept.class, HospitalDeptMapped_.deptCode, selectedApproval.getItemCode());
        final HospitalDeptDto currentHospDeptDto = new HospitalDeptDto();
        if (currentHospDept != null) {
          this.screenMonitor.switchScreen(ScreenCodes.HOSPITAL_DEPT_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentHospDept, currentHospDeptDto);
        this.hospitalDeptManageBean.setCurrentHospitalDept(currentHospDeptDto);
        break;
      case MemsDataType.CORPORATE:
        final Corporate currentCorp = this.approvalDataService.findEntity(Corporate.class, CorporateMapped_.corpCode, selectedApproval.getItemCode());
        final CorporateDto currentCorpDto = new CorporateDto();
        if (currentCorp != null) {
          this.screenMonitor.switchScreen(ScreenCodes.CORPORATE_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentCorp, currentCorpDto);
        this.corporateManageBean.setCurrentCorporate(currentCorpDto);
        break;
      case MemsDataType.PRESET_PHRASES_MEANING:
        final PresetPhrases currentPreset =
            this.approvalDataService.findEntity(PresetPhrases.class, PresetPhrasesMapped_.meaningCode, selectedApproval.getItemCode());
        final PresetPhrasesDto currentPresetDto = new PresetPhrasesDto();
        if (currentPreset != null) {
          this.screenMonitor.switchScreen(ScreenCodes.MANAGE_PRESET_PHRASES);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        BeanCopier.copy(currentPreset, currentPreset);
        this.presetPhrasesBean.setCurrentPresetPhrases(currentPresetDto);
        break;
      case MemsDataType.HUMAN_RESOURCE:
        final String keys = selectedApproval.getItemCode();
        final String[] splitKeys = keys.split(";");
        final HumanResource currentHumanResouce =
            this.approvalDataService.findEntityByThreeAttr(HumanResource.class, HumanResourceMapped_.hospCode, HumanResourceMapped_.hospDeptCode,
                HumanResourceMapped_.psnCode, splitKeys[0], splitKeys[1], splitKeys[2]);
        if (currentHumanResouce != null) {
          this.screenMonitor.switchScreen(ScreenCodes.HUMAN_RESOURCE_MANAGEMENT);
        } else {
          MAPMessages.MAP00005().show();
          break;
        }
        final HumanResourceDto currentResourceDto = new HumanResourceDto();
        BeanCopier.copy(currentHumanResouce, currentResourceDto);
        this.humanResourceManageBean.setCurrentHumanResource(currentResourceDto);
        break;
      default:
        System.out.println("Nothing dataType match");
        break;
    }
  }

  public void setListApprovalSummaries(List<ApprovalSummary> pListApprovalSummaries) {
    this.listApprovalSummaries = pListApprovalSummaries;
  }

  public HashMap<Integer, String> getValueOfApprovalType() {
    return this.valueOfApprovalType;
  }

  public void setValueOfApprovalType(HashMap<Integer, String> pValueOfApprovalType) {
    this.valueOfApprovalType = pValueOfApprovalType;
  }

  private int approvalId;


  public int getApprovalId() {
    return this.approvalId;
  }

  public void setApprovalId(int pApprovalId) {
    this.approvalId = pApprovalId;
  }

}
