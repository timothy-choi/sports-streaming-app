import { addCommunityMembers } from "@components/app/api";
import { useEffect, useState } from "react";

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
    const addNewMember = async (accountId, name, username, communityId) => {
        await addCommunityMembers({communityId, accountId, name, username})
        .then((response) => {return response.ok()})
        .catch((err) => {
            throw Error(err);
        });
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
        return ();
    }
};