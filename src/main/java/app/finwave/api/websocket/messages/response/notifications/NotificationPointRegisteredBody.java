package app.finwave.api.websocket.messages.response.notifications;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.ResponseMessage;

import java.util.UUID;

public class NotificationPointRegisteredBody extends MessageBody { // newNotificationRegistered
    public final long id;
    public final UUID uuid;

    public NotificationPointRegisteredBody(long id, UUID uuid) {
        this.id = id;
        this.uuid = uuid;
    }
}
