var elasticsearch = require('elasticsearch');
var elasticClient = new elasticsearch.Client({
    host: 'localhost:9200',
    log: 'info'
});

var redis = require('redis');
var flat = require('flat');

var axios = require('axios');
const searchClient = redis.createClient();

const getCommunities = function(communityName) {
    var community = null;
    searchClient.get(communityName, function(err, data) {
        if (err) {
            throw err;
        }
        if (data) {
            return JSON.parse(unflatten(data));
        }
        else {
            axios.get('')
            .then(function (response) {
                var communities = response.communities;
                if (communities.length == 0) {
                    return null;
                }
                for (let i = 0; i < communities.length; i++) {
                    if (communities[i].name == communityName) {
                        community = communities[i];
                    }
                }
            })
            .catch(function (error) {
                throw error;
            });

            searchClient.setex(communityName, 604800, JSON.stringify(flatten(community)));
        }

        return JSON.parse(unflatten(community));
    }); 
};

const getUsers = function(username) {
    var res = null;
    searchClient.get(username, function(err, data) {
        if (err) {
            throw err;
        }
        if (data) {
            return JSON.parse(unflatten(data));
        }
        else {
            axios.get('')
            .then(function (response) {
                res = response.users;
                if (res.length == 0) {
                    return null;
                }
            })
            .catch(function (error) {
                throw error;
            });
            searchClient.setex(username, 604800, JSON.stringify(flatten(res)));
        }
    });
    return JSON.parse(unflatten(res));
};


const createIndex = function(indexName) {
    elasticClient.indices.create({
        index: indexName
    })
    .then(function(resp) {
        return resp;
    }, function(err) {
        throw err;
    });

    return null;
};

const addDocument = function(indexName, id, data) {
    elasticClient.index({
        index: indexName,
        id: id,
        body: data
    })
    .then(function(resp) {
        return resp;
    }, function(err) {
        throw err;
    });

    return null;
};

const deleteDocument = function(indexName, id) {
    elasticClient.delete({
        index: indexName,
        id: id
    })
    .then(function(resp) {
        return resp;
    }, function(err) {
        throw err;
    });

    return null;
};

const deleteIndex = function(indexName) {
    elasticClient.indices.delete({
        index: indexName
    })
    .then(function(resp) {
        return resp;
    }, function(err) {
        throw err;
    });

    return null;
};

const searchOperation = function(searchTerm) {
    var results = [];
    searchClient.get(searchTerm, function(err, data) {
        if (err) {
            throw err;
        }
        if (data) {
            return JSON.parse(unflatten(data));
        }
        else {
            elasticClient.search({
                index: '_all',
                body: {
                    query: {
                        fuzzy: {
                            "name": searchTerm
                        }
                    }
                }
            }).then(function(resp) {
                results = resp.hits.hits;
            }).catch(function(err) {
                throw err;
            });

            searchClient.setex(searchTerm, 3600, JSON.stringify(flatten(results)));
        }});

    return JSON.parse(unflatten(results));
};


module.exports = { createIndex, addDocument, deleteDocument, deleteIndex, getUsers, getCommunities, searchOperation};