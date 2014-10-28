package arrow.mems.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.CollectionUtils;
import arrow.framework.util.i18n.Messages;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.AcquisitionMaster;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MtCurrency;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.PersonClass;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.AcquisitionMasterRepository;
import arrow.mems.persistence.repository.ActionTypeMasterRepository;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.HumanResourceRepository;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.MtCurrencyRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PartEstimateRepository;
import arrow.mems.persistence.repository.PartsListRepository;
import arrow.mems.persistence.repository.PersonClassRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.service.DevicesManagementService;

@Named
@ViewScoped
public class CommonAutoCompleteBean extends AbstractBean {

  private List<Hospital> listAllActiveHospital;
  private List<HospitalDept> listAllActiveHospitalDepartment;
  private List<Person> listAllActivePerson;
  private List<PersonClass> listAllPersonClass;
  private List<Corporate> listAllActiveCorporate;
  private List<Office> listAllActiveOffice;
  private List<Users> listAllActiveSupervisorUser;
  private List<Address> listAllActiveAddress;
  private List<ActionTypeMaster> listAllActionTypes;
  private List<Device> listActiveDeviceInInputRepairRequest;
  private List<Device> listMeasurement;
  private List<Person> listHospitalPerson;
  private List<MDevice> listAllActiveMdevice;
  private List<AcquisitionMaster> listAllAcquisitionMaster;
  private List<MtCurrency> listAllMtCurrency;
  private List<PartsList> listPartsList;
  private List<PartEstimate> listPartEstimate;
  private List<Hospital> listActiveHospitalFromHumanResource;

  private MDevice othersPart;

  @Inject
  HospitalRepository hospRepo;
  @Inject
  HospitalDeptRepository hospDeptRepo;
  @Inject
  PersonRepository personRepo;
  @Inject
  PersonClassRepository personClassRepo;
  @Inject
  AddressRepository addressRepo;
  @Inject
  CorporateRepository corporateRepo;
  @Inject
  OfficeRepository officeRepo;
  @Inject
  UsersRepository usersRepository;
  @Inject
  ActionTypeMasterRepository actionTypeRepository;
  @Inject
  DeviceRepository deviceRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  PartsListRepository partsListRepository;
  @Inject
  ReplacedPartBean replacePartBean;
  @Inject
  InputRepairRequestBean inputRepairRequestBean;
  @Inject
  ChecklistRepository checklistRepository;
  @Inject
  HumanResourceRepository humanResourceRepository;
  @Inject
  MDeviceRepository mDeviceRepo;
  @Inject
  AcquisitionMasterRepository acquisMasterRepo;
  @Inject
  MtCurrencyRepository mtCurrencyRepo;
  @Inject
  PartEstimateRepository partEstimateRepository;
  @Inject
  DevicesManagementService deviceService;

