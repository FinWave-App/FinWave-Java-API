package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.List;

public class TransactionTagApi {

    public record NewTagRequest(int type, Long parentId, String name, String description) implements IRequest<NewTagResponse> {
        @Override
        public Class<NewTagResponse> getResponseClass() {
            return NewTagResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/tags/new",
                    "type", type,
                    "parentId", parentId,
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
            return "user/transactions/tags/getList";
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

    public record EditTagTypeRequest(long tagId, int type) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/tags/editType",
                    "tagId", tagId,
                    "type", type);
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

    public record EditTagParentRequest(long tagId, Long parentId, boolean setToRoot) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            String url;

            if (setToRoot)
                url = Misc.formatQueryURL("user/transactions/tags/editParent",
                        "tagId", tagId,
                        "setToRoot", "true");
            else
                url = Misc.formatQueryURL("user/transactions/tags/editParent",
                    "tagId", tagId,
                    "parentId", parentId);

            return url;
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

    public record EditTagNameRequest(long tagId, String name) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/tags/editName",
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
            return Misc.formatQueryURL("user/transactions/tags/editDescription",
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

    public record GetTagsResponse(List<TagEntry> tags) implements IResponse {}
    public record TagEntry(long tagId, short type, String parentsTree, String name, String description) {}
    public record NewTagResponse(long tagId) implements IResponse {}
}
