package arrow.mems.rest.entities;

import java.time.LocalDate;

public class ChecklistRest {
  private String checklistName;
  private LocalDate checklistDate;
  private String cklistLogCode;
  private String hospPsn;
  private String measureDev1;
  private String measureDev2;
  private String referCklistCode;
  private int isDelete;

  public String getChecklistName() {
    return this.checklistName;
  }

  public void setChecklistName(String pChecklistName) {
    this.checklistName = pChecklistName;
  }

  public LocalDate getChecklistDate() {
    return this.checklistDate;
  }

  public void setChecklistDate(LocalDate pChecklistDate) {
    this.checklistDate = pChecklistDate;
  }

  public String getCklistLogCode() {
    return this.cklistLogCode;
  }

  public void setCklistLogCode(String pCklistLogCode) {
    this.cklistLogCode = pCklistLogCode;
  }

  public String getHospPsn() {
    return this.hospPsn;
  }

  public void setHospPsn(String pHospPsn) {
    this.hospPsn = pHospPsn;
  }

  public String getMeasureDev1() {
    return this.measureDev1;
  }

  public void setMeasureDev1(String pMeasureDev1) {
    this.measureDev1 = pMeasureDev1;
  }

  public String getMeasureDev2() {
    return this.measureDev2;
  }

  public void setMeasureDev2(String pMeasureDev2) {
    this.measureDev2 = pMeasureDev2;
  }

  public String getReferCklistCode() {
    return this.referCklistCode;
  }

  public void setReferCklistCode(String pReferCklistCode) {
    this.referCklistCode = pReferCklistCode;
  }

  public int getIsDelete() {
    return this.isDelete;
  }

  public void setIsDelete(int pIsDelete) {
    this.isDelete = pIsDelete;
  }


}
