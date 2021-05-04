package com.ordestiny.bdd;

import com.ordestiny.bdd.enums.DriverType;
import com.ordestiny.bdd.enums.EnvironmentType;
import com.ordestiny.bdd.provider.ConfigReader;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
  private WebDriver driver;
  private static DriverType driverType;
  private static EnvironmentType environmentType;

  public WebDriverFactory() {
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
      case FIREFOX :
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(new FirefoxOptions().
                setAcceptInsecureCerts(ConfigReader.getInstance().getAcceptInsecureCerts()));
        break;
      case CHROME :
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(new ChromeOptions().
                setAcceptInsecureCerts(ConfigReader.getInstance().getAcceptInsecureCerts()));
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
