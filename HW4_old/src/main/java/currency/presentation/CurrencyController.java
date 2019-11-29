package currency.presentation;

import currency.application.CurrencyService;
import currency.domain.Currency;
import currency.domain.CurrencyDTO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Scope("session")
public class CurrencyController {
  static final String DEFAULT_PAGE_URL = "/";
  static final String CURRENCY_URL = "currency.html";
  static final String CONVERT_URL = "convert";
  private static final String CONVERT_FORM_OBJ_NAME = "convertForm";
  @Autowired
  private CurrencyService service;
  private CurrencyDTO currency;

  @GetMapping(DEFAULT_PAGE_URL)
  public String showDefaultView(){
    return "redirect:" + CURRENCY_URL;
  }

  @PostMapping("/" + CONVERT_URL)
  public String convert(@Valid @ModelAttribute(CONVERT_FORM_OBJ_NAME) ConvertForm convertForm, BindingResult bindingResult, Model model){
    return showConvertPage(model, new ConvertForm());
  }

  private String showConvertPage(Model model, ConvertForm convertForm) {
    model.addAttribute(CONVERT_FORM_OBJ_NAME, convertForm);
    return CURRENCY_URL;
  }

}
