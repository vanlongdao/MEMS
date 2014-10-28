//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.CountRecord;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class CountRecordDto extends CountRecord {
  private int countRecordId;

  @Override
  public int getCountRecordId() {
    return this.countRecordId;
  }

  public void setCountRecordId(final int countRecordId) {
    this.countRecordId = countRecordId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  

  @Override
  @NotNull(message = "{" + MessageCode.MPM00020 + "}")
  public Double getRawValue() {
    return super.getRawValue();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}