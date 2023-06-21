const express = require('express');
const app = express();
const {hasBucket, createBucket} = require('./createBucket.js');
const {uploadVideo, downloadVideo, deleteVideo } = require('./uploadVideo.js');

const {invokeLambda} = require('./lambda.js');
const {createNewTopic, configToTriggerLambda} = require('./SNSimpl.js');
const {createNewQueue, subscribeToQueue } = require('./SQSimpl.js');
const { invokeLambda } = require('./lambda.js');

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
        var res = uploadVideo(req.body.videoName, req.body.videoDir, bucketname);
    } catch (err) {
        return res.send(500).send({"message": "Error processing video"});
    }

    if (!res) {
        return res.send(500).send({"message": "Error uploading video"});
    }

    return res.send(200).send({"message": "Video uploaded successfully", "bucketName": req.body.bucketname + "_out", "videoName": req.body.videoName});
};

app.post('/getVideo', getVideo);

const getVideo = function (req, res) {
    var url = downloadVideo(req.body.videoName, req.body.username + "_out");
    if (!url) {
        return res.send(500).send({"message": "Error downloading video"});
    }

    return res.send(200).send({"message": "Video downloaded successfully", "url": url});
};

const deleteVideoObj = function (req, res) {
    var res = deleteVideo(req.body.videoName, req.body.username + "_out");
    if (!res) {
        return res.send(500).send({"message": "Error deleting video"});
    }

    return res.send(200).send({"message": "Video deleted successfully"});
};

app.delete('/deleteVideo', deleteVideoObj);