package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;

/**
 * view to test exception handling
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@ViewScoped
@Named("exceptionView")
public class ExceptionView {

  public void throwUnsupportedOperationException()  {
    throw new UnsupportedOperationException ("An UnsupportedOperationException");
  }

  public void throwNullPointerException()  {
    throw new NullPointerException ("A NullPointerException");
  }

  public void throwViewExpiredException()  {
    throw new ViewExpiredException("A ViewExpiredException!");
  }

  public void throwOptimisticLockException()  {
    throw new OptimisticLockException("A OptimisticLockException!");
  }

}
