import { useState } from "react";
import { searchAPI } from "../api/index";

export default function SearchPage() {
    const [users, setUsers] = useState<any[]>([]);
    const [communities, setCommunities] = useState<any[]>([]);
    const [videos, setVideos] = useState<any[]>([]);
    const [isLoading, setLoading] = useState(true);
    const [query, setQuery] = useState('');

    const changeQuery = (event) => {
        setQuery(event.target.values);
    }

    const search = async (event) => {
        setLoading(true);
        var searchQuery = query;
        var allResults;
        await searchAPI({searchQuery})
        .then((response) => {allResults = response})
        .catch((err) => {throw Error(err)})

        setUsers(allResults.users);
        setCommunities(allResults.communities);
        setVideos(allResults.videos);
        setLoading(false);
    }

    return (
        <div>
            <div className="searchForm">
                <h1>Search Videos, Users, and Communities!</h1>
                <form onSubmit={search}>
                    <input
                        type="text"
                        value={query}
                        onChange={(e) => changeQuery(e.target.value)}
                        placeholder="Search..."
                    />
                    <button type="submit">Search</button>
                </form>
            </div>
            {!isLoading ? (
                <div className="searchResults">
                    <div className="users">
                        {users === null ? (
                            <h1>No Users Found</h1>
                        ) : (
                            <ul>
                                {users.map((user, index) => (
                                    <button key={index} onClick={() => location.href = user.url}>
                                        <li>
                                            {user.name}
                                            {user.username} 
                                        </li>
                                    </button>
                                ))}
                            </ul>
                        )}
                    </div>
                    <div className="videos">
                        {videos === null ? (
                            <h1>No Videos Found</h1>
                        ) : (
                            <ul>
                                {videos.map((video, index) => (
                                    <button key={index} onClick={() => location.href = video.url}>
                                        <li>
                                            {video.name}
                                            {video.owner}
                                            {video.description}
                                            {video.rating} 
                                        </li>
                                    </button>
                                ))}
                            </ul>
                        )}
                    </div>
                    <div className="communities">
                        {communities === null ? (
                            <h1>No Communities Found</h1>
                        ) : (
                            <ul>
                                {communities.map((community, index) => (
                                    <button key={index} onClick={() => location.href = community.url}>
                                        <li>
                                            {community.name}
                                            {community.owner}
                                            {community.description}
                                            {community.members.length} Members 
                                        </li>
                                    </button>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
            ) : null}
        </div>
    );
};