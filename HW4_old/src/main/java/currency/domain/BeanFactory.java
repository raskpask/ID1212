package currency.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanFactory implements ApplicationContextAware {
  private static ApplicationContext context;

  static <T> T getBean(Class<T> beanClass){
    return context.getBean(beanClass);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}
