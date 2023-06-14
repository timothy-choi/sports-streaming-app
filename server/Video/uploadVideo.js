const AWS = require('aws-sdk');
const fs = require('fs');

let s3 = AWS.S3({});

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