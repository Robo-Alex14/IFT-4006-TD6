package td6.multiComponent;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"td6.multiComponent.glue"})
public class RunnerTest {

}
