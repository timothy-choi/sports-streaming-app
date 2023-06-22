package API.Video;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoAccountsRepository extends MongoRepository<VideoAccounts, String> {
    List<VideoAccounts> findAll();

    VideoAccounts findByVideoAccountId(Long videoAccountId);

    void deleteVideoAccounts(Long videoAccountId);

    List<Video> findVideosByAccountId(Long videoAccountId);
}
