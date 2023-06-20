const AWS = require('aws-sdk');

const config = {
    accessKeyId: process.env.AWS_ACCESS_KEY_ID,
    secretAccessKey: process.env.AWS_SECRET_ACCESS_KEY,
}
const s3 = new AWS.S3(config);

const sns = AWS.SNS();


module.exports = { AWS, s3, sns};