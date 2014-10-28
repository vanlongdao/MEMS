package arrow.mems.faces.converter;

import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import arrow.framework.faces.converter.EntityConstraint;
import arrow.mems.persistence.entity.AcquisitionMaster;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.MtCurrency;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.PersonClass;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.entity.Users;


@Named
public class ConverterStorage {

  @Inject
  EntityManager em;

  // NOTES: List your converter here.
  // AND EACH converter have to has Unit Test
  public Converter getAddressConverter() {
    return new EntityConstraint<>(Address.class, this.em).getConverter();
  }

  public Converter getHospitalConverter() {
    return new EntityConstraint<>(Hospital.class, this.em).getConverter();
  }

  public Converter getHospitalDepartmentConverter() {
    return new EntityConstraint<>(HospitalDept.class, this.em).getConverter();
  }

  public Converter getPersonConverter() {
    return new EntityConstraint<>(Person.class, this.em).getConverter();
  }

  public Converter getPersonClassConverter() {
    return new EntityConstraint<>(PersonClass.class, this.em).getConverter();
  }

  public Converter getCorporateConverter() {
    return new EntityConstraint<>(Corporate.class, this.em).getConverter();
  }

  public Converter getOfficeConverter() {
    return new EntityConstraint<>(Office.class, this.em).getConverter();
  }

  public Converter getCountryConverter() {
    return new EntityConstraint<>(MtCountry.class, this.em).getConverter();
  }

  public Converter getMdevChecklistItemConverter() {
    return new EntityConstraint<>(MdevChecklistItem.class, this.em).getConverter();
  }

  public Converter getMdeviceConverter() {
    return new EntityConstraint<>(MDevice.class, this.em).getConverter();
  }

  public Converter getArrowTimeConverter() {
    return new TimeConverter();
  }

  public Converter getUserConverter() {
    return new EntityConstraint<>(Users.class, this.em).getConverter();
  }

  public Converter getQuatityConverter() {
    return ArrowConverterFactory.getQuatityConverter();
  }

  public Converter getMoneyConverter() {
    return ArrowConverterFactory.getMoneyConverter();
  }

  public Converter getActionTypeConverter() {
    return new EntityConstraint<>(ActionTypeMaster.class, this.em).getConverter();
  }

  public Converter getHumanResourceConverter() {
    return new EntityConstraint<>(HumanResource.class, this.em).getConverter();
  }

  public Converter getDeviceConverter() {
    return new EntityConstraint<>(Device.class, this.em).getConverter();
  }

  public Converter getMDeviceConverter() {
    return new EntityConstraint<>(MDevice.class, this.em).getConverter();
  }

  public Converter getAcquisitionMaster() {
    return new EntityConstraint<>(AcquisitionMaster.class, this.em).getConverter();
  }

  public Converter getCurrency() {
    return new EntityConstraint<>(MtCurrency.class, this.em).getConverter();
  }

  public Converter getReplacePartConverter() {
    return new EntityConstraint<>(ReplPart.class, this.em).getConverter();
  }

  public Converter getPercentConverter() {
    return ArrowConverterFactory.getPercentConverter();
  }

  public Converter getChecklistConverter() {
    return new EntityConstraint<>(Checklist.class, this.em).getConverter();
  }

  public Converter getMdevChecklistConverter() {
    return new EntityConstraint<>(MdevChecklist.class, this.em).getConverter();
  }

  public Converter getActionLogConverter() {
    return new EntityConstraint<>(ActionLog.class, this.em).getConverter();
  }

  public Converter getDocumentConverter() {
    return new EntityConstraint<>(Document.class, this.em).getConverter();
  }

  public Converter getPartOrderConverter() {
    return new EntityConstraint<>(PartOrder.class, this.em).getConverter();
  }

  public Converter getPartsListConverter() {
    return new EntityConstraint<>(PartsList.class, this.em).getConverter();
  }

  public Converter getPartEstimateConverter() {
    return new EntityConstraint<>(PartEstimate.class, this.em).getConverter();
  }
}
