package de.muehlencord.pfadm.showcase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * a statistic model
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stats implements Serializable {

  private String season;
  private int win;
  private int loss;
}
