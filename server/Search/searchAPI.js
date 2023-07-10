const express = require('express');
const app = express();

const {createIndex, addDocument, deleteDocument, deleteIndex, searchOperation, getCommunities, getUsers} = require('./init.js');

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



const search = function(req, res) {
    var results = {};
    try {
        var searchResults = searchOperation(req.params.searchTerm);
        var users = getUsers(req.params.searchTerm);
        var communities = getCommunities(req.params.searchTerm);

        var userResults = [];
        if (users.length > 0) {
            for (let i = 0; i < users.length; i++) {
                userResults.push(users[i]);
            }
        }

        var commResults = [];
        if (communities.length > 0) {
            for (let i = 0; i < communities.length; i++) {
                commResults.push(communities[i]);
            }
        }

        var esResults = {};
        if (searchResults.length > 0) {
            for (let i = 0; i < searchResults.length; i++) {
                if (esResults.hasOwnProperty(searchResults[i]._index)) {
                    esResults[searchResults[i]._index].push(searchResults[i]._source);
                }
                else {
                    esResults[searchResults[i]._index] = [];
                    esResults[searchResults[i]._index].push(searchResults[i]._source);
                }
            }
        }

        results["users"] = userResults;
        results["communities"] = commResults;
        results["videos"] = esResults;

    } catch (err) {
        return res.send(500).send({"message": "Error searching"});
    }   

    return res.send(200).send({"results": results});
};

app.get('/search/:searchTerm', search);