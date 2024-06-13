package su.knst.finwave.api.tools;

public interface IRequest<T extends IResponse> {
    Class<T> getResponseClass();

    String getUrl();

    RequestMethod getMethod();

    Object getData();
}
