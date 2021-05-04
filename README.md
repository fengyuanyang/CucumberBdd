## CucumberBdd
### Background
* It's difficult to retest functions manually each time application get updated.    
* A spoken language might be helpful for testing and generating reports.


### Cucumber And Selenium
[What is cucumber](https://cucumber.io/docs/guides/overview/)    
Briefly:Cucumber is a tool that supports Behaviour-Driven Development(BDD).

[What is Selenium](https://www.selenium.dev/)    
Briefly: Selenium automates browsers

Prerequisites
* download [chrome webdrivers](https://chromedriver.chromium.org/downloads) - check your chrome version first 


Cucumber reads executable specifications written in plain text and validates that the software does what those specifications say(Scenario).

**Basic Scenario**    
```
Scenario: Breaker guesses a word
  Given the Maker has chosen a word
  When the Breaker makes a guess
  Then the Maker is asked to score
```

### Usage
Update **driverPath** value in config.properties    
ex.
```
# config.properties
driverPath=D:\\driver\\chromedriver_win32\\chromedriver.exe
```

Run all the tests    
```mvn verify```

Check reports    
```
/target/reports/cucumber-reports.html
/target/reports/index.html
```
