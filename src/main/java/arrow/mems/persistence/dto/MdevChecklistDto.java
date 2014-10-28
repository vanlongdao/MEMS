//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.entity.MdevChecklist;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MdevChecklistDto extends MdevChecklist {
  private int cklistId;

  @Override
  public int getCklistId() {
    return this.cklistId;
  }

  public void setCklistId(final int cklistId) {
    this.cklistId = cklistId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  private List<MdevChecklistItemDto> checklistItems = new ArrayList<MdevChecklistItemDto>();

  public List<MdevChecklistItemDto> getChecklistItems() {
    return this.checklistItems;
  }

  public void setChecklistItems(List<MdevChecklistItemDto> pChecklistItems) {
    this.checklistItems = pChecklistItems;
  }

  @Override
  @NotNull()
  public String getName() {
    return super.getName();
  }

  public boolean isNew() {
    return StringUtils.isEmpty(this.cklistCode);
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}