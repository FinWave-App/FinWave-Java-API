package app.finwave.api;

import app.finwave.api.tools.*;

import java.math.BigDecimal;
import java.util.List;

public class AccountApi {
    public record NewAccountRequest(long tagId, long currencyId, String name, String description) implements IRequest<NewAccountResponse> {
        @Override
        public Class<NewAccountResponse> getResponseClass() {
            return NewAccountResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/new",
                    "tagId", tagId,
                    "currencyId", currencyId,
                    "name", name,
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

    public record GetAccountsRequest() implements IRequest<GetAccountsListResponse> {
        @Override
        public Class<GetAccountsListResponse> getResponseClass() {
            return GetAccountsListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/accounts/getList";
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

    public record HideAccountRequest(long accountId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/hide",
                    "accountId", accountId);
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

    public record ShowAccountRequest(long accountId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/show",
                    "accountId", accountId);
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

    public record EditAccountNameRequest(long accountId, String name) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/editName",
                    "accountId", accountId,
                    "name", name);
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

    public record EditAccountDescriptionRequest(long accountId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/editDescription",
                    "accountId", accountId,
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

    public record EditAccountTagRequest(long accountId, long tagId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/editTag",
                    "accountId", accountId,
                    "tagId", tagId);
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

    public record GetAccountsListResponse(List<AccountEntry> accounts) implements IResponse {}

    public record AccountEntry(long accountId, long tagId, long currencyId, BigDecimal amount, boolean hidden, String name, String description) {}

    public record NewAccountResponse(long accountId) implements IResponse {}
}
