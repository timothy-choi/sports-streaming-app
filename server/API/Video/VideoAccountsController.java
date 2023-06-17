package API.Video;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/videoAccounts")
public class VideoAccountsController {
    @Autowired
    private VideoAccountsRepository videoAccountsRepository;

    @GetMapping(value="/videoAccounts")
    public

    @GetMapping(value="/videoAccounts/{videoAccountId}")
    public

    @DeleteMapping(value="/delete/videoAccounts/{videoAccountId}")
    public

    @GetMapping(value="/videoAccounts/videos/{username}")
    public

    @GetMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public 
    
    @PostMapping(value="/videoAccounts/videos/{username}")
    public

    @DeleteMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public

}
