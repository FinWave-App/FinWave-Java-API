package app.finwave.api;

import app.finwave.api.websocket.FinWaveWebSocketClient;
import app.finwave.api.websocket.messages.handler.AbstractWebSocketHandler;
import app.finwave.api.websocket.messages.ResponseMessage;
import app.finwave.api.websocket.messages.handler.QueueWebSocketHandler;
import app.finwave.api.websocket.messages.requests.NewNotificationPointRequest;
import app.finwave.api.websocket.messages.requests.SubscribeNotificationsRequest;
import app.finwave.api.websocket.messages.response.NotifyUpdateBody;
import app.finwave.api.websocket.messages.response.auth.AuthStatusBody;
import app.finwave.api.websocket.messages.response.notifications.Notification;
import app.finwave.api.websocket.messages.response.notifications.NotificationPointRegisteredBody;
import app.finwave.api.websocket.messages.response.notifications.NotificationSubscribeBody;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.*;

import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebSocketTest {
    private FinWaveClient client;
    private FinWaveWebSocketClient webSocketClient;
    private QueueWebSocketHandler handler;

    private NotificationPointRegisteredBody registeredPoint;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();

        handler = new QueueWebSocketHandler() {
            @Override
            public void opened(ServerHandshake serverHandshake) {

            }

            @Override
            public void onError(Exception exception) {

            }

            @Override
            public void closed(int code, String reason, boolean remote) {

            }
        };
    }

    @Test
    @Order(1)
    void connect() throws ExecutionException, InterruptedException, URISyntaxException {
        webSocketClient = client.connectToWebsocket(handler);

        ResponseMessage message = handler.messageExpected().get();

        assertNotNull(message);
        assertEquals("auth", message.type);

        AuthStatusBody body = message.getBody(AuthStatusBody.class);
        assertEquals("Successful", body.status);
    }

    @Test
    @Order(2)
    void update() throws ExecutionException, InterruptedException {

        var tag = client.runRequest(new AccountTagApi.NewTagRequest("test", "test")).get();
        assertNotNull(tag);

        var account = client.runRequest(new AccountApi.NewAccountRequest(tag.tagId(), 1, "Test account", null)).get();

        assertNotNull(account);
        assertTrue(account.accountId() > 0);


        ResponseMessage message = handler.messageExpected().get();

        assertNotNull(message);
        assertEquals("update", message.type);

        NotifyUpdateBody body = message.getBody(NotifyUpdateBody.class);
        assertEquals("accountTags", body.updated);


        message = handler.messageExpected().get();

        assertNotNull(message);
        assertEquals("update", message.type);

        body = message.getBody(NotifyUpdateBody.class);
        assertEquals("accounts", body.updated);
    }

    @Test
    @Order(3)
    void newNotificationPoint() throws ExecutionException, InterruptedException {
        webSocketClient.send(new NewNotificationPointRequest("WebSocket Test", true));

        ResponseMessage message = handler.messageExpected().get();

        assertNotNull(message);
        assertEquals("newNotificationRegistered", message.type);

        NotificationPointRegisteredBody body = message.getBody(NotificationPointRegisteredBody.class);
        assertNotNull(body.uuid);
        assertTrue(body.id > 0);

        registeredPoint = body;
    }

    @Test
    @Order(4)
    void subscribePoint() throws ExecutionException, InterruptedException {
        webSocketClient.send(new SubscribeNotificationsRequest(registeredPoint.uuid));

        ResponseMessage message = handler.messageExpected().get();

        assertNotNull(message);
        assertEquals("subscribeNotification", message.type);

        NotificationSubscribeBody body = message.getBody(NotificationSubscribeBody.class);
        assertEquals("Subscribed", body.status);
    }

    @Test
    @Order(5)
    void notificationTest() throws ExecutionException, InterruptedException {
        client.runRequest(new NotificationApi.PushNotificationRequest(registeredPoint.id, "Test notification", false));

        ResponseMessage message = handler.messageExpected().get();
        assertEquals("notification", message.type);

        Notification notification = message.getBody(Notification.class);

        assertEquals("Test notification", notification.text());
        assertFalse(notification.options().silent());
    }

    @Test
    @Order(6)
    void close() throws InterruptedException {
        webSocketClient.closeBlocking();

        assertFalse(webSocketClient.isOpen());
    }
}