package currency.application;

import currency.domain.CurrencyDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class CurrencyService {

  public void convert(CurrencyDTO currency, int amount){

  }

}
