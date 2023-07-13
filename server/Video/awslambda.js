'use strict'

const AWS = require('aws');

const s3 = AWS.s3({});

const estranscoder = AWS.elasticTranscoder({});


export const handler = async(event, context) => {
    // TODO implement
    const bucketname = event.Records[0].s3.bucket.name;
    var key = event.Records[0].s3.object.key;

    var pipelineId = "1618142748073-skxu76";
    
    var bucketParams = {
        Id: pipelineId,
        OutputBucket: bucketname.splice(0, -3) + "_out" 
    }
    
    estranscoder.updatePipeline(bucketParams, (err, data) => {
        if (err) {
            throw err;
        }
    });

    var srcKey = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' '));
    
    //the object may have spaces
    
    var newKey = key.split(".")[0];
    var fk= '400K';
    var sk= '600K';
    var om= '1.5M';
    var tm= '2M';
    var th= 'Thumb';
    
    var params = {

        PipelineId: pipelineId,
        OutputKeyPrefix: newKey + "/",
        Input: {
            Key: srcKey,
            FrameRate: "auto",
            Resolution: "auto",
            AspectRatio: "auto",
            Interlaced: "auto",
            Container: "auto"
        },
        
        Outputs: [{
        
                Key: fk + "/" +"hls4K-" + newKey + ".ts",
                ThumbnailPattern: th +"/"+"thumbs4K-{count}",
                PresetId: "1562047543931–59morw",//HLS v3 400K/s
                SegmentDuration:'5'
            },
            {
            
                Key: sk + "/" +"hls6K-" + newKey + ".ts",
                
                ThumbnailPattern: th +"/"+"thumbs6K-{count}",
                
                PresetId: "1562047718852–9h1hs7", //HLS v3 600K/s
                
                SegmentDuration:'5'
            
            },
        ],
        
        Playlists: [{
        
            Format: "HLSv3",
            
            Name: "hls-"+ newKey,
            
            OutputKeys: [ fk+ "/" +"hls4K-"+ newKey + ".ts",sk + "/" +"hls6K-" + newKey + ".ts",om + "/"
            
            +"hls1.5M-" + newKey + ".ts",,tm + "/" +"hls2M-" + newKey + ".ts"]
            
        }]
        
    };
    
    estranscoder.createJob(params, function(err, data){
        
        if (err){
        
            throw err;
        
        }
    
    });

};
