package API.Community;

import Community.Communities;
import Community.User;
import Community.VideoPost;
import CommunitiesService;
import CommunitiesRepository;

public class CommunitiesServiceImpl implements CommunitiesService {
    @autowired
    private CommunitiesRepository communitiesRepository;

    @Override
    public List<Communities> findAll() {
        return communitiesRepository.findAll();
    }

    @Override
    public Communities findByCommunityId(Long communityId) {
        return communitiesRepository.findByCommunityId(communityId);
    }

    @Override
    public void deleteCommunities(Long communityId) {
        communitiesRepository.deleteCommunitiesBycommunityId(communityId);
    }

    @Override
    public List<User> findMembers(Long communityId) {
        return communitiesRepository.findMembersByCommunityId(communityId);
    }

    @Override
    public List<VideoPost> findVideoPosts(Long communityId) {
        return communitiesRepository.findVideoPostsByCommunityId(communityId);
    }

    @Override
    public void createCommunities(String communityName, String communityDescription, String communityOwner, Int communityRatingReq, Int communityExpirationTime) {
        Communities newCommunity = new Communities(communityName, communityDescription, communityOwner, communityRatingReq, communityExpirationTime);
        communitiesRepository.save(newCommunity);
    }

    @Override
    public void deleteVideo(Long communityId, String videoPostId) {
        Communities community = communitiesRepository.findByCommunityId(communityId);
        List<VideoPost> videoPosts = community.getVideoPosts();
        for (VideoPost videoPost : videoPosts) {
            if (videoPost.getVideoPostId().equals(videoPostId)) {
                videoPosts.remove(videoPost);
                break;
            }
        }
        communitiesRepository.save(community);
    }

    @Override
    public void addVideo(Long communityId, VideoPost newVideoPost) {
        Communities community = communitiesRepository.findByCommunityId(communityId);
        community.getVideoPosts().add(newVideoPost);
        communitiesRepository.save(community);
    }

    @Override
    public void addUser(Long communityId, User newUser) {
        Communities community = communitiesRepository.findByCommunityId(communityId);
        community.getMembers().add(newUser);
        communitiesRepository.save(community);
    }

    @Override
    public void deleteUser(Long communityId, Long userId) {
        Communities community = communitiesRepository.findByCommunityId(communityId);
        List<User> members = community.getMembers();
        for (User user : members) {
            if (user.getUserId().equals(userId)) {
                members.remove(user);
                break;
            }
        }
        communitiesRepository.save(community);
    }

    @Override
    public Communities findCommunitiesByName(String communityName) {
        return communitiesRepository.findCommunitiesByName(communityName);
    }
}
