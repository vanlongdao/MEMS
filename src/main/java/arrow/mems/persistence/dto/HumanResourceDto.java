//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.Person;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class HumanResourceDto extends HumanResource {
  private int hrId;

  @Override
  public int getHrId() {
    return this.hrId;
  }

  public void setHrId(final int hrId) {
    this.hrId = hrId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Override
  @NotNull(message = "{" + MessageCode.MFM00015 + "}")
  public HospitalDept getHospitalDept() {
    return super.getHospitalDept();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00014 + "}")
  public Hospital getHospital() {
    return super.getHospital();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00017 + "}")
  public Person getPerson() {
    return super.getPerson();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}