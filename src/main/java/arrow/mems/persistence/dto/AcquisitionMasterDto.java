//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.AcquisitionMaster;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class AcquisitionMasterDto extends AcquisitionMaster {
  private int acqId;

  @Override
  public int getAcqId() {
    return this.acqId;
  }

  public void setAcqId(final int acqId) {
    this.acqId = acqId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}