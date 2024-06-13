package su.knst.finwave.api;


import org.junit.jupiter.api.*;
import su.knst.finwave.api.tools.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurringTransactionApiTest {

    private FinWaveClient client;
    protected long recurringTransactionId;
    protected long tagId;
    protected long accountId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();

        var accountTag = client.runRequest(new AccountTagApi.NewTagRequest("Test Tag", "Test Description")).get();
        assertNotNull(accountTag);

        var account = client.runRequest(new AccountApi.NewAccountRequest(accountTag.tagId(), 1, "Test Account", "Test Account Description")).get();
        assertNotNull(account);

        accountId = account.accountId();

        var transactionTag = client.runRequest(new TransactionTagApi.NewTagRequest(0, null, "Test", "test")).get();
        assertNotNull(transactionTag);

        tagId = transactionTag.tagId();
    }

    @Test
    @Order(1)
    void newRecurringTransaction() throws ExecutionException, InterruptedException {
        OffsetDateTime nextRepeat = OffsetDateTime.now().plusMonths(1);
        var response = client.runRequest(new RecurringTransactionApi.NewRecurringTransactionRequest(
                tagId, accountId, nextRepeat, 1, (short) 1, 1, BigDecimal.TEN, "Test Recurring Transaction"
        )).get();

        assertNotNull(response);
        assertTrue(response.recurringTransactionId() > 0);

        recurringTransactionId = response.recurringTransactionId();
    }

    @Test
    @Order(2)
    void editRecurringTransaction() throws ExecutionException, InterruptedException {
        OffsetDateTime newNextRepeat = OffsetDateTime.now().plusMonths(2);
        var response = client.runRequest(new RecurringTransactionApi.EditRecurringTransactionRequest(
                recurringTransactionId, tagId, accountId, newNextRepeat, 1, (short) 2, 1, BigDecimal.valueOf(20), "Edited Recurring Transaction"
        )).get();

        assertNotNull(response);
        assertEquals("Recurring transaction edited", response.message());

        var recurringListResponse = client.runRequest(new RecurringTransactionApi.GetRecurringListRequest()).get();
        var editedTransaction = recurringListResponse.recurringList().stream()
                .filter(r -> r.recurringTransactionId() == recurringTransactionId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTransaction);
        assertEquals(newNextRepeat.truncatedTo(ChronoUnit.SECONDS), editedTransaction.nextRepeat().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(BigDecimal.valueOf(20), editedTransaction.delta());
        assertEquals("Edited Recurring Transaction", editedTransaction.description());
    }

    @Test
    @Order(3)
    void getRecurringList() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new RecurringTransactionApi.GetRecurringListRequest()).get();
        assertNotNull(response);
        assertNotNull(response.recurringList());

        var createdTransaction = response.recurringList().stream()
                .filter(r -> r.recurringTransactionId() == recurringTransactionId)
                .findFirst()
                .orElse(null);

        assertNotNull(createdTransaction);
        assertEquals("Edited Recurring Transaction", createdTransaction.description());
    }

    @Test
    @Order(4)
    void deleteRecurringTransaction() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new RecurringTransactionApi.DeleteRecurringTransactionRequest(recurringTransactionId)).get();
        assertNotNull(response);
        assertEquals("Recurring deleted", response.message());

        var recurringListResponse = client.runRequest(new RecurringTransactionApi.GetRecurringListRequest()).get();
        var deletedTransaction = recurringListResponse.recurringList().stream()
                .filter(r -> r.recurringTransactionId() == recurringTransactionId)
                .findFirst()
                .orElse(null);

        assertNull(deletedTransaction, "Transaction should not be found after deletion");
    }
}