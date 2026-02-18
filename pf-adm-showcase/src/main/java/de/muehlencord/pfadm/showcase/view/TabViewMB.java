package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Car;
import de.muehlencord.pfadm.showcase.service.CarService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2026-01-25
 * @since TODO - add version
 */
@ViewScoped
@Named("tabViewMB")
@Getter
@Setter
public class TabViewMB {

  private List<Car> cars;

  @Inject
  public TabViewMB(CarService carService) {
    this.cars = carService.createCars(10);
  }

  public void onTabChange(TabChangeEvent event) {
    FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onTabClose(TabCloseEvent event) {
    FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
}
