package arrow.mems.constant;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Contract;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.CountRecord;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.mapped.ActionLogMapped_;
import arrow.mems.persistence.mapped.AddressMapped_;
import arrow.mems.persistence.mapped.AlertByCountMapped_;
import arrow.mems.persistence.mapped.BudgetMapped_;
import arrow.mems.persistence.mapped.ChecklistItemMapped_;
import arrow.mems.persistence.mapped.ChecklistMapped_;
import arrow.mems.persistence.mapped.ContractMapped_;
import arrow.mems.persistence.mapped.CorporateMapped_;
import arrow.mems.persistence.mapped.CountRecordMapped_;
import arrow.mems.persistence.mapped.DeviceMapped_;
import arrow.mems.persistence.mapped.HospitalDeptMapped_;
import arrow.mems.persistence.mapped.HospitalMapped_;
import arrow.mems.persistence.mapped.HumanResourceMapped_;
import arrow.mems.persistence.mapped.MDeviceMapped_;
import arrow.mems.persistence.mapped.MdevChecklistItemMapped_;
import arrow.mems.persistence.mapped.MdevChecklistMapped_;
import arrow.mems.persistence.mapped.OfficeMapped_;
import arrow.mems.persistence.mapped.PartEstimateMapped_;
import arrow.mems.persistence.mapped.PartOrderMapped_;
import arrow.mems.persistence.mapped.PresetPhrasesMapped_;
import arrow.mems.persistence.mapped.ScheduleItemMapped_;
import arrow.mems.persistence.mapped.ScheduleMapped_;

public class MemsDataType {
  public static final String ACTION_LOG = "01";
  public static final String ACTION_TYPE_MASTER = "02";
  public static final String ADDRESS = "03";
  public static final String ALERT_BY_COUNT = "04";
  public static final String ACQUISITION_MASTER = "05";
  public static final String CORPORATE = "06";
  public static final String CHECK_ITEM = "07";
  public static final String CHECK = "08";
  public static final String CONTRACTS = "09";
  public static final String COUNT_RECORD = "10";
  public static final String DEVICE = "11";
  public static final String HOSPITAL = "12";
  public static final String HOSPITAL_DEPARTMENT = "13";
  public static final String MDEVICE = "14";
  public static final String MDEV_CHECKLIST = "15";
  public static final String MDEV_CHECKLIST_ITEM = "16";
  public static final String OFFICE = "17";
  public static final String PART_ESTIMATE = "18";
  public static final String PART_ORDER = "19";
  public static final String PERSON = "20";
  public static final String PERSON_CLASS = "21";
  public static final String PRESET_PHRASES_MEANING = "22";
  public static final String PRESET_PHRASES_CATEGORY = "23";
  public static final String ROLE_MASTER = "24";
  public static final String SCHEDULE = "25";
  public static final String SCHEDULE_ITEM = "26";
  public static final String BUDGET = "27";
  public static final String HUMAN_RESOURCE = "28";

  private static Map<String, String> memsDataTypeMaps = new HashMap<String, String>();

  public static Map<String, String> memsDataType() {


    return MemsDataType.memsDataTypeMaps;
  }

