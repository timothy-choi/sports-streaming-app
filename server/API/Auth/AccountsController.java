
package API.Auth;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping(value="/")
    public 


    @GetMapping(value="/id/{accountId}") 
    public 


    @GetMapping(value="/username/{username}")
    public 


    @PostMapping(value="/add")
    public


    @DeleteMapping(value="/delete/{accountId}")
    public

    @PutMapping(value="/update/{Username}")
    public

    @PutMapping(value="/update/{Password}")
    public

    @PostMapping(value="/login")
    public

    @PostMapping(value="/logout")
    public

    @PostMapping(value="/signup")
    public
    

}
