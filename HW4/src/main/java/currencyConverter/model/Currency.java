package currencyConverter.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency implements Serializable {

  @Id
  private String name;
  private double rate;

  public Currency(){}

  public Currency(String name, double rate) {
    this.name = name;
    this.rate = rate;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