  @SuppressWarnings("rawtypes")
  public static Map<String, SingularAttribute> buildMapTypeAndCodeField() {
    final Map<String, SingularAttribute> initMap = new HashMap<String, SingularAttribute>();
    initMap.put(MemsDataType.ACTION_LOG, ActionLogMapped_.actionCode);
    initMap.put(MemsDataType.ADDRESS, AddressMapped_.addrCode);
    initMap.put(MemsDataType.ACTION_LOG, ActionLogMapped_.actionCode);
    initMap.put(MemsDataType.ALERT_BY_COUNT, AlertByCountMapped_.counterBaseCode);
    initMap.put(MemsDataType.CORPORATE, CorporateMapped_.corpCode);
    initMap.put(MemsDataType.CHECK, ChecklistMapped_.cklistLogCode);
    initMap.put(MemsDataType.CHECK_ITEM, ChecklistItemMapped_.ckiLogCode);
    initMap.put(MemsDataType.CONTRACTS, ContractMapped_.contractCode);
    initMap.put(MemsDataType.COUNT_RECORD, CountRecordMapped_.countRecCode);
    initMap.put(MemsDataType.DEVICE, DeviceMapped_.devCode);
    initMap.put(MemsDataType.HOSPITAL, HospitalMapped_.hospCode);
    initMap.put(MemsDataType.HOSPITAL_DEPARTMENT, HospitalDeptMapped_.deptCode);
    initMap.put(MemsDataType.MDEVICE, MDeviceMapped_.mdevCode);
    initMap.put(MemsDataType.MDEV_CHECKLIST, MdevChecklistMapped_.cklistCode);
    initMap.put(MemsDataType.MDEV_CHECKLIST_ITEM, MdevChecklistItemMapped_.ckiCode);
    initMap.put(MemsDataType.OFFICE, OfficeMapped_.officeCode);
    initMap.put(MemsDataType.PART_ESTIMATE, PartEstimateMapped_.peCode);
    initMap.put(MemsDataType.PART_ORDER, PartOrderMapped_.poCode);
    initMap.put(MemsDataType.PRESET_PHRASES_MEANING, PresetPhrasesMapped_.meaningCode);
    initMap.put(MemsDataType.PRESET_PHRASES_CATEGORY, PresetPhrasesMapped_.categoryCode);
    initMap.put(MemsDataType.SCHEDULE, ScheduleMapped_.schedBaseCode);
    initMap.put(MemsDataType.SCHEDULE_ITEM, ScheduleItemMapped_.schedCode);
    initMap.put(MemsDataType.BUDGET, BudgetMapped_.budgetId);
    initMap.put(MemsDataType.HUMAN_RESOURCE, HumanResourceMapped_.hrId);

    return initMap;
  }

  public static Map<String, Class<? extends AbstractApprovalEntity>> buildMapTypeAndEntity() {
    final Map<String, Class<? extends AbstractApprovalEntity>> initMap = new HashMap<String, Class<? extends AbstractApprovalEntity>>();
    initMap.put(MemsDataType.ACTION_LOG, ActionLog.class);
    initMap.put(MemsDataType.ADDRESS, Address.class);
    initMap.put(MemsDataType.ACTION_LOG, ActionLog.class);
    initMap.put(MemsDataType.ALERT_BY_COUNT, AlertByCount.class);
    initMap.put(MemsDataType.CORPORATE, Corporate.class);
    initMap.put(MemsDataType.CHECK, Checklist.class);
    initMap.put(MemsDataType.CHECK_ITEM, ChecklistItem.class);
    initMap.put(MemsDataType.CONTRACTS, Contract.class);
    initMap.put(MemsDataType.COUNT_RECORD, CountRecord.class);
    initMap.put(MemsDataType.DEVICE, Device.class);
    initMap.put(MemsDataType.HOSPITAL, Hospital.class);
    initMap.put(MemsDataType.HOSPITAL_DEPARTMENT, HospitalDept.class);
    initMap.put(MemsDataType.MDEVICE, MDevice.class);
    initMap.put(MemsDataType.MDEV_CHECKLIST, MdevChecklist.class);
    initMap.put(MemsDataType.MDEV_CHECKLIST_ITEM, MdevChecklistItem.class);
    initMap.put(MemsDataType.OFFICE, Office.class);
    initMap.put(MemsDataType.PART_ESTIMATE, PartEstimate.class);
    initMap.put(MemsDataType.PART_ORDER, PartOrder.class);
    initMap.put(MemsDataType.PRESET_PHRASES_MEANING, PresetPhrases.class);
    initMap.put(MemsDataType.PRESET_PHRASES_CATEGORY, PresetPhrases.class);
    initMap.put(MemsDataType.SCHEDULE, Schedule.class);
    initMap.put(MemsDataType.SCHEDULE_ITEM, ScheduleItem.class);
    initMap.put(MemsDataType.BUDGET, Budget.class);
    initMap.put(MemsDataType.HUMAN_RESOURCE, HumanResource.class);

    return initMap;
  }
}
