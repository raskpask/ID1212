package currencyConverter.controller;

import currencyConverter.integration.CurrencyDAO;
import currencyConverter.integration.CurrencyRepository;
import currencyConverter.model.Convert;
import currencyConverter.model.Currency;
import java.util.Optional;
import javax.ejb.EJB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConvertController {

  @Autowired
  private CurrencyRepository currencyRepository;

  @GetMapping("/convert")
  public String greetingForm(Model model) {
    model.addAttribute("convert", new Convert());
    return "convert";
  }

  @PostMapping("/convert")
  public String greetingSubmit(@ModelAttribute Convert convert) {
    double amount = convert.getAmount();
    convert.setAmountBefore(amount);
    String currency = convert.getFromCurrency();
    String toCurrency = convert.getToCurrency();
    Optional<Currency> f = currencyRepository.findById(currency);
    if(!f.isPresent()){
      return "result";
    }
    switch(toCurrency){
      case "SEK":
        convert.setAmount(amount * f.get().getSEKconversionRate());
        break;
      case "EUR":
        convert.setAmount(amount * f.get().getEURconversionRate());
        break;
      case "USD" :
        convert.setAmount(amount * f.get().getUSDconversionRate());
        break;
      case "DKK":
        convert.setAmount(amount * f.get().getDKKconversionRate());
        break;
      default:
        convert.setToCurrency("currency not exists");
    }
    return "result";
  }

  private void setRates(){
    currencyRepository.save(new Currency("SEK",
        0.095,
        1,
        0.71,
        0.1));
    currencyRepository.save(new Currency("DKK",
        0.13,
        1.41,
        1,
        0.15));
    currencyRepository.save(new Currency("EUR",
        1,
        10.53,
        7.47,
        1.1));
    currencyRepository.save(new Currency("USD",
        0.91,
        9.56,
        6.78,
        1));
  }
}
