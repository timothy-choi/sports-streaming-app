import axios from "axios";

export function Login(payload: {
    username: string,
    password: string
}) {
    var res;
    axios.post('/accounts/login', payload)
    .then((response) => {
        res = response.data
    })
    .catch(function (err) {
        return err;
    });

    return res;
}

export function Register(payload: {
    name: string,
    age: Number,
    email: string,
    username: string,
    password: string
}) {
    var res;
    axios.post('/accounts/signup', payload)
    .then((response) => {
        res = response.data;
    })
    .catch(function (err) {
        return err;
    });

    var payload2 = {
        username: String, password: String
    };

    axios.post('/videoAccounts/register', payload2)
    .then((response) => {
        res = response.data;
    })
    .catch(function (err) {
        return err;
    });

    return res;
}

export function Logout() {
    const res = axios.post('/accounts/logout')
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });

    return res;
}

export function uploadVideo(payload: {
    videoAccount: Number,
    title: String,
    description: String,
    category: String,
    key: String,
    username: String,
    videoDir: String
}) {
    const res = axios.post(`/videoAccounts/videos/${payload.videoAccount}`, payload)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });

    return res;
}

export function searchAPI(payload: {
    searchQuery: String
}) {
    const results = axios.get(`/search/${payload.searchQuery}`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return results;
}

export function createCommunity(payload: {
    name: String,
    description: String,
    owner: String,
    ratingRequirement: Number,
    daysUntilExpriation: Number
}) {
    const res = axios.post('/communities', payload)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return res;
}

export function addCommunityMembers(payload: {
    communityId: Number,
    accountId: Number,
    name: String,
    username: String
}) {
    const res = axios.post(`/communities/members/${payload.communityId}/${payload.accountId}`, payload)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return res;
}

export function deleteCommunityMembers(payload: {
    communityId: Number,
    userId: Number
}) {
    const res = axios.delete(`/communities/members/${payload.communityId}/${payload.userId}/`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return res;
}

export function removeCommunityVideo(payload: {
    communityId: Number,
    videoPostId: Number
}) {
    const res = axios.delete(`/communities/videos/${payload.communityId}/${payload.videoPostId}/`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return res;
}

export function getVideo(payload: {
    username: String,
    videoId: Number
}) {
    const video = axios.get(`/videoAccounts/videos/${payload.username}/${payload.videoId}/`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return video;
}

export function getUserVideos(payload: {
    username: String
}) {
    const videos = axios.get(`/videoAccounts/videos/${payload.username}`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return videos;
}

export function getUserAccount(payload: {
    videoAccountId: Number
}) {
    const account = axios.get(`/videoAccounts/${payload.videoAccountId}/`)
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });

    return account;
}

export function getUserAccounts() {
    const userAccounts = axios.get('/videoAccounts')
    .then((response) => response.data)
    .catch(function (err) {
        return err;
    });
    
    return userAccounts;
}