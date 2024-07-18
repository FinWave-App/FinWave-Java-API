package app.finwave.api.websocket.messages.requests;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.RequestMessage;

public class AuthMessageRequest extends RequestMessage<AuthMessageRequest.AuthMessageBody> {
    public AuthMessageRequest(String token) {
        super("auth", new AuthMessageBody(token));
    }

    protected static class AuthMessageBody extends MessageBody {
        public final String token;

        public AuthMessageBody(String token) {
            this.token = token;
        }
    }
}
