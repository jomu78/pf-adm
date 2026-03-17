package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Car;
import de.muehlencord.pfadm.showcase.service.CarService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 * control TabView page
 *
 * @author Joern Muehlencord, 2026-01-25
 * @since 0.2.0
 */
@ViewScoped
@Named
@Getter
@Setter
public class TabView implements Serializable {

  private List<Car> cars;

  @Inject
  public TabView(CarService carService) {
    this.cars = carService.createCars(10);
  }

  public void onTabChange(TabChangeEvent<?> event) {
    FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onTabClose(TabCloseEvent<?> event) {
    FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
}
