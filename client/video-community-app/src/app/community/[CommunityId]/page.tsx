import { addCommunityMembers } from "@components/app/api";
import { useEffect, useState } from "react";

const MembersListButton = ({membersList}) => {
    const redirectToVideo = async (videoId) => {
        location.href = `/videos/${videoId}`;
    }
    const [showList, setShowList] = useState(false);

    const activateList = () => {
        setShowList(!showList);
    }

    return (
        <div>
            <button onClick={activateList}>See Members</button>
            {showList && (
                <ul>
                    {membersList.map((object, index) => (
                        <li key={index}>
                            {object.name}
                            {object.username}
                            {object.videos.map((video, index) => (
                                <li key={index}>
                                    <button onClick={() => redirectToVideo(video.videoId)}>
                                        {video.name}
                                    </button>
                                </li>
                            ))}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

interface User {
    userId: number,
    name: String,
    username: String,
    allVideos: []
};

interface Videos {
    videoId: number,
    name: String,
    description: String,
    rating: number,
    url: String
};

export default function CommunityPage({communityParams}) {
    var user = JSON.parse(localStorage.getItem("userInfo") || '{}');
    const addNewMember = async (accountId, name, username, communityId) => {
        await addCommunityMembers({communityId, accountId, name, username})
        .then((response) => {return response.ok()})
        .catch((err) => {
            throw Error(err);
        });
    }

    const redirectToVideo = async (videoId) => {
        location.href = `/videos/${videoId}`;
    }
    const [isLoading, setLoading] = useState(true);
    const [communityData, setCommunityData] = useState({
        name: "",
        description: "",
        owner: "",
        ratingRequirement: 0,
        daysUntilExpiration: 0,
        members: [] as User[],
        videos: [] as Videos[]
    });

    useEffect(() => {
        var commName = "";
        var desc = "";
        var commOwner = "";
        var ratingReq = 0;
        var numDays = 0;
        var allMembers : User[] = [];
        var allVideos : Videos[] = [];
        const fetchCommunity = async (communityId) => {
            var res;
            await fetch(`/community/${communityId}`)
            .then((response) => res = response.json());
            commName = res.name;
            desc = res.description;
            commOwner = res.owner;
            ratingReq = res.ratingRequirement;
            numDays = res.daysUntilExpiration;
        }

        const getAllMembers = async (communityId) => {
            var commMembers: User[] = await fetch(`/community/members/${communityId}`)
            .then((response) => response.json());
            allMembers = commMembers;
        }

        const getAllVideos = async (communityId) => {
            var commVideos: Videos[] = await fetch(`/community/videos/${communityId}`)
            .then((response) => response.json());
            allVideos = commVideos;
        }

        fetchCommunity(communityParams.communityId);
        getAllMembers(communityParams.communityId);
        getAllVideos(communityParams.communityId);

        allVideos = allVideos.sort((a, b) => (a.rating < b.rating) ? 1 : (a.rating > b.rating) ? -1 : 0);

        setCommunityData((community) => ({
            ...community,
            name: commName,
            description: desc,
            owner: commOwner,
            ratingRequirement: ratingReq,
            daysUntilExpiration: numDays,
            members: allMembers,
            videos: allVideos 
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
            <div className="community">
                <p>{communityData.name}</p>
                <p>Created by {communityData.owner}</p>
                <p>{communityData.members.length} members</p>
                <p>{communityData.description}</p>

                <div className = "addMember">
                    <input type="button" value="Join Community" onClick={() => addNewMember(communityParams.communityId, communityParams.accountId, communityParams.name, user.username)}></input>
                </div>

                <div className = "seeMembers">
                    <MembersListButton membersList={communityData.members} />
                </div>

                <div className = "videos">
                    <ul>
                        {communityData.videos.map((video, index) => (
                            <button onClick={() => redirectToVideo(video.videoId)}>
                                <li key={index}>
                                    {video.videoId}
                                    {video.name}
                                    {video.description}
                                    {video.rating}
                                </li>
                            </button>
                        ))}
                    </ul>
                </div>

                <div className = "allExpiringVideos">
                    <ul>
                        {communityData.videos.filter((video) => video.rating < communityData.ratingRequirement)
                        .map((video, index) => (
                            <button onClick={() => redirectToVideo(video.videoId)}>
                                <li key={index}>
                                    {video.videoId}
                                    {video.name}
                                    {video.description}
                                    {video.rating}
                                </li>
                            </button>
                        ))}
                    </ul>
                </div>
            </div>
        );
    }
};