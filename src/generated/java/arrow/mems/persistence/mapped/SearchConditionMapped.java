//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractDeletable;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class SearchConditionMapped extends AbstractDeletable {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.SearchCondition> getEntityClass() {
    return arrow.mems.persistence.entity.SearchCondition.class;
  }
  
  //default constructor
  public SearchConditionMapped() {
  }
  
  @Column(name="SEARCH_COND_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_condition_seq_gen")
  @SequenceGenerator(name = "search_condition_seq_gen", sequenceName = "search_condition_search_cond_id_seq", allocationSize=1)
  protected int searchCondId;
  
  public int getSearchCondId() {
    return this.searchCondId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.searchCondId;
  }

  //Normal columns
  @Column(name="CONDITION")
  protected java.lang.String condition;

  public java.lang.String getCondition() {
    return this.condition;
  }

  public void setCondition(java.lang.String condition) {
    this.condition = condition;
  }
  @Column(name="COND_FORMAT_VER")
  protected java.lang.Integer condFormatVer;

  public java.lang.Integer getCondFormatVer() {
    return this.condFormatVer;
  }

  public void setCondFormatVer(java.lang.Integer condFormatVer) {
    this.condFormatVer = condFormatVer;
  }
  @Column(name="CREATOR_DISPLAY")
  protected java.lang.Integer creatorDisplay;

  public java.lang.Integer getCreatorDisplay() {
    return this.creatorDisplay;
  }

  public void setCreatorDisplay(java.lang.Integer creatorDisplay) {
    this.creatorDisplay = creatorDisplay;
  }
  @Column(name="LABEL")
  protected java.lang.String label;

  public java.lang.String getLabel() {
    return this.label;
  }

  public void setLabel(java.lang.String label) {
    this.label = label;
  }
  @Column(name="OFFICE_CODE")
  protected java.lang.String officeCode;

  public java.lang.String getOfficeCode() {
    return this.officeCode;
  }

  public void setOfficeCode(java.lang.String officeCode) {
    this.officeCode = officeCode;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}