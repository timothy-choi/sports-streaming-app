import { useState } from "react";
import { uploadVideo } from "../api/index";

export default function uploadVideoPage({uploadParams}) {
    const [video, setVideo] = useState(null);
    const [createVideoUrl, setCreateVideoUrl] = useState<string | null>(null);

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');

    var url = ""; 

    const clientUpload = (event) => {
        if (event.target.files[0]) {
            setVideo(event.target.files[0]);
            const i = event.target.files[0];
            url = URL.createObjectURL(i);
            setCreateVideoUrl(url);
        }
    };

    const changeTitle = (event) => {
        setTitle(event.target.values);
    }

    const changeDescription = (event) => {
        setDescription(event.target.values);
    }

    const serverUpload = async (event, videoAccount, category) => {
        event.preventDefault();
        const body = new FormData();

        body.append("title", title);
        body.append("description", description);

        var username = JSON.parse(localStorage.getItem("userInfo") || '{}').username;
        var videoDir = url;
        var key = url;
        await uploadVideo({videoAccount, title, description, category, key, username, videoDir})
        .then((response) => {return response.ok()})
        .catch((err) => { throw Error(err); });
    }

    return (
        <div>
            <h1>Video Upload Form</h1>
            <form onSubmit={(event) => serverUpload(event, uploadParams.videoAccountId, uploadParams.community)}>
                <label htmlFor="videoTitle">Video Title:</label>
                <input
                    type="text"
                    id="videoTitle"
                    name="videoTitle"
                    value={title}
                    onChange={changeTitle}
                />
                <br/>
                <label htmlFor="videoDescription">Video Description:</label>
                <input
                    type="text"
                    id="videoDescription"
                    name="videoDescription"
                    value={description}
                    onChange={changeDescription}
                />
                <br />
                <label htmlFor="videoFile">Choose a video file:</label>
                <input
                    type="file"
                    id="videoFile"
                    name="videoFile"
                    accept="video/*"
                    onChange={clientUpload}
                />
            </form>
        </div>
    );

};