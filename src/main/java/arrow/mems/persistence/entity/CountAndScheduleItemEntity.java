package arrow.mems.persistence.entity;

public class CountAndScheduleItemEntity {
  public static final int SCHEDULE_ITEM_TYPE = 1;
  public static final int COUNT_SCHEDULE_ITEM_TYPE = 2;

  // Item type equal 1 is schedule_item , equal 2 is count_schedule_item
  private int itemType;
  private String scheduleTile;
  private String code;
  private String cklistCode;
  private String actionCode;
  private String description;
  private String devCode;

  public int getItemType() {
    return this.itemType;
  }

  public void setItemType(int pItemType) {
    this.itemType = pItemType;
  }

  public String getScheduleTile() {
    return this.scheduleTile;
  }

  public void setScheduleTile(String pScheduleTile) {
    this.scheduleTile = pScheduleTile;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String pCode) {
    this.code = pCode;
  }

  public String getCklistCode() {
    return this.cklistCode;
  }

  public void setCklistCode(String pCklistCode) {
    this.cklistCode = pCklistCode;
  }

  public String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(String pActionCode) {
    this.actionCode = pActionCode;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String pDescription) {
    this.description = pDescription;
  }

  public String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(String pDevCode) {
    this.devCode = pDevCode;
  }
}
