package de.muehlencord.pfbs.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2025-04-15
 * @since TODO - add version
 */
@AutoConfiguration
@ComponentScan(basePackages = {"de.muehlencord.pfbs"})
public class PfbsAutoConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(PfbsAutoConfiguration.class);


  public PfbsAutoConfiguration() {
    logger.info("PFBS enabled");
  }

}
