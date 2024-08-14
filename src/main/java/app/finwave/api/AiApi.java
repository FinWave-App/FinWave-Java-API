package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class AiApi {
    public record NewContextRequest(String additionalSystemMessage) implements IRequest<NewContextResponse> {
        @Override
        public Class<NewContextResponse> getResponseClass() {
            return NewContextResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/ai/newContext",
                    "additionalSystemMessage", additionalSystemMessage);
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

    public record AttachFileRequest(long contextId, String fileId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/ai/attachFile",
                    "contextId", contextId,
                    "fileId", fileId
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

    public record AskRequest(long contextId, String message) implements IRequest<AnswerResponse> {
        @Override
        public Class<AnswerResponse> getResponseClass() {
            return AnswerResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/ai/ask",
                    "contextId", contextId,
                    "message", message
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

    public record NewContextResponse(long contextId) implements IResponse {
    }

    public record AnswerResponse(String answer) implements IResponse {
    }
}