const {AWS, s3} = require('./index.js');
const { deleteVideo } = require('./uploadVideo.js');

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

    s3.listObjects(params, (err, data) => {
        if (err) {
            throw new Error(err);
        }

        let objects = data.Contents;
        if (objects.length > 0) {
            objects.forEach((object) => {
                deleteVideo(object.Key, bucketName);
            });
        }
    });

    s3.deleteBucket(params, (err, data) => {
        if (err) {
            throw new Error(err);
        }
    });

    return 1;
};

function getAllObjects(bucketName) {
    let params = {
        Bucket: bucketName
    }

    var objects = [];

    s3.listObjects(params, (err, data) => {
        if (err) {
            throw new Error(err);
        }
        objects = data.Contents;
    });

    return objects;
}

module.exports = { hasBucket, createBucket, deleteBucket};