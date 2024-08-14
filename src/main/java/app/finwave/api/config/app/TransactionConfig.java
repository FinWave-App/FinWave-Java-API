package app.finwave.api.config.app;

public class TransactionConfig {
    public int maxTransactionsInListPerRequest = 128;
    public int maxDescriptionLength = 256;

    public TransactionCategoryConfig categories = new TransactionCategoryConfig();

    public static class TransactionCategoryConfig {
        public int maxCategoriesPerUser = 256;
        public int maxNameLength = 64;
        public int maxDescriptionLength = 128;
    }
}
