//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class HospitalDeptDto extends HospitalDept {
  private int deptId;

  @Override
  public int getDeptId() {
    return this.deptId;
  }

  public void setDeptId(final int deptId) {
    this.deptId = deptId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Override
  @NotNull(message = "{" + MessageCode.MFM00014 + "}")
  public Hospital getHospital() {
    return super.getHospital();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00015 + "}")
  public String getName() {
    return super.getName();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00016 + "}")
  public Integer getNumBeds() {
    return super.getNumBeds();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00021 + "}")
  public Integer getCheckDaysMargin() {
    return super.getCheckDaysMargin();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00022 + "}")
  public Integer getNoticeDaysBefore() {
    return super.getNoticeDaysBefore();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00023 + "}")
  public Integer getPickupDays() {
    return super.getPickupDays();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}