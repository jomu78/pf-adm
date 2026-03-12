package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.awt.event.ActionEvent;

/**
 * view to test exception handling
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@ViewScoped
@Named("exceptionView")
public class ExceptionView {

  public void raiseException(ActionEvent actionEvent)  {
    throw new UnsupportedOperationException ("Exception raised from bean");
  }

}
