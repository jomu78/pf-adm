package de.muehlencord.pfadm.showcase.model;

/**
 * product inventory status entity.
 *
 * @author Joern Muehlencord, 2025-10-16
 * @since 0.1.0
 */
public enum InventoryStatus {
  INSTOCK("In Stock"),
  OUTOFSTOCK("Out of Stock"),
  LOWSTOCK("Low Stock");

  private String text;

  InventoryStatus(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
