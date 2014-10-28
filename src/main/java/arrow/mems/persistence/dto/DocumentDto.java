//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Document;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class DocumentDto extends Document {
  private int docId;

  @Override
  public int getDocId() {
    return this.docId;
  }

  public void setDocId(final int docId) {
    this.docId = docId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MDM00010 + "}")
  public String getSoftwareRev() {
    return super.getSoftwareRev();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}