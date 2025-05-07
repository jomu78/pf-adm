package de.muehlencord.pfbs.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * configure
 *
 * @author Joern Muehlencord, 2025-04-15
 * @since 0.1.0
 */
@AutoConfiguration
@ComponentScan(basePackages = {"de.muehlencord.pfbs"})
public class PfbsAutoConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(PfbsAutoConfiguration.class);


  public PfbsAutoConfiguration() {
    logger.info("PFBS enabled");
  }

}
