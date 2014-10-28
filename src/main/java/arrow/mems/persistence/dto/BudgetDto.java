//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Budget;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class BudgetDto extends Budget {
  private int budgetId;

  @Override
  public int getBudgetId() {
    return this.budgetId;
  }

  public void setBudgetId(final int budgetId) {
    this.budgetId = budgetId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MFM00010 + "}")
  public LocalDate getEndDate() {
    return super.getEndDate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00009 + "}")
  public LocalDate getStartDate() {
    return super.getStartDate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MFM00007 + "}")
  public Double getBudget() {
    return super.getBudget();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}