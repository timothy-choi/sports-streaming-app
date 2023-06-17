import java.util.List;

import API.Video.Video;
import API.Video.VideoAccounts;

public interface VideoAccountsService {
    List<VideoAccounts> findAll();

    VideoAccounts findByVideoAccountId(String videoAccountId);

    void deleteVideoAccount(Long videoAccountId);

    void addVideo(Video newVideo);

    Video getVideo(String username, String videoId);

    void deleteVideo(String username, String videoId);

    List<Video> getVideos(String username);
}
