package app.finwave.api.config.app;

public class TransactionConfig {
    public int maxTransactionsInListPerRequest = 128;
    public int maxDescriptionLength = 256;

    public TransactionTagConfig tags = new TransactionTagConfig();

    public static class TransactionTagConfig {
        public int maxTagsPerUser = 256;
        public int maxNameLength = 64;
        public int maxDescriptionLength = 128;
    }
}
