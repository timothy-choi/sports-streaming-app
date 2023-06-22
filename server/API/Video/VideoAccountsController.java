package API.Video;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;

import java.util.List;

@RestController
@RequestMapping("/videoAccounts")
public class VideoAccountsController {
    @Autowired
    private VideoAccountsService videoAccountsService;
    RestTemplate restTemplate;

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
            restTemplate.delete("http://localhost:8080/deleteAcct", videoAccountId);
            VideoAccounts videoAcct = videoAccountsService.findByVideoAccountId(videoAccountId);
            List<Video> videos = videoAcct.getVideos();
            for (let i = 0; i < videos.length; i++) {
                videoAccountsService.deleteVideo(videoAccountId, videos[i].getVideoId());
            }
            videoAccountsService.deleteVideoAccount(videoAccountId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }
        return ResponseEntity("Video account deleted successfully", HttpStatus.OK);
    }

    @GetMapping(value="/videoAccounts/videos/{username}")
    public ResponseEntity getUserVideos(@PathVariable String username) {
        try {
            VideoAccounts videoAcct = videoAccountsService.findByUsername(username);
            String urls = RestTemplate.getForObject("http://localhost:8080/getUsersVideos", String.class, username);
            Gson gson = new Gson();
            String[] videos = gson.fromJson(urls, String[].class);
            if (videoAcct == null || videos.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
            }
            List<Map> allVideos = new ArrayList<Map>();
            for (let i = 0; i < videoAcct.getVideos().length; ++i) {
                Map videoMap = new HashMap();
                videoMap.put("videoId", videoAcct[i].getVideoId());
                videoMap.put("title", videoAcct[i].getTitle());
                videoMap.put("description", videoAcct[i].getDescription());
                videoMap.put("category", videoAcct[i].getCategory());
                for (let j = 0; j < videos.length; j++) {
                    URL url = new URL(videos[j]);
                    if (url.getPath().substr(1) == videoAcct[i].getKey()) {
                        videoMap.put("url", videos[j]);
                        break;
                    }
                }
                allVideos.add(videoMap);
            }
            Map videoAcctMap = new HashMap();
            videoAcctMap.put("username", videoAcct.getUsername());
            videoAcctMap.put("videos", allVideos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoAcctMap);
    }

    @GetMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public 
    
    @PostMapping(value="/videoAccounts/videos/{username}")
    public

    @DeleteMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public

}
