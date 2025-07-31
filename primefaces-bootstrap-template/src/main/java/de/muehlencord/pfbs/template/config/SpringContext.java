package de.muehlencord.pfbs.template.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * applicationContextAware SpringContext
 *
 * @author Joern Muehlencord, 2025-04-15
 * @since 0.1.0
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

