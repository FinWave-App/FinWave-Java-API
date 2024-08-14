package app.finwave.api;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionCategoryApiTest {

    private FinWaveClient client;
    protected long categoryId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newCategory() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.NewCategoryRequest(
                0, null, "Test Category", "Test Description"
        )).get();
        assertNotNull(response);
        assertTrue(response.categoryId() > 0);

        categoryId = response.categoryId();
    }

    @Test
    @Order(2)
    void getCategories() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        assertNotNull(response);
        assertNotNull(response.categories());

        var categoryEntry = response.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(categoryEntry);
        assertEquals("Test Category", categoryEntry.name());
        assertEquals("Test Description", categoryEntry.description());
        assertEquals(0, categoryEntry.type());
    }

    @Test
    @Order(3)
    void editCategoryType() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.EditCategoryTypeRequest(
                categoryId, 1
        )).get();
        assertNotNull(response);
        assertEquals("category type edited", response.message());

        var categoriesResponse = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        var editedCategory = categoriesResponse.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedCategory);
        assertEquals(1, editedCategory.type());
    }

    @Test
    @Order(4)
    void editCategoryParent() throws ExecutionException, InterruptedException {
        var parentResponse = client.runRequest(new TransactionCategoryApi.NewCategoryRequest(
                1, null, "Parent Category", "Parent Description"
        )).get();
        assertNotNull(parentResponse);
        long parentId = parentResponse.categoryId();

        var response = client.runRequest(new TransactionCategoryApi.EditCategoryParentRequest(
                categoryId, parentId, false
        )).get();
        assertNotNull(response);
        assertEquals("category parent edited", response.message());

        var categoriesResponse = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        var editedCategory = categoriesResponse.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedCategory);
        assertTrue(editedCategory.parentsTree().contains(String.valueOf(parentId)));
    }

    @Test
    @Order(5)
    void setCategoryToRoot() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.EditCategoryParentRequest(
                categoryId, null, true
        )).get();
        assertNotNull(response);
        assertEquals("category parent edited", response.message());

        var categoriesResponse = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        var editedCategory = categoriesResponse.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedCategory);
        assertEquals("", editedCategory.parentsTree());
    }

    @Test
    @Order(6)
    void editCategoryName() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.EditCategoryNameRequest(
                categoryId, "Updated Test Category"
        )).get();
        assertNotNull(response);
        assertEquals("category name edited", response.message());

        var categoriesResponse = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        var editedCategory = categoriesResponse.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedCategory);
        assertEquals("Updated Test Category", editedCategory.name());
    }

    @Test
    @Order(7)
    void editCategoryDescription() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionCategoryApi.EditCategoryDescriptionRequest(
                categoryId, "Updated Test Description"
        )).get();
        assertNotNull(response);
        assertEquals("Category description edited", response.message());

        var categoriesResponse = client.runRequest(new TransactionCategoryApi.GetCategoriesRequest()).get();
        var editedCategory = categoriesResponse.categories().stream()
                .filter(t -> t.categoryId() == categoryId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedCategory);
        assertEquals("Updated Test Description", editedCategory.description());
    }
}