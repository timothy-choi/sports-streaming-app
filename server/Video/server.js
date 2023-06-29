const express = require('express');
const app = express();
const {hasBucket, createBucket, deleteBucket, getAllObjects} = require('./createBucket.js');
const {uploadVideo, downloadVideo, deleteVideo } = require('./uploadVideo.js');

const {invokeLambda} = require('./lambda.js');
const {createNewTopic, configToTriggerLambda, deleteTopic, getTopicARN} = require('./SNSimpl.js');
const {createNewQueue, subscribeToQueue, deleteQueue, getQueueUrl} = require('./SQSimpl.js');
const { invokeLambda } = require('./lambda.js');
const redis = require('redis');
const client = redis.createClient();
const flat = require('flat');

const PORT = 3000;
app.listen(PORT, () => {});

app.post('/sendVideo', sendVideo);

const setUpUpload = function(bucketName) {
    var topicArn = createNewTopic(bucketName);
    var queueArn = createNewQueue(bucketName);
    var queueUrl = queueArn;
    queueArn = queueArn.split('/').slice(0, -1).join('/');
    subscribeToQueue(topicArn, queueArn, queueUrl);
    configToTriggerLambda(topicArn, queueArn, bucketName);
    invokeLambda(queueArn);
};

const sendVideo = function (req, res) {
    var bucketname = req.body.bucketName + "_in";
    var firstTime = false;
    if (!hasBucket(bucketname)) {
        firstTime = true;
        createBucket(bucketname);
        bucketName = req.body.bucketname + "_out";
        createBucket(bucketname);
    }

    bucketname = req.body.bucketName + "_in";
    try {
        if (firstTime) {
            setUpUpload(req.body.bucketName);
        }
        var res = uploadVideo(req.body.videoName, req.body.videoDir, bucketname, req.body.title, req.body.category);
    } catch (err) {
        return res.send(500).send({"message": "Error processing video"});
    }

    if (!res) {
        return res.send(500).send({"message": "Error uploading video"});
    }

    return res.send(200).send({"message": "Video uploaded successfully", "bucketName": req.body.bucketname + "_out", "videoName": req.body.videoName, "title": req.body.title});
};

app.post('/getVideo', getVideo);

const getVideo = function (req, res) {
    var url = downloadVideo(req.params.videoName, req.params.bucketname + "_out");
    if (!url) {
        return res.send(500).send({"message": "Error downloading video"});
    }

    return res.send(200).send({"url": url});
};

app.get('/getUsersVideos', getUsersVideos);

const getUsersVideos = function (req, res) {
    var usersVideos = [];
    try {
        client.get(req.params.username, (err, data) => {
            if (err) {
                return res.send(500).send({"message": "Error getting videos"});
            }
            if (data) {
                return res.send(200).send({"videos": unflatten(data)});
            }
            else {
                var allVideos = getAllObjects(req.params.username + "_out");
                for (let i = 0; i < allVideos.length; i++) {
                    axios({
                        method: 'get',
                        url: '/getVideo',
                        params: {
                            "videoName": allVideos[i].Key,
                            "bucketName": req.params.username
                        }
                    })
                    .then((response) => {
                        usersVideos.push(response.data);
                    })
                    .catch((error) => {
                        return res.send(500).send({"message": "Error getting video"});
                    });
                }
                client.setex(req.body.username, 604800, JSON.stringify(flatten(usersVideos)));
            }
        });
    }
    catch (err) {
        return res.send(500).send({"message": "Error getting videos"});
    }
    return res.send(200).send({"videos": JSON.stringify(usersVideos)});
}

const deleteVideoObj = function (req, res) {
    var res = deleteVideo(req.body.videoName, req.body.username + "_out");
    if (!res) {
        return res.send(500).send({"message": "Error deleting video"});
    }

    return res.send(200).send({"message": "Video deleted successfully"});
};

app.delete('/deleteVideo', deleteVideoObj);

const deleteAcct = function (req, res) {
    try {
        var queueUrl = getQueueUrl(req.body.bucketName);
        var topicARN = getTopicARN(req.body.bucketName);
        deleteTopic(topicARN);
        deleteQueue(queueUrl);
        deleteBucket(req.body.bucketName + "_in");
        deleteBucket(req.body.bucketName + "_out");
    } catch (err) {
        return res.send(500).send({"message": "Error deleting account"});
    }

    return res.send(200).send({"message": "Account deleted successfully"});
};

app.delete('/deleteAcct', deleteAcct);