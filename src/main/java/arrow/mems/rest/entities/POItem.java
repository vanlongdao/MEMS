package arrow.mems.rest.entities;

public class POItem {
  private int poID;
  private String actionCode;
  private int createdBy;
  private String poCode;
  private String modelNo;

  public int getPoID() {
    return this.poID;
  }

  public void setPoID(int pPoID) {
    this.poID = pPoID;
  }

  public String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(String pActionCode) {
    this.actionCode = pActionCode;
  }

  public int getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(int pCreatedBy) {
    this.createdBy = pCreatedBy;
  }

  public String getPoCode() {
    return this.poCode;
  }

  public void setPoCode(String pPoCode) {
    this.poCode = pPoCode;
  }

  public String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(String pModelNo) {
    this.modelNo = pModelNo;
  }

}
