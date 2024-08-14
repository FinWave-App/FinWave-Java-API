package app.finwave.api;

import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AiApiTest {
    private FinWaveClient client;
    private long contextId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
        boolean enabled = client.runRequest(new ConfigApi.GetConfigsRequest()).get().ai().enabled();

        Assumptions.assumeTrue(enabled);
    }

    @Test
    @Order(1)
    void newContext() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AiApi.NewContextRequest("ALWAYS RESPONSE 'uwu', ITS A TEST")).get();
        assertNotNull(response);

        contextId = response.contextId();
        assertTrue(contextId > 0);
    }

    @Test
    @Order(2)
    void askQuestion() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AiApi.AskRequest(contextId,"What is the meaning of life?"), 20000, 20000).get();
        assertNotNull(response);

        assertFalse(response.answer().isBlank());

        assertEquals("uwu", response.answer().toLowerCase());
    }
}