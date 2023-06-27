package API.Community;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/communities")
public class CommunitiesController {


    @GetMapping(value="/communities")
    public ResponseEntity getAllCommunities() {

    }

    @GetMapping(value="/communities/{communityId}") 
    public ResponseEntity getCommunityById(@PathVariable Long communityId) {

    }

    @GetMapping(value="/communities/members/{communityId}")
    public ResponseEntity getCommunityMembers(@PathVariable Long communityId) {

    }

    @GetMapping(value="/communities/videos/{communityId}")
    public ResponseEntity getCommunityVideos(@PathVariable Long communityId) {

    }

    @PostMapping(value="/communities")
    public ResponseEntity createCommunity(@RequestBody String name, @RequestBody String description, @RequestBody String owner, @RequestBody Int ratingRequirement, @RequestBody Int daysUntilExpiration) {

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
