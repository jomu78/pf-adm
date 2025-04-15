package de.muehlencord.pfbs.template;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO Add a short description of the class
 *
 * @author Joern Muehlencord, 2025-04-14
 * @since TODO - add version
 */
@Configuration
@ConfigurationProperties(prefix = "pfbs")
//@Data
public class PfbsProperties {

//  private Properties adminConfigFile;//default config
//  private Properties userConfigFile;//user defined properties
//  private String loginPage;
//  private String indexPage;
//  private String dateFormat;
//  private String templatePath;
//  private Integer breadCrumbMaxSize;
//  private boolean renderMessages;
//  private boolean skipMessageDetailIfEqualsSummary;
//  private boolean renderAjaxStatus;
//  private boolean disableFilter;
//  private boolean enableRipple;
//  private boolean renderBreadCrumb;
//  private boolean extensionLessUrls;
  private boolean enableSlideMenu = true;
//  private String rippleElements;
//  private String skin;
//  private boolean autoShowNavbar;
//  private String ignoredResources;//comma separated resources (pages or urls) to be ignored in AdminFilter
//  private String loadingImage;
//  private boolean renderControlSidebar;
//  private boolean leftMenuTemplate;
//  private boolean renderMenuSearch;
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


  public void setEnableSlideMenu(boolean enableSlideMenu) {
    this.enableSlideMenu = enableSlideMenu;
  }

  public boolean isEnableSlideMenu() {
    return enableSlideMenu;
  }
}
