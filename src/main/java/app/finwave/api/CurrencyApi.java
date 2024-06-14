package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.List;

public class CurrencyApi {
    public record NewCurrencyRequest(String code, String symbol, int decimals, String description) implements IRequest<NewCurrencyResponse> {
        @Override
        public Class<NewCurrencyResponse> getResponseClass() {
            return NewCurrencyResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/currencies/new",
                    "code", code,
                    "symbol", symbol,
                    "decimals", decimals,
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

    public record GetCurrenciesRequest() implements IRequest<GetCurrenciesResponse> {
        @Override
        public Class<GetCurrenciesResponse> getResponseClass() {
            return GetCurrenciesResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/currencies/getList";
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

    public record EditCurrencyCodeRequest(long currencyId, String code) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/currencies/editCode",
                    "currencyId", currencyId,
                    "code", code);
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

    public record EditCurrencySymbolRequest(long currencyId, String symbol) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/currencies/editSymbol",
                    "currencyId", currencyId,
                    "symbol", symbol);
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

    public record EditCurrencyDecimalsRequest(long currencyId, int decimals) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/currencies/editDecimals",
                    "currencyId", currencyId,
                    "decimals", decimals);
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

    public record EditCurrencyDescriptionRequest(long currencyId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/currencies/editDescription",
                    "currencyId", currencyId,
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

    public record NewCurrencyResponse(long currencyId) implements IResponse {}

    public record GetCurrenciesResponse(List<CurrencyEntry> currencies) implements IResponse {}

    public record CurrencyEntry(long currencyId, boolean owned, String code, String symbol, short decimals, String description) {}
}
