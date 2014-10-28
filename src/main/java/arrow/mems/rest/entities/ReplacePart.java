package arrow.mems.rest.entities;

public class ReplacePart {
  private String devCode;
  private String removed_devCode;
  private String modelNo;
  private String serialNo;
  private String old_serialNo;

  public String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(String pDevCode) {
    this.devCode = pDevCode;
  }

  public String getRemoved_devCode() {
    return this.removed_devCode;
  }

  public void setRemoved_devCode(String pRemoved_devCode) {
    this.removed_devCode = pRemoved_devCode;
  }

  public String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(String pModelNo) {
    this.modelNo = pModelNo;
  }

  public String getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(String pSerialNo) {
    this.serialNo = pSerialNo;
  }

  public String getOld_serialNo() {
    return this.old_serialNo;
  }

  public void setOld_serialNo(String pOld_serialNo) {
    this.old_serialNo = pOld_serialNo;
  }


}
