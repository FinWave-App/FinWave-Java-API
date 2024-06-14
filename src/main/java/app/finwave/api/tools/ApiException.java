package app.finwave.api.tools;

public class ApiException extends Exception {
    public final ApiMessage message;

    public ApiException(ApiMessage message) {
        super(message.message());
        this.message = message;
    }
}
