package app.finwave.api;

import app.finwave.api.tools.*;

import java.math.BigDecimal;
import java.util.List;

public class AccumulationApi {
    public record SetAccumulationRequest(long sourceAccountId, long targetAccountId, long tagId, List<AccumulationStep> steps) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return "user/accumulations/set";
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

    public record RemoveAccumulationRequest(long accountId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accumulations/remove",
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

    public record GetAccumulationsListRequest() implements IRequest<GetAccumulationsListResponse> {
        @Override
        public Class<GetAccumulationsListResponse> getResponseClass() {
            return GetAccumulationsListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/accumulations/getList";
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

    public record GetAccumulationsListResponse(List<AccumulationData> accumulations) implements IResponse {}

    public record AccumulationData(long sourceAccountId, long targetAccountId, long tagId, long userId, List<AccumulationStep> steps) {}
    public record AccumulationStep(BigDecimal from, BigDecimal to, BigDecimal step) {}
}
