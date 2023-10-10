import { url } from "inspector";
import { useEffect, useState } from "react";

 
export default function VideoPage({videoParams}) {
    const [isLoading, setLoading] = useState(true);
    const [VideoData, setVideoData] = useState({
        title: "",
        url: "",
        description: "",
        community: "",
        username: ""
    });

    useEffect(() => {
        const fetchVideo = async (username, videoId) => { 
            var res;
            await fetch(`/api/${username}/${videoId}`)
            .then((response) => response.json());
            res = await res.json();

            setVideoData((vid) => ({
                ...vid,
                title: res.title,
                url: res.url,
                description: res.description,
                community: res.category,
                username: res.username
            }));
            setLoading(false);
        
            return res;
        }
        fetchVideo(videoParams.username, videoParams.videoId);
    }, []);

    if (isLoading) {
        return (
            <div id="loader"></div>
        );
    }
    else {
        return (
            <div className="videoPost">
                <video>
                    <source src={VideoData.url} type="video/mp4" />
                </video>
                <p> Title: {VideoData.title} </p>
                <p> {VideoData.description} </p>
                <p> Posted by: {VideoData.username} </p>
                <p> Community: {VideoData.community} </p>
            </div>
        );
    }
}