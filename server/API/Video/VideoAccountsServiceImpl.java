import java.util.List;

import API.Video.Video;
import API.Video.VideoAccounts;

public class VideoAccountsServiceImpl implements VideoAccountsService {
    @autowired
    private VideoAccountsRepository videoAccountsRepository;

    @Override
    public List<VideoAccounts> findAll() {
        return videoAccountsRepository.findAll();
    }

    @Override
    public VideoAccounts findByVideoAccountId(String videoAccountId) {
        return videoAccountsRepository.findByVideoAccountId(videoAccountId);
    }

    @Override
    public void deleteVideoAccount(Long videoAccountId) {

    }

    @Override
    public void addVideo(Video newVideo) {

    }

    @Override    
    public Video getVideo(String username, String videoId) {

    }

    @Override
    public void deleteVideo(String username, String videoId) {

    }

    @Override
    public List<Video> getVideos(String username) {

    }
}
