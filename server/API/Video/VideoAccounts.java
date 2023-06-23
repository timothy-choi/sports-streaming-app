package API.Video;

import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import org.springframework.data.mongodb.core.mapping.Document;

import java.org.list;

@Embeddable
public class Video {
    @Id
    private @GeneratedValue Long videoId;
    private String title;
    private String description;
    private String username;
    private String bucket;
    private String key;
    private String category;

    Video(String title, String description, String username, String bucket, String key, String category) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.bucket = bucket;
        this.key = key;
        this.category = category;
    }

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

    public String getBucket() {
        return this.bucket;
    }

    public String getKey() {
        return this.key;
    }

    public String getCategory() {
        return this.category;
    }
}


@Document(collection = "videoAccounts")
public class VideoAccounts {
    @Id
    private @GeneratedValue Long videoAccountId;
    private String username;
    private String email;
    @Embedded
    List<Video> Videos;

    public Long getVideoAccountId() {
        return this.videoAccountId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Video> getVideos() {
        return this.Videos;
    }
}
