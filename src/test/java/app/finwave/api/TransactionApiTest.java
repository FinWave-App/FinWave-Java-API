package app.finwave.api;


import app.finwave.api.tools.TransactionsFilter;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionApiTest {

    private FinWaveClient client;
    protected long transactionId;
    protected long tagId;
    protected long accountId;
    protected long toAccountId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();

        var accountTag = client.runRequest(new AccountTagApi.NewTagRequest("Test Tag", "Test Description")).get();
        assertNotNull(accountTag);
        var accountTagId = accountTag.tagId();

        var account = client.runRequest(new AccountApi.NewAccountRequest(accountTagId, 1, "Test Account", "Test Account Description")).get();
        assertNotNull(account);
        accountId = account.accountId();

        var toAccount = client.runRequest(new AccountApi.NewAccountRequest(accountTagId, 1, "Transfer Account", "Transfer Account Description")).get();
        assertNotNull(toAccount);
        toAccountId = toAccount.accountId();

        var transactionTag = client.runRequest(new TransactionTagApi.NewTagRequest(0, null, "Test", "test")).get();
        assertNotNull(transactionTag);

        tagId = transactionTag.tagId();
    }

    @Test
    @Order(1)
    void newTransaction() throws ExecutionException, InterruptedException {
        OffsetDateTime createdAt = OffsetDateTime.now();
        var response = client.runRequest(new TransactionApi.NewTransactionRequest(
                tagId, accountId, createdAt, BigDecimal.TEN, "Test Transaction"
        )).get();

        assertNotNull(response);
        assertTrue(response.transactionId() > 0);

        transactionId = response.transactionId();
    }

    @Test
    @Order(2)
    void getTransactionsList() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionApi.GetTransactionsRequest(0, 10, TransactionsFilter.EMPTY)).get();
        assertNotNull(response);
        assertNotNull(response.transactions());

        var transactionEntry = response.transactions().stream()
                .filter(t -> t.transactionId() == transactionId)
                .findFirst()
                .orElse(null);

        assertNotNull(transactionEntry);
        assertEquals("Test Transaction", transactionEntry.description());
    }

    @Test
    @Order(3)
    void editTransaction() throws ExecutionException, InterruptedException {
        OffsetDateTime createdAt = OffsetDateTime.now().plusDays(1);
        var response = client.runRequest(new TransactionApi.EditTransactionRequest(
                transactionId, tagId, accountId, createdAt, BigDecimal.valueOf(20), "Edited Transaction"
        )).get();

        assertNotNull(response);
        assertEquals("Transaction edited", response.message());


        var transactionsResponse = client.runRequest(new TransactionApi.GetTransactionsRequest(0, 100, TransactionsFilter.EMPTY)).get();
        var editedTransaction = transactionsResponse.transactions().stream()
                .filter(t -> t.transactionId() == transactionId)
                .findFirst()
                .orElse(null);

        assertNotNull(editedTransaction);
        assertTrue(createdAt.truncatedTo(ChronoUnit.SECONDS).isEqual(
                editedTransaction.createdAt().truncatedTo(ChronoUnit.SECONDS)
        ));
        assertEquals(BigDecimal.valueOf(20), editedTransaction.delta());
        assertEquals("Edited Transaction", editedTransaction.description());
    }

    @Test
    @Order(4)
    void deleteTransaction() throws ExecutionException, InterruptedException {
        var response = client.runRequest(new TransactionApi.DeleteTransactionRequest(transactionId)).get();
        assertNotNull(response);
        assertEquals("Transaction deleted", response.message());

        var transactionsResponse = client.runRequest(new TransactionApi.GetTransactionsRequest(0, 100, TransactionsFilter.EMPTY)).get();
        var deletedTransaction = transactionsResponse.transactions().stream()
                .filter(t -> t.transactionId() == transactionId)
                .findFirst()
                .orElse(null);

        assertNull(deletedTransaction, "Transaction should not be found after deletion");
    }

    @Test
    @Order(5)
    void newInternalTransfer() throws ExecutionException, InterruptedException {
        OffsetDateTime createdAt = OffsetDateTime.now();
        var response = client.runRequest(new TransactionApi.NewInternalTransferRequest(
                tagId, accountId, toAccountId, createdAt, BigDecimal.TEN.multiply(BigDecimal.valueOf(-1)), BigDecimal.TEN, "Test Internal Transfer"
        )).get();

        assertNotNull(response);
        assertTrue(response.transactionId() > 0);
    }

    @Test
    @Order(6)
    void newBulkTransactions() throws ExecutionException, InterruptedException {
        OffsetDateTime createdAt = OffsetDateTime.now();
        var entries = List.of(
                new TransactionApi.BulkEntry(0, tagId, accountId, createdAt, BigDecimal.TEN, null, null, "Bulk Transaction 1"),
                new TransactionApi.BulkEntry(1, tagId, accountId, createdAt, BigDecimal.valueOf(20).multiply(BigDecimal.valueOf(-1)), toAccountId, BigDecimal.valueOf(20), "Bulk Transaction 2")
        );
        var response = client.runRequest(new TransactionApi.NewBulkTransactionsRequest(entries)).get();

        assertNotNull(response);
        assertEquals("Successful", response.message());
    }
}