package de.muehlencord.pfadm.template.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * support methods for handling exception
 * TODO: rename to PfAdmErrorView and put to RequestScope if possible
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Named
@SessionScoped
public class PfAdminSessionView implements Serializable {

  private Throwable getLastException() {
    var request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    var exceptionObj = request.getAttribute("jakarta.servlet.error.exception");
    if (exceptionObj instanceof Throwable th) {
      return th;
    }
    return null;
  }

  public String getRootCauseMessage() {
    var ex = getLastException();
    if (ex == null) {
      return null;
    }
    return ExceptionUtils.getRootCauseMessage(ex);
  }

  public String getStackTrace() {
    var ex = getLastException();
    if (ex == null) {
      return null;
    }
    return ExceptionUtils.getStackTrace(ex);
  }

  public String getExceptionType() {
    var request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    var exceptionTypeObj = request.getAttribute("jakarta.servlet.error.exception_type");
    if (exceptionTypeObj instanceof Class<?> clazz) {
      return clazz.getName();
    } else if (exceptionTypeObj instanceof String s) {
      return s;
    }
    return null;
  }

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
