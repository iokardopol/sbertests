package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        plugin = "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
        features = "src/test/java/features",
        glue = "stepdefs",
        tags = "@weather-information, @negative-scenarios"
)
public class RunnerTest {
}
