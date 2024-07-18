package app.finwave.api.websocket.messages;

import app.finwave.api.tools.Misc;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RequestMessage<T extends MessageBody> {
    public final String type;
    protected final T body;

    public RequestMessage(String type, T body) {
        this.type = type;
        this.body = body;
    }

}
