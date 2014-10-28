//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.mapped.ChecklistItemMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="CHECKLIST_ITEM")
public class ChecklistItem extends ChecklistItemMapped {

  public ChecklistItem() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REFER_CKI_CODE", referencedColumnName = "CKI_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.MdevChecklistItem mdevChecklistItem;

  public arrow.mems.persistence.entity.MdevChecklistItem getMdevChecklistItem() {
    return this.mdevChecklistItem;
  }

  public void setMdevChecklistItem(arrow.mems.persistence.entity.MdevChecklistItem mdevChecklistItem) {
    this.mdevChecklistItem = mdevChecklistItem;
    if (this.mdevChecklistItem != null) {
      this.referCkiCode = this.mdevChecklistItem.getCkiCode();
      this.isDeleted = this.mdevChecklistItem.getIsDeleted();
    }
  }

  @Transient
  private String fakeId;

  public void setFakeId(String pFakeId) {
    this.fakeId = pFakeId;
  }

  public String getFakeId() {
    if (StringUtils.isEmpty(this.fakeId)) {
      this.fakeId = UUID.randomUUID().toString();
    }
    return this.fakeId;
  }

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}