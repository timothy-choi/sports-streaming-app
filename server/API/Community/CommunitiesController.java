package API.Community;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/communities")
public class CommunitiesController {

    @Autowired
    private CommunitiesService communitiesService;
    RestTemplate restTemplate;

    @GetMapping(value="/communities")
    public ResponseEntity getAllCommunities() {
        List<Communities> comms = communitiesService.findAll();
        if (comms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No communities found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comms);
    }

    @GetMapping(value="/communities/{communityId}") 
    public ResponseEntity getCommunityById(@PathVariable Long communityId) {
        Communities comm = communitiesService.findByCommunityId(communityId);
        if (comm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comm);

    }

    @GetMapping(value="/communities/members/{communityId}")
    public ResponseEntity getCommunityMembers(@PathVariable Long communityId) {
        Communities comm = communitiesService.findByCommunityId(communityId);
        if (comm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }
        List<User> members = communitiesService.findMembersByCommunityId(communityId);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No members found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @GetMapping(value="/communities/videos/{communityId}")
    public ResponseEntity getCommunityVideos(@PathVariable Long communityId) {
        Communities comm = communitiesService.findByCommunityId(communityId);
        if (comm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }
        List<VideoPost> videos = communitiesService.findVideoPostsByCommunityId(communityId);
        List<Map> videoMaps = new ArrayList<Map>();
        for (VideoPost currVideo : videos) {
            String vidUrl = restTemplate.getForObject("http://localhost:8080/getVideo/" + currVideo.getVideoId() + "/" + currVideo.getBucket(), String.class);
            Map<String, String> vidMap = new HashMap();
            vidMap.put("videoId", currVideo.getVideoPostId());
            vidMap.put("name", currVideo.getName());
            vidMap.put("description", currVideo.getDescription());
            vidMap.put("bucket", currVideo.getBucket());
            vidMap.put("key", currVideo.getKey());
            vidMap.put("rating", currVideo.getRating());
            vidMap.put("videoUrl", vidUrl);
            videoMaps.add(vidMap);
        }
        if (videos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No videos found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoMaps);
    }

    @PostMapping(value="/communities")
    public ResponseEntity createCommunity(@RequestBody String name, @RequestBody String description, @RequestBody String owner, @RequestBody Int ratingRequirement, @RequestBody Int daysUntilExpiration) {
        try {
            Communities comm = communitiesService.findCommunitiesByName(name);
            if (comm != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Community already exists");
            }
            int result = communitiesService.createCommunity(name, description, owner, ratingRequirement, daysUntilExpiration);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding community");
            }
            JSONObject reqBody = new JSONObject();
            request.put("indexname", name);

            HttpEntity<String> request = new HttpEntity<String>(reqBody.toString());
            ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/index", HttpMethod.POST, request, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding community");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Community created");
    } 

    @PostMapping(value="/communities/members/{communityId}/{accountId}")
    public ResponseEntity addCommunityMember(@PathVariable Long communityId, @PathVariable Long accountId, @RequestBody String name, @RequestBody String username) {
        try {
            Communities comm = communitiesService.findByCommunityId(communityId);
            if (comm == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
            }
            communitiesService.addUser(communityId, new User(name, username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Member added");
    }

    @PostMapping(value="/communities/videos/{communityId}/{videoId}")
    public ResponseEntity addCommunityVideo(@PathVariable Long communityId, @PathVariable Long videoId, @RequestBody String name, @RequestBody String description, @RequestBody String bucket, @RequestBody String key) {
        try {
            Communities comm = communitiesService.findByCommunityId(communityId);
            if (comm == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
            }
            communitiesService.addVideoPost(communityId, new VideoPost(name, description, bucket, key));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Video added");
    }

    @DeleteMapping(value="/communities/members/{communityId}/{userId}")
    public ResponseEntity deleteCommunityMember(@PathVariable Long communityId, @PathVariable Long userId) {
        try {
            Communities comm = communitiesService.findByCommunityId(communityId);
            if (comm == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
            }
            communitiesService.deleteUser(communityId, userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Member deleted");
    }

    @DeleteMapping(value="/communities/videos/{communityId}/{videoPostId}")
    public ResponseEntity deleteCommunityVideo(@PathVariable Long communityId, @PathVariable Long videoPostId) {
        try {
            Communities comm = communitiesService.findByCommunityId(communityId);
            if (comm == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
            }
            communitiesService.deleteVideo(communityId, videoPostId);
            JSONObject reqBody = new JSONObject();
            reqBody.put("indexname", comm.getName());
            reqBody.put("videoId", videoPostId);

            HttpEntity<String> request = new HttpEntity<String>(reqBody.toString());
            ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/video", HttpMethod.DELETE, request, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting community");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Video deleted");
    }

    @DeleteMapping(value="/communities/{communityId}")
    public ResponseEntity deleteCommunity(@PathVariable Long communityId) {
        try {
            Communities comm = communitiesService.findByCommunityId(communityId);
            if (comm == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
            }
            communitiesService.deleteCommunities(communityId);

            JSONObject reqBody = new JSONObject();
            reqBody.put("indexname", name);

            HttpEntity<String> request = new HttpEntity<String>(reqBody.toString());
            ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/index", HttpMethod.DELETE, request, String.class);
            if (res.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting community");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Community deleted");
    }
}
