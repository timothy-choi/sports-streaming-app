const { AWS } = require('./index.js');

const sqs = new AWS.SQS({apiVersion: '2012-11-05'});

const createNewQueue = function () {
    var params = {
        QueueName: 'VideoQueue',
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