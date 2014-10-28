package arrow.mems.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.exception.ServiceException;
import arrow.framework.faces.messages.Message;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Users;
import arrow.mems.service.DevicesManagementService;

@Named
@ViewScoped
public class PartListDeviceMasterBean extends AbstractBean {

  // For MDM02_04 : Master partlist of devices master
  private List<MDevice> originalParts;
  private List<MDevice> listOfAddedParts;
  private List<MDevice> listOfAvailableParts;
  private String keyword;
  private MDevice selectedInfoPartslist;
  private MDevice selectedInfoPartslistSearch;

  // Variable to store temporary
  private List<MDevice> listOfDeletingParts;
  private List<MDevice> listOfNewParts;

  @Inject
  private DevicesManagementBean deviceManagerBean;
  @Inject
  private DevicesManagementService devicesManagerService;


  /* Area code of MDM02_04 */

  public void partlistEditBtnAction() {
    this.getInfoOfPartlist();
  }

  public void getInfoOfPartlist() {
    if (this.deviceManagerBean.getCurrentDevice() != null) {
      if (this.listOfAddedParts == null) {
        this.originalParts =
            this.devicesManagerService.getInfoPartlistByMdevcode(this.deviceManagerBean.getCurrentDevice().getMdevCode(), CommonConstants.ACTIVE,
                CommonConstants.ACTIVE);
        if (this.listOfAddedParts == null) {
          this.listOfAddedParts = new ArrayList<MDevice>();
        }
        this.listOfAddedParts.addAll(this.originalParts);
      }
    }
  }

  public void toggleSelectionPartslist(MDevice infoPartslist) {
    if (infoPartslist.isSelected()) {
      if (!this.selectedAddedParts.contains(infoPartslist)) {
        this.selectedAddedParts.add(infoPartslist);
      }
    } else {
      this.selectedAddedParts.remove(infoPartslist);
    }
  }

  private List<MDevice> selectedParts = new ArrayList<MDevice>();

  public List<MDevice> getSelectedParts() {
    return this.selectedParts;
  }

  public void setSelectedParts(List<MDevice> pSelectedParts) {
    this.selectedParts = pSelectedParts;
  }

  public void toggleSelectionInfoPartlistSearch(MDevice infoPartslistSearch) {
    if (infoPartslistSearch.isSelected()) {
      if (!this.selectedParts.contains(infoPartslistSearch)) {
        this.selectedParts.add(infoPartslistSearch);
      }
    } else {
      this.selectedParts.remove(infoPartslistSearch);
    }
  }

  public String getCreatedByNameOfMDevice(MDevice currentDevice) {
    final Users creator = this.devicesManagerService.getNameUserByCreatedBy(currentDevice.getCreatedBy());
    if (creator != null)
      return creator.getName();
    return null;
  }

  public void apply() {
    if (this.listOfAddedParts != null) {
      this.deviceManagerBean.setListPartsList(this.listOfAddedParts);
    }
  }

  private List<MDevice> selectedAddedParts = new ArrayList<MDevice>();

  public List<MDevice> getSelectedAddedParts() {
    return this.selectedAddedParts;
  }

  public void setSelectedAddedParts(List<MDevice> pSelectedAddedParts) {
    this.selectedAddedParts = pSelectedAddedParts;
  }

  public void removePartsFromDevice() {
    final Iterator<MDevice> list = this.selectedAddedParts.iterator();
    while (list.hasNext()) {
      final MDevice selectedPart = list.next();
      this.listOfAddedParts.remove(selectedPart);
      this.addRemovedPartsIntoDeletingParts(selectedPart);
      list.remove();
    }
  }

  public void addRemovedPartsIntoDeletingParts(MDevice deletingPart) {
    // Delete in list temporary
    if (this.listOfDeletingParts == null) {
      this.listOfDeletingParts = new ArrayList<MDevice>();
    }
    if (this.originalParts.contains(deletingPart)) {
      this.listOfDeletingParts.add(deletingPart);
    }
    if ((this.listOfNewParts != null) && this.listOfNewParts.contains(deletingPart)) {
      this.listOfNewParts.remove(deletingPart);
    }
  }

  public void searchAvailableParts() {
    this.listOfAvailableParts = this.devicesManagerService.searchAvailableParts(this.deviceManagerBean.getCurrentDevice(), this.keyword);
  }


  public void addSelectedPartsToDevice() {
    for (final MDevice selectedPart : this.selectedParts) {

      boolean isAdded = false;
      for (final MDevice device : this.listOfAddedParts) {
        if (device.getMdevCode().equalsIgnoreCase(selectedPart.getMdevCode())) {
          MDMMessages.MDM00008(selectedPart.getMdevCode()).show();
          isAdded = true;
          break;
        }
      }
      if (!isAdded) {
        final MDeviceDto newSeletedDevice = new MDeviceDto();
        BeanCopier.copy(selectedPart, newSeletedDevice);
        newSeletedDevice.setSelected(false);
        this.listOfAddedParts.add(newSeletedDevice);
        this.addPartIntoListOfNewPart(newSeletedDevice);
      }
    }
  }

