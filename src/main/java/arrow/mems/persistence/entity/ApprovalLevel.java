//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.ApprovalLevelMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="APPROVAL_LEVEL")
public class ApprovalLevel extends ApprovalLevelMapped {

  public ApprovalLevel() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @OneToMany(mappedBy = "approvalLevel", cascade = CascadeType.ALL)
  protected List<ApprovalLevelSupervisor> listSupervisors = new ArrayList<>();


  public List<ApprovalLevelSupervisor> getListSupervisors() {
    return this.listSupervisors;
  }


  public void setListSupervisors(List<ApprovalLevelSupervisor> pListSupervisors) {
    this.listSupervisors = pListSupervisors;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}