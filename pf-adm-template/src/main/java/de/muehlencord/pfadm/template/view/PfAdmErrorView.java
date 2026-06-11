package de.muehlencord.pfadm.template.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Request-scoped support methods for error pages.
 *
 * @author Joern Muehlencord, 2026-05-28
 * @since 0.3.0
 */
@Named
@RequestScoped
public class PfAdmErrorView implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(PfAdmErrorView.class);
  private static final String ERROR_ID_ATTRIBUTE = PfAdmErrorView.class.getName() + ".ERROR_ID";
  private static final String ERROR_LOGGED_ATTRIBUTE = PfAdmErrorView.class.getName() + ".ERROR_LOGGED";

  private final PfAdmConfig pfAdmConfig;
  private String errorId;

  @Inject
  public PfAdmErrorView(PfAdmConfig pfAdmConfig) {
    this.pfAdmConfig = pfAdmConfig;
  }

  @PostConstruct
  public void init() {
    if (!pfAdmConfig.getError().isReferenceIdEnabled()) {
      return;
    }

    var request = getRequest();
    if (request == null) {
      return;
    }

    var existingErrorId = request.getAttribute(ERROR_ID_ATTRIBUTE);
    if (existingErrorId instanceof String existing && !existing.isBlank()) {
      errorId = existing;
    } else {
      errorId = UUID.randomUUID().toString();
      request.setAttribute(ERROR_ID_ATTRIBUTE, errorId);
    }

    logErrorReference(request);
  }

  public boolean isDevelopmentProjectStage() {
    return FacesContext.getCurrentInstance().isProjectStage(ProjectStage.Development);
  }

  public boolean isReferenceIdAvailable() {
    return errorId != null && !errorId.isBlank();
  }

  public String getErrorId() {
    return errorId;
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
    var request = getRequest();
    if (request == null) {
      return null;
    }

    var exceptionTypeObj = request.getAttribute("jakarta.servlet.error.exception_type");
    if (exceptionTypeObj instanceof Class<?> clazz) {
      return clazz.getName();
    } else if (exceptionTypeObj instanceof String s) {
      return s;
    }
    return null;
  }

  private Throwable getLastException() {
    var request = getRequest();
    if (request == null) {
      return null;
    }

    var exceptionObj = request.getAttribute("jakarta.servlet.error.exception");
    if (exceptionObj instanceof Throwable th) {
      return th;
    }
    return null;
  }

  private HttpServletRequest getRequest() {
    var facesContext = FacesContext.getCurrentInstance();
    if (facesContext == null) {
      return null;
    }
    return (HttpServletRequest) facesContext.getExternalContext().getRequest();
  }

  private void logErrorReference(HttpServletRequest request) {
    if (Boolean.TRUE.equals(request.getAttribute(ERROR_LOGGED_ATTRIBUTE))) {
      return;
    }
    request.setAttribute(ERROR_LOGGED_ATTRIBUTE, Boolean.TRUE);

    var exception = getLastException();
    var statusCode = request.getAttribute("jakarta.servlet.error.status_code");
    var requestUri = request.getAttribute("jakarta.servlet.error.request_uri");

    if (exception == null) {
      logger.error("Error page rendered. errorId={}, statusCode={}, requestUri={}",
        errorId, statusCode, requestUri);
    } else if (pfAdmConfig.getError().isLogReferenceStacktrace()) {
      logger.error("Unhandled exception. errorId={}, statusCode={}, requestUri={}",
        errorId, statusCode, requestUri, exception);
    } else {
      logger.error("Unhandled exception. errorId={}, statusCode={}, requestUri={}, exceptionType={}, message={}",
        errorId, statusCode, requestUri, exception.getClass().getName(), ExceptionUtils.getRootCauseMessage(exception));
    }
  }
}