  public void addPartIntoListOfNewPart(MDevice newPart) {
    // New partlist
    if (this.listOfNewParts == null) {
      this.listOfNewParts = new ArrayList<MDevice>();
    }
    if (!this.listOfNewParts.contains(newPart)) {
      this.listOfNewParts.add(newPart);
    }
  }

  public void resetAll() {
    this.originalParts = null;
    this.listOfAddedParts = null;
    this.listOfAvailableParts = null;
    this.keyword = null;
    this.selectedInfoPartslist = null;
    this.selectedInfoPartslistSearch = null;
    this.listOfDeletingParts = null;
    this.listOfNewParts = null;
    this.selectedParts.clear();
    this.selectedAddedParts.clear();
    this.deviceManagerBean.setListPartsList(null);
  }

  public void resetPartlistWhenRegisterNewPartlist() {
    this.resetAll();
    this.resetStateOfGeneralMtDevices();
    this.deviceManagerBean.addNewDeviceAction();
    this.deviceManagerBean.setListChecklist(null);
    this.deviceManagerBean.setListPartsList(null);
  }

  public void registerNewPartlist() {
    try {
      final boolean saveResult = this.deviceManagerBean.saveInfoDevice();
      // Save successfull
      if (saveResult) {
        this.resetPartlistWhenRegisterNewPartlist();
        this.deviceManagerBean.setTabIndex(0);
      } else {
        this.deviceManagerBean.setTabIndex(2);
      }

    } catch (final IOException e) {
      this.log.debug(e.getMessage(), e);
    } catch (final ServiceException e) {
      for (final Message msg : e.getErrors()) {
        msg.show();
      }
    }
  }

  public void cancelConfirmRegisterNewPartlist() {
    if (!this.deviceManagerBean.getCurrentDevice().isPersisted()) {
      MDMMessages.MDM00025().show();
      // this.deviceManagerBean.setTabIndex(2);
      return;
    }
    this.resetPartlistWhenRegisterNewPartlist();
  }

  public void resetStateOfGeneralMtDevices() {
    this.deviceManagerBean.setCurrentDevice(null);
    this.deviceManagerBean.setFile(null);
    this.deviceManagerBean.setSelectedSoldSupplier(null);
    this.deviceManagerBean.setSelectedSoldSupplierContact(null);
    this.deviceManagerBean.setTabIndex(0);
  }

  /* End code area code of MDM02_04 */



  public List<MDevice> getListInfoPartlist() {
    return this.originalParts;
  }

  public void setListInfoPartlist(List<MDevice> pListInfoPartlist) {
    this.originalParts = pListInfoPartlist;
  }

  public List<MDevice> getListInfoPartlistTemp() {
    if (this.listOfAddedParts == null) {
      this.partlistEditBtnAction();
    }
    return this.listOfAddedParts;
  }

  public void setListInfoPartlistTemp(List<MDevice> pListInfoPartlistTemp) {
    this.listOfAddedParts = pListInfoPartlistTemp;
  }

  public List<MDevice> getListSearchInfoPartlist() {
    return this.listOfAvailableParts;
  }

  public void setListSearchInfoPartlist(List<MDevice> pListSearchInfoPartlist) {
    this.listOfAvailableParts = pListSearchInfoPartlist;
  }

  public String getKeyword() {
    return this.keyword;
  }

  public void setKeyword(String pKeyword) {
    this.keyword = pKeyword;
  }

  public MDevice getSelectedInfoPartslist() {
    return this.selectedInfoPartslist;
  }

  public void setSelectedInfoPartslist(MDevice pSelectedInfoPartslist) {
    this.selectedInfoPartslist = pSelectedInfoPartslist;
  }

  public MDevice getSelectedInfoPartslistSearch() {
    return this.selectedInfoPartslistSearch;
  }

  public void setSelectedInfoPartslistSearch(MDevice pSelectedInfoPartslistSearch) {
    this.selectedInfoPartslistSearch = pSelectedInfoPartslistSearch;
  }

  public List<MDevice> getListDeletedPartlist() {
    return this.listOfDeletingParts;
  }

  public void setListDeletedPartlist(List<MDevice> pListDeletedPartlist) {
    this.listOfDeletingParts = pListDeletedPartlist;
  }

  public List<MDevice> getListNewPartlist() {
    return this.listOfNewParts;
  }

  public void setListNewPartlist(List<MDevice> pListNewPartlist) {
    this.listOfNewParts = pListNewPartlist;
  }
}
