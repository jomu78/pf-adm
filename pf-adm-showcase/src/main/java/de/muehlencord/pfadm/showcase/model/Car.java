package de.muehlencord.pfadm.showcase.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * a car model
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

  public String id;
  public String brand;
  public Integer year;
  public String color;
  public int price;
  public boolean sold;

}
