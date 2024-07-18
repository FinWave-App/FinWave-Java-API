package app.finwave.api.websocket.messages.response.notifications;

import app.finwave.api.websocket.messages.MessageBody;
import app.finwave.api.websocket.messages.ResponseMessage;

public class NotificationSubscribeBody extends MessageBody { // subscribeNotification
    public final String status;

    public NotificationSubscribeBody(String status) {
        this.status = status;
    }

}
