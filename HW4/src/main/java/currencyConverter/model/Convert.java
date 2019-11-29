package currencyConverter.model;

public class Convert {

  private double amount;
  private double amountBefore;
  private String fromCurrency;
  private String toCurrency;

  public double getAmountBefore() {
    return amountBefore;
  }

  public void setAmountBefore(double amountBefore) {
    this.amountBefore = amountBefore;
  }


  public String getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(String toCurrency) {
    this.toCurrency = toCurrency;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

}
