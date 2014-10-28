//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Office;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MDeviceDto extends MDevice {
  private int mdevId;

  @Override
  public int getMdevId() {
    return this.mdevId;
  }

  public void setMdevId(final int mdevId) {
    this.mdevId = mdevId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  private int isDeleted;

  @Override
  public int getIsDeleted() {
    return this.isDeleted;
  }

  @Override
  public void setIsDeleted(final int isDeleted) {
    this.isDeleted = isDeleted;
  }

  @Override
  public void setModelNo(String pModelNo) {
    super.setModelNo(StringUtils.upperCase(pModelNo));
  }

  private List<MdevChecklistDto> checklists = new ArrayList<MdevChecklistDto>();

  public List<MdevChecklistDto> getChecklists() {
    return this.checklists;
  }

  public void setChecklists(List<MdevChecklistDto> pChecklists) {
    this.checklists = pChecklists;
  }

  @Override
  public void setSupplierOffice(Office pSupplierOffice) {
    this.setSupplierPerson(null);
    super.setSupplierOffice(pSupplierOffice);
  }

  @Override
  public void setManufacturerOffice(Office pOffice) {
    this.setManufacturerPerson(null);
    super.setManufacturerOffice(pOffice);
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}