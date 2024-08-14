package app.finwave.api;

import app.finwave.api.tools.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyticsApi {

    public record GetAnalyticsByMonthsRequest(TransactionsFilter filter) implements IRequest<AnalyticsByMonths> {
        @Override
        public Class<AnalyticsByMonths> getResponseClass() {
            return AnalyticsByMonths.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/analytics/getByMonths", filter.toOptions());
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

    public record GetAnalyticsByDaysRequest(TransactionsFilter filter) implements IRequest<AnalyticsByDays> {
        @Override
        public Class<AnalyticsByDays> getResponseClass() {
            return AnalyticsByDays.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/analytics/getByDays", filter.toOptions());
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

    public record AnalyticsByMonths(HashMap<LocalDate, ArrayList<Entry>> total) implements IResponse {}

    public record AnalyticsByDays(HashMap<LocalDate, ArrayList<Entry>> total) implements IResponse {}

    public record Entry(long currencyId, long categoryId, BigDecimal delta) {}
}
