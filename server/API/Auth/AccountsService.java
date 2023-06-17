import java.util.List;

import API.Auth.Accounts;

public interface AccountsService {
    Accounts findByUsername(String username);

    Accounts findByAccountId(String accountId);

    void deleteAccountByID(Long accountId);

    void login(String username, String password);

    void register(String name, int age, String email, String username, String password);

    void logout();

    void updateUsername(String username);

    void updatePassword(String password);

    List<Accounts> findAll();
}
