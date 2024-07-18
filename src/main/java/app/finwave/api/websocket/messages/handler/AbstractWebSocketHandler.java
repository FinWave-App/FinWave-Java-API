package app.finwave.api.websocket.messages.handler;

import app.finwave.api.websocket.FinWaveWebSocketClient;
import app.finwave.api.websocket.messages.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import org.java_websocket.handshake.ServerHandshake;

import static app.finwave.api.tools.Misc.GSON;

public abstract class AbstractWebSocketHandler {
    protected FinWaveWebSocketClient client;

    public void init(FinWaveWebSocketClient client) {
        this.client = client;
    }

    public void onMessage(String rawMessage) {
        ResponseMessage message;

        try {
            message = GSON.fromJson(rawMessage, ResponseMessage.class);
        } catch (JsonSyntaxException e) {
            unparsedMessage(rawMessage);

            return;
        }

        onMessage(message);
    }

    public abstract void onMessage(ResponseMessage message);

    public void unparsedMessage(String rawMessage) {}

    public abstract void opened(ServerHandshake serverHandshake);
    public abstract void onError(Exception exception);
    public abstract void closed(int code, String reason, boolean remote);

}
