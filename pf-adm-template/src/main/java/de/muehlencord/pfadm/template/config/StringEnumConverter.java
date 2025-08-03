package de.muehlencord.pfadm.template.config;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2025-07-30
 * @since TODO - add version
 */
@Component
public class StringEnumConverter implements Converter<String, SkinEnum> {
  @Override
  public SkinEnum convert(String source) {
    var rv = SkinEnum.fromLabel(source);
    if (rv == null) {
      throw new ConversionFailedException(
        TypeDescriptor.valueOf(String.class),
        TypeDescriptor.valueOf(SkinEnum.class),
        source, new Exception("source is not a valid skin"));
    }
    return rv;
  }
}
