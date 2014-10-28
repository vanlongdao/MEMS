//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.mapped.ApprovalConfigMapped;
import arrow.mems.util.SelectItemGenerator;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="APPROVAL_CONFIG")
public class ApprovalConfig extends ApprovalConfigMapped {

  public ApprovalConfig() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  public boolean isEnableApproval() {
    return (this.getDisableApprove() == CommonConstants.ENABLE);
  }

  public void setEnableApproval(boolean enableApproval) {
    this.setDisableApprove(enableApproval ? 0 : 1);
  }

  @Transient
  private boolean enableApproval;

  public String getDataTypeName() {
    return (SelectItemGenerator.getMapDataTypes().get(this.getDataType()) != null) ? SelectItemGenerator.getMapDataTypes().get(this.getDataType())
        .getLabel() : null;
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  