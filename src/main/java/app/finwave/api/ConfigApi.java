package app.finwave.api;

import app.finwave.api.config.app.*;
import app.finwave.api.config.general.UserConfig;
import app.finwave.api.tools.IRequest;
import app.finwave.api.tools.IResponse;
import app.finwave.api.tools.RequestMethod;

public class ConfigApi {
    public static class GetConfigsRequest implements IRequest<PublicConfigs> {
        @Override
        public Class<PublicConfigs> getResponseClass() {
            return PublicConfigs.class;
        }

        @Override
        public String getUrl() {
            return "configs/get";
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
// TODO: Accept raw string as response

//        public static class GetConfigHashRequest implements IRequest<String> {
//            @Override
//            public Class<String> getResponseClass() {
//                return String.class;
//            }
//
//            @Override
//            public String getUrl() {
//                return "configs/hash";
//            }
//
//            @Override
//            public RequestMethod getMethod() {
//                return RequestMethod.GET;
//            }
//
//            @Override
//            public Object getData() {
//                return null;
//            }
//        }

    public record PublicConfigs(UserConfig users,
                         AccountsConfig accounts,
                         CurrencyConfig currencies,
                         NotesConfig notes,
                         TransactionConfig transactions,
                         AnalyticsConfig analytics,
                         NotificationsConfig notifications,
                         AccumulationConfig accumulation,
                         RecurringTransactionConfig recurring,
                         ReportConfig reports,
                         AiPublic ai) implements IResponse { }

    public record AiPublic(boolean enabled) {}
}
