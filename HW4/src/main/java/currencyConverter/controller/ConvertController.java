package currencyConverter.controller;

import currencyConverter.integration.CurrencyRepository;
import currencyConverter.model.Admin;
import currencyConverter.model.Convert;
import currencyConverter.model.Currency;
import java.util.Optional;
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

  @GetMapping("/admin")
  public String adminPage(Model model){
    model.addAttribute("admin", new Admin());
    return "admin";
  }

  @PostMapping("/admin")
  public String adminSubmit(@ModelAttribute Admin admin){
    double rate = admin.getRate();
    String fromCurrency = admin.getFromCurrency();
    currencyRepository.save(new Currency(fromCurrency, rate));
    return "admin";
  }

  @GetMapping("/")
  public String homePage(Model model){
    return "redirect:/convert";
  }

  @GetMapping("/convert")
  public String convertPage(Model model) {
    model.addAttribute("convert", new Convert());
    return "convert";
  }

  @PostMapping("/convert")
  public String convertSubmit(@ModelAttribute Convert convert) {
    double amount = convert.getAmount();
    convert.setAmountBefore(amount);
    Optional<Currency> dbObject = currencyRepository.findById(convert.getFromCurrency());
    Optional<Currency> toCurrencyObj = currencyRepository.findById(convert.getToCurrency());

    if(!dbObject.isPresent() || !toCurrencyObj.isPresent()){
      return "result";
    }
    Currency toCurrency = toCurrencyObj.get();
    double inSEK = amount * dbObject.get().getRate();

    if(toCurrency.getName().equals("SEK")){
      convert.setAmount(inSEK);
    } else {
      convert.setAmount(inSEK / toCurrency.getRate());
    }

    return "result";
  }

  private void setRates(){
    currencyRepository.save(new Currency("SEK", 1));
    currencyRepository.save(new Currency("DKK", 1.41));
    currencyRepository.save(new Currency("EUR", 10.53));
    currencyRepository.save(new Currency("USD", 9.56));
  }
}
