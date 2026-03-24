package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Product;
import de.muehlencord.pfadm.showcase.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
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
public class DataView implements Serializable {
  private static final Logger logger = LoggerFactory.getLogger(DataView.class);

  @Getter
  private List<Product> products;

  @Getter
  private final ProductService service;

  @Inject
  public DataView(ProductService service) {
    this.service = service;
  }

  @PostConstruct
  public void init() {
    products = service.getProducts(24);
  }

  public void addToCart(Product product) {
    logger.info("{} - addToCart called", product.getName());
    var message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Added to cart", product.getName());
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

}
