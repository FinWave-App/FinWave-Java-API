package app.finwave.api.websocket.messages.handler;

import app.finwave.api.websocket.messages.ResponseMessage;
import app.finwave.api.websocket.messages.response.GenericMessageBody;
import app.finwave.api.websocket.messages.response.NotifyUpdateBody;
import app.finwave.api.websocket.messages.response.auth.AuthStatusBody;
import app.finwave.api.websocket.messages.response.notifications.Notification;
import app.finwave.api.websocket.messages.response.notifications.NotificationPointRegisteredBody;
import app.finwave.api.websocket.messages.response.notifications.NotificationSubscribeBody;

import java.util.UUID;

public abstract class RoutedWebSocketHandler extends AbstractWebSocketHandler {
    @Override
    public void onMessage(ResponseMessage message) {
        switch (message.type) {
            case "generic" -> {
                GenericMessageBody body = message.getBody(GenericMessageBody.class);
                genericMessage(body.message, body.code);
            }
            case "update" -> notifyUpdate(message.getBody(NotifyUpdateBody.class).updated);
            case "auth" -> authStatus(message.getBody(AuthStatusBody.class).status);
            case "notification" -> notification(message.getBody(Notification.class));
            case "newNotificationRegistered" -> {
                NotificationPointRegisteredBody body = message.getBody(NotificationPointRegisteredBody.class);
                notificationPointRegistered(body.id, body.uuid);
            }
            case "subscribeNotification" -> notificationPointSubscribe(message.getBody(NotificationSubscribeBody.class).status);
            default -> unknownMessage(message);
        }
    }

    public void unknownMessage(ResponseMessage message) {}

    public abstract void notifyUpdate(String updated);
    public abstract void genericMessage(String message, int code);
    public abstract void notification(Notification notification);
    public abstract void notificationPointRegistered(long pointId, UUID uuid);
    public abstract void notificationPointSubscribe(String status);
    public abstract void authStatus(String status);
}
