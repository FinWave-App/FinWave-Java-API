package app.finwave.api;

import app.finwave.api.tools.*;

public class AuthApi {
    public record LoginRequest(String login, String password, String description) implements IRequest<LoginResponse> {
        @Override
        public Class<LoginResponse> getResponseClass() {
            return LoginResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("auth/login",
                    "login", login,
                    "password", password,
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

    public record RegisterRequest(String login, String password) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("auth/register",
                    "login", login,
                    "password", password);
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

    public static class DemoRequest implements IRequest<DemoAccountResponse> {
        @Override
        public Class<DemoAccountResponse> getResponseClass() {
            return DemoAccountResponse.class;
        }

        @Override
        public String getUrl() {
            return "auth/demo";
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

    public record LoginResponse(String token, int lifetimeDays) implements IResponse {}

    public record DemoAccountResponse(String username, String password) implements IResponse {}
}
