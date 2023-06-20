const { AWS, sns} = require('./index.js');

const sqs = new AWS.SQS({apiVersion: '2012-11-05'});

const createNewQueue = function (QueueName) {
    var params = {
        QueueName: QueueName,
        Attributes: {
            'DelaySeconds': '0',
            'MessageRetentionPeriod': '86400'
        }
    };
    
    sqs.createQueue(params, (err, data) => {
        if (err) {
            throw new Error(err);
        } else {
            return data.QueueUrl;
        }
    });

    return null;
};

const getQueueUrl = function () {
    var params = {
        QueueName: 'VideoQueue'
    };

    sqs.getQueueUrl(params, (err, data) => {
        if (err) {
            throw new Error(err);
        } else {
            return data.QueueUrl;
        }
    });

    return null;
}

const deleteQueue = function () {
    var params = {
        QueueUrl: getQueueUrl()
    };

    sqs.deleteQueue(params, (err, data) => {
        if (err) {
            throw new Error(err);
        } else {
            return 1;
        }
    });

    return 0;
}

const subscribeToQueue = function (TopicArn, QueueArn, QueueUrl) {
    var params = {
        Protocol: 'sqs',
        TopicArn: TopicArn,
        Endpoint: QueueArn
    };

    sns.subscribe(params, (err, data) => {  
        if (err) {
            throw new Error(err);
        }

        var policy = {
            Version: '2012-10-17',
            Attributes: {
                Policy: JSON.stringify({
                    Version: '2012-10-17',
                    Statement: [
                      {
                        Sid: 'Allow-SNS-SendMessage',
                        Effect: 'Allow',
                        Principal: '*',
                        Action: 'sqs:SendMessage',
                        Resource: QueueUrl,
                        Condition: {
                          ArnEquals: {
                            'aws:SourceArn': TopicArn,
                          },
                        },
                      },
                    ],
                  }),
                }
            };

        sqs.setQueueAttributes({QueueUrl: QueueUrl, Attributes: policy}, (err, data) => {
            if (err) {
                throw new Error(err);
            }
        });
    });
};

module.exports = { createNewQueue, getQueueUrl, deleteQueue, subscribeToQueue };