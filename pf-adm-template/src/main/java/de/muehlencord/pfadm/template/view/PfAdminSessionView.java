package de.muehlencord.pfadm.template.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Session support methods for error navigation.
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Named
@SessionScoped
public class PfAdminSessionView implements Serializable {

  private static final long serialVersionUID = 1L;

  public String getRequestRelativeURI() {
    var request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    var requestUriObject = request.getAttribute("jakarta.servlet.error.request_uri");
    if (!(requestUriObject instanceof String uriWithContextPath) || uriWithContextPath.isBlank()) {
      return null;
    }

    var contextPath = request.getContextPath();
    if (contextPath == null || contextPath.isEmpty() || !uriWithContextPath.startsWith(contextPath)) {
      return uriWithContextPath;
    }

    return uriWithContextPath.substring(contextPath.length());
  }
}
