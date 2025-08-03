package de.muehlencord.pfadm.showcase.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * a Team model
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@Data
public class Team implements Serializable {

  private String name;
  private List<Stats> stats;

  public Team(String name) {
    this.name = name;
    this.stats = new ArrayList<>();
  }

  public int getAllWins() {
    int sum = 0;

    for (Stats s : stats) {
      sum += s.getWin();
    }

    return sum;
  }

  public int getAllLosses() {
    int sum = 0;

    for (Stats s : stats) {
      sum += s.getLoss();
    }

    return sum;
  }
}
