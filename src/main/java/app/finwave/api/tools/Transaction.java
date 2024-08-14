package app.finwave.api.tools;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transaction(long transactionId, long categoryId, long accountId, long currencyId, OffsetDateTime createdAt,
                          BigDecimal delta, String description, AbstractMetadata metadata) {
}
