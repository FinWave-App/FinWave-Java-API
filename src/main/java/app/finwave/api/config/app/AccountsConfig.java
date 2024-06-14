package app.finwave.api.config.app;

public class AccountsConfig {

    public int maxAccountsPerUser = 64;
    public int maxNameLength = 64;
    public int maxDescriptionLength = 128;

    public AccountTagsConfig tags = new AccountTagsConfig();

    public static class AccountTagsConfig {
        public int maxTagsPerUser = 32;
        public int maxNameLength = 64;
        public int maxDescriptionLength = 128;
    }
}
