package currencyConverter.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Convert {

  private double amount;
  private String resultAmount;
  private double amountBefore;
  private String fromCurrency;
  private String toCurrency;

  public String getResultAmount() {
    return resultAmount;
  }

  public void setResultAmount(String resultAmount) {
    this.resultAmount = resultAmount;
  }


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
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    this.amount = amount;
    this.resultAmount = df.format(amount);
  }

  public String getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(String fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

}
