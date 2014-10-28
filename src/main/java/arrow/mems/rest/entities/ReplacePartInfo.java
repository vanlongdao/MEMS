package arrow.mems.rest.entities;

import java.util.ArrayList;
import java.util.List;

import arrow.mems.persistence.entity.Device;

public class ReplacePartInfo {
  private List<Device> listOldPart;
  private List<Device> listNewPart;

  public List<Device> getListOldPart() {
    if (this.listOldPart == null) {
      this.listOldPart = new ArrayList<Device>();
    }
    return this.listOldPart;
  }

  public void setListOldPart(List<Device> pListOldPart) {
    this.listOldPart = pListOldPart;
  }

  public List<Device> getListNewPart() {
    if (this.listNewPart == null) {
      this.listNewPart = new ArrayList<Device>();
    }
    return this.listNewPart;
  }

  public void setListNewPart(List<Device> pListNewPart) {
    this.listNewPart = pListNewPart;
  }


}
