package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class ManufacturerDeviceMasterBean extends AbstractBean {

  // For MDM02_05 : Manager manufacturer of devices master
  private List<Office> listOffice;
  private List<MDevice> listMdevice;
  private List<Person> listPerson;

  private List<Office> listSearchOffice;
  private List<Person> listPersonByOffice;
  private Office selectedOffice;
  private Person selectedPerson;

  private Office realSeletedOffice;
  private Person realSeletedPerson;

  // Form keysearch show candidates
  private String keywordOffice;

  public Office getRealSeletedOffice() {
    return this.realSeletedOffice;
  }

  public void setRealSeletedOffice(Office pRealSeletedOffice) {
    this.realSeletedOffice = pRealSeletedOffice;
  }

  public Person getRealSeletedPerson() {
    return this.realSeletedPerson;
  }

  public void setRealSeletedPerson(Person pRealSeletedPerson) {
    this.realSeletedPerson = pRealSeletedPerson;
  }

  private String keywordPerson;
  private String keywordDevice;

  private boolean isEditManufacture = true;


  public boolean isEditManufacture() {
    return this.isEditManufacture;
  }

  public void enableEidtManufacturer() {
    this.isEditManufacture = true;
  }

  public void disableEidtManufacturer() {
    this.isEditManufacture = false;
  }

  @Inject
  private DevicesManagementService devicesManagerService;
  @Inject
  private DevicesManagementBean devicesManagerBean;

  @Inject
  private UserCredential currentUser;

  /* Start Area code of MDM02_05 */

  public void searchManufacturer() {
    this.listSearchOffice =
        this.devicesManagerService.searchOfficeManufacturer(this.keywordOffice, this.keywordDevice, this.keywordPerson, CommonConstants.ACTIVE);
    this.listPersonByOffice = null;
  }

  public void selectRowListOfficeSearch(SelectEvent event) {
    this.selectedOffice = (Office) event.getObject();
    this.listPersonByOffice = this.devicesManagerService.getPersonByOfficeCode(this.selectedOffice);
    if (CollectionUtils.isEmpty(this.listPersonByOffice)) {
      this.selectedPerson = null;
    }
  }

  public void selectRowListPesonByOffice(SelectEvent event) {
    this.selectedPerson = (Person) event.getObject();
  }

  public void closeManufacturerForm() {
    this.selectedOffice = null;
    this.selectedPerson = null;
    this.realSeletedOffice = null;
    this.realSeletedPerson = null;
    this.listSearchOffice = null;
    this.listSearchOffice = null;
    this.listPersonByOffice = null;
  }

  private boolean validateSelectedOfficeAndPerson() {
    if ((this.selectedOffice == null) && (this.selectedPerson == null)) {
      MDMMessages.MDM00007().show();
      return false;
    }
    return true;
  }

  public void chooseOfficeAndContactPerson() {
    if (!this.validateSelectedOfficeAndPerson())
      return;
    this.realSeletedOffice = this.selectedOffice;
    this.realSeletedPerson = this.selectedPerson;
  }

  public void setOfficeAndPerson() {
    if (this.realSeletedOffice != null) {
      if (this.isEditManufacture) {
        this.devicesManagerBean.getCurrentDevice().setManufacturerOffice(this.realSeletedOffice);
      } else {
        this.devicesManagerBean.getCurrentDevice().setSupplierOffice(this.realSeletedOffice);
      }
    }
    if (this.realSeletedPerson != null) {
      if (this.isEditManufacture) {
        this.devicesManagerBean.getCurrentDevice().setManufacturerPerson(this.realSeletedPerson);
      } else {
        this.devicesManagerBean.getCurrentDevice().setSupplierPerson(this.realSeletedPerson);
      }
    }
    this.closeManufacturerForm();
  }

  /* End area code of MDM02_05 */

  public List<Office> getListOffice() {
    if (this.listOffice == null) {
      this.listOffice = this.devicesManagerService.autoCompleteOfficeManufacturer(CommonConstants.ACTIVE);
    }
    return this.listOffice;
  }

  public void setListOffice(List<Office> pListOfficeManufacturer) {
    this.listOffice = pListOfficeManufacturer;
  }

  public List<MDevice> getListMdevice() {
    if (this.listMdevice == null) {
      this.listMdevice =
          this.devicesManagerService.findMDeviceByOwnedOfficeCodeAndIsDeleted(this.currentUser.getUserInfo().getOfficeCode(), CommonConstants.ACTIVE);
    }
    return this.listMdevice;
  }

  public void setListMdevice(List<MDevice> pListMdeviceManufacturer) {
    this.listMdevice = pListMdeviceManufacturer;
  }

  public List<Person> getListPerson() {
    if (this.listPerson == null) {
      this.listPerson = this.devicesManagerService.autoCompletePersonManufacturer(CommonConstants.ACTIVE);
    }
    return this.listPerson;
  }

  public void setListPerson(List<Person> pListPersonManufacturer) {
    this.listPerson = pListPersonManufacturer;
  }

  public List<Office> getListSearchOffice() {
    return this.listSearchOffice;
  }

  public void setListSearchOffice(List<Office> pListSearchOfficeManufacturer) {
    this.listSearchOffice = pListSearchOfficeManufacturer;
  }

  public List<Person> getListPersonByOffice() {
    return this.listPersonByOffice;
  }

  public void setListPersonByOffice(List<Person> pListPersonByOfficeManufacturer) {
    this.listPersonByOffice = pListPersonByOfficeManufacturer;
  }

  public Office getSelectedOffice() {
    return this.selectedOffice;
  }

  public void setSelectedOffice(Office pSelectedOfficeManufacturer) {
    this.selectedOffice = pSelectedOfficeManufacturer;
  }

  public Person getSelectedPerson() {
    return this.selectedPerson;
  }

  public void setSelectedPerson(Person pSelectedPersonManufacturer) {
    this.selectedPerson = pSelectedPersonManufacturer;
  }


  public List<String> autocompleteOfficeCandidate(String query) {
    final List<Office> result =
        this.devicesManagerService.getAllOfficeByOwnedOfficeAndIsDeleted(this.currentUser.getUserInfo().getOfficeCode(), CommonConstants.ACTIVE);
    final List<String> candidateResults = new ArrayList<String>();
    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(result)) {
      for (final Office office : result) {
        if (candidateResults.contains(office.getName()) || candidateResults.contains(office.getCorporate().getName())) {
          continue;
        }
        if (StringUtils.isNotEmpty(office.getName())) {
          if (StringUtils.startsWithIgnoreCase(office.getName(), query)) {
            candidateResults.add(office.getName());
          } else if (ArrowStrUtils.isNotEmpty(office.getCorporate().getName())) {
            if (StringUtils.startsWithIgnoreCase(office.getCorporate().getName(), query)) {
              candidateResults.add(office.getCorporate().getName());
            }
          }
        }
      }
    }
    return candidateResults;
  }

  public List<String> autocompletePersonCandidate(String query) {
    final List<Person> result =
        this.devicesManagerService.getAllPersonByOwnedOfficeAndIsDeleted(this.currentUser.getUserInfo().getOfficeCode(), CommonConstants.ACTIVE);
    final List<String> candidateResults = new ArrayList<String>();
    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(result)) {
      for (final Person key : result) {
        if (candidateResults.contains(key.getName()) || candidateResults.contains(key.getPsnCode())) {
          continue;
        }
        if (StringUtils.isEmpty(key.getName())) {
          continue;
        }
        if (StringUtils.startsWithIgnoreCase(key.getName(), query)) {
          candidateResults.add(key.getName());
        } else if (StringUtils.startsWithIgnoreCase(key.getPsnCode(), query)) {
          candidateResults.add(key.getPsnCode());
        }
      }
    }
    return candidateResults;
  }

  public List<String> autocompleteMDeviceCandidate(String query) {
    final List<MDevice> result = this.getListMdevice();
    final List<String> candidateStr = new ArrayList<String>();
    if (CollectionUtils.isNotEmpty(result)) {
      for (final MDevice key : result) {
        if (candidateStr.contains(key.getName()) || candidateStr.contains(key.getMdevCode())) {
          continue;
        }
        if (StringUtils.isEmpty(key.getName())) {
          continue;
        }

        if (StringUtils.startsWithIgnoreCase(key.getName(), query)) {
          candidateStr.add(key.getName());
        } else if (StringUtils.startsWithIgnoreCase(key.getMdevCode(), query)) {
          candidateStr.add(key.getMdevCode());
        }
      }
    }
    return candidateStr;
  }

  public String getKeywordOffice() {
    return this.keywordOffice;
  }

  public void setKeywordOffice(String pKeywordOffice) {
    this.keywordOffice = pKeywordOffice;
  }

  public String getKeywordPerson() {
    return this.keywordPerson;
  }

  public void setKeywordPerson(String pKeywordPerson) {
    this.keywordPerson = pKeywordPerson;
  }

  public String getKeywordDevice() {
    return this.keywordDevice;
  }

  public void setKeywordDevice(String pKeywordDevice) {
    this.keywordDevice = pKeywordDevice;
  }


}
