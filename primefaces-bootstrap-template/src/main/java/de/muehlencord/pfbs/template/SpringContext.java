package de.muehlencord.pfbs.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2025-04-15
 * @since TODO - add version
 */
@Component
public class SpringContext implements ApplicationContextAware {

  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext ac) {
    context = ac;
  }

  public static <T> T getBean(Class<T> requiredType) {
    return context.getBean(requiredType);
  }
}

