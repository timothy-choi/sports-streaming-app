const {AWS, s3} = require('./index.js');
const fs = require('fs');
const { getSignedUrl } = require('@aws-sdk/cloudfront-signer');

function uploadVideo(videoname, videodir, bucketName, title, category) {
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

    param.Bucket = 'AllVideos';
    params.Body = {
        Videoname: videoname,
        Title: title,
        VideoBucket: bucketName,
        Category: category
    };

    s3.putObject(params, (err, data) => {
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

function deleteVideo(videoname, bucketName) {
    var params = {
        Bucket: bucketName,
        Key: videoname
    };
    
    s3.deleteObject(params, (err, data) => {
        if (err) {
            return 0;
        }
    });

    params.Bucket = 'AllVideos';
    s3.deleteObject(params, (err, data) => {
        if (err) {
            return 0;
        }
    });

    return 1;
};

module.exports = { uploadVideo, downloadVideo, deleteVideo};