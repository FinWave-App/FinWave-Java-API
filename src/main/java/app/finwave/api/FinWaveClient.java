package app.finwave.api;

import app.finwave.api.tools.*;
import app.finwave.api.websocket.FinWaveWebSocketClient;
import app.finwave.api.websocket.messages.handler.AbstractWebSocketHandler;
import app.finwave.api.websocket.messages.requests.AuthMessageRequest;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinWaveClient {
    protected static ExecutorService threadPool = Executors.newCachedThreadPool(
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

    public FinWaveWebSocketClient connectToWebsocket(String url, String path, AbstractWebSocketHandler handler) throws URISyntaxException, InterruptedException {
        FinWaveWebSocketClient client = new FinWaveWebSocketClient(new URI(url + path));
        client.setHandler(handler);

        boolean connected = client.connectBlocking();

        if (connected && token != null && !token.isBlank())
            client.send(new AuthMessageRequest(token));

        return client;
    }

    public FinWaveWebSocketClient connectToWebsocket(String path, AbstractWebSocketHandler handler) throws URISyntaxException, InterruptedException {
        return connectToWebsocket(
                baseURL.replaceFirst("http://", "ws://")
                        .replaceFirst("https://", "wss://"),
                path, handler
        );
    }

    public FinWaveWebSocketClient connectToWebsocket(AbstractWebSocketHandler handler) throws URISyntaxException, InterruptedException {
        return connectToWebsocket("websockets/events", handler);
    }

    public <R extends IResponse, T extends IRequest<R>> CompletableFuture<R> runRequest(T request) {
        return runRequest(request, connectTimeout, readTimeout);
    }

    public <R extends IResponse, T extends IRequest<R>> CompletableFuture<R> runRequest(T request, int connectTimeout, int readTimeout) {
        CompletableFuture<R> future = new CompletableFuture<>();

        threadPool.submit(() -> {
            try {
                future.complete(sendRequest(request, connectTimeout, readTimeout));
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

    protected <R extends IResponse, T extends IRequest<R>> R sendRequest(T request, int connectTimeout, int readTimeout) throws Exception {
        Object data = request.getData();
        HttpURLConnection connection = getConnection(request, data, connectTimeout, readTimeout);

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

    private <R extends IResponse, T extends IRequest<R>> HttpURLConnection getConnection(T request, Object data, int connectTimeout, int readTimeout) throws IOException {
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
