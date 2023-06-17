import java.util.List;

import API.Auth.Accounts;
import javax.servlet.http.HttpServletResponse;

public interface AccountsService {
    Accounts findByUsername(String username);

    Accounts findByAccountId(String accountId);

    void deleteAccountByID(Long accountId);

    void login(String username, String password, HttpServletResponse response);

    void register(String name, int age, String email, String username, String password);

    void logout(HttpServletResponse response);

    void updateUsername(String username, HttpServletResponse response);

    void updatePassword(String password);

    List<Accounts> findAll();
}
