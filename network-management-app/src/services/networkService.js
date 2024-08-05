import axios from 'axios';

const API_URL = 'http://localhost:8080/network';

const updateName = (id, name) => {
    console.log('Updating node name with payload:', name);
    return axios.put(`${API_URL}/updateName/${id}`, name, {
        headers: {
            'Content-Type': 'text/plain'
        }
    });
};

const updateLocation = (id, location) => {
    console.log('Updating node location with payload:', location);
    return axios.put(`${API_URL}/updateLocation/${id}`, location, {
        headers: {
            'Content-Type': 'text/plain'
        }
    });
}

const updateCoords = (id, coords) => {
    return axios.put(`${API_URL}/updateCoords/${id}`, coords);
};

const getNodes = () => {
    return axios.get(API_URL);
};

const createNode = (node) => {
    console.log('Creating node with payload:', node);
    return axios.post(API_URL, node);
};

const getNode = (id) => {
    return axios.get(`${API_URL}/${id}`);
};

const deleteNode = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};

export default {
    updateName,
    updateLocation,
    updateCoords,
    getNodes,
    createNode,
    getNode,
    deleteNode,
};
