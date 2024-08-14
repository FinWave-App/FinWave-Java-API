package app.finwave.api;

import app.finwave.api.tools.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionApi {
    public record NewBulkTransactionsRequest(List<BulkEntry> entries) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return "user/transactions/newBulk";
        }

        @Override
        public RequestMethod getMethod() {
            return RequestMethod.POST;
        }

        @Override
        public Object getData() {
            return this;
        }
    }

    public record NewInternalTransferRequest(long categoryId, long fromAccountId, long toAccountId, OffsetDateTime createdAt, BigDecimal fromDelta, BigDecimal toDelta, String description) implements IRequest<NewTransactionResponse> {
        @Override
        public Class<NewTransactionResponse> getResponseClass() {
            return NewTransactionResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/newInternal",
                    "categoryId", categoryId,
                    "fromAccountId", fromAccountId,
                    "toAccountId", toAccountId,
                    "createdAt", createdAt,
                    "fromDelta", fromDelta,
                    "toDelta", toDelta,
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

    public record NewTransactionRequest(long categoryId, long accountId, OffsetDateTime createdAt, BigDecimal delta, String description) implements IRequest<NewTransactionResponse> {
        @Override
        public Class<NewTransactionResponse> getResponseClass() {
            return NewTransactionResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/new",
                    "categoryId", categoryId,
                    "accountId", accountId,
                    "createdAt", createdAt,
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

    public record DeleteTransactionRequest(long transactionId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/delete", "transactionId", transactionId);
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

    public record EditTransactionRequest(long transactionId, long categoryId, long accountId, OffsetDateTime createdAt, BigDecimal delta, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/edit",
                    "transactionId", transactionId,
                    "categoryId", categoryId,
                    "accountId", accountId,
                    "createdAt", createdAt,
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

    public record GetTransactionsCountRequest(TransactionsFilter filter) implements IRequest<TransactionsCountResponse> {
        @Override
        public Class<TransactionsCountResponse> getResponseClass() {
            return TransactionsCountResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/getCount", filter.toOptions());
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

    public record GetTransactionsRequest(int offset, int count, TransactionsFilter filter) implements IRequest<GetTransactionsListResponse> {
        @Override
        public Class<GetTransactionsListResponse> getResponseClass() {
            return GetTransactionsListResponse.class;
        }

        @Override
        public String getUrl() {
            ArrayList<Object> params = new ArrayList<>();

            params.addAll(filter.toOptions());

            params.addAll(List.of(
                    "offset", offset,
                    "count", count
            ));

            return Misc.formatQueryURL("user/transactions/getList", params);
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

    public record GetTransactionsListResponse(List<Transaction> transactions) implements IResponse {}

    public record TransactionsCountResponse(int count) implements IResponse {}

    public record NewTransactionResponse(long transactionId) implements IResponse {}

    public record BulkEntry(int type, long categoryId, long accountId, OffsetDateTime created, BigDecimal delta, Long toAccountId, BigDecimal toDelta, String description) {}
}