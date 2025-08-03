package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Car;
import de.muehlencord.pfadm.showcase.service.CarService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.util.List;

/**
 * backing bean for datatable page
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@ViewScoped
@Named("datatableEditMB")
public class DatatableEditMB implements Serializable {

  private final CarService carService;

  @Getter
  @Setter
  private List<Car> cars1;
  @Getter
  @Setter
  private List<Car> cars2;

  @Inject
  public DatatableEditMB(CarService carService) {
    this.carService = carService;

    cars1 = carService.createCars(10);
    cars2 = carService.createCars(10);
  }

  public List<String> getBrands() {
    return carService.getBrands();
  }

  public List<String> getColors() {
    return carService.getColors();
  }

  public void onRowEdit(RowEditEvent<Car> event) {
    FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onRowCancel(RowEditEvent<Car> event) {
    FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onCellEdit(CellEditEvent<Car> event) {
    Car oldValue = event.getOldValue();
    Car newValue = event.getNewValue();

    if (newValue != null && !newValue.equals(oldValue)) {
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
  }
}
