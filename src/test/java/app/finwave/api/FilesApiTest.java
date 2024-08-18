package app.finwave.api;

import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilesApiTest {
    private FinWaveClient client;
    private String fileId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void uploadFromGithub() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new FilesApi.UploadFromURLRequest(
                1, true, "image/png", "FinWave icon", "Java REST API wrapper test",
                "https://github.com/FinWave-App/.github/blob/main/icons/icon.png?raw=true")
        ).get();

        assertNotNull(response);
        assertFalse(response.fileId().isBlank());

        fileId = response.fileId();
    }

    @Test
    @Order(2)
    void checkList() throws ExecutionException, InterruptedException {
        var list = client.runRequest(new FilesApi.GetFilesListRequest()).get();

        Optional<FilesApi.GetListResponse.Entry> optionalFile = list.files().stream().filter((f) -> f.fileId().equals(fileId)).findFirst();
        assertFalse(optionalFile.isEmpty());

        FilesApi.GetListResponse.Entry file = optionalFile.get();

        assertEquals("FinWave icon", file.name());
        assertEquals("Java REST API wrapper test", file.description());
        assertEquals("image/png", file.mimeType());
        assertTrue(file.isPublic());
    }

    @Test
    @Order(3)
    void deleteAll() throws ExecutionException, InterruptedException {
        client.runRequest(new FilesApi.DeleteAllFilesRequest()).get();

        var list = client.runRequest(new FilesApi.GetFilesListRequest()).get();
        assertTrue(list.files().isEmpty());
    }
}
