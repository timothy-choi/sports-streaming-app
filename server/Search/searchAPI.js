const express = require('express');
const app = express();

const {createIndex } = require('./init.js');

const PORT = 1100;
app.listen(PORT, () => {});


const postCreateIndex = function(req, res) {
    try {
        createIndex(req.body.indexname);
    } catch (err) {
        return res.send(500).send({"message": "Error creating index"});
    }
    return res.send(200).send({"message": "Index created successfully"});
}


app.post('/index', postCreateIndex);