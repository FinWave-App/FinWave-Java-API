package su.knst.finwave.api;

import java.util.concurrent.ExecutionException;

public class DemoLogin {
    public static FinWaveClient createDemoAndLogin() throws ExecutionException, InterruptedException {
        FinWaveClient client = new FinWaveClient("http://localhost:8080/");

        var demo = client.runRequest(new AuthApi.DemoRequest()).get();
        var response = client.runRequest(
                new AuthApi.LoginRequest(
                        demo.username(),
                        demo.password(),
                        "FinWaveAPI account test")
        ).get();

        client.setToken(response.token());

        return client;
    }
}
