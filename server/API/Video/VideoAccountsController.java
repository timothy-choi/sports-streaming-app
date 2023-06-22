package API.Video;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/videoAccounts")
public class VideoAccountsController {
    @Autowired
    private VideoAccountsService videoAccountsService;

    @GetMapping(value="/videoAccounts")
    public ResponseEntity getAllVideoAccounts() {
        List<VideoAccounts> videoAccts = videoAccountsService.findAll();
        if (videoAccts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No video accounts found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoAccts);
    }

    @GetMapping(value="/videoAccounts/{videoAccountId}")
    public ResponseEntity getUserVideoAccount(@PathVariable Long videoAccountId) {
        VideoAccounts videoAcct = videoAccountsService.findByVideoAccountId(videoAccountId);
        if (videoAcct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoAcct);
    }

    @DeleteMapping(value="/delete/videoAccounts/{videoAccountId}")
    public ResponseEntity deleteVideoAccountId(@PathVariable Long videoAccountId) {
        try {
            videoAccountsService.deleteVideoAccount(videoAccountId);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }
        return ResponseEntity("Video account deleted successfully", HttpStatus.OK);
    }

    @GetMapping(value="/videoAccounts/videos/{username}")
    public

    @GetMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public 
    
    @PostMapping(value="/videoAccounts/videos/{username}")
    public

    @DeleteMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public

}
