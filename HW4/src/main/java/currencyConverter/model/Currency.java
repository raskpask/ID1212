package currencyConverter.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency implements CurrencyDTO, Serializable {

  @Id
  private String name;
  private double EURconversionRate;
  private double SEKconversionRate;
  private double DKKconversionRate;
  private double USDconversionRate;

  public Currency(String name, double euRconversionRate, double seKconversionRate,
      double dkKconversionRate, double usDconversionRate) {
    this.name = name;
    EURconversionRate = euRconversionRate;
    SEKconversionRate = seKconversionRate;
    DKKconversionRate = dkKconversionRate;
    USDconversionRate = usDconversionRate;
  }

  public Currency() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getEURconversionRate() {
    return EURconversionRate;
  }

  public void setEURconversionRate(float EURconversionRate) {
    this.EURconversionRate = EURconversionRate;
  }

  public double getSEKconversionRate() {
    return SEKconversionRate;
  }

  public void setSEKconversionRate(float SEKconversionRate) {
    this.SEKconversionRate = SEKconversionRate;
  }

  public double getDKKconversionRate() {
    return DKKconversionRate;
  }

  public void setDKKconversionRate(float DKKconversionRate) {
    this.DKKconversionRate = DKKconversionRate;
  }

  public double getUSDconversionRate() {
    return USDconversionRate;
  }

  public void setUSDconversionRate(float USDconversionRate) {
    this.USDconversionRate = USDconversionRate;
  }


}
