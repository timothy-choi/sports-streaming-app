const {AWS, s3} = require('./index.js');
const fs = require('fs');
const { getSignedUrl } = require('@aws-sdk/cloudfront-signer');

function uploadVideo(videoname, videodir, bucketName) {
    var params = {
        Bucket: bucketName,
        Key: videoname,
        Body: fs.createReadStream(videodir)
    };

    s3.upload(params, (err, data) => {
        if (err) {
            return 0;
        }
    });

    return 1;
}

function convertDaysToSeconds(days) {
    return days * 24 * 60 * 60;
}

function downloadVideo(videoname, bucketName) {
    var params = {
        Bucket: bucketName,
        Key: videoname,
        Expires: convertDaysToSeconds(6)
    };

    s3.getSignedUrl('getObject', params, (err, data) => {
        if (err) {
            return null;
        }
        return data;
    });

    return null;
}

module.exports = { uploadVideo, downloadVideo};