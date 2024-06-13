package su.knst.finwave.api;

import com.google.gson.JsonElement;
import su.knst.finwave.api.tools.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinWaveClient {
    protected static ExecutorService threadPool = Executors.newFixedThreadPool(4,
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);

                return thread;
            });

    protected String token;
    protected String baseURL;
    protected int connectTimeout = 1000;
    protected int readTimeout = 1000;

    public FinWaveClient(String baseURL, String token, int connectTimeout, int readTimeout) {
        this.baseURL = baseURL;
        this.token = token;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public FinWaveClient(String baseURL, String token) {
        this.baseURL = baseURL;
        this.token = token;
    }

    public FinWaveClient(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public  <R extends IResponse, T extends IRequest<R>> CompletableFuture<R> runRequest(T request) {
        CompletableFuture<R> future = new CompletableFuture<>();

        threadPool.submit(() -> {
            try {
                future.complete(sendRequest(request));
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    protected <R extends IResponse, T extends IRequest<R>> R sendRequest(T request) throws Exception {
        Object data = request.getData();
        HttpURLConnection connection = getConnection(request, data);

        if (data != null) {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
            Misc.GSON.toJson(data, writer);
            writer.close();
        }

        boolean isError = isError(connection.getResponseCode());

        InputStreamReader reader = new InputStreamReader(
                !isError ? connection.getInputStream() : connection.getErrorStream(),
                StandardCharsets.UTF_8
        );

        JsonElement response = Misc.GSON.fromJson(reader, JsonElement.class);
        reader.close();

        if (!isError) {
            return Misc.GSON.fromJson(response, request.getResponseClass());
        }

        throw new ApiException(Misc.GSON.fromJson(response, ApiMessage.class));
    }

    private <R extends IResponse, T extends IRequest<R>> HttpURLConnection getConnection(T request, Object data) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(baseURL + request.getUrl()).openConnection();

        if (data != null)
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        if (token != null)
            connection.setRequestProperty("Authorization", "Bearer " + token);

        connection.setDoOutput(true);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod(request.getMethod().name());

        return connection;
    }

    protected boolean isError(int code){
        return code < 200 || code >= 300;
    }
}
