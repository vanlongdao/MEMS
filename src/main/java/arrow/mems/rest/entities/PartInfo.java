package arrow.mems.rest.entities;

public class PartInfo {
  private String devCode;
  private String serialNo;
  private String modelNo;

  public String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(String pDevCode) {
    this.devCode = pDevCode;
  }

  public String getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(String pSerialNo) {
    this.serialNo = pSerialNo;
  }

  public String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(String pModelNo) {
    this.modelNo = pModelNo;
  }


}
