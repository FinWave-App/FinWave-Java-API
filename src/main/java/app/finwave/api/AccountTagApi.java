package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.List;

public class AccountTagApi {
    public record NewTagRequest(String name, String description) implements IRequest<NewTagResponse> {
        @Override
        public Class<NewTagResponse> getResponseClass() {
            return NewTagResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/tags/new",
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

    public record GetTagsRequest() implements IRequest<GetTagsResponse> {
        @Override
        public Class<GetTagsResponse> getResponseClass() {
            return GetTagsResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/accounts/tags/getList";
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

    public record EditTagNameRequest(long tagId, String name) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/tags/editName",
                    "tagId", tagId,
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

    public record EditTagDescriptionRequest(long tagId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/tags/editDescription",
                    "tagId", tagId,
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

    public record DeleteTagRequest(long tagId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/tags/delete",
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

    public record GetTagsResponse(List<TagEntry> tags) implements IResponse {}

    public record TagEntry(long tagId, String name, String description) {}

    public record NewTagResponse(long tagId) implements IResponse {}

}
