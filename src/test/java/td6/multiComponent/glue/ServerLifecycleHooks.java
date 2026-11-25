package td6.multiComponent.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import td6.Main;
import td6.api.ConfigurationServerRest;
import td6.multiComponent.utils.ResponseMemory;

public class ServerLifecycleHooks {
    @Before
    public void startServer(){
        ResponseMemory.clear();
        ConfigurationServerRest.useInMemoryRepositories();
        Main.runForTest();
        System.out.println("Server started");
    }

    @After
    public void stopServer() throws Exception {
        Main.stop();
        System.out.println("Server stopped");
    }
}
