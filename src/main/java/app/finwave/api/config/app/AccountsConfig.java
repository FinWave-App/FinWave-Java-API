package app.finwave.api.config.app;

public class AccountsConfig {

    public int maxAccountsPerUser = 64;
    public int maxNameLength = 64;
    public int maxDescriptionLength = 128;

    public AccountFoldersConfig folders = new AccountFoldersConfig();

    public static class AccountFoldersConfig {
        public int maxFoldersPerUser = 32;
        public int maxNameLength = 64;
        public int maxDescriptionLength = 128;
    }
}
