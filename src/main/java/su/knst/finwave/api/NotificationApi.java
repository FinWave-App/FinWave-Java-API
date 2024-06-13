package su.knst.finwave.api;

import su.knst.finwave.api.tools.*;

import java.time.OffsetDateTime;
import java.util.List;

public class NotificationApi {
    public record RegisterNewWebPushPointRequest(String endpoint, String auth, String p256dh, String description, boolean isPrimary) implements IRequest<NewPointResponse> {
        @Override
        public Class<NewPointResponse> getResponseClass() {
            return NewPointResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notifications/points/newWebPush",
                    "endpoint", endpoint,
                    "auth", auth,
                    "p256dh", p256dh,
                    "description", description,
                    "primary", isPrimary);
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record GetPointsRequest() implements IRequest<GetPointsResponse> {
        @Override
        public Class<GetPointsResponse> getResponseClass() {
            return GetPointsResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/notifications/points/getList";
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.GET;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record EditPointDescriptionRequest(long pointId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notifications/points/editDescription",
                    "pointId", pointId,
                    "description", description);
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record EditPointPrimaryRequest(long pointId, boolean isPrimary) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notifications/points/editPrimary",
                    "pointId", pointId,
                    "primary", isPrimary);
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record DeletePointRequest(long pointId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notifications/points/delete",
                    "pointId", pointId);
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record PushNotificationRequest(long pointId, String text, boolean silent) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notifications/push",
                    "pointId", pointId,
                    "text", text,
                    "silent", silent);
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record GetKeyRequest() implements IRequest<VapidKeyResponse> {
        @Override
        public Class<VapidKeyResponse> getResponseClass() {
            return VapidKeyResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/notifications/points/vapidKey";
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.GET;
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public record GetPointsResponse(List<PointEntry> points) implements IResponse {
    }

    public record PointEntry(long pointId, boolean isPrimary, short type, OffsetDateTime createdAt, String description) { }

    public record NewPointResponse(long pointId) implements IResponse {}

    public record VapidKeyResponse(String publicKey) implements IResponse {}
}