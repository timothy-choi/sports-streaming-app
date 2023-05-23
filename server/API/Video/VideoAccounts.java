package API.Video;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.org.list;

public class Video {
    private Long videoId;
    private String title;
    private String description;
    private String username;

    public Long getVideoId() {
        return this.videoId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUsername() {
        return this.username;
    }
}

public class Playlists {
    private Long playlistId;
    private String title;
    private String description;
    private String username;
    List<Video> videos;

    public Long getPlaylistId() {
        return this.playlistId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public List<Video> getVideos() {
        return this.videos;
    }
}

@Document(collection = "videoAccounts")
public class VideoAccounts {
    @Id
    private @GeneratedValue Long videoAccountId;
    private String username;
    private String email;
    List<Playlists> playlists;

    public Long getVideoAccountId() {
        return this.videoAccountId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Playlists> getPlaylists() {
        return this.playlists;
    }

    public void changeUsername(String username) {
        this.username = username;
    }
}
