package su.knst.finwave.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import su.knst.finwave.api.tools.*;

import java.util.concurrent.ExecutionException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyApiTest {
    private FinWaveClient client;
    protected long currencyId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newCurrency() throws ExecutionException, InterruptedException {
        var currency = client.runRequest(new CurrencyApi.NewCurrencyRequest("USD", "$", 2, "United States Dollar")).get();
        assertNotNull(currency);
        assertTrue(currency.currencyId() > 0);

        currencyId = currency.currencyId();
    }

    @Test
    @Order(2)
    void editCurrencyCode() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new CurrencyApi.EditCurrencyCodeRequest(currencyId, "USDT")).get();
        assertNotNull(result);
        assertEquals("Currency code edited", result.message());
    }

    @Test
    @Order(3)
    void editCurrencySymbol() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new CurrencyApi.EditCurrencySymbolRequest(currencyId, "₮")).get();
        assertNotNull(result);
        assertEquals("Currency symbol edited", result.message());
    }

    @Test
    @Order(4)
    void editCurrencyDecimals() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new CurrencyApi.EditCurrencyDecimalsRequest(currencyId, 3)).get();
        assertNotNull(result);
        assertEquals("Currency decimals edited", result.message());
    }

    @Test
    @Order(5)
    void editCurrencyDescription() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new CurrencyApi.EditCurrencyDescriptionRequest(currencyId, "US Dollar Tether")).get();
        assertNotNull(result);
        assertEquals("Currency description edited", result.message());
    }

    @Test
    @Order(6)
    void getCurrencies() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new CurrencyApi.GetCurrenciesRequest()).get();
        assertNotNull(result);
        assertNotNull(result.currencies());

        var currencyEntryOptional = result.currencies().stream().filter(c -> c.currencyId() == currencyId).findAny();
        assertTrue(currencyEntryOptional.isPresent());

        var currencyEntry = currencyEntryOptional.get();

        assertEquals("USDT", currencyEntry.code());
        assertEquals("₮", currencyEntry.symbol());
        assertEquals(3, currencyEntry.decimals());
        assertEquals("US Dollar Tether", currencyEntry.description());
    }
}