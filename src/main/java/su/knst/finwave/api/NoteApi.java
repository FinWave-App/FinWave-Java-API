package su.knst.finwave.api;

import su.knst.finwave.api.tools.*;

import java.time.OffsetDateTime;
import java.util.List;

public class NoteApi {
    public record NewNoteRequest(OffsetDateTime notificationTime, String text) implements IRequest<NewNoteResponse> {
        @Override
        public Class<NewNoteResponse> getResponseClass() {
            return NewNoteResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notes/new",
                    "notificationTime", notificationTime,
                    "text", text);
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

    public record EditNoteRequest(long noteId, String text) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notes/edit",
                    "noteId", noteId,
                    "text", text);
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

    public record EditNoteNotificationTimeRequest(long noteId, OffsetDateTime notificationTime) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notes/editTime",
                    "noteId", noteId,
                    "notificationTime", notificationTime);
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

    public record GetNoteRequest(long noteId) implements IRequest<GetNoteResponse> {
        @Override
        public Class<GetNoteResponse> getResponseClass() {
            return GetNoteResponse.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notes/get", "noteId", noteId);
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

    public record GetNotesListRequest() implements IRequest<GetNotesListResponse> {
        @Override
        public Class<GetNotesListResponse> getResponseClass() {
            return GetNotesListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/notes/getList";
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

    public record GetImportantNotesRequest() implements IRequest<GetNotesListResponse> {
        @Override
        public Class<GetNotesListResponse> getResponseClass() {
            return GetNotesListResponse.class;
        }

        @Override
        public String getUrl() {
            return "user/notes/getImportant";
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

    public record DeleteNoteRequest(long noteId) implements IRequest<ApiMessage> {
        @Override
        public Class<ApiMessage> getResponseClass() {
            return ApiMessage.class;
        }

        @Override
        public String getUrl() {
            return Misc.formatQueryURL("user/notes/delete", "noteId", noteId);
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

    public record NewNoteResponse(long noteId) implements IResponse {}

    public record GetNoteResponse(OffsetDateTime notificationTime, OffsetDateTime lastEdit, String text) implements IResponse {}

    public record GetNotesListResponse(List<NoteEntry> notes) implements IResponse {}

    public record NoteEntry(long noteId, OffsetDateTime notificationTime, OffsetDateTime lastEdit, String text) {}
}
