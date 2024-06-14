package app.finwave.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccumulationApiTest {

    private FinWaveClient client;
    protected long sourceAccountId;
    protected long targetAccountId;
    protected long tagId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();

        var tagResponse = client.runRequest(new AccountTagApi.NewTagRequest("test tag", "test description")).get();
        assertNotNull(tagResponse);

        var accountTagId = tagResponse.tagId();

        var sourceAccount = client.runRequest(new AccountApi.NewAccountRequest(accountTagId, 1, "Source Account", null)).get();
        assertNotNull(sourceAccount);
        sourceAccountId = sourceAccount.accountId();

        var targetAccount = client.runRequest(new AccountApi.NewAccountRequest(accountTagId, 1, "Target Account", null)).get();
        assertNotNull(targetAccount);
        targetAccountId = targetAccount.accountId();

        var tag = client.runRequest(new TransactionTagApi.NewTagRequest(0, null, "test", "test")).get();
        assertNotNull(tag);
        tagId = tag.tagId();
    }

    @Test
    @Order(1)
    void setAccumulation() throws ExecutionException, InterruptedException {
        var steps = List.of(
                new AccumulationApi.AccumulationStep(BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE)
        );
        var result = client.runRequest(new AccumulationApi.SetAccumulationRequest(sourceAccountId, targetAccountId, tagId, steps)).get();

        assertNotNull(result);
        Assertions.assertEquals("Accumulation set", result.message());
    }

    @Test
    @Order(2)
    void getAccumulationsList() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccumulationApi.GetAccumulationsListRequest()).get();
        assertNotNull(result);

        var accumulationOptional = result.accumulations()
                .stream()
                .filter(a -> a.sourceAccountId() == sourceAccountId && a.targetAccountId() == targetAccountId && a.tagId() == tagId)
                .findAny();
        Assertions.assertTrue(accumulationOptional.isPresent());

        var accumulationData = accumulationOptional.get();
        Assertions.assertEquals(tagId, accumulationData.tagId());

        var step = accumulationData.steps().get(0);
        Assertions.assertEquals(BigDecimal.ZERO, step.from());
        Assertions.assertEquals(BigDecimal.TEN, step.to());
        Assertions.assertEquals(BigDecimal.ONE, step.step());
    }

    @Test
    @Order(3)
    void removeAccumulation() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new AccumulationApi.RemoveAccumulationRequest(sourceAccountId)).get();
        assertNotNull(result);
        Assertions.assertEquals("Accumulation removed", result.message());
    }
}