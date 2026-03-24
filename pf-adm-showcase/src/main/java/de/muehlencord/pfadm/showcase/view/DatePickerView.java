package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * backing bean for the date picker showcase page
 *
 * @author Joern Muehlencord, 2026-03-19
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class DatePickerView implements Serializable {

  private Date legacyDate = new Date();
  private Date legacyYear = new Date();

  private LocalDate combinedDate = LocalDate.now().plusDays(1);
  private LocalDate date = LocalDate.now();
  private LocalDate inlineDate = LocalDate.now().plusDays(3);
  private LocalDateTime appointment = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
}
