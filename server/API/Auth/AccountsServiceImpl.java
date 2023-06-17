
package API.Auth;

import Java.API.Auth.AccountsService;
import Java.API.Auth.AccountsRepository;
import Java.API.Auth.Accounts;

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
    public updateUsername(String username) {
        Accounts account = accountsRepository.findByUsername(username);
        account.changeUsername(username);
        accountsRepository.save(account);
        //add call to change cookie
    }

    @Override
    public updatePassword(String password) {
        Accounts account = accountsRepository.findByUsername(username);
        account.changePassword(password);
        accountsRepository.save(account);
    }

    @Override
    public void deleteAccountByID(Long accountId) {
        Accounts account = accountsRepository.findByAccountId(accountId);
        accountsRepository.delete(account);
    }

    @Override
    public void login(String username, String password) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account == null) {
            throw new Exception("Username not found");
        }
        String hashedPassword = account.getPassword(); //encode password
        if (!BCrypt.checkpw(password, hashedPassword)) {
            throw new Exception("Wrong password");
        }
        //add call to set cookie
    }

    @Override
    public void logout() {
        //add call to delete cookie
        return;
    }

    @Override
    public void register(String name, int age, String email, String username, String password) {
        Accounts account = accountsRepository.findByUsername(username);
        if (account != null) {
            throw new Exception("Username already exists");
        }
        password = null; //encode password
        Accounts newAcct = new Accounts(name, age, email, username, password);
        accountsRepository.save(newAcct);
    }
}
