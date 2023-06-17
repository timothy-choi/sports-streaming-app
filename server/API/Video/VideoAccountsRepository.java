package API.Video;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoAccountsRepository extends MongoRepository<VideoAccounts, String> {
    List<VideoAccounts> findAll();

    VideoAccounts findByVideoAccountId(String videoAccountId);

    void deleteVideoAccounts(Long videoAccountId);

    List<Video> getVideos(String username);
}