  public List<Hospital> filterHospital(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveHospital();

    return CollectionUtils.filter(this.getListAllActiveHospital(), arg0 -> {
      final Hospital item = (Hospital) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getHospCode()).contains(query);
    });
  }

  public List<HospitalDept> filterHospitalDept(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveHospitalDepartment();

    return CollectionUtils.filter(this.getListAllActiveHospitalDepartment(), arg0 -> {
      final HospitalDept item = (HospitalDept) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getHospCode()).contains(query);
    });
  }

  public List<HospitalDept> filterHospitalDeptInHosp(String query, String hospCode) {
    if (StringUtils.isEmpty(query) && StringUtils.isEmpty(hospCode))
      return this.getListAllActiveHospitalDepartment();

    return CollectionUtils.filter(this.getListAllActiveHospitalDepartment(), arg0 -> {
      final HospitalDept item = (HospitalDept) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getHospCode().toString(), query);
    });
  }

  public List<Person> filterPerson(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActivePerson();

    return CollectionUtils.filter(this.getListAllActivePerson(), arg0 -> {
      final Person item = (Person) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getPsnCode()).contains(query);
    });
  }

  public List<PersonClass> filterPersonClass(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllPersonClass();

    return CollectionUtils
        .filter(
            this.getListAllPersonClass(),
            arg0 -> {
              final PersonClass item = (PersonClass) arg0;
              return StringUtils.containsIgnoreCase(item.getClassLocalPost(), query) || StringUtils.containsIgnoreCase(item.getClassLocalPre(), query) || StringUtils
                  .containsIgnoreCase(item.getMtCountry().getName(), query);
            });
  }

  public List<Address> filterAddress(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllAddress();

    return CollectionUtils.filter(this.getListAllAddress(), arg0 -> {
      final Address item = (Address) arg0;
      return StringUtils.containsIgnoreCase(item.getAddress1(), query) || item.getAddrCode().contains(query);
    });
  }

  public List<Corporate> filterCorporate(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveCorporate();

    return CollectionUtils.filter(this.getListAllActiveCorporate(), object -> {
      final Corporate item = (Corporate) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getCorpCode().contains(query);
    });
  }

  public List<Office> filterOffice(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveOffice();

    return CollectionUtils.filter(this.getListAllActiveOffice(), arg0 -> {
      final Office item = (Office) arg0;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getOfficeCode().contains(query);
    });
  }

  public List<Users> filterUser(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActiveSupervisorUser();
    return CollectionUtils.filter(this.getListAllActiveSupervisorUser(), object -> {
      final Users item = (Users) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getPsnCode().contains(query);
    });
  }

  public List<ActionTypeMaster> filterActionType(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAllActionTypes();
    return CollectionUtils.filter(this.getListAllActionTypes(), object -> {
      final ActionTypeMaster item = (ActionTypeMaster) object;
      return StringUtils.containsIgnoreCase(item.getLabel(), query) || item.getActionTypeCode().contains(query);
    });
  }

  public List<Device> filterDeviceInInputRepairRequest(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListActiveDeviceInInputRepairRequest();
    return CollectionUtils.filter(
        this.getListActiveDeviceInInputRepairRequest(),
        object -> {
          final Device item = (Device) object;
          return ((item.getMDevice() != null) && StringUtils.containsIgnoreCase(item.getMDevice().getName(), query)) || StringUtils
              .containsIgnoreCase(item.getSerialNo(), query);
        });
  }

  public List<Device> filterMeasurement(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListMeasurement();
    return CollectionUtils.filter(this.getListMeasurement(), object -> {
      final Device item = (Device) object;
      return StringUtils.containsIgnoreCase(item.getMDevice().getModelNo(), query) || StringUtils.containsIgnoreCase(item.getSerialNo(), query);
    });
  }

  public List<Person> filterHospitalPerson(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListHospitalPerson();
    return CollectionUtils.filter(this.getListHospitalPerson(), object -> {
      final Person item = (Person) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getPsnCode().contains(query);
    });
  }

  public List<MDevice> filterMDevice(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListActiveMDevice();
    return CollectionUtils.filter(
        this.getListActiveMDevice(),
        object -> {
          final MDevice item = (MDevice) object;
          return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getCatName(), query) || StringUtils
              .contains(item.getModelNo(), query);
        });
  }


  public List<Device> filterActiveDevice(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListActiveDevice();
    return CollectionUtils.filter(this.getListActiveDevice(), object -> {
      final Device item = (Device) object;
      return StringUtils.containsIgnoreCase(item.getMDevice().getName(), query) || StringUtils.containsIgnoreCase(item.getSerialNo(), query);
    });
  }



  public List<AcquisitionMaster> filterAcquiMaster(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListAcquisitionMaster();
    return CollectionUtils
        .filter(
            this.getListAcquisitionMaster(),
            object -> {
              final AcquisitionMaster item = (AcquisitionMaster) object;
              return StringUtils.containsIgnoreCase(item.getMsgLocal(), query) || StringUtils.containsIgnoreCase(item.getMsgPriceEntry(), query) || StringUtils
                  .containsIgnoreCase(item.getAcqCode(), query);
            });
  }

  public List<MtCurrency> filterMtCurrency(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListMtCurrency();
    return CollectionUtils.filter(this.getListMtCurrency(), object -> {
      final MtCurrency item = (MtCurrency) object;
      return StringUtils.containsIgnoreCase(item.getCcyName(), query);
    });
  }

  public List<PartsList> filterPartsList(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListPartsList();
    return CollectionUtils.filter(
        this.getListPartsList(),
        object -> {
          final PartsList item = (PartsList) object;
          return ((item.getMDevice() != null) && StringUtils.containsIgnoreCase(item.getMDevice().getName(), query)) || StringUtils
              .containsIgnoreCase(item.getPartCode(), query);
        });
  }

  public List<MDevice> filterPart(String query) {
    if (StringUtils.isEmpty(query))
      return this.getAvailableParts();
    return arrow.framework.util.CollectionUtils.filter(this.getAvailableParts(), pObject -> {
      final MDevice device = (MDevice) pObject;
      return (device.getMdevId() <= 0) || StringUtils.containsIgnoreCase(device.getName(), query);
    });
  }

  public List<PartEstimate> filterPartEstimate(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListPartEstimate();
    return CollectionUtils.filter(this.getListPartEstimate(), object -> {
      final PartEstimate item = (PartEstimate) object;
      return item.getPeCode().contains(query);
    });
  }

  public List<Hospital> filterHospitalFromHumanResource(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListActiveHospitalFromHumanResource();
    return CollectionUtils.filter(this.getListActiveHospitalFromHumanResource(), object -> {
      final Hospital item = (Hospital) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getHospCode(), query);
    });
  }

  public List<MtCurrency> getListMtCurrency() {
    if ((this.listAllMtCurrency == null) || this.listAllMtCurrency.isEmpty()) {
      this.listAllMtCurrency = this.mtCurrencyRepo.findAllCurrencyInOneOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllMtCurrency;
  }

  public List<AcquisitionMaster> getListAcquisitionMaster() {
    if ((this.listAllAcquisitionMaster == null) || this.listAllAcquisitionMaster.isEmpty()) {
      this.listAllAcquisitionMaster = this.acquisMasterRepo.findAll();
    }
    return this.listAllAcquisitionMaster;
  }

  public List<MDevice> getListActiveMDevice() {
    if ((this.listAllActiveMdevice == null) || this.listAllActiveMdevice.isEmpty()) {
      this.listAllActiveMdevice = this.mDeviceRepo.findActiveMdeviceInOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveMdevice;
  }

  private List<Device> listActiveDevices;

  public List<Device> getListActiveDevice() {
    if ((this.listActiveDevices == null) || this.listActiveDevices.isEmpty()) {
      this.listActiveDevices = this.deviceRepository.findActiveDevices(this.userCredential.getOfficeCode()).getResultList();
    }
    return this.listActiveDevices;
  }


  public List<Address> getListAllAddress() {
    if ((this.listAllActiveAddress == null) || this.listAllActiveAddress.isEmpty()) {
      this.listAllActiveAddress = new ArrayList<>();
      this.listAllActiveAddress = this.addressRepo.findAllActiveAddressInOneOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveAddress;
  }

  public void setListAllAddress(List<Address> pListAllAddress) {
    this.listAllActiveAddress = pListAllAddress;
  }

  public List<Hospital> getListAllActiveHospital() {
    if ((this.listAllActiveHospital == null) || this.listAllActiveHospital.isEmpty()) {
      this.listAllActiveHospital = new ArrayList<>();
      this.listAllActiveHospital = this.hospRepo.findAllActiveHospitalInOneOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveHospital;
  }

  public void setListAllActiveHospital(List<Hospital> pListAllActiveHospital) {
    this.listAllActiveHospital = pListAllActiveHospital;
  }

  public List<HospitalDept> getListAllActiveHospitalDepartment() {
    if ((this.listAllActiveHospitalDepartment == null) || this.listAllActiveHospitalDepartment.isEmpty()) {
      this.listAllActiveHospitalDepartment = this.hospDeptRepo.findAllActiveHospitalDeptInOneOfficce(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveHospitalDepartment;
  }

  public void setListAllActiveHospitalDepartment(List<HospitalDept> pListAllActiveHospitalDepartment) {
    this.listAllActiveHospitalDepartment = pListAllActiveHospitalDepartment;
  }

  public List<Person> getListAllActivePerson() {
    if ((this.listAllActivePerson == null) || this.listAllActivePerson.isEmpty()) {
      this.listAllActivePerson = this.personRepo.findAllActivePersonInOneOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllActivePerson;
  }

  public void setListAllActivePerson(List<Person> pListAllActivePerson) {
    this.listAllActivePerson = pListAllActivePerson;
  }

  public List<PersonClass> getListAllPersonClass() {
    if ((this.listAllPersonClass == null) || this.listAllPersonClass.isEmpty()) {
      this.listAllPersonClass = this.personClassRepo.findAll();
    }
    return this.listAllPersonClass;
  }

  public void setListAllPersonClass(List<PersonClass> pListAllActivePersonClass) {
    this.listAllPersonClass = pListAllActivePersonClass;
  }

  public List<Corporate> getListAllActiveCorporate() {
    if ((this.listAllActiveCorporate == null) || this.listAllActiveCorporate.isEmpty()) {
      this.listAllActiveCorporate = new ArrayList<>();
      this.listAllActiveCorporate = this.corporateRepo.findAllActiveCorporateInOneOfficce(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveCorporate;
  }

  public void setListAllCorporate(List<Corporate> pListAllCorporate) {
    this.listAllActiveCorporate = pListAllCorporate;
  }

  public List<Office> getListAllActiveOffice() {
    if ((this.listAllActiveOffice == null) || this.listAllActiveOffice.isEmpty()) {
      this.listAllActiveOffice = this.officeRepo.findAllActiveOffice(this.userCredential.getOfficeCode());
    }
    return this.listAllActiveOffice;
  }

  public void setListAllActiveOffice(List<Office> pListAllActiveOffice) {
    this.listAllActiveOffice = pListAllActiveOffice;
  }

  public List<Users> getListAllActiveSupervisorUser() {
    if ((this.listAllActiveSupervisorUser == null) || this.listAllActiveSupervisorUser.isEmpty()) {
      this.listAllActiveSupervisorUser = new ArrayList<>();
      final List<Integer> listUsersInOneOffice = this.usersRepository.findUserInOneOffice(this.userCredential.getOfficeCode());
      this.listAllActiveSupervisorUser = this.usersRepository.findActiveUsersInOneOffice(listUsersInOneOffice);
    }
    return this.listAllActiveSupervisorUser;
  }

  public void setListAllActiveSupervisorUser(List<Users> pListAllActiveSupervisorUser) {
    this.listAllActiveSupervisorUser = pListAllActiveSupervisorUser;
  }

  public List<ActionTypeMaster> getListAllActionTypes() {
    if ((this.listAllActionTypes == null) || this.listAllActionTypes.isEmpty()) {
      this.listAllActionTypes = new ArrayList<>();
      this.listAllActionTypes = this.actionTypeRepository.findAll();
    }
    return this.listAllActionTypes;
  }

  public void setListAllActionTypes(List<ActionTypeMaster> pListAllActionTypes) {
    this.listAllActionTypes = pListAllActionTypes;
  }

  public List<Device> getListActiveDeviceInInputRepairRequest() {
    if ((this.listActiveDeviceInInputRepairRequest == null) || this.listActiveDeviceInInputRepairRequest.isEmpty()) {
      this.listActiveDeviceInInputRepairRequest = new ArrayList<>();
      final String devCode = this.inputRepairRequestBean.getCurrentDevice().getDevCode();
      this.listActiveDeviceInInputRepairRequest =
          this.deviceRepository.getAllListActiveDeviceInInputRepairRequest(devCode, this.userCredential.getOfficeCode());
    }
    return this.listActiveDeviceInInputRepairRequest;
  }

  public void setListActiveDeviceInInputRepairRequest(List<Device> pListActiveDeviceInInputRepairRequest) {
    this.listActiveDeviceInInputRepairRequest = pListActiveDeviceInInputRepairRequest;
  }

  public List<Device> getListMeasurement() {
    if ((this.listMeasurement == null) || this.listMeasurement.isEmpty()) {
      this.listMeasurement = new ArrayList<>();
      this.listMeasurement = this.deviceRepository.getAllMeasurementInOneOffice(this.userCredential.getOfficeCode()).getResultList();
    }
    return this.listMeasurement;
  }

  public void setListMeasurement(List<Device> pListMeasurement) {
    this.listMeasurement = pListMeasurement;
  }

  public List<Person> getListHospitalPerson() {
    if (this.inputRepairRequestBean.getCurrentDevice() != null) {
      this.listHospitalPerson = new ArrayList<>();

      if (this.inputRepairRequestBean.getCurrentDevice().getHospitalDept() == null)
        return this.listHospitalPerson;

      List<String> listPersonCode = new ArrayList<>();
      final String hospDeptCode = this.inputRepairRequestBean.getCurrentDevice().getHospDeptCode();
      final String officeCode = this.userCredential.getOfficeCode();
      listPersonCode = this.humanResourceRepository.getListPersonCodeByHospDeptCode(hospDeptCode, officeCode).getResultList();

      if (!listPersonCode.isEmpty()) {
        this.listHospitalPerson = this.personRepo.findAllPersonInOneDepartment(listPersonCode).getResultList();
      }
    }
    return this.listHospitalPerson;
  }

  public void setListHospitalPerson(List<Person> pListHospitalPerson) {
    this.listHospitalPerson = pListHospitalPerson;
  }

  public List<PartsList> getListPartsList() {
    if ((this.listPartsList == null) || this.listPartsList.isEmpty()) {
      this.listPartsList = new ArrayList<>();

      if (this.inputRepairRequestBean.getCurrentDevice().getMDevice() == null)
        return this.listPartsList;

      final String mdevCode = this.inputRepairRequestBean.getCurrentDevice().getMdevCode();
      final String officeCode = this.userCredential.getOfficeCode();

      this.listPartsList = this.partsListRepository.getAllPartsListByMDevCode(mdevCode, officeCode).getResultList();

    }
    return this.listPartsList;
  }

  public void setListPartsList(List<PartsList> pListPartsList) {
    this.listPartsList = pListPartsList;
  }

  public List<MDevice> getAvailableParts() {
    if (this.inputRepairRequestBean.getCurrentDevice().getMDevice() == null)
      return Collections.emptyList();
    final List<MDevice> existingParts = this.getPartsListOfMasterDevice(this.inputRepairRequestBean.getCurrentDevice().getMDevice());
    final List<MDevice> availableParts = new ArrayList<MDevice>();
    availableParts.addAll(existingParts);

    // add Others item
    availableParts.add(this.getOtherPartItem());
    return availableParts;
  }

  private List<MDevice> getPartsListOfMasterDevice(MDevice masterDevice) {
    final ServiceResult<List<MDevice>> result = this.deviceService.getAllPartsOfMasterDevice(masterDevice.getMdevCode());
    if (result.isSuccess())
      return result.getData();
    return Collections.emptyList();
  }

  public MDevice getOtherPartItem() {
    if (this.othersPart == null) {
      this.othersPart = new MDevice();
      this.othersPart.setName(Messages.get("others"));
    }
    return this.othersPart;
  }

  public List<PartEstimate> getListPartEstimate() {
    if ((this.listPartEstimate == null) || this.listPartEstimate.isEmpty()) {
      this.listPartEstimate = new ArrayList<>();
      final String actionCode = this.inputRepairRequestBean.getCurrentActionLog().getActionCode();
      if (!StringUtils.isEmpty(actionCode)) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listPartEstimate = this.partEstimateRepository.getAllActivePartEstimateUseActionCodeInOneOffice(actionCode, officeCode).getResultList();
      }
    }
    return this.listPartEstimate;
  }

  public void setListPartEstimate(List<PartEstimate> pListPartEstimate) {
    this.listPartEstimate = pListPartEstimate;
  }

  public List<Hospital> getListActiveHospitalFromHumanResource() {
    if ((this.listActiveHospitalFromHumanResource == null) || this.listActiveHospitalFromHumanResource.isEmpty()) {
      this.listActiveHospitalFromHumanResource = new ArrayList<>();
      final String officeCode = this.userCredential.getOfficeCode();
      final List<String> listHospCode = this.humanResourceRepository.getHospCode(officeCode);
      if (!listHospCode.isEmpty()) {
        this.listActiveHospitalFromHumanResource = this.hospRepo.findHospitalFromListHospCode(listHospCode).getResultList();
      }
    }
    return this.listActiveHospitalFromHumanResource;
  }

  public void setListActiveHospitalFromHumanResource(List<Hospital> pListActiveHospitalFromHumanResource) {
    this.listActiveHospitalFromHumanResource = pListActiveHospitalFromHumanResource;
  }

}
