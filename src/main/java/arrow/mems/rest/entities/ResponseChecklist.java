package arrow.mems.rest.entities;

import java.util.List;

import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Device;

public class ResponseChecklist {
  private List<ChecklistItem> listChecklistInfo;
  private List<Device> listMeasure1;
  private List<Device> listMeasure2;

  public List<ChecklistItem> getListChecklistInfo() {
    return this.listChecklistInfo;
  }

  public void setListChecklistInfo(List<ChecklistItem> pListChecklistInfo) {
    this.listChecklistInfo = pListChecklistInfo;
  }

  public List<Device> getListMeasure1() {
    return this.listMeasure1;
  }

  public void setListMeasure1(List<Device> pListMeasure1) {
    this.listMeasure1 = pListMeasure1;
  }

  public List<Device> getListMeasure2() {
    return this.listMeasure2;
  }

  public void setListMeasure2(List<Device> pListMeasure2) {
    this.listMeasure2 = pListMeasure2;
  }

  public String getPersonName() {
    return this.personName;
  }

  public void setPersonName(String pPersonName) {
    this.personName = pPersonName;
  }

  private String personName;


}
