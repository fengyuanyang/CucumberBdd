package com.ordestiny.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumStepDefinitions {
    private SharedWebDriver sharedWebDriver;

    public SeleniumStepDefinitions(SharedWebDriver sharedWebDriver) {
        this.sharedWebDriver = sharedWebDriver;
    }

    @Given("I am on the Google search page")
    public void I_visit_google() {
        sharedWebDriver.getOrCreateDriver().get("https://www.google.com");
        System.out.format("Thread ID - %2d",
                Thread.currentThread().getId());
    }

    @When("I search for {string}")
    public void search_for(String query) {
        WebElement element = sharedWebDriver.getOrCreateDriver().findElement(By.name("q"));
        element.sendKeys(query);
        element.submit();
    }

    @Then("the page title should start with {string}")
    public void checkTitle(String titleStartsWith) {
        new WebDriverWait(sharedWebDriver.getOrCreateDriver(),10L).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(titleStartsWith);
            }
        });
    }

    @After()
    public void closeBrowser(Scenario scenario) {
        if (sharedWebDriver.getDriver()!= null) {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) sharedWebDriver.getOrCreateDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "name");
            }
            sharedWebDriver.closeDriver();
        }
    }
}
