package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Car;
import de.muehlencord.pfadm.showcase.model.Stats;
import de.muehlencord.pfadm.showcase.model.Team;
import de.muehlencord.pfadm.showcase.service.CarService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * backing bean for datatable page
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@ViewScoped
@Named("datatableMB")
@Getter
@Setter
public class DatatableMB implements Serializable {

  private final CarService carService;

  private List<Team> teams;
  private List<Car> cars;
  private Car selectedCar;
  private List<String> selectedColors;

  private List<Car> filteredCars;


  @Inject
  public DatatableMB(CarService carService) {
    this.carService = carService;

    teams = new ArrayList<Team>();
    selectedColors = new ArrayList<>();
    Team lakers = new Team("Los Angeles Lakers");
    lakers.getStats().add(new Stats("2005-2006", 50, 32));
    lakers.getStats().add(new Stats("2006-2007", 44, 38));
    lakers.getStats().add(new Stats("2007-2008", 40, 42));
    lakers.getStats().add(new Stats("2008-2009", 45, 37));
    lakers.getStats().add(new Stats("2009-2010", 48, 34));
    lakers.getStats().add(new Stats("2010-2011", 42, 42));
    teams.add(lakers);

    Team celtics = new Team("Boston Celtics");
    celtics.getStats().add(new Stats("2005-2006", 46, 36));
    celtics.getStats().add(new Stats("2006-2007", 50, 32));
    celtics.getStats().add(new Stats("2007-2008", 41, 41));
    celtics.getStats().add(new Stats("2008-2009", 45, 37));
    celtics.getStats().add(new Stats("2009-2010", 38, 44));
    celtics.getStats().add(new Stats("2010-2011", 35, 47));
    teams.add(celtics);

    cars = carService.createCars(30);
  }

  public List<String> getBrands() {
    return carService.getBrands();
  }

  public List<String> getColors() {
    return carService.getColors();
  }

  public int getRandomPrice() {
    return (int) (Math.random() * 100000);
  }

}
