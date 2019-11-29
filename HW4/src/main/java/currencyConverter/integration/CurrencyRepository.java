package currencyConverter.integration;

import org.springframework.data.repository.CrudRepository;
import currencyConverter.model.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, String> {

}
