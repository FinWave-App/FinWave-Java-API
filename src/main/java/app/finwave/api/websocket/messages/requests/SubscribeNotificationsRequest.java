package app.finwave.api.websocket.messages.requests;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.RequestMessage;

import java.util.UUID;

public class SubscribeNotificationsRequest extends RequestMessage<SubscribeNotificationsRequest.SubscribeNotificationsBody> {
    public SubscribeNotificationsRequest(UUID pointUUID) {
        super("subscribeNotification", new SubscribeNotificationsBody(pointUUID));
    }

    protected static class SubscribeNotificationsBody extends MessageBody {
        public final UUID pointUUID;

        public SubscribeNotificationsBody(UUID pointUUID) {
            this.pointUUID = pointUUID;
        }
    }
}
