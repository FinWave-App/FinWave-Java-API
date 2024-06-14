package app.finwave.api;

import app.finwave.api.tools.*;

public class UserApi {
    public static class UsernameRequest implements IRequest<UsernameResponse> {
        @Override
        public Class<UsernameResponse> getResponseClass() {
            return UsernameResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/getUsername";
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

    public record ChangePasswordRequest(String password) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/changePassword",
                    "password", password
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

    public record LogoutRequest() implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return "user/logout";
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

    public record UsernameResponse(String username) implements IResponse {
    }
}
