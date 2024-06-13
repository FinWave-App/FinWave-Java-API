package su.knst.finwave.api;

import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountApiTest {

    private FinWaveClient client;
    protected long accountId;
    protected long lastTag;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newAccount() throws ExecutionException, InterruptedException {
        var tag = client.runRequest(new AccountTagApi.NewTagRequest("test", "test")).get();
        assertNotNull(tag);

        var account = client.runRequest(new AccountApi.NewAccountRequest(tag.tagId(), 1, "Test account", null)).get();

        assertNotNull(account);
        assertTrue(account.accountId() > 0);

        accountId = account.accountId();
    }

    @Test
    @Order(2)
    void hideAccount() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccountApi.HideAccountRequest(accountId)).get();

        assertNotNull(result);
        assertEquals("Account hided", result.message());
    }

    @Test
    @Order(3)
    void showAccount() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccountApi.ShowAccountRequest(accountId)).get();

        assertNotNull(result);
        assertEquals("Account showed", result.message());
    }

    @Test
    @Order(4)
    void editAccountName() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccountApi.EditAccountNameRequest(accountId, "New name")).get();

        assertNotNull(result);
        assertEquals("Account name edited", result.message());
    }

    @Test
    @Order(5)
    void editAccountDescription() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccountApi.EditAccountDescriptionRequest(accountId, "New description")).get();

        assertNotNull(result);
        assertEquals("Account description edited", result.message());
    }

    @Test
    @Order(6)
    void editAccountTag() throws ExecutionException, InterruptedException {
        var tag = client.runRequest(new AccountTagApi.NewTagRequest("test 2", "test 2")).get();
        assertNotNull(tag);

        lastTag = tag.tagId();
        var result = client.runRequest(new AccountApi.EditAccountTagRequest(accountId, tag.tagId())).get();

        assertNotNull(result);
        assertEquals("Account tag edited", result.message());
    }

    @Test
    @Order(7)
    void getAccounts() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccountApi.GetAccountsRequest()).get();
        assertNotNull(result);

        var accountEntryOptional = result.accounts().stream().filter(a -> a.accountId() == accountId).findAny();
        assertTrue(accountEntryOptional.isPresent());

        var accountEntry = accountEntryOptional.get();

        assertEquals("New name", accountEntry.name());
        assertEquals("New description", accountEntry.description());
        assertFalse(accountEntry.hidden());
        assertEquals(lastTag, accountEntry.tagId());
    }
}