package com.ordestiny.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/reports/cucumber-reports.html", "timeline:target/reports/"})
public class RunCucumberIT {

}
