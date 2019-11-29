package currencyConverter.integration;
import currencyConverter.model.Currency;
import currencyConverter.model.CurrencyDTO;
import javax.ejb.*;
import javax.persistence.*;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CurrencyDAO {
  @PersistenceContext(unitName = "currencyPU")
  private EntityManager em;

  public CurrencyDTO findCurrency(String name){
    Currency currency = em.find(Currency.class, name);
    if(currency == null){
      throw new EntityNotFoundException("Currency not found");
    }
    return currency;
  }

  public void storeCurrency(Currency currency){
    em.persist(currency);
  }

}
