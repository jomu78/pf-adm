package de.muehlencord.pfbs.template.config;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2025-07-30
 * @since TODO - add version
 */
public class SkinEnumValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return String.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors,"skin", "field.required");

    String skin = (String) target;
    SkinEnum skinEnum = SkinEnum.fromLabel(skin);
    if (skinEnum == null) {
      errors.rejectValue("skin", "field.enum.required", new Object[]{skin}, "not a a valid skin");
    }
  }
}
