import { List } from "@mui/material";
import { useEffect, useState} from "react";

interface Community {
    communityId: number,
    name: string,
    url: string
};

interface Video {
    videoName: string,
    videoTitle: string,
    videoCategory: string,
    videoUrl: string
};

export default function Profile() {
    const [isLoading, setLoading] = useState(true);
    const [profileData, setProfileData] = useState({
        name: "",
        email: "",
        username: "",
        communities: [] as Community[],
        videosPosted: [] as Video[]
    });

    useEffect(() => {

        let email = "";
        var fullname = "";
        var username = "";
        var joinedCommunities : Community[] = [];
        var videos : Video[] = [];
        const fetchAccount = async () => {
            username = JSON.parse(localStorage.getItem("userInfo") || '{}');
            var account = await fetch(`/accounts/${username}`)
            .then((response) => response.json());
            account = await account.json();
            email = account.email;
            fullname = account.name;
        }

        const findJoinedCommunities = async () => {
            var allCommunities: Community[] = await fetch("/communities")
            .then((response) => response.json());
            for (var i = 0; i < allCommunities.length; ++i) {
                var members = await fetch(`/communities/members/${allCommunities[i].communityId}`)
                .then((response) => response.json());
                for (var j = 0; j < members.length; ++j) {
                    if (members[j].username == username) {
                        joinedCommunities.push(allCommunities[i]);
                        break;
                    } 
                }
            }
        }

        const findPostedVideos = async () => {
            let currVideos : Video[] = await fetch(`/videoAccounts/videos/${username}`)
            .then((response) => response.json());
            videos = currVideos;
        }

        fetchAccount();
        findJoinedCommunities();
        findPostedVideos();

        setProfileData((profile) => ({
            ...profile,
            name: fullname,
            username: username,
            email: email,
            communities: joinedCommunities,
            videosPosted: videos
        }));
        setLoading(false);
    }, []);

    if (isLoading) {
        return (
            <div className="loading"></div>
        );
    }
    else {
        return (
            <div className="profile">
                <p>profileData.name</p>
                <p>profileData.username</p>
                <p>profileData.email</p>
                <div className="joinedCommunities">
                    <p>The communities you have joined:</p>
                    <ul>
                        {profileData.communities.map((community, index) => (
                            <li key={index}>
                                {community.name}
                                <a href={community.url}>
                                    Click to see.
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="videos">
                    <p>The videos you have created:</p>
                    <ul>
                        {profileData.videosPosted.map((video, index) => (
                            <li key={index}>
                                {video.videoTitle}
                                <a href={video.videoUrl}>
                                    Click to see.
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        );
    }
};
