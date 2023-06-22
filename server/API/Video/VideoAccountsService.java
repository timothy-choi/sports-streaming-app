import java.util.List;

import API.Video.Video;
import API.Video.VideoAccounts;

public interface VideoAccountsService {
    List<VideoAccounts> findAll();

    VideoAccounts findByVideoAccountId(Long videoAccountId);

    void deleteVideoAccount(Long videoAccountId);

    void addVideo(Long videoAccountId, Video newVideo);

    Video getVideo(Long videoAccountId, String videoId);

    void deleteVideo(Long videoAccountId, String videoId);

    List<Video> getVideos(Long videoAccountId);
}
