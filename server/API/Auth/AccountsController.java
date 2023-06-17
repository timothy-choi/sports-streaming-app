
package API.Auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping(value="/accounts")
    public ResponseEntity getAllAccounts() {
        List<Accounts> accts = accountsService.findAll();
        if (accts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(accts);
    }


    @GetMapping(value="/accounts/{accountId}") 
    public ResponseEntity getAccountById(@PathVariable Long accountId) {
        Accounts acct = accountsService.findByAccountId(accountId);
        if (acct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(acct);
    }


    @GetMapping(value="/accounts/{username}")
    public ResponseEntity getAccountByUsername(@PathVariable String username) {
        Accounts acct = accountsService.findByUsername(username);
        if (acct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(acct);
    }


    @DeleteMapping(value="/accounts/{accountId}")
    public ResponseEntity deleteAcct(@PathVariable Long accountId) {
        try {
            accountsService.deleteAccountByID(accountId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity("Account deleted successfully", HttpStatus.OK);
    }


    @PutMapping(value="/accounts/username/{Username}")
    public ResponseEntity updateUsername(@PathVariable String username) {
        try {
            HttpServletResponse response = new HttpServletResponse();
            accountsService.updateUsername(username, response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity("Username updated successfully", HttpStatus.OK);
    }

    @PutMapping(value="/accounts/password/{Password}")
    public ResponseEntity<?> updatePassword(@PathVariable String password) {
        try {
            accountsService.findByPassword(password);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        return ResponseEntity("Password updated successfully", HttpStatus.OK);
    }

    @PostMapping(value="/accounts/login")
    public ResponseEntity login(@RequestBody String username, @RequestBody String password) {
        try {
            HttpServletResponse response = new HttpServletResponse();
            accountsService.login(username, password, response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login Failed");
        }
        return ResponseEntity("Login successful", HttpStatus.OK);
    }

    @PostMapping(value="/accounts/logout")
    public ResponseEntity logout() {
        try {
            HttpServletResponse response = new HttpServletResponse();
            accountsService.logout(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logout Failed");
        }
        return ResponseEntity("Logout successful", HttpStatus.OK);
    }

    @PostMapping(value="/accounts/signup")
    public ResponseEntity signup(@RequestBody String name, @RequestBody int age, @RequestBody String email, @RequestBody String username, @RequestBody String password) {
        try {
            accountsService.register(name, age, email, username, password);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Signup Failed");
        }
        return ResponseEntity("Signup successful", HttpStatus.OK);
    }
}
