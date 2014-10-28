//@formatter:off
package arrow.mems.persistence.dto;

/* =================== Start import section after the marker line ================== */
/* =================== Please ensure all new imports go into this section ================== */

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.rest.serializer.LocalDateDeserializer;
import arrow.mems.rest.serializer.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/* =================== End of import section before the marker line =================== */
/* =================== There must be one blank line before the marker line =================== */

public class ActionLogDto extends ActionLog {
  private int actionId;

  @Override
  public int getActionId() {
    return this.actionId;
  }

  public void setActionId(final int actionId) {
    this.actionId = actionId;
  }

  // @formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Override
  @NotNull(message = "{" + MessageCode.MRR00016 + "}")
  public String getActionType() {
    return super.getActionType();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00017 + "}")
  public String getDevCode() {
    return super.getDevCode();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00018 + "}")
  public String getHospCode() {
    return super.getHospCode();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00019 + "}")
  public String getHospDeptCode() {
    return super.getHospDeptCode();
  }

  private String hospitalContactPersonName;
  private String supplierContactPersonName;
  private String hospitalDepartmentName;

  public String getHospitalContactPersonName() {
    return this.hospitalContactPersonName;
  }

  public void setHospitalContactPersonName(String pHospitalContactPersonName) {
    this.hospitalContactPersonName = pHospitalContactPersonName;
  }

  public String getSupplierContactPersonName() {
    return this.supplierContactPersonName;
  }

  public void setSupplierContactPersonName(String pSupplierContactPersonName) {
    this.supplierContactPersonName = pSupplierContactPersonName;
  }

  public String getHospitalDepartmentName() {
    return this.hospitalDepartmentName;
  }

  @Override
  @JsonSerialize(using = LocalDateSerializer.class)
  public LocalDate getOccurDate() {
    return super.getOccurDate();
  }

  @Override
  @JsonSerialize(using = LocalDateSerializer.class)
  public void setActionDate(LocalDate pActionDate) {
    super.setActionDate(pActionDate);
  }

  @Override
  @JsonSerialize(using = LocalDateSerializer.class)
  public void setContactDate(LocalDate pContactDate) {
    super.setContactDate(pContactDate);
  }

  @Override
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  public LocalDate getFinishDate() {
    return super.getFinishDate();
  }

  public void setHospitalDepartmentName(String pHospitalDepartmentName) {
    this.hospitalDepartmentName = pHospitalDepartmentName;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}