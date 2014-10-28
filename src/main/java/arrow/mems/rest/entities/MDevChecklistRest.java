package arrow.mems.rest.entities;

import java.time.LocalDateTime;

public class MDevChecklistRest {
  private String cklistCode;
  private String name;
  private LocalDateTime createAt;
  private int createBy;

  public String getCklistCode() {
    return this.cklistCode;
  }

  public void setCklistCode(String pCklistCode) {
    this.cklistCode = pCklistCode;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String pName) {
    this.name = pName;
  }

  public LocalDateTime getCreateAt() {
    return this.createAt;
  }

  public void setCreateAt(LocalDateTime pCreateAt) {
    this.createAt = pCreateAt;
  }

  public int getCreateBy() {
    return this.createBy;
  }

  public void setCreateBy(int pCreateBy) {
    this.createBy = pCreateBy;
  }

}
