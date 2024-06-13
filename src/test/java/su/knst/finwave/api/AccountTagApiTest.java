package su.knst.finwave.api;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTagApiTest {
    private FinWaveClient client;
    protected long tagId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newTag() throws ExecutionException, InterruptedException {
        var tagResponse = client.runRequest(new AccountTagApi.NewTagRequest("test tag", "test description")).get();
        assertNotNull(tagResponse);

        tagId = tagResponse.tagId();
        assertTrue(tagId > 0);
    }

    @Test
    @Order(2)
    void editTagName() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountTagApi.EditTagNameRequest(tagId, "updated tag name")).get();

        assertNotNull(response);
        assertEquals("Tag name edited", response.message());
    }

    @Test
    @Order(3)
    void editTagDescription() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountTagApi.EditTagDescriptionRequest(tagId, "updated description")).get();

        assertNotNull(response);
        assertEquals("Tag description edited", response.message());
    }

    @Test
    @Order(4)
    void getTags() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountTagApi.GetTagsRequest()).get();

        assertNotNull(response);

        var tagEntryOptional = response.tags().stream().filter(t -> t.tagId() == tagId).findAny();
        assertTrue(tagEntryOptional.isPresent());

        var tagEntry = tagEntryOptional.get();
        assertEquals("updated tag name", tagEntry.name());
        assertEquals("updated description", tagEntry.description());
    }

    @Test
    @Order(5)
    void deleteTag() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new AccountTagApi.DeleteTagRequest(tagId)).get();

        assertNotNull(response);
        assertEquals("Tag deleted", response.message());
    }
}