package app.finwave.api.websocket.messages.response.notifications;

public record NotificationOptions(boolean silent, long pointId, Object args) { }
