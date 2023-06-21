const {AWS, s3} = require('./index.js');

function hasBucket(bucketName) {
    return s3.bucketExists(bucketName);
}

function createBucket(bucketName) {
    let params = {
        Bucket: bucketName
    }

    s3.createBucket(params, (err, data) => {
        if (err) {
            return 0;
        }
    });

    return 1;
}

function deleteBucket(bucketName) {
    let params = {
        Bucket: bucketName
    }

    s3.deleteBucket(params, (err, data) => {
        if (err) {
            return 0;
        }
    });

    return 1;
};

module.exports = { hasBucket, createBucket, deleteBucket};