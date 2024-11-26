import java.util.HashMap;

public class AccountManager {
    private final HashMap<String, String> accounts;

    public AccountManager() {
        this.accounts = new HashMap<>();
    }

    public boolean createAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            return false; 
        }
        accounts.put(username, password);
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }

    public void addUser(String username, String password) {
        if (createAccount(username, password)) {
            System.out.println("\nAccount created successfully for user: " + username);
        } else {
            System.out.println("\nUsername already exists. Try a different username.");
        }
    }
}
