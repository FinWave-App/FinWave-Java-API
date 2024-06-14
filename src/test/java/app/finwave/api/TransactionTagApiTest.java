package app.finwave.api;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionTagApiTest {

    private FinWaveClient client;
    protected long tagId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newTag() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.NewTagRequest(
                0, null, "Test Tag", "Test Description"
        )).get();
        assertNotNull(response);
        assertTrue(response.tagId() > 0);

        tagId = response.tagId();
    }

    @Test
    @Order(2)
    void getTags() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        assertNotNull(response);
        assertNotNull(response.tags());

        var tagEntry = response.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(tagEntry);
        assertEquals("Test Tag", tagEntry.name());
        assertEquals("Test Description", tagEntry.description());
        assertEquals(0, tagEntry.type());
    }

    @Test
    @Order(3)
    void editTagType() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.EditTagTypeRequest(
                tagId, 1
        )).get();
        assertNotNull(response);
        assertEquals("Tag type edited", response.message());

        var tagsResponse = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        var editedTag = tagsResponse.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTag);
        assertEquals(1, editedTag.type());
    }

    @Test
    @Order(4)
    void editTagParent() throws ExecutionException, InterruptedException {
        var parentResponse = client.runRequest(new TransactionTagApi.NewTagRequest(
                1, null, "Parent Tag", "Parent Description"
        )).get();
        assertNotNull(parentResponse);
        long parentId = parentResponse.tagId();

        var response = client.runRequest(new TransactionTagApi.EditTagParentRequest(
                tagId, parentId, false
        )).get();
        assertNotNull(response);
        assertEquals("Tag parent edited", response.message());

        var tagsResponse = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        var editedTag = tagsResponse.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTag);
        assertTrue(editedTag.parentsTree().contains(String.valueOf(parentId)));
    }

    @Test
    @Order(5)
    void setTagToRoot() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.EditTagParentRequest(
                tagId, null, true
        )).get();
        assertNotNull(response);
        assertEquals("Tag parent edited", response.message());

        var tagsResponse = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        var editedTag = tagsResponse.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTag);
        assertEquals("", editedTag.parentsTree());
    }

    @Test
    @Order(6)
    void editTagName() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.EditTagNameRequest(
                tagId, "Updated Test Tag"
        )).get();
        assertNotNull(response);
        assertEquals("Tag name edited", response.message());

        var tagsResponse = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        var editedTag = tagsResponse.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTag);
        assertEquals("Updated Test Tag", editedTag.name());
    }

    @Test
    @Order(7)
    void editTagDescription() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionTagApi.EditTagDescriptionRequest(
                tagId, "Updated Test Description"
        )).get();
        assertNotNull(response);
        assertEquals("Tag description edited", response.message());

        var tagsResponse = client.runRequest(new TransactionTagApi.GetTagsRequest()).get();
        var editedTag = tagsResponse.tags().stream()
                .filter(t -> t.tagId() == tagId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTag);
        assertEquals("Updated Test Description", editedTag.description());
    }
}