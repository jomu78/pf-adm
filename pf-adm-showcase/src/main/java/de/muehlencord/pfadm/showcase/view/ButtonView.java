package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * backing bean for the buttons showcase page
 *
 * @author Joern Muehlencord, 2026-03-19
 * @since 0.2.0
 */
@Named
@ViewScoped
public class ButtonView implements Serializable {

  public void buttonAction() {
    addMessage("Action executed", "Button action completed.");
  }

  public void save() {
    addMessage("Saved", "Primary split-button action executed.");
  }

  public void update() {
    addMessage("Updated", "Update action executed.");
  }

  public void delete() {
    addMessage("Deleted", "Delete action executed.");
  }

  private void addMessage(String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(null,
      new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
  }
}
