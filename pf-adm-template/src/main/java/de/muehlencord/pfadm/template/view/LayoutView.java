package de.muehlencord.pfadm.template.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * control layout settings
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Named
@SessionScoped
@Getter
@Setter
public class LayoutView implements Serializable {

  @Serial
  private static final long serialVersionUID = 5274204394411925524L;

  private static final Logger logger = LoggerFactory.getLogger(LayoutView.class);

  //template bundled in admin-template
  private static final String DEFAULT_TEMPLATE = "/admin.xhtml";
  //template bundled in admin-template
  private static final String DEFAULT_TEMPLATE_TOP = "/admin-top.xhtml";
  // application template (left menu)
  private static final String DEFAULT_APP_TEMPLATE_PATH = "/templates/template.xhtml";

  //application template (top menu)
  private static final String DEFAULT_APP_TEMPLATE_TOP_PATH = "/templates/template-top.xhtml";
  private static final String RESOURCES_PREFIX = "";
  // template webapp prefix path
  private static final String WEBAPP_PREFIX = "/WEB-INF";

  protected PfAdmConfig pfAdmConfig;
  protected String appTemplatePath;
  protected String appTemplateTopPath;

  protected String template;
  protected Boolean leftMenuTemplate;

  @Inject
  public LayoutView(PfAdmConfig pfAdmConfig) {
    this.pfAdmConfig = pfAdmConfig;
    this.appTemplatePath = templateExists(pfAdmConfig.getTemplatePath())
      ? findTemplate(pfAdmConfig.getTemplatePath(), DEFAULT_TEMPLATE)
      : findTemplate(DEFAULT_APP_TEMPLATE_PATH, DEFAULT_TEMPLATE);
    this.appTemplateTopPath = templateExists(pfAdmConfig.getTemplateTopPath())
      ? findTemplate(pfAdmConfig.getTemplateTopPath(), DEFAULT_TEMPLATE_TOP)
      : findTemplate(DEFAULT_APP_TEMPLATE_TOP_PATH, DEFAULT_TEMPLATE_TOP);

    if (pfAdmConfig.isLeftMenuTemplate()) {
      this.leftMenuTemplate = true;
      this.template = appTemplatePath;
    } else {
      this.leftMenuTemplate = false;
      this.template = appTemplateTopPath;
    }
  }

  public void setLeftMenuTemplate(Boolean leftMenuTemplate) {
    this.leftMenuTemplate = leftMenuTemplate != null && leftMenuTemplate;
    if (this.leftMenuTemplate.equals(true)) {
      this.template = appTemplatePath;
    } else {
      this.template = appTemplateTopPath;
    }
  }


  private String findTemplate(String appTemplatePath, String bundledPath) {
    String result;
    if (templateExists(WEBAPP_PREFIX + appTemplatePath)) {
      result = WEBAPP_PREFIX + appTemplatePath;
    } else if (templateExists(RESOURCES_PREFIX + appTemplatePath)) {
      result = RESOURCES_PREFIX + appTemplatePath;
    } else {
      result = bundledPath;
    }
    return result;
  }

  private boolean templateExists(String templateName) {
    if (templateName == null) {
      return false;
    }
    try {
      ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
      return ec.getResourceAsStream(templateName) != null;
    } catch (Exception e) {
      logger.error("Could not find application defined template in path '{}' due to following error: {}. " +
          "Falling back to default admin template. See application template documentation for more details"
        // FIXME - add own documentation and link here
//        ": https://github.com/adminfaces/admin-template#application-template"
        , appTemplatePath, e.getMessage());
      return false;
    }
  }
}
