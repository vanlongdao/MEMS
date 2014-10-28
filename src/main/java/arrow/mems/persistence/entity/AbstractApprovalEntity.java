//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.MappedSuperclass;

import arrow.framework.util.Instance;
import arrow.mems.persistence.mapped.AbstractApprovalEntityMapped;
import arrow.mems.persistence.repository.UsersRepository;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class AbstractApprovalEntity extends AbstractApprovalEntityMapped {

  public AbstractApprovalEntity() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  /**
   * @return true if the data is passed approval process (can be Approved or Rejected)
   */
  public boolean isPassedApprovalProcess() {
    return (this.checkedAt != null) && (this.checkedBy != null);
  }

  public boolean isPendingApprove() {
    return false;
  }

  @com.fasterxml.jackson.annotation.JsonIgnore
  public Users getCheckedByUser() {
    if ((this.checkedBy != null) && (this.checkedBy > 0))
      return Instance.get(UsersRepository.class).findBy(this.checkedBy);
    return null;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}