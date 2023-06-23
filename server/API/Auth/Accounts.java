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

    Accounts(String name, int age, String email, String username, String password) {
        this.accountId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public void changePassword(String password) {
        this.password = password;
    }
}
