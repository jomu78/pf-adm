package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Product;
import jakarta.el.MethodExpression;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataView example page view bean.
 *
 * @author Joern Muehlencord, 2026-03-17
 * @since 0.2.0
 */
@ViewScoped
@Named
@Getter
@Setter
public class DataView implements Serializable {
  private static final Logger logger = LoggerFactory.getLogger(DataView.class);

  public void addToCard(Product p) {
    logger.info ("{} - addToCard called", p.getName());
  }

}
