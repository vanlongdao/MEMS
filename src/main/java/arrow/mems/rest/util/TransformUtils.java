package arrow.mems.rest.util;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.util.BeanCopier;
import arrow.framework.util.Instance;
import arrow.mems.persistence.dto.ActionLogDto;
import arrow.mems.persistence.dto.ActionTypeMasterDto;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.dto.FaultDto;
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.HospitalDeptRepository;

public class TransformUtils {
  public static ActionLogDto transform(ActionLog item) {
    final ActionLogDto dto = new ActionLogDto();
    BeanCopier.flatCopy(item, dto);
    dto.setHospitalContactPersonName(item.getHospitalContactPsn() != null ? item.getHospitalContactPsn().getName() : StringUtils.EMPTY);
    dto.setSupplierContactPersonName(item.getPerson() != null ? item.getPerson().getName() : StringUtils.EMPTY);
    final HospitalDept dept = Instance.get(HospitalDeptRepository.class).findHospitalDeptByHospDeptCode(item.getHospDeptCode()).getAnyResult();

    dto.setHospitalDepartmentName(dept != null ? dept.getName() : StringUtils.EMPTY);
    if (item.getContactPerson() != null) {
      final PersonDto contactPerson = TransformUtils.transform(item.getContactPerson());
      dto.setContactPerson(contactPerson);
    }

    if (item.getHospitalContactPerson() != null) {
      final PersonDto hospitalContactPerson = TransformUtils.transform(item.getHospitalContactPerson());
      dto.setHospitalContactPerson(hospitalContactPerson);
    }

    if (item.getActionTypeMaster() != null) {
      final ActionTypeMasterDto actionType = TransformUtils.transform(item.getActionTypeMaster());
      dto.setActionTypeMaster(actionType);
    }

    if (item.getFault() != null) {
      final FaultDto fault = TransformUtils.transform(item.getFault());
      dto.setFault(fault);
    }
    return dto;
  }

  public static DeviceDto transform(Device item) {
    final DeviceDto dto = new DeviceDto();
    BeanCopier.flatCopy(item, dto);
    dto.setMdevModelNo(item.getMDevice().getModelNo());
    dto.setMdevName(item.getMDevice().getName());
    dto.setMdevCatName(item.getMDevice().getCatName());
    dto.setManufactureName(item.getMDevice().getManufacturerOffice() != null ? item.getMDevice().getManufacturerOffice().getName() : null);
    dto.setSupplierName(item.getSupplierOffice() != null ? item.getSupplierOffice().getName() : null);
    dto.setHospitalDeparmentName(item.getHospitalDept() != null ? item.getHospitalDept().getName() : null);
    return dto;
  }

  public static ActionTypeMasterDto transform(ActionTypeMaster item) {
    final ActionTypeMasterDto dto = new ActionTypeMasterDto();
    BeanCopier.flatCopy(item, dto);
    return dto;
  }

  public static PersonDto transform(Person item) {
    final PersonDto dto = new PersonDto();
    BeanCopier.flatCopy(item, dto);
    return dto;
  }

  public static FaultDto transform(Fault item) {
    final FaultDto dto = new FaultDto();
    BeanCopier.flatCopy(item, dto);
    return dto;
  }
}
