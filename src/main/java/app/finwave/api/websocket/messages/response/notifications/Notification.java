package app.finwave.api.websocket.messages.response.notifications;

import app.finwave.api.websocket.messages.MessageBody;

import java.time.OffsetDateTime;
import java.util.Objects;

public final class Notification extends MessageBody {
    private final long id;
    private final String text;
    private final NotificationOptions options;
    private final int userId;
    private final OffsetDateTime createdAt;

    public Notification(long id, String text, NotificationOptions options, int userId, OffsetDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public long id() {
        return id;
    }

    public String text() {
        return text;
    }

    public NotificationOptions options() {
        return options;
    }

    public int userId() {
        return userId;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Notification) obj;
        return this.id == that.id &&
                Objects.equals(this.text, that.text) &&
                Objects.equals(this.options, that.options) &&
                this.userId == that.userId &&
                Objects.equals(this.createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, options, userId, createdAt);
    }

    @Override
    public String toString() {
        return "Notification[" +
                "id=" + id + ", " +
                "text=" + text + ", " +
                "options=" + options + ", " +
                "userId=" + userId + ", " +
                "createdAt=" + createdAt + ']';
    }
}
