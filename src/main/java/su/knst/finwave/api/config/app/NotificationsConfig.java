package su.knst.finwave.api.config.app;

public class NotificationsConfig {
    public int maxPointsPerUser = 64;
    public int maxDescriptionLength = 128;
    public int maxNotificationLength = 256;

    public WebPushConfig webPush = new WebPushConfig();

    public static class WebPushConfig {
        public int maxEndpointLength = 2048;
        public int maxAuthLength = 128;
        public int maxP256dhLength = 128;
    }
}
