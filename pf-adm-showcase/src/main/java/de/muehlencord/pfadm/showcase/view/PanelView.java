package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

/**
 * backing bean for the panel showcase page
 *
 * @author Joern Muehlencord, 2026-03-19
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class PanelView implements Serializable {

  private List<String> boxColors = List.of(
    "box-default",
    "box-primary",
    "box-success",
    "box-info",
    "box-warning",
    "box-danger",
    "box-fatal"
  );

  private String selectedColor = "box-primary";

  public void onClose(CloseEvent event) {
    addMessage("Panel closed", event.getComponent().getId());
  }

  public void onToggle(ToggleEvent event) {
    addMessage("Panel toggled", event.getVisibility().name());
  }

  private void addMessage(String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(null,
      new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
  }
}
