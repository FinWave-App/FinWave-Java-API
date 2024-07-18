package app.finwave.api.websocket.messages.requests;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.RequestMessage;

public class NewNotificationPointRequest extends RequestMessage<NewNotificationPointRequest.NewNotificationPointBody> {
    public NewNotificationPointRequest(String description, boolean isPrimary) {
        super("newNotification", new NewNotificationPointBody(description, isPrimary));
    }

    protected static class NewNotificationPointBody extends MessageBody {
        public final String description;
        public final boolean isPrimary;

        public NewNotificationPointBody(String description, boolean isPrimary) {
            this.description = description;
            this.isPrimary = isPrimary;
        }
    }
}
