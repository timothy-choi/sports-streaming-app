const { AWS, sns } = require('./index.js');


const topicExists = function (TopicName) {
    sns.listTopics()
    .then((data) => {
        for (var i = 0; i < data.Topics.length; i++) {
            if (data.Topics[i].TopicArn.includes(TopicName)) {
                return 1;
            }
        }
        return 0;
    })
    .catch((err) => {
        throw new Error(err);
    });

    return 0;
};
    

const createNewTopic = function (TopicName) {
    sns.createTopic({Name: TopicName})
    .then((data) => {
        return data.TopicArn;
    })
    .catch((err) => {
        throw new Error(err);
    }); 

    return null;
};

const deleteTopic = function (TopicArn) {
    sns.deleteTopic({TopicArn: TopicArn}, (err, data) => {
        if (err) {
            throw new Error(err);
        } else {
            return 1;
        }
    });

    return 0;
};

const getTopicARN = function (TopicName) {
    sns.listTopics()
    .then((data) => {
        for (var i = 0; i < data.Topics.length; i++) {
            if (data.Topics[i].TopicArn.includes(TopicName)) {
                return data.Topics[i].TopicArn;
            }
        }
        return null;
    })
    .catch((err) => {
        throw new Error(err);
    });

    return null;
};

const configToTriggerLambda = function (TopicArn, QueueArn, bucketName) {
    var bucketParams = {
        Bucket: bucketName,
        TopicConfiguration: [
            {
                TopicArn: TopicArn,
                Events: [
                    's3:ObjectCreated:*'
                ],
            },
        ], 
        QueueConfigurations: [
            {
                QueueArn: QueueArn,
                Events: [
                    's3:ObjectCreated:*'
                ],
            },
        ]
    };
            
    s3.putBucketNotificationConfiguration(bucketParams, (err, data) => {
        if (err) {
            return 0;
        }
    });
};


module.exports = { topicExists, createNewTopic, configToTriggerLambda, deleteTopic, getTopicARN};