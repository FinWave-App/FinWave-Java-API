package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.List;

public class TransactionCategoryApi {

    public record NewCategoryRequest(int type, Long parentId, String name, String description) implements IRequest<NewCategoryResponse> {
        @Override
        public Class<NewCategoryResponse> getResponseClass() {
            return NewCategoryResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/categories/new",
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

    public record GetCategoriesRequest() implements IRequest<GetCategoriesResponse> {
        @Override
        public Class<GetCategoriesResponse> getResponseClass() {
            return GetCategoriesResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/transactions/categories/getList";
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

    public record EditCategoryTypeRequest(long categoryId, int type) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/categories/editType",
                    "categoryId", categoryId,
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

    public record EditCategoryParentRequest(long categoryId, Long parentId, boolean setToRoot) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            String url;

            if (setToRoot)
                url = Misc.formatQueryURL("user/transactions/categories/editParent",
                        "categoryId", categoryId,
                        "setToRoot", "true");
            else
                url = Misc.formatQueryURL("user/transactions/categories/editParent",
                    "categoryId", categoryId,
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

    public record EditCategoryNameRequest(long categoryId, String name) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/categories/editName",
                    "categoryId", categoryId,
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

    public record EditCategoryDescriptionRequest(long categoryId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/transactions/categories/editDescription",
                    "categoryId", categoryId,
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

    public record GetCategoriesResponse(List<CategoryEntry> categories) implements IResponse {}
    public record CategoryEntry(long categoryId, short type, String parentsTree, String name, String description) {}
    public record NewCategoryResponse(long categoryId) implements IResponse {}
}
