package su.knst.finwave.api;

import su.knst.finwave.api.tools.*;

import java.time.LocalDateTime;
import java.util.List;

public class SessionsApi {
    public static class GetListRequest implements IRequest<GetListResponse> {
        @Override
        public Class<GetListResponse> getResponseClass() {
            return GetListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/sessions/getList";
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

    public record NewSessionRequest(int lifetimeDays, String description) implements IRequest<NewSessionResponse> {
        @Override
        public Class<NewSessionResponse> getResponseClass() {
            return NewSessionResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/sessions/new",
                    "lifetimeDays", lifetimeDays,
                    "description", description
            );
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

    public record DeleteSessionRequest(long sessionId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/sessions/delete",
                    "sessionId", sessionId
            );
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

    public record NewSessionResponse(String token) implements IResponse {}

    public record GetListResponse(List<Session> sessions) implements IResponse {}

    public record Session(long sessionId, String token, LocalDateTime createdAt, LocalDateTime expiresAt, String description) {}
}
