const {AWS, s3} = require('./index.js');
const fs = require('fs');

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

module.exports = { uploadVideo };