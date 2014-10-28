//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractDeletable;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class AbstractApprovalEntityMapped extends AbstractDeletable {

  //Normal columns
  @Column(name="CHECKED_AT")
  protected java.time.LocalDateTime checkedAt;

  public java.time.LocalDateTime getCheckedAt() {
    return this.checkedAt;
  }

  public void setCheckedAt(java.time.LocalDateTime checkedAt) {
    this.checkedAt = checkedAt;
  }
  @Column(name="CHECKED_BY")
  protected java.lang.Integer checkedBy;

  public java.lang.Integer getCheckedBy() {
    return this.checkedBy;
  }

  public void setCheckedBy(java.lang.Integer checkedBy) {
    this.checkedBy = checkedBy;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}