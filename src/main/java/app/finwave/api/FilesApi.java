package app.finwave.api;

import app.finwave.api.tools.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class FilesApi {

    public record AvailableSpaceRequest() implements IRequest<AvailableSpaceResponse> {
        @Override
        public Class<AvailableSpaceResponse> getResponseClass() {
            return AvailableSpaceResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/files/availableSpace";
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

    public record DeleteFileRequest(String fileId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/files/delete", "fileId", fileId);
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

    public record DeleteAllFilesRequest() implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return "user/files/deleteAll";
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

    public record GetFilesListRequest() implements IRequest<GetListResponse> {
        @Override
        public Class<GetListResponse> getResponseClass() {
            return GetListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/files/getList";
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

    public record AvailableSpaceResponse(long usedBytes, long freeBytes, long maxBytes) implements IResponse {}

    public record GetListResponse(List<Entry> files) implements IResponse {
        public record Entry(String fileId, boolean isPublic, OffsetDateTime createdAt, OffsetDateTime expiresAt, long size, String mimeType, String name, String description) {}
    }
}