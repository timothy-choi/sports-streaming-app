const express = require('express');
const app = express();

const {createIndex, addDocument, deleteDocument, deleteIndex} = require('./init.js');

const PORT = 1100;
app.listen(PORT, () => {});


const postCreateCommunity = function(req, res) {
    try {
        createIndex(req.body.indexname);
    } catch (err) {
        return res.send(500).send({"message": "Error creating index"});
    }
    return res.send(200).send({"message": "Index created successfully"});
}


app.post('/community', postCreateCommunity);


const addNewVideo = function(req, res) {
    try {
        addDocument(req.body.indexname, req.body.id, req.body.data);
    }  catch (err) {
        return res.send(500).send({"message": "Error adding video"});
    }
    return res.send(200).send({"message": "Video added successfully"});
};


app.post('/video', addNewVideo);

const deleteVideo = function(req, res) {
    try {
        deleteDocument(req.body.indexname, req.body.id);
    } catch (err) {
        return res.send(500).send({"message": "Error deleting video"});
    }
    return res.send(200).send({"message": "Video deleted successfully"});
};

app.delete('/video', deleteVideo);


const deleteCommunity = function(req, res) {
    try {
        deleteIndex(req.body.indexname);
    } catch (err) {
        return res.send(500).send({"message": "Error deleting index"});
    }
    return res.send(200).send({"message": "Index deleted successfully"});
};

app.delete('/community', deleteCommunity);