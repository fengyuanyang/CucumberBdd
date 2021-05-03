package com.ordestiny.bdd;

import com.ordestiny.bdd.enums.DriverType;
import com.ordestiny.bdd.enums.EnvironmentType;
import com.ordestiny.bdd.provider.ConfigReader;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager {
  private WebDriver driver;
  private static DriverType driverType;
  private static EnvironmentType environmentType;
  private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

  public WebDriverManager() {
    driverType = ConfigReader.getInstance().getBrowser();
    environmentType = ConfigReader.getInstance().getEnvironment();
  }

  public WebDriver getDriver() {
    if(driver == null) driver = createDriver();
    return driver;
  }

  private WebDriver createDriver() {
    switch (environmentType) {
      case LOCAL : driver = createLocalDriver();
        break;
      case REMOTE : driver = createRemoteDriver();
        break;
    }
    return driver;
  }

  private WebDriver createRemoteDriver() {
    throw new RuntimeException("RemoteWebDriver is not yet implemented");
  }

  private WebDriver createLocalDriver() {
    switch (driverType) {
      case FIREFOX : driver = new FirefoxDriver();
        break;
      case CHROME :
        System.setProperty(CHROME_DRIVER_PROPERTY, ConfigReader.getInstance().getDriverPath());
        driver = new ChromeDriver();
        break;
    }

    if(ConfigReader.getInstance().getBrowserWindowSize()) driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(ConfigReader.getInstance().getImplicitlyWait(), TimeUnit.SECONDS);
    return driver;
  }

  public void closeDriver() {
    driver.close();
    driver.quit();
  }

}
