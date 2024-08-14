package app.finwave.api;

import app.finwave.api.tools.*;

import java.util.List;

public class AccountFolderApi {
    public record NewFolderRequest(String name, String description) implements IRequest<NewFolderResponse> {
        @Override
        public Class<NewFolderResponse> getResponseClass() {
            return NewFolderResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/folders/new",
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

    public record GetFoldersRequest() implements IRequest<GetFoldersResponse> {
        @Override
        public Class<GetFoldersResponse> getResponseClass() {
            return GetFoldersResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/accounts/folders/getList";
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

    public record EditFolderNameRequest(long folderId, String name) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/folders/editName",
                    "folderId", folderId,
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

    public record EditFolderDescriptionRequest(long folderId, String description) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/folders/editDescription",
                    "folderId", folderId,
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

    public record DeleteFolderRequest(long folderId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/accounts/folders/delete",
                    "folderId", folderId);
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

    public record GetFoldersResponse(List<FolderEntry> folders) implements IResponse {}

    public record FolderEntry(long folderId, String name, String description) {}

    public record NewFolderResponse(long folderId) implements IResponse {}

}
