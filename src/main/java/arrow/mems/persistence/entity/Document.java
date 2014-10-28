//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import arrow.mems.persistence.mapped.DocumentMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="DOCUMENT")
public class Document extends DocumentMapped {

  public Document() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  

  @Transient
  private String uploadFolderName;

  public String getUploadFolderName() {
    return this.uploadFolderName;
  }

  public void setUploadFolderName(String pUploadFolderName) {
    this.uploadFolderName = pUploadFolderName;
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  