package app.finwave.api.websocket;

import app.finwave.api.websocket.messages.handler.AbstractWebSocketHandler;
import app.finwave.api.websocket.messages.RequestMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static app.finwave.api.tools.Misc.GSON;

public class FinWaveWebSocketClient extends WebSocketClient {
    protected AbstractWebSocketHandler handler;

    protected long lastPing = 0;

    protected static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);

                return thread;
            });

    protected ScheduledFuture<?> future;

    public FinWaveWebSocketClient(URI uri) {
        super(uri);

        future = scheduledExecutorService.scheduleAtFixedRate(() -> {
           if (isOpen() && System.currentTimeMillis() - lastPing > 15000) {
               send("ping");
           }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public void setHandler(AbstractWebSocketHandler handler) {
        handler.init(this);

        this.handler = handler;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        if (handler != null)
            handler.opened(serverHandshake);
    }

    @Override
    public void onMessage(String rawMessage) {
        if (rawMessage.equals("pong")) {
            lastPing = System.currentTimeMillis();

            return;
        }

        if (handler != null)
            handler.onMessage(rawMessage);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (handler != null)
            handler.closed(code, reason, remote);
    }

    @Override
    public void onError(Exception e) {
        if (handler != null)
            handler.onError(e);
    }

    public void send(RequestMessage<?> message) {
        lastPing = System.currentTimeMillis();

        send(GSON.toJson(message));
    }

    @Override
    public void close() {
        future.cancel(true);

        super.close();
    }
}
