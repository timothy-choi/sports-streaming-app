package API.Auth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.org.list;

public interface AccountsRepository extends MongoRepository<Accounts, String> {

    Accounts findByUsername(String username);

    Accounts findByAccountId(String accountId);

    Accounts findByEmail(String email);

    List<Accounts> findAll();
    
}
