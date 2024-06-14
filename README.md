# FinWave Java REST API Wrapper 

Java wrapper for the convenient interaction with the FinWave REST API. It provides a simple interface to perform requests to FinWave server.

## Implemented API classes

- **AccountApi**
- **AccountTagApi**
- **AccumulationApi**
- **AnalyticsApi**
- **AuthApi**
- **ConfigApi**
- **CurrencyApi**
- **NoteApi**
- **NotificationApi**
- **RecurringTransactionApi**
- **ReportsApi**
- **SessionsApi**
- **TransactionApi**
- **TransactionTagApi**
- **UserApi**

## Install
### Gradle

```gradle
repositories {
    mavenCentral()
    ...
    maven {
        url "https://maven.pkg.github.com/finwave-app/finwave-java-api"
    }
}

dependencies {
    ...
    implementation 'app.finwave.api:finwave-java-api:1.0.1'
}
```

## Usage Example

```java
public class FinWaveExample {
    public static void main(String[] args) {
        // Authenticate and create a session
        FinWaveClient client = new FinWaveClient("https://demo.finwave.app/api/");

        var demo = client.runRequest(new AuthApi.DemoRequest()).get();
        var response = client.runRequest(
                new AuthApi.LoginRequest(
                        demo.username(),
                        demo.password(),
                        "FinWaveAPI account test")
        ).get();

        client.setToken(response.token());

        try {
            OffsetDateTime notificationTime = OffsetDateTime.now().plusDays(1);
            var note = client.runRequest(new NoteApi.NewNoteRequest(notificationTime, "Test note")).get();
            noteId = note.noteId();
            ...
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

### Asynchronously:

```java
void sendRequest() {
    client.runRequest(new AccountApi.GetAccountsRequest())
            .whenCompleteAsync(this::getAccounts);
}

void getAccounts(AccountApi.GetAccountsListResponse response, Throwable e) {
    if (e != null)
        return; // Some error

    List<AccountApi.AccountEntry> accounts = response.accounts();
}
```

This project also has [tests](https://github.com/FinWave-App/FinWave-Java-API/tree/main/src/test/java/su/knst/finwave/api) where you can see more usage examples.
