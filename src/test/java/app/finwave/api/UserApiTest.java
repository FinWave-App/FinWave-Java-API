package app.finwave.api;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserApiTest {
    private FinWaveClient client;
    private String username;
    private String newPassword = "$%@W$*(jifjgsKDOFK)($I($I#(%4";

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void getUsername() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new UserApi.UsernameRequest()).get();
        assertNotNull(response);
        assertNotNull(response.username());

        assertNotEquals("", response.username(), "Username should not be empty");
        username = response.username();
    }

    @Test
    @Order(2)
    void changePassword() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new UserApi.ChangePasswordRequest(newPassword)).get();
        assertNotNull(response);
        assertEquals("Password changed", response.message());
        client.setToken(null);
    }

    @Test
    @Order(3)
    void login() throws ExecutionException, InterruptedException {
        var response = client.runRequest(
                new AuthApi.LoginRequest(
                        username,
                        newPassword,
                        "FinWaveAPI test")
        ).get();
        assertNotNull(response);

        client.setToken(response.token());
    }

    @Test
    @Order(4)
    void logout() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new UserApi.LogoutRequest()).get();
        assertNotNull(response);
        assertEquals("Logged out", response.message());
    }
}