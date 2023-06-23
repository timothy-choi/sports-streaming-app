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
    public VideoAccounts findByVideoAccountId(Long videoAccountId) {
        return videoAccountsRepository.findByVideoAccountId(videoAccountId);
    }

    @Override
    public void deleteVideoAccount(Long videoAccountId) {
        videoAccountsRepository.deleteVideoAccounts(videoAccountId);
    }

    @Override
    public void addVideo(Long videoAccountId, Video newVideo) {
       VideoAccount user = videoAccountsRepository.findByVideoAccountId(videoAccountId);
       user.getVideos().add(newVideo);
       videoAccountsRepository.save(user);
    }

    @Override    
    public Video getVideo(Long videoAccountId, String videoId) {
        VideoAccount user = videoAccountsRepository.findByUsername(videoAccountId);
        List<Video> videos = user.getVideos();
        for (Video video : videos) {
            if (video.getVideoId().equals(videoId)) {
                return video;
            }
        }
        return null;
    }

    @Override
    public void deleteVideo(Long videoAccountId, String videoId) {
        VideoAccount user = videoAccountsRepository.findByUsername(videoAccountId);
        List<Video> videos = user.getVideos();
        for (Video video : videos) {
            if (video.getVideoId().equals(videoId)) {
                videos.remove(video);
                break;
            }
        }
        videoAccountsRepository.save(user);
    }

    @Override
    public List<Video> getVideos(Long videoAccountId) {
        return videoAccountsRepository.getVideos(videoAccountId);
    }

    @Override
    public void signup(String username, String email) {
        VideoAccount newUser = new VideoAccount(username, email);
        videoAccountsRepository.save(newUser);
    }
}
