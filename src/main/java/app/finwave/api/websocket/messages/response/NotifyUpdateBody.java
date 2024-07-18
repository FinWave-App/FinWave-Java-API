package app.finwave.api.websocket.messages.response;

import app.finwave.api.websocket.messages.MessageBody;

public class NotifyUpdateBody extends MessageBody { // update
    public final String updated;

    public NotifyUpdateBody(String updated) {
        this.updated = updated;
    }

}
