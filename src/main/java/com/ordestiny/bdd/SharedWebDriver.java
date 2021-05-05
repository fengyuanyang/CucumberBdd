package com.ordestiny.bdd;

import com.ordestiny.bdd.enums.EnvironmentType;
import com.ordestiny.bdd.provider.ConfigReader;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SharedWebDriver {
  private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

  public WebDriver getDriver() {
    return threadDriver.get();
  }
  public WebDriver getOrCreateDriver() {
    if(threadDriver.get() == null) {
      threadDriver.set(createDriver());
    }
    return threadDriver.get();
  }
  private WebDriver createDriver() {
    EnvironmentType environmentType = ConfigReader.getInstance().getEnvironment();
    switch (environmentType) {
      case LOCAL : threadDriver.set(createLocalDriver());
        break;
      case REMOTE : threadDriver.set(createRemoteDriver());
        break;
    }
    return threadDriver.get();
  }

  private WebDriver createRemoteDriver() {
    throw new RuntimeException("RemoteWebDriver is not yet implemented");
  }

  private WebDriver createLocalDriver() {
    WebDriver driver;
    DriverManagerType driverType = ConfigReader.getInstance().getBrowser();
    switch (driverType) {
      case FIREFOX :
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(new FirefoxOptions().
                setAcceptInsecureCerts(ConfigReader.getInstance().getAcceptInsecureCerts()));
        break;
      case CHROME :
        if (ConfigReader.getInstance().getDriverPath()!= null)
          System.setProperty("webdriver.chrome.driver", ConfigReader.getInstance().getDriverPath());
        else
          WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(new ChromeOptions().
                setAcceptInsecureCerts(ConfigReader.getInstance().getAcceptInsecureCerts()));
        break;
      default:
        return null;
    }

    if(ConfigReader.getInstance().getBrowserWindowSize()) driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(ConfigReader.getInstance().getImplicitlyWait(), TimeUnit.SECONDS);
    return driver;
  }

  public void closeDriver() {
    WebDriver driver = threadDriver.get();
    driver.close();
    driver.quit();
    threadDriver.remove();
  }
}
