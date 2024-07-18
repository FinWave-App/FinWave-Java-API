package app.finwave.api.websocket.messages;

import app.finwave.api.tools.Misc;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ResponseMessage {
    public final String type;
    protected final JsonElement body;

    public ResponseMessage(String type, JsonObject body) {
        this.type = type;
        this.body = body;
    }

    public <T extends MessageBody> T getBody(Class<T> clazz) {
        return Misc.GSON.fromJson(body, clazz);
    }
}
