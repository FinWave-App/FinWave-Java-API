package app.finwave.api.websocket.messages.response.auth;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.ResponseMessage;

public class AuthStatusBody extends MessageBody { // auth
    public final String status;

    public AuthStatusBody(String status) {
        this.status = status;
    }
}
