package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.showcase.model.Product;
import de.muehlencord.pfadm.showcase.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

/**
 * backing bean for data grid page
 *
 * @author Joern Muehlencord, 2025-10-16
 * @since 0.1.0
 */
@Named
@ViewScoped
public class DataGridView implements Serializable {

  @Getter
  private List<Product> products;

  @Getter
  @Setter
  private Product selectedProduct;

  @Getter
  private final ProductService service;

  @Inject
  public DataGridView(ProductService service) {
    this.service = service;
  }

  @PostConstruct
  public void init() {
    products = service.getProducts(48);
  }

  public void clearMultiViewState() {
    FacesContext context = FacesContext.getCurrentInstance();
    String viewId = context.getViewRoot().getViewId();
    PrimeFaces.current().multiViewState().clearAll(viewId, true, this::showMessage);
  }

  private void showMessage(String clientId) {
    FacesContext.getCurrentInstance()
      .addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
  }
}
