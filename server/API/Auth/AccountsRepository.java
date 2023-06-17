package API.Auth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.org.list;

public interface AccountsRepository extends MongoRepository<Accounts, String> {
    Accounts findByAccountId(Long accountId);

    Accounts findByUsername(String username);

    List<Accounts> findAll();
}
