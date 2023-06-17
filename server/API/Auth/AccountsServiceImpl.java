
package API.Auth;

import Java.API.Auth.AccountsService;
import Java.API.Auth.AccountsRepository;
import Java.API.Auth.Accounts;
import javax.servlet.http.HttpServletResponse;

public class AccountsServiceImpl implements AccountsService {
    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public List<Accounts> findAll() {
        return accountsRepository.findAll();
    }

    @Override
    public Accounts findByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }

    @Override
    public Accounts findByAccountId(String accountId) {
        return accountsRepository.findByAccountId(accountId);
    }

    @Override
    public updateUsername(String username, String newUser, HttpServletResponse response) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account == null) {
            throw new Exception("Username not found");
        }

        Account newAccount = accountsRepository.findByUsername(newUser);
        if (newAccount != null) {
            throw new Exception("Username already exists");
        }
        account.changeUsername(newUser);
        accountsRepository.save(account);
        //add call to change cookie
        Cookie newCookie = new Cookie("username", username);
        response.addCookie(newCookie);
    }

    @Override
    public updatePassword(String password) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account == null) {
            throw new Exception("Username not found");
        }
        account.changePassword(password);
        accountsRepository.save(account);
    }

    @Override
    public void deleteAccountByID(Long accountId) {
        Accounts account = accountsRepository.findByAccountId(accountId);
        if (account == null) {
            throw new Exception("Account not found");
        }
        accountsRepository.delete(account);
    }

    @Override
    public void login(String username, String password, HttpServletResponse response) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account == null) {
            throw new Exception("Username not found");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(account.getPassword()); //encode password
        if (!BCrypt.checkpw(password, hashedPassword)) {
            throw new Exception("Wrong password");
        }
        response.addCookie(new Cookie("username", username));
    }

    @Override
    public void logout(HttpServletResponse response) {
        response.addCookie(new Cookie("username", null).setMaxAge(0));
        return;
    }

    @Override
    public void register(String name, int age, String email, String username, String password) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account != null) {
            throw new Exception("Username already exists");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Accounts newAcct = new Accounts(name, age, email, username, password);
        newAcct.setPassword(passwordEncoder.encode(newAcct.getPassword()));
        accountsRepository.save(newAcct);
    }
}
