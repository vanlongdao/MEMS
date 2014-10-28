package arrow.mems.bean.data;

import arrow.mems.constant.CommonConstants;

public class SearchCriteria {

  private int type;

  private String param;

  private int operator;

  public int getType() {
    return this.type;
  }

  public void setType(int pType) {
    this.type = pType;
  }

  public String getParam() {
    return this.param;
  }

  public void setParam(String pParam) {
    this.param = pParam;
  }

  public int getOperator() {
    return this.operator;
  }

  public void setOperator(int pOperator) {
    this.operator = pOperator;
  }

  public SearchCriteria() {
    this.type = CommonConstants.PULLDOWN_MANUFACTORY_CONTACT_PERSON;
    this.operator = CommonConstants.PULLDOWN_EQUAL_TYPE;
  }
}
