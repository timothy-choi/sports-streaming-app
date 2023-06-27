import java.util.List;

import API.Community.Communities;
import API.Community.User;
import API.Community.VideoPost;

public interface CommunitiesService {
    List<Communities> findAll();

    Communities findByCommunityId(Long communityId);

    void deleteCommunities(Long communityId);

    List<User> findMembers(Long communityId);

    List<VideoPost> findVideoPosts(Long communityId);

    void deleteVideo(Long communityId, String videoPostId);

    void addVideo(Long communityId, VideoPost newVideoPost);

    void addUser(Long communityId, User newUser);
    
    void deleteUser(Long communityId, Long userId);

    void createCommunities(String communityName, String communityDescription, String communityOwner, Int communityRatingReq, Int communityExpirationTime);
}
