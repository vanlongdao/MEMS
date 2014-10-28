package arrow.mems.rest.entities;

public class ActionLogRest {
  private int actionId;
  private String actionCode;
  private int devId;
  private String devCode;

  public int getActionId() {
    return this.actionId;
  }

  public void setActionId(int pActionId) {
    this.actionId = pActionId;
  }

  public String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(String pActionCode) {
    this.actionCode = pActionCode;
  }

  public int getDevId() {
    return this.devId;
  }

  public void setDevId(int pDevId) {
    this.devId = pDevId;
  }

  public String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(String pDevCode) {
    this.devCode = pDevCode;
  }


}
