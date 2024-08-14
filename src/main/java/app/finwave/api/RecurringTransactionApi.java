package app.finwave.api;

import app.finwave.api.tools.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class RecurringTransactionApi {
    public record NewRecurringTransactionRequest(long categoryId, long accountId, OffsetDateTime nextRepeat, int repeatType, short repeatArg, int notificationMode, BigDecimal delta, String description) implements IRequest<NewRecurringTransactionResponse> {
        @Override
        public Class<NewRecurringTransactionResponse> getResponseClass() {
            return NewRecurringTransactionResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/recurring/new",
                    "categoryId", categoryId,
                    "accountId", accountId,
                    "nextRepeat", nextRepeat,
                    "repeatType", repeatType,
                    "repeatArg", repeatArg,
                    "notificationMode", notificationMode,
                    "delta", delta,
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

    public record EditRecurringTransactionRequest(long recurringTransactionId, long categoryId, long accountId, OffsetDateTime nextRepeat, int repeatType, short repeatArg, int notificationMode, BigDecimal delta, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/recurring/edit",
                    "recurringTransactionId", recurringTransactionId,
                    "categoryId", categoryId,
                    "accountId", accountId,
                    "nextRepeat", nextRepeat,
                    "repeatType", repeatType,
                    "repeatArg", repeatArg,
                    "notificationMode", notificationMode,
                    "delta", delta,
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

    public record DeleteRecurringTransactionRequest(long recurringId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/recurring/delete",
                    "recurringId", recurringId);
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

    public record GetRecurringListRequest() implements IRequest<GetRecurringListResponse> {
        @Override
        public Class<GetRecurringListResponse> getResponseClass() {
            return GetRecurringListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/transactions/recurring/getList";
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

    public record NewRecurringTransactionResponse(long recurringTransactionId) implements IResponse {}

    public record GetRecurringListResponse(List<Entry> recurringList) implements IResponse {
    }

    public record Entry(long recurringTransactionId, long categoryId, long accountId, long currencyId, OffsetDateTime lastRepeat, OffsetDateTime nextRepeat, short repeatType, short repeatArg, int notificationMode, BigDecimal delta, String description) implements IResponse {
    }
}
