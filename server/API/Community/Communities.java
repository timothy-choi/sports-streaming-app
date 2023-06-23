package API.Community;

import java.util.List;

import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import Java.util.UUID;

public class User {

}

public class VideoPost {
    
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

    List<User> users;
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