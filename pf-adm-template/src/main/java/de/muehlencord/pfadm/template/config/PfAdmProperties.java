package de.muehlencord.pfadm.template.config;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * properties available to configure the template look and feel.
 *
 * @author Joern Muehlencord, 2025-04-14
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "pf-adm")
@Setter
@Getter
@Validated
public class PfAdmProperties implements Serializable {

  /* *** adminface inspired / compatible *** */
//  private Properties adminConfigFile;//default config
//  private Properties userConfigFile;//user defined properties
//  private String loginPage;
  private String indexPage = "index.xhtml";
  private String dateFormat;
  // path to template in use */
  private String templatePath = "/admin.xhtml";
  // breadCrumbMaxSize: intentionally NOT implemented - it caps AdminFaces'
  // automatic visited-history breadcrumb, which pf-adm replaces with an
  // explicit, hierarchical breadcrumb (deliberate non-goal). Do not wire up.
  //  private Integer breadCrumbMaxSize;
  private boolean renderMessages = true;
  private boolean skipMessageDetailIfEqualsSummary = true;
  //  private boolean renderAjaxStatus;
//  private boolean disableFilter;
//  private boolean enableRipple;
  private boolean renderBreadCrumb = true;
  //  private boolean extensionLessUrls;
  private boolean enableSlideMenu = true;
  //  private String rippleElements;
  private SkinEnum skin = SkinEnum.SKIN_BLUE;
  //  private boolean autoShowNavbar;
//  private String ignoredResources;//comma separated resources (pages or urls) to be ignored in AdminFilter
//  private String loadingImage;
//  private boolean renderControlSidebar;
  private boolean leftMenuTemplate = true;
  private boolean renderMenuSearch = false;
  private boolean renderFormAsterisks = false;
//  private boolean closableLoading;
//  private boolean enableMobileHeader;
  //controlsidebar
//  private ControlSidebarConfig controlSidebar;
//  private String pageSuffix;
//  private boolean rippleMobileOnly;
//  private String messagesHideTimeout;
//  private boolean autoHideMessages;
//  private boolean iconsEffect;


  // pf-adm only
  private boolean renderLogo = true;
  private boolean renderBrandText = false;
  private boolean renderFullScreenToggle = false;
  private boolean renderSlideMenuToggle = true;
  private boolean renderScrollToTop = false;
  private boolean supportBootstrapIcons = false;
  private boolean supportFontAwesome = false;

  private String templateTopPath = "/admin-top.xhtml";

  @NestedConfigurationProperty
  private ErrorPageProperties error = new ErrorPageProperties();


  public PfAdmProperties() {
    if (!StringUtils.hasText(dateFormat)) {
      dateFormat = ((SimpleDateFormat) DateFormat.getDateTimeInstance()).toLocalizedPattern();
    }
  }
}
