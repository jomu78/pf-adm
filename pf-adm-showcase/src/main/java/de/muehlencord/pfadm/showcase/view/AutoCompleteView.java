package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.service.CarService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;

/**
 * backing bean for auto complete showcase page
 *
 * @author Joern Muehlencord, 2026-03-19
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class AutoCompleteView implements Serializable {

  private final CarService carService;

  private String selectedBrand;
  private String dropdownBrand;
  private String forcedSelectionColor;
  private List<String> selectedColors;

  @Inject
  public AutoCompleteView(CarService carService) {
    this.carService = carService;
    this.selectedBrand = "Audi";
    this.dropdownBrand = "Volkswagen";
    this.forcedSelectionColor = "Silver";
    this.selectedColors = new ArrayList<>();
    this.selectedColors.add("Blue");
    this.selectedColors.add("Black");
  }

  public List<String> completeBrand(String query) {
    return filterValues(carService.getBrands(), query);
  }

  public List<String> completeColor(String query) {
    return filterValues(carService.getColors(), query);
  }

  private List<String> filterValues(List<String> values, String query) {
    String normalizedQuery = query == null ? "" : query.toLowerCase(Locale.ROOT).trim();
    List<String> filtered = new ArrayList<>();
    for (String value : values) {
      if (normalizedQuery.isEmpty() || value.toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
        filtered.add(value);
      }
    }
    return filtered;
  }
}
