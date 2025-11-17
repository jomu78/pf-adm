package de.muehlencord.pfadm.showcase.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * product order entity
 *
 * @author Joern Muehlencord, 2025-10-16
 * @since 0.1.0
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

  private final int number;
  private final String imagePath;
}
