package API.Community;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/communities")
public class CommunitiesController {

    @Autowired
    private CommunitiesService communitiesService;

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
        if (videos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No videos found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videos);
    }

    @PostMapping(value="/communities")
    public ResponseEntity createCommunity(@RequestBody String name, @RequestBody String description, @RequestBody String owner, @RequestBody Int ratingRequirement, @RequestBody Int daysUntilExpiration) {
        try {
            Communities comm = communitiesService.findCommunitiesByName(name);
            if (comm != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Community already exists");
            }
            communitiesService.createCommunity(name, description, owner, ratingRequirement, daysUntilExpiration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Community not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Community created");
    } 

    @PostMapping(value="/communities/members/{communityId}/{accountId}")
    public ResponseEntity addCommunityMember(@PathVariable Long communityId, @PathVariable Long accountId) {

    }

    @PostMapping(value="/communities/videos/{communityId}/{videoId}")
    public ResponseEntity addCommunityVideo(@PathVariable Long communityId, @PathVariable Long videoId, @RequestBody String name, @RequestBody String description, @RequestBody String bucket, @RequestBody String key) {


    }

    @DeleteMapping(value="/communities/members/{communityId}/{userId}")
    public ResponseEntity deleteCommunityMember(@PathVariable Long communityId, @PathVariable Long userId) {

    }

    @DeleteMapping(value="/communities/videos/{communityId}/{videoPostId}")
    public ResponseEntity deleteCommunityVideo(@PathVariable Long communityId, @PathVariable Long videoPostId) {

    }

    @DeleteMapping(value="/communities/{communityId}")
    public ResponseEntity deleteCommunity(@PathVariable Long communityId) {

    }
}
