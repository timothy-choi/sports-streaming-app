const AWS = require('aws-sdk');
const s3 = new AWS.S3({});


module.exports = { AWS, s3 };