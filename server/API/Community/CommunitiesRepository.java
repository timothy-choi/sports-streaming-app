
package API.Community;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.org.List;


public class CommunitiesRepository extends MongoRepository<Communities, String> {
    List<Communities> findAll();

    Communities findByCommunityId(Long communityId);

    void deleteCommunitiesBycommunityId(Long communityId);

    List<User> findMembersByCommunityId(Long communityId);

    List<VideoPost> findVideoPostsByCommunityId(Long communityId);
}
