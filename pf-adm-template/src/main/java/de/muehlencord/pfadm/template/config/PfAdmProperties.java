package de.muehlencord.pfadm.template.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * properties available to configure the template look and feel.
 *
 * @author Joern Muehlencord, 2025-04-14
 * @since 0.1.0
 */
@Configuration
@ConfigurationProperties(prefix = "pf-adm")
@Setter
@Getter
@Validated
public class PfAdmProperties {

  /* *** adminface inspired / compatible *** */
//  private Properties adminConfigFile;//default config
//  private Properties userConfigFile;//user defined properties
//  private String loginPage;
  private String indexPage = "index.xhtml";
//  private String dateFormat;
//  private String templatePath;
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
//  private boolean leftMenuTemplate;
  private boolean renderMenuSearch = false;
//  private boolean renderFormAsterisks;
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



}
