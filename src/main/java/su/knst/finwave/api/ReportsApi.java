package su.knst.finwave.api;

import su.knst.finwave.api.tools.IRequest;
import su.knst.finwave.api.tools.IResponse;
import su.knst.finwave.api.tools.RequestMethod;
import su.knst.finwave.api.tools.TransactionsFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ReportsApi {
    public static class GetListRequest implements IRequest<GetListResponse> {
        @Override
        public Class<GetListResponse> getResponseClass() {
            return GetListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/reports/getList";
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

    public record NewRequest(String description, TransactionsFilter filter, int type, Map<String, String> lang) implements IRequest<GetListResponse> {
        @Override
        public Class<GetListResponse> getResponseClass() {
            return GetListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/reports/new";
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

    public record GetListResponse(List<Report> reports) implements IResponse {}

    public record Report(String token, String description, short status, short type, OffsetDateTime created, OffsetDateTime expires) {}
}
