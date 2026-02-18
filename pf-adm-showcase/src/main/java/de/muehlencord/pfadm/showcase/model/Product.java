package de.muehlencord.pfadm.showcase.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * example product dto
 *
 * @author Joern Muehlencord, 2025-10-16
 * @since 0.1.0
 */
@NoArgsConstructor
@Data
public class Product implements Serializable {

  private int id;
  private String code;
  private String name;
  private String description;
  private String image;
  private double price;
  private String category;
  private int quantity;
  private InventoryStatus inventoryStatus;
  private int rating;
  private List<Order> orders;

  public Product(int id, String code, String name, String description, String image, double price, String category, int quantity,
                 InventoryStatus inventoryStatus, int rating) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.image = image;
    this.price = price;
    this.category = category;
    this.quantity = quantity;
    this.inventoryStatus = inventoryStatus;
    this.rating = rating;
  }

  @Override
  public Product clone() {
    return new Product(getId(), getCode(), getName(), getDescription(), getImage(), getPrice(), getCategory(), getQuantity(),
      getInventoryStatus(), getRating());
  }

}
