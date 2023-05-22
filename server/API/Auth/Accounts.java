package API.Auth;

import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "accounts")
public class Accounts {

    @Id
    private @GeneratedValue Long accountId;
    private String username;
    private String password;

    private String name;
    private int age;
    private String email;

    Accounts() {}

    Accounts(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
