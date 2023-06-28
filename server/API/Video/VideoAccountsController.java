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
            String urls = RestTemplate.getForObject("http://localhost:8080/getUsersVideos/" + username, String.class);
            Gson gson = new Gson();
            String[] videos = gson.fromJson(urls, String[].class);
            if (videoAcct == null || videos.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
            }
            List<Map> allVideos = new ArrayList<Map>();
            for (let i = 0; i < videoAcct.getVideos().length; ++i) {
                Map videoMap = new HashMap();
                videoMap.put("videoId", videoAcct[i].getVideos().getVideoId());
                videoMap.put("title", videoAcct[i].getVideos().getTitle());
                videoMap.put("description", videoAcct[i].getVideos().getDescription());
                videoMap.put("category", videoAcct[i].getVideos().getCategory());
                for (let j = 0; j < videos.length; j++) {
                    URL url = new URL(videos[j]);
                    if (url.getPath().substr(1) == videoAcct[i].getVideos().getKey()) {
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
    public ResponseEntity getSingleVideo(@PathVariable String username, @PathVariable Long videoId) {
        try {
            Video video = getVideo(username, videoId);
            String url = RestTemplate.getForObject("http://localhost:8080/getVideo/" + video.getUsername() + "/" + video.getKey(), String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }
        Map videoMap = new HashMap();
        videoMap.put("videoId", video.getVideoId());
        videoMap.put("title", video.getTitle());
        videoMap.put("description", video.getDescription());
        videoMap.put("category", video.getCategory());
        videoMap.put("url", url);
        return ResponseEntity.status(HttpStatus.OK).body(videoMap);
    }
    
    @PostMapping(value="/videoAccounts/videos/{videoAccountId}")
    public ResponseEntity addVideo(@PathVariable Long videoAccountId, @RequestBody String title, @RequestBody String description, @RequestBody String category, @RequestBody String key, @RequestBody String username, @RequestBody String videoDir) {
        try {
            Video video = VideoAccountsService.findByAccountId(videoAccountId);
            if (video == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
            }
            
            Video newVideo = new Video(title, description, username, username + "_out", key, category);
            addVideo(videoAccountId, newVideo);

            HttpHeader headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> req = new HashMap<String, String>();
            map.put("videoDir", videoDir);
            map.put("bucketName", username);
            map.put("VideoName", key);
            map.put("title", title);
            map.put("category", category);

            HttpEntity<String> reqEntity = new HttpEntity<String>(req, headers);

            ResponseEntity<String> res = RestTemplate.postForEntity("http://localhost:8080/sendVideo", reqEntity, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Video added successfully");
    }

    @DeleteMapping(value="/videoAccounts/videos/{username}/{videoId}")
    public ResponsiveEntity deleteVideo() {
        try {
            Video video = getVideo(username, videoId);
            HttpHeader headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> req = new HashMap<String, String>();
            req.put("videoName", video.getKey());
            req.put("username", username);
            HttpEntity<String> reqEntity = new HttpEntity<String>(req, headers);
            ResponseEntity<String> res = RestTemplate.exchange("http://localhost:8080/deleteVideo", HttpMethod.DELETE, reqEntity, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
            }
            videoAccountsService.deleteVideo(videoAccountId, videoId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video account not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Video deleted successfully");
    }

    @PostMapping(value="/videoAccounts/register")
    public void register(@RequestBody String username, @RequestBody String email) {
        try {
            videoAccountsService.signup(username, email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error in creating Video account");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Video account created successfully");
    }
}
