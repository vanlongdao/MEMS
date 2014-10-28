package arrow.mems.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.AlertByCountDto;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.dto.MdevChecklistDto;
import arrow.mems.persistence.dto.MdevChecklistItemDto;
import arrow.mems.persistence.dto.ScheduleDto;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class MasterDeviceChecklistManageBean extends AbstractBean {
  @Inject
  private DevicesManagementBean deviceBean;

  @Inject
  private DevicesManagementService devicesManagerService;

  @Inject
  private ScheduleBean scheduleBean;


  // Variable to store temporary checklist is deleted or add new
  private List<MdevChecklistDto> deletedChecklistTemp;
  private List<MdevChecklistDto> oldStateChecklistTemp;

  private MDeviceDto currentDevice;

  public MDeviceDto getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(MDeviceDto pCurrentDevice) {
    this.reset();
    this.currentDevice = pCurrentDevice;
    this.initChecklist();
  }

  private void reset() {
    this.currentChecklist = null;
    this.currentChecklistItem = null;
    if (this.checklistItems != null) {
      this.checklistItems.clear();
    }
    if (this.selectedChecklist != null) {
      this.selectedChecklist.setSelected(false);
      this.selectedChecklist = null;
    }
  }

  public void discardChangeChecklist() {
    final Iterator<MdevChecklistDto> checklistIterator = this.getChecklists().iterator();
    final List<String> checklistName = new ArrayList<String>();
    while (checklistIterator.hasNext()) {
      final MdevChecklistDto checklist = checklistIterator.next();
      if (checklist.isNew() && !this.validateBeforeDeleteChecklist(checklist)) {
        checklistName.add(checklist.getName());
      }
    }

    if (CollectionUtils.isNotEmpty(checklistName)) {
      MDMMessages.MDM00022(StringUtils.join(checklistName.toArray(), ",")).show();
      return;
    }
    this.reset();
    this.initChecklist();
    this.closeConfirmDlg();
  }

  private MdevChecklistItemDto currentChecklistItem;

  public MdevChecklistItemDto getCurrentChecklistItem() {
    return this.currentChecklistItem;
  }

  public void setCurrentChecklistItem(MdevChecklistItemDto pCurrentChecklistItem) {
    this.currentChecklistItem = pCurrentChecklistItem;
  }

  private MdevChecklistDto currentChecklist;

  public MdevChecklistDto getCurrentChecklist() {
    return this.currentChecklist;
  }

  public void setCurrentChecklist(MdevChecklistDto pCurrentChecklist) {
    this.currentChecklist = pCurrentChecklist;
  }

  public void setTabIndex() {
    this.deviceBean.setTabIndex(4);
  }

  // Confirm dialog
  private boolean renderConfirmDlg;

  public boolean isRenderConfirmDlg() {
    return this.renderConfirmDlg;
  }

  public void setRenderConfirmDlg(boolean pRenderConfirmDlg) {
    this.renderConfirmDlg = pRenderConfirmDlg;
  }

  private List<MdevChecklistDto> originChecklist;

  public boolean showConfirmDiscardChange() {
    if (this.oldStateChecklistTemp == null) {
      if (this.checklists.size() > 0) {
        this.renderConfirmDlg = true;
        return true;
      } else {
        this.renderConfirmDlg = false;
        return false;
      }
    }
    if (this.checklists.size() != this.oldStateChecklistTemp.size()) {
      this.renderConfirmDlg = true;
      return true;
    }
    if (this.originChecklist == null) {
      this.originChecklist = new ArrayList<MdevChecklistDto>();
      this.originChecklist = this.getMdevChecklist();
    }
    final Map<String, Integer> mapOldStateCklist = new HashMap<String, Integer>();
    final Map<String, MdevChecklistDto> mapOldChecklistData = new HashMap<String, MdevChecklistDto>();
    final Map<String, MdevChecklistItem> mapOldCklistItemData = new HashMap<String, MdevChecklistItem>();
    final Map<String, Integer> mapOldStateCklistItem = new HashMap<String, Integer>();
    for (final MdevChecklistDto oldCklist : this.originChecklist) {
      mapOldChecklistData.put(oldCklist.getCklistCode(), oldCklist);
      mapOldStateCklist.put(oldCklist.getCklistCode(), oldCklist.getCklistId());
      if (oldCklist.getChecklistItems() != null) {
        for (final MdevChecklistItem oldCklistItem : oldCklist.getChecklistItems()) {
          mapOldStateCklistItem.put(oldCklistItem.getCkiCode(), oldCklistItem.getCkiId());
          mapOldCklistItemData.put(oldCklistItem.getCkiCode(), oldCklistItem);
        }
      }
    }
    for (final MdevChecklistDto ckDto : this.checklists) {
      if (ArrowStrUtils.isEmpty(ckDto.getCklistCode())) {
        this.renderConfirmDlg = true;
        return true;
      } else if (ckDto.getChecklistItems() != null) {
        for (final MdevChecklistItem cki : ckDto.getChecklistItems()) {
          if (!mapOldStateCklistItem.containsKey(cki.getCkiCode())) {
            this.renderConfirmDlg = true;
            return true;
          }
          // Check if change item
          final MdevChecklistItem oldCklistItem = mapOldCklistItemData.get(cki.getCkiCode());
          if (!this.compareBeanMdevChecklistItem(oldCklistItem, cki)) {
            this.renderConfirmDlg = true;
            return true;
          }
        }
      }
      // Check if change item
      final MdevChecklistDto oldCklist = mapOldChecklistData.get(ckDto.getCklistCode());
      if (!this.compareBeanMdevChecklist(oldCklist, ckDto)) {
        this.renderConfirmDlg = true;
        return true;
      }
    }
    this.renderConfirmDlg = false;
    return false;
  }

  /**
   * Compare bean daily report dto.
   *
   * @param bean1 the bean1
   * @param bean2 the bean2
   * @return true, if them equal , and false if not equal
   */
  public boolean compareBeanMdevChecklist(final MdevChecklistDto bean1, final MdevChecklistDto bean2) {
    final ComparatorChain comparatorChain = new ComparatorChain();

    // comparatorChain.addComparator(new BeanComparator("dai_work_date"));
    if (ArrowStrUtils.isNotEmpty(bean1.getCklistCode()) && ArrowStrUtils.isNotEmpty(bean2.getCklistCode())) {
      comparatorChain.addComparator(new BeanComparator<Object>("cklistCode"));
    } else
      return false;
    if (ArrowStrUtils.isNotEmpty(bean1.getName()) && ArrowStrUtils.isNotEmpty(bean2.getName())) {
      comparatorChain.addComparator(new BeanComparator<Object>("name"));
    } else
      return false;

    final int result = comparatorChain.compare(bean1, bean2);
    if (result == 0)
      return true;
    return false;
  }

  public boolean compareBeanMdevChecklistItem(final MdevChecklistItem bean1, final MdevChecklistItem bean2) {
    final ComparatorChain comparatorChain = new ComparatorChain();

    // comparatorChain.addComparator(new BeanComparator("dai_work_date"));
    if (ArrowStrUtils.isNotEmpty(bean1.getDescription()) && ArrowStrUtils.isNotEmpty(bean2.getDescription())) {
      comparatorChain.addComparator(new BeanComparator<Object>("description"));
    } else
      return false;

    final int result = comparatorChain.compare(bean1, bean2);
    if (result == 0)
      return true;
    return false;
  }

  public void closeConfirmDlg() {
    this.renderConfirmDlg = false;
  }

  private void initChecklist() {
    if (this.deviceBean.getCurrentDevice().getPersisted()) {
      List<MdevChecklistDto> newChecklist = new ArrayList<MdevChecklistDto>();
      newChecklist = this.getMdevChecklist();
      this.checklists = newChecklist;
      if (this.checklists != null) {
        this.oldStateChecklistTemp = new ArrayList<MdevChecklistDto>();
        this.oldStateChecklistTemp.addAll(this.checklists);
      }
    } else {
      this.checklists = new ArrayList<MdevChecklistDto>();
    }
  }

  public List<MdevChecklistItemDto> setDefaultChecklistItemDto(MdevChecklist checklist) {
    final List<MdevChecklistItemDto> newChecklistItemDto = new ArrayList<MdevChecklistItemDto>();
    final List<MdevChecklistItem> currentChecklistItem =
        this.devicesManagerService.getMdevChecklistItemByCklistCode(checklist.getCklistCode(), CommonConstants.ACTIVE);
    if (currentChecklistItem != null) {
      for (final MdevChecklistItem mdevChecklistItem : currentChecklistItem) {
        final MdevChecklistItemDto tempChecklist = new MdevChecklistItemDto();
        BeanCopier.copy(mdevChecklistItem, tempChecklist);
        newChecklistItemDto.add(tempChecklist);
      }
    }
    return newChecklistItemDto;
  }

  public List<MdevChecklistDto> getMdevChecklist() {
    final List<MdevChecklist> existChecklist =
        this.devicesManagerService.getMdevChecklistByMdevcode(this.deviceBean.getCurrentDevice().getMdevCode(), CommonConstants.ACTIVE);
    final List<MdevChecklistDto> newChecklist = new ArrayList<MdevChecklistDto>();
    for (final MdevChecklist mdevChecklist : existChecklist) {
      final MdevChecklistDto tempChecklist = new MdevChecklistDto();
      BeanCopier.copy(mdevChecklist, tempChecklist);

      // copy createdBy, createdAt
      tempChecklist.setCreatedAt(mdevChecklist.getCreatedAt());
      tempChecklist.setCreatedBy(mdevChecklist.getCreatedBy());
      tempChecklist.setChecklistItems(this.setDefaultChecklistItemDto(mdevChecklist));
      newChecklist.add(tempChecklist);
    }
    return newChecklist;
  }

  public void resetAll() {
    this.initChecklist();
  }

  private List<MdevChecklistDto> checklists;

  public List<MdevChecklistDto> getChecklists() {
    if (this.checklists == null) {
      this.initChecklist();
    }
    return this.checklists;
  }

  public void setChecklists(List<MdevChecklistDto> pChecklists) {
    this.checklists = pChecklists;
  }

  public void clickCreateNewChecklist() {
    this.checklistItems = new ArrayList<MdevChecklistItemDto>();
    this.currentChecklist = this.createNewChecklist();
  }

  private MdevChecklistItemDto createNewChecklistItem() {
    final MdevChecklistItemDto checklistItem = new MdevChecklistItemDto();
    return checklistItem;
  }

  private MdevChecklistDto createNewChecklist() {
    final MdevChecklistDto newChecklist = new MdevChecklistDto();
    if (this.selectedChecklist != null) {
      this.selectedChecklist.setSelected(false);
      this.selectedChecklist = null;
    }
    return newChecklist;
  }

  public void saveChecklistItem() {
    if (!this.getChecklistItems().contains(this.currentChecklistItem)) {
      this.getChecklistItems().add(this.currentChecklistItem);
    }
    this.currentChecklistItem = null;
  }

  public void saveChecklist() {
    // Copy checklist items back to checklist.
    this.currentChecklist.getChecklistItems().clear();
    if (this.getChecklistItems() != null) {
      for (final MdevChecklistItemDto item : this.getChecklistItems()) {
        MdevChecklistItemDto copyItem = new MdevChecklistItemDto();
        copyItem = item;
        this.currentChecklist.getChecklistItems().add(copyItem);
      }
    }

    if (!this.checklists.contains(this.currentChecklist)) {
      this.currentChecklist.setCklistId(Integer.parseInt(ArrowStrUtils.randomString(9))); // 20140927
      this.checklists.add(this.currentChecklist);
    }
    this.reset();
  }

  public void editChecklistItem(MdevChecklistItemDto item) {
    this.currentChecklistItem = item;
  }

  private MdevChecklistDto selectedChecklist;

  public MdevChecklistDto getSelectedChecklist() {
    return this.selectedChecklist;
  }

  public void setSelectedChecklist(MdevChecklistDto pSelectedChecklist) {
    this.selectedChecklist = pSelectedChecklist;
  }

  public void toggleSelectChecklist(MdevChecklistDto selectedChecklist) {
    if (selectedChecklist.isSelected()) {
      if (this.selectedChecklist != null) {
        this.selectedChecklist.setSelected(false);
      }
      this.selectedChecklist = selectedChecklist;
      this.editSelectedChecklist();
    } else {
      this.selectedChecklist = null;
      this.currentChecklist = null;
    }
  }

  private List<MdevChecklistItemDto> checklistItems = new ArrayList<MdevChecklistItemDto>();

  public List<MdevChecklistItemDto> getChecklistItems() {
    return this.checklistItems;
  }

  public void setChecklistItems(List<MdevChecklistItemDto> pChecklistItems) {
    this.checklistItems = pChecklistItems;
  }

  public void editSelectedChecklist() {
    this.currentChecklist = this.selectedChecklist;
    // copy checklist items to checklistItems
    this.checklistItems = new ArrayList<MdevChecklistItemDto>();
    for (final MdevChecklistItemDto item : this.currentChecklist.getChecklistItems()) {
      final MdevChecklistItemDto copyItem = new MdevChecklistItemDto();
      BeanCopier.copy(item, copyItem);
      this.checklistItems.add(copyItem);
    }
  }

  private boolean validateBeforeDeleteChecklist(MdevChecklistDto selectedChecklist) {
    if (this.scheduleBean.getListMasterSchedule() != null) {
      for (final ScheduleDto scheduleDto : this.scheduleBean.getListMasterSchedule()) {
        if (scheduleDto.getMdevChecklist() != null)
          if (selectedChecklist.getCklistId() == scheduleDto.getMdevChecklist().getCklistId())
            return false;
      }
    }
    if (this.scheduleBean.getListMasterScheduleAlert() != null) {
      for (final AlertByCountDto alertDto : this.scheduleBean.getListMasterScheduleAlert()) {
        if (alertDto.getMdevChecklist() != null)
          if (selectedChecklist.getCklistId() == alertDto.getMdevChecklist().getCklistId())
            return false;
      }
    }
    return true;
  }

  public void deleteSelectedChecklist() {
    if (!this.validateBeforeDeleteChecklist(this.selectedChecklist)) {
      MDMMessages.MDM00017().show();
      return;
    }

    this.checklists.remove(this.selectedChecklist);
    if (this.oldStateChecklistTemp != null) {
      if (this.oldStateChecklistTemp.contains(this.selectedChecklist)) {
        if (this.deletedChecklistTemp == null) {
          this.deletedChecklistTemp = new ArrayList<MdevChecklistDto>();
        }
        this.deletedChecklistTemp.add(this.selectedChecklist);
      }
    }
    this.reset();
  }

  public void addNewChecklistItem() {
    this.currentChecklistItem = this.createNewChecklistItem();
  }

  public void deleteChecklistItem() {
    this.getChecklistItems().remove(this.currentChecklistItem);
    this.currentChecklistItem = null;
  }

  public void apply() {
    // set checklist into current device, and poss it back to Device master screen.
    this.deviceBean.getCurrentDevice().setChecklists(this.checklists);
    this.deviceBean.setListChecklist(this.checklists);
  }

  public List<MdevChecklistDto> getDeletedChecklistTemp() {
    return this.deletedChecklistTemp;
  }

  public void setDeletedChecklistTemp(List<MdevChecklistDto> pDeletedChecklistTemp) {
    this.deletedChecklistTemp = pDeletedChecklistTemp;
  }

  public List<MdevChecklistDto> getOldStateChecklistTemp() {
    return this.oldStateChecklistTemp;
  }

  public void setOldStateChecklistTemp(List<MdevChecklistDto> pOldStateChecklistTemp) {
    this.oldStateChecklistTemp = pOldStateChecklistTemp;
  }

}
