const {AWS} = require('./index.js');

const Lambda = new AWS.Lambda();


const invokeLambda = function (QueueArn) {
    var params = {
        FunctionName: 'VideoTranscoderFunction',
        EventSourceArn: QueueArn
    };

    Lambda.createEventSourceMapping(params, (err, data) => {
        if (err) {
            throw new Error(err);
        } else {
            return 1;
        }
    });
};

module.exports = { invokeLambda };