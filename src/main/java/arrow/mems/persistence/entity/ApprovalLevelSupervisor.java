//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.ApprovalLevelSupervisorMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="APPROVAL_LEVEL_SUPERVISOR")
public class ApprovalLevelSupervisor extends ApprovalLevelSupervisorMapped {

  public ApprovalLevelSupervisor() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  @ManyToOne
  @JoinColumn(name = "SUPERVISOR_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
  private Users user;

  public Users getUser() {
    return this.user;
  }

  public void setUser(Users pUser) {
    this.user = pUser;
    if (this.user != null) {
      this.supervisorId = this.user.getUserId();
    }
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  