import Image from 'next/image'
import { useState } from 'react'; 
import { useRouter } from 'next/router';
import { Logout, getAllCommunities, getCommunityVideos } from './api';
import { redirect } from 'next/dist/server/api-utils';
import CommunityPage from './community/[CommunityId]/page';

export default function Home() {
  const [isLoading, setLoading] = useState(true);
  const [communities, setCommunities] = useState<any[]>([]);
  const [videos, setVideos] = useState<any[]>([]);
  const router = useRouter();

  const redirectToLogin = () => {
    router.push("/Login");
  };

  const redirectToRegister = () => {
    router.push("/Register");
  };

  const redirectToSearch = () => {
    router.push("/search");
  };

  const redirectToUploadVideo = (vidAcctId, communityId) => {
    router.push("/uploadVideo/[videoAccountId]/[communityId]", `/${vidAcctId}/${communityId}`);
  };

  const Logout = async () => {
    await Logout()
    .catch((err) => {throw Error(err)});
  };

  const getAllActiveCommunities = async (username) => {
    var allJoined;
    var allCommunities;
    await getAllCommunities()
    .then((response) => {allCommunities = response})
    .catch((err) => {throw Error(err)});
    
    for (let i = 0; i < allCommunities.length; ++i) {
      var members = allCommunities.getUsers();
      if (members.find((member) => member.username == username)) {
        allJoined.push(allCommunities[i].getCommunityId);
      }
    }

    return allJoined;
  };

  const getTopNVideos = async (n, communityId) => {
    var communityVideos;
    await getCommunityVideos({communityId})
    .then((res) => {communityVideos = res})
    .catch((err) => {throw Error(err)});

    communityVideos.sort((a, b) => (a.rating < b.rating) ? 1 : (a.rating > b.rating) ? -1 : 0);

    return communityVideos.slice(0, n);
  };

  if (localStorage.getItem("userInfo") !== null) {
    var username = JSON.parse(localStorage.getItem("userInfo")!).username;
    var comm; 
    const getCommunities = async (username) => {
      return await getAllActiveCommunities(username)
      .catch((err) => {return []});
    }
    comm = getCommunities(username)
    setCommunities(comm);

    var vids;
    for (let i = 0; i < comm.length; ++i) {
      const getVideos = async (n, id) => {
        return await getTopNVideos(n, id);
      }
      var subVids = getVideos(5, comm[i]);
      vids.push(subVids);
    }

    setVideos(vids);
  }

  setLoading(false);

  var id = JSON.parse(localStorage.getItem("userInfo")!).videoAccountId;

  if (isLoading) {
    return (
      <div className="loading"></div>
    );
  }
  else {
    return (
      <div>
        {localStorage.getItem("userInfo") === null ? (
          <>
          <div className="login">
            <button onClick={redirectToLogin}>Login</button>
          </div><div className="register">
              <button onClick={redirectToRegister}>Register</button>
            </div><div className="search">
              <button onClick={redirectToSearch}>Search</button>
            </div><h1>Log In or Sign Up to view account</h1></>
        ) : (
          <>
          <div className="logout">
              <button onClick={Logout}>Logout</button>
          </div>
          {communities === null ? (
            <h1>You are not in any community.</h1>
          ) : (
            <ul>
              {communities.map((community, index) => (
                <li key={index}>
                  {community.name}
                  <button onClick={() => redirectToUploadVideo(id, community.communityId)}>Upload Video</button>
                </li>
              ))}
            </ul>
          )} 
          {videos === null ? (
            <h1>You are not in any community.</h1>
          ) : (
            <ul>
              {videos.map((video, index) => (
                <li key={index}>
                  <button onClick={() => {location.href = video.url}}>
                    {video.category}
                    {video.name}
                    {video.description}
                    {video.owner}
                    {video.rating}
                  </button>
                </li>
              ))}
            </ul>
          )} 
          </>
        )}
    </div>
    );
  }
};
