package su.knst.finwave.api;

import org.junit.jupiter.api.*;
import su.knst.finwave.api.tools.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SessionsApiTest {

    private FinWaveClient client;
    protected long sessionId;
    protected String token;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newSession() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new SessionsApi.NewSessionRequest(30, "Test session")).get();
        assertNotNull(response);
        assertNotNull(response.token());

        token = response.token();

        var sessionResponse = client.runRequest(new SessionsApi.GetListRequest()).get();
        assertNotNull(sessionResponse.sessions());

        var newSession = sessionResponse.sessions().stream()
                .filter(s -> token.equals(s.token()))
                .findFirst()
                .orElse(null);

        assertNotNull(newSession);
        assertTrue(newSession.sessionId() > 0);
        sessionId = newSession.sessionId();
    }

    @Test
    @Order(2)
    void getListSessions() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new SessionsApi.GetListRequest()).get();
        assertNotNull(response);
        assertNotNull(response.sessions());

        var session = response.sessions().stream()
                .filter(s -> s.sessionId() == sessionId)
                .findFirst()
                .orElse(null);

        assertNotNull(session);
        assertEquals("Test session", session.description());
    }

    @Test
    @Order(3)
    void deleteSession() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new SessionsApi.DeleteSessionRequest(sessionId)).get();
        assertNotNull(response);
        assertEquals("Session deleted", response.message());

        var sessionResponse = client.runRequest(new SessionsApi.GetListRequest()).get();
        var deletedSession = sessionResponse.sessions().stream()
                .filter(s -> s.sessionId() == sessionId)
                .findFirst()
                .orElse(null);

        assertNull(deletedSession, "Session should not be found after deletion");
    }
}