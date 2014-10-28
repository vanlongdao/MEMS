package arrow.mems.bean.data;


public class RememberSearchCriteria {

  private int compareSearchType;

  private String rememberSearchContain;

  private boolean shareCondition;

  public boolean isShareCondition() {
    return this.shareCondition;
  }

  public void setShareCondition(boolean pShareCondition) {
    this.shareCondition = pShareCondition;
  }

  public int getCompareSearchType() {
    return this.compareSearchType;
  }

  public void setCompareSearchType(int pCompareSearchType) {
    this.compareSearchType = pCompareSearchType;
  }

  public String getRememberSearchContain() {
    return this.rememberSearchContain;
  }

  public void setRememberSearchContain(String pRememberSearchContain) {
    this.rememberSearchContain = pRememberSearchContain;
  }

}
