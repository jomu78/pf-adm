package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Car;
import de.muehlencord.pfadm.showcase.model.InventoryStatus;
import de.muehlencord.pfadm.showcase.model.Product;
import de.muehlencord.pfadm.showcase.service.CarService;
import de.muehlencord.pfadm.showcase.service.ProductService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;

/**
 * Backing bean for the showcase dashboard page.
 *
 * @author Joern Muehlencord, 2026-03-23
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
public class DashboardView implements Serializable {

  private final List<Product> recentProducts;
  private final List<Car> recentCars;
  private final int productCount;
  private final int inStockCount;
  private final int lowStockCount;
  private final int soldCars;
  private final double inventoryValue;

  @Inject
  public DashboardView(ProductService productService, CarService carService) {
    var products = productService.getProducts();
    var cars = carService.createCars(6);

    recentProducts = products.stream()
      .sorted(Comparator.comparingInt(Product::getId).reversed())
      .limit(5)
      .toList();
    recentCars = cars;
    productCount = products.size();
    inStockCount = (int) products.stream()
      .filter(product -> product.getInventoryStatus() == InventoryStatus.INSTOCK)
      .count();
    lowStockCount = (int) products.stream()
      .filter(product -> product.getInventoryStatus() == InventoryStatus.LOWSTOCK)
      .count();
    soldCars = (int) cars.stream()
      .filter(Car::isSold)
      .count();
    inventoryValue = products.stream()
      .mapToDouble(product -> product.getPrice() * product.getQuantity())
      .sum();
  }

  public String getInventoryValueLabel() {
    return String.format("$%,.0f", inventoryValue);
  }

  public String getProductStatusClass(Product product) {
    return switch (product.getInventoryStatus()) {
      case INSTOCK -> "text-bg-success";
      case LOWSTOCK -> "text-bg-warning";
      case OUTOFSTOCK -> "text-bg-danger";
    };
  }
}
