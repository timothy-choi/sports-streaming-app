package API.Community;

import java.util.List;

import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import Java.util.UUID;

@Embeddable
public class User {
    @Id
    private @GeneratedValue Long userId;
    private String name;
    private String username;

    List<Long> videoPostIds;

    User(String name, String username) {
        this.userId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.name = name;
        this.username = username;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public List<Long> getVideoPostIds() {
        return this.videoPostIds;
    }
}

@Embeddable
public class VideoPost {
    @Id 
    private @GeneratedValue Long videoPostId;
    private String name;
    private String description;
    private String Bucket;
    private String Key;
    private Int Rating;

    VideoPost(String name, String description, String bucket, String key) {
        this.videoPostId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.name = name;
        this.description = description;
        this.Bucket = bucket;
        this.Key = key;
        this.Rating = 1;
    }

    public Long getVideoPostId() {
        return this.videoPostId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBucket() {
        return this.Bucket;
    }

    public String getKey() {
        return this.Key;
    }

    public Int getRating() {
        return this.Rating;
    }

    public void changeRating(Int rating) {
        this.Rating = rating;
    }
}


@Document(collection = "communities")
public class Communities {
    @Id
    private @GeneratedValue Long communityId;
    private String name;
    private String description;
    private String owner;
    private Int ratingRequirement;
    private Int daysUntilExpiration;

    @Embedded
    List<User> users;

    @Embedded
    List<VideoPost> videoPosts;

    Communities(String name, String description, String owner, Int ratingRequirement, Int daysUntilExpiration) {
        this.communityId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.ratingRequirement = ratingRequirement;
        this.DaysUntilExpiration = daysUntilExpiration;
    }

    public Long getCommunityId() {
        return this.communityId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOwner() {
        return this.owner;
    }

    public Int getRatingRequirement() {
        return this.ratingRequirement;
    }

    public Int getDaysUntilExpiration() {
        return this.DaysUntilExpiration;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<VideoPost> getVideoPosts() {
        return this.videoPosts;
    }
}