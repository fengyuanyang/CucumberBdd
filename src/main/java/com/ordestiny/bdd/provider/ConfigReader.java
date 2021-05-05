package com.ordestiny.bdd.provider;

import com.ordestiny.bdd.enums.EnvironmentType;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
  private Properties properties;
  private final String propertyFilePath= Paths.get("src","test", "resources", "config.properties").toFile().getAbsolutePath();
  private static final ConfigReader configReader = new ConfigReader();

  public static ConfigReader getInstance() {
    return configReader;
  }

  private ConfigReader(){
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(propertyFilePath));
      properties = new Properties();
      try {
        properties.load(reader);
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("config.properties not found at " + propertyFilePath);
    }
  }

  public String getDriverPath(){
    String driverPath = properties.getProperty("driverPath");
    if(driverPath!= null) return driverPath;
    else throw new RuntimeException("Driver Path not specified in the config.properties file for the Key:driverPath");
  }

  public long getImplicitlyWait() {
    String implicitlyWait = properties.getProperty("implicitlyWait");
    if(implicitlyWait != null) {
      try{
        return Long.parseLong(implicitlyWait);
      }catch(NumberFormatException e) {
        throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
      }
    }
    return 30;
  }

  public String getApplicationUrl() {
    String url = properties.getProperty("url");
    if(url != null) return url;
    else throw new RuntimeException("Application Url not specified in the config.properties file for the Key:url");
  }

  public DriverManagerType getBrowser() {
    String browserName = properties.getProperty("browser");
    if(browserName == null || browserName.equals("chrome")) return DriverManagerType.CHROME;
    else if(browserName.equalsIgnoreCase("firefox")) return DriverManagerType.FIREFOX;
    else throw new RuntimeException("Browser Name Key value in config.properties is not matched : " + browserName);
  }

  public EnvironmentType getEnvironment() {
    String environmentName = properties.getProperty("environment");
    if(environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
    else if(environmentName.equals("remote")) return EnvironmentType.REMOTE;
    else throw new RuntimeException("Environment Type Key value in config.properties is not matched : " + environmentName);
  }

  public Boolean getBrowserWindowSize() {
    String windowSize = properties.getProperty("windowMaximize");
    if(windowSize != null) return Boolean.valueOf(windowSize);
    return true;
  }

  public Boolean getAcceptInsecureCerts() {
    String acceptInsecureCerts = properties.getProperty("acceptInsecureCerts");
    if(acceptInsecureCerts != null) return Boolean.valueOf(acceptInsecureCerts);
    return true;
  }

  public String getJasyptPassword() {
    String password = properties.getProperty("jasypt.encryptor.password");

    if(password != null) return password;
    return "";
  }
}