package app.finwave.api;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountFolderApiTest {
    private FinWaveClient client;
    protected long folderId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newFolder() throws ExecutionException, InterruptedException {
        var folderResponse = client.runRequest(new AccountFolderApi.NewFolderRequest("test folder", "test description")).get();
        assertNotNull(folderResponse);

        folderId = folderResponse.folderId();
        assertTrue(folderId > 0);
    }

    @Test
    @Order(2)
    void editFolderName() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountFolderApi.EditFolderNameRequest(folderId, "updated folder name")).get();

        assertNotNull(response);
        assertEquals("Folder name edited", response.message());
    }

    @Test
    @Order(3)
    void editFolderDescription() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountFolderApi.EditFolderDescriptionRequest(folderId, "updated description")).get();

        assertNotNull(response);
        assertEquals("Folder description edited", response.message());
    }

    @Test
    @Order(4)
    void getFolders() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountFolderApi.GetFoldersRequest()).get();

        assertNotNull(response);

        var folderEntryOptional = response.folders().stream().filter(t -> t.folderId() == folderId).findAny();
        assertTrue(folderEntryOptional.isPresent());

        var folderEntry = folderEntryOptional.get();
        assertEquals("updated folder name", folderEntry.name());
        assertEquals("updated description", folderEntry.description());
    }

    @Test
    @Order(5)
    void deleteFolder() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountFolderApi.DeleteFolderRequest(folderId)).get();

        assertNotNull(response);
        assertEquals("Folder deleted", response.message());
    }
}