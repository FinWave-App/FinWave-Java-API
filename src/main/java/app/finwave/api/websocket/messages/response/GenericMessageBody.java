package app.finwave.api.websocket.messages.response;

import app.finwave.api.websocket.messages.MessageBody;

public class GenericMessageBody extends MessageBody { // generic
    public final String message;
    public final int code;

    public GenericMessageBody(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
