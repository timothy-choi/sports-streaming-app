const express = require('express');
const app = express();
const {hasBucket, createBucket} = require('./createBucket.js');
const {uploadVideo } = require('./uploadVideo.js');

const PORT = 3000;
app.listen(PORT, () => {});

app.post('/sendVideo', sendVideo);

const sendVideo = function (req, res) {
    var bucketname = req.body.bucketName + "_in";
    if (!hasBucket(bucketname)) {
        createBucket(bucketname);
        bucketName = req.body.bucketname + "_out";
        createBucket(bucketname);
    }

    bucketname = req.body.bucketName + "_in";
    try {
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