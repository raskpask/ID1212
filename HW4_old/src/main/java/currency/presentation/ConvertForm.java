package currency.presentation;

import currency.util.Util;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ConvertForm {
  /**
   * A form bean for the deposit form.
   */
    @NotNull(message = "Please specify amount")
    @Positive(message = "Amount must be greater than zero")
    private Integer amount;
    private String currency;

    /**
     * @return The amount of the searched account.
     */
    public Integer getAmount() {
      return this.amount;
    }

    public String getCurrency(){
      return this.currency;
    }

    /**
     * @param amount The amount of the searched account.
     */
    public void setAmount(Integer amount) {
      this.amount = amount;
    }

    public void setCurrency(String currency){
      this.currency = currency;
    }

    @Override
    public String toString() {
      return Util.toString(this);
    }
  }

