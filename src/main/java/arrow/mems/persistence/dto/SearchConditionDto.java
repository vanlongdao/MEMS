//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.SearchCondition;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class SearchConditionDto extends SearchCondition {
  private int searchCondId;

  @Override
  public int getSearchCondId() {
    return this.searchCondId;
  }

  public void setSearchCondId(final int searchCondId) {
    this.searchCondId = searchCondId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}