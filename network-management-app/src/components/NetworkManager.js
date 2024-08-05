import React, { useState, useEffect, useRef } from 'react';
import networkService from '../services/networkService';
import MapView from './MapView'; // Import the MapView component
import './NetworkManager.css'; // Import the CSS file

const NetworkManager = () => {
    const [nodes, setNodes] = useState([]);
    const [newNode, setNewNode] = useState({ name: '', location: '', latitude: '', longitude: '' });
    const [updateData, setUpdateData] = useState({ id: '', name: '', location: '', latitude: '', longitude: '' });
    const [newNodeErrors, setNewNodeErrors] = useState({ latitude: '', longitude: '' });
    const [updateDataErrors, setUpdateDataErrors] = useState({ latitude: '', longitude: '' });
    const [selectedNodeId, setSelectedNodeId] = useState(null);
    const nodeRefs = useRef({});

    useEffect(() => {
        fetchNodes();
    }, []);

    const fetchNodes = async () => {
        try {
            const response = await networkService.getNodes();
            setNodes(response.data);
        } catch (error) {
            console.error('Error fetching nodes', error);
        }
    };

    const validateLongitude = (value) => {
        const parsedValue = parseFloat(value);
        if (isNaN(parsedValue) || parsedValue < -180 || parsedValue > 180) {
            return 'Must be a valid number between -180 and 180';
        }
        return '';
    };

    const validateLatitude = (value) => {
        const parsedValue = parseFloat(value);
        if (isNaN(parsedValue)) {
            return 'Must be a valid number between -90 and 90';
        }
        return '';
    };

    const handleCreateNode = async () => {
        const latitudeError = validateLatitude(newNode.latitude);
        const longitudeError = validateLongitude(newNode.longitude);

        if (latitudeError || longitudeError) {
            setNewNodeErrors({ latitude: latitudeError, longitude: longitudeError });
            return;
        }

        try {
            const response = await networkService.createNode({
                ...newNode,
                latitude: parseFloat(newNode.latitude),
                longitude: parseFloat(newNode.longitude),
            });
            setNodes([...nodes, response.data]);
            setNewNode({ name: '', location: '', latitude: '', longitude: '' });
            setNewNodeErrors({ latitude: '', longitude: '' });
        } catch (error) {
            console.error('Error creating node', error);
        }
    };

    const handleUpdateName = async () => {
        if (updateData.id && updateData.name) {
            try {
                await networkService.updateName(updateData.id, updateData.name);
                fetchNodes();
                setUpdateData(prev => ({ id: '', name: '' }));
            } catch (error) {
                console.error('Error updating name', error);
            }
        }
    };

    const handleUpdateLocation = async () => {
        if (updateData.id && updateData.location) {
            try {
                await networkService.updateLocation(updateData.id, updateData.location);
                fetchNodes();
                setUpdateData(prev => ({ ...prev, location: '' }));
            } catch (error) {
                console.error('Error updating location', error);
            }
        }
    };

    const handleUpdateCoords = async () => {
        const latitudeError = validateLatitude(updateData.latitude);
        const longitudeError = validateLongitude(updateData.longitude);

        if (latitudeError || longitudeError) {
            setUpdateDataErrors({ latitude: latitudeError, longitude: longitudeError });
            return;
        }

        const latitude = parseFloat(updateData.latitude);
        const longitude = parseFloat(updateData.longitude);

        if (updateData.id) {
            try {
                await networkService.updateCoords(updateData.id, { latitude, longitude });
                fetchNodes();
                setUpdateData(prev => ({ ...prev, latitude: '', longitude: '' }));
                setUpdateDataErrors({ latitude: '', longitude: '' });
            } catch (error) {
                console.error('Error updating coordinates', error);
            }
        }
    };

    const handleDeleteNode = async (id) => {
        // Show confirmation dialog
        if (window.confirm('Are you sure you want to delete this node?')) {
            try {
                await networkService.deleteNode(id);
                setNodes(nodes.filter(node => node.id !== id));
            } catch (error) {
                console.error('Error deleting node', error);
            }
        }
    };

    const scrollToNode = (id) => {
        setSelectedNodeId(id);
        if (nodeRefs.current[id]) {
            nodeRefs.current[id].scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    };

    return (
        <div className="container">
            <div className="node-list">
                <h1>Nodes</h1>
                <ul>
                    {nodes.map(node => (
                        <li
                            key={node.id}
                            className={`node-item ${node.id === selectedNodeId ? 'highlighted' : ''}`}
                            ref={el => (nodeRefs.current[node.id] = el)}
                            onClick={() => scrollToNode(node.id)}
                        >
                            <div>
                                <strong>ID: </strong>
                                <span>{node.id}</span>
                            </div>
                            <div>
                                <strong>Name: </strong>
                                <span>{node.name}</span>
                            </div>
                            <div>
                                <strong>Location: </strong>
                                <span>{node.location}</span>
                            </div>
                            <div>
                                <strong>Coordinates: </strong>
                                <span>{`${node.latitude}, ${node.longitude}`}</span>
                            </div>
                            <button className="button" onClick={() => handleDeleteNode(node.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            </div>
            <div className="main-content">
                <div className="map-container">
                    <MapView nodes={nodes} onMarkerClick={scrollToNode} />
                </div>
            </div>
            <div className="form-container">
                <h1>Network Manager</h1>

                <div className="section">
                    <h2>Create New Node</h2>
                    <div className="input-group">
                        <label htmlFor="new-name">Name</label>
                        <input
                            id="new-name"
                            type="text"
                            placeholder="Name"
                            value={newNode.name}
                            onChange={(e) => setNewNode(prev => ({...prev, name: e.target.value}))}
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="new-location">Location</label>
                        <input
                            id="new-location"
                            type="text"
                            placeholder="Location"
                            value={newNode.location}
                            onChange={(e) => setNewNode(prev => ({...prev, location: e.target.value}))}
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="new-latitude">Latitude</label>
                        <input
                            id="new-latitude"
                            type="text"
                            placeholder="Latitude"
                            value={newNode.latitude}
                            onChange={(e) => setNewNode(prev => ({...prev, latitude: e.target.value}))}
                        />
                        {newNodeErrors.latitude && <div className="error-message">{newNodeErrors.latitude}</div>}
                    </div>
                    <div className="input-group">
                        <label htmlFor="new-longitude">Longitude</label>
                        <input
                            id="new-longitude"
                            type="text"
                            placeholder="Longitude"
                            value={newNode.longitude}
                            onChange={(e) => setNewNode(prev => ({...prev, longitude: e.target.value}))}
                        />
                        {newNodeErrors.longitude && <div className="error-message">{newNodeErrors.longitude}</div>}
                    </div>
                    <button className="button" onClick={handleCreateNode}>Create Node</button>
                </div>

                <div className="section">
                    <h2>Update Node</h2>
                    <div className="input-group">
                        <label htmlFor="update-id">Node ID</label>
                        <input
                            id="update-id"
                            type="text"
                            placeholder="Node ID"
                            value={updateData.id}
                            onChange={(e) => setUpdateData(prev => ({...prev, id: e.target.value}))}
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="update-name">New Name</label>
                        <input
                            id="update-name"
                            type="text"
                            placeholder="New Name"
                            value={updateData.name}
                            onChange={(e) => setUpdateData(prev => ({...prev, name: e.target.value}))}
                        />
                        <button className="button" onClick={handleUpdateName}>Update Name</button>
                    </div>
                    <div className="input-group">
                        <label htmlFor="update-location">New Location</label>
                        <input
                            id="update-location"
                            type="text"
                            placeholder="New Location"
                            value={updateData.location}
                            onChange={(e) => setUpdateData(prev => ({...prev, location: e.target.value}))}
                        />
                        <button className="button" onClick={handleUpdateLocation}>Update Location</button>
                    </div>
                    <div className="input-group">
                        <label htmlFor="update-latitude">New Latitude</label>
                        <input
                            id="update-latitude"
                            type="text"
                            placeholder="New Latitude"
                            value={updateData.latitude}
                            onChange={(e) => setUpdateData(prev => ({...prev, latitude: e.target.value}))}
                        />
                        {updateDataErrors.latitude && <div className="error-message">{updateDataErrors.latitude}</div>}
                    </div>
                    <div className="input-group">
                        <label htmlFor="update-longitude">New Longitude</label>
                        <input
                            id="update-longitude"
                            type="text"
                            placeholder="New Longitude"
                            value={updateData.longitude}
                            onChange={(e) => setUpdateData(prev => ({...prev, longitude: e.target.value}))}
                        />
                        {updateDataErrors.longitude &&
                            <div className="error-message">{updateDataErrors.longitude}</div>}
                        <button className="button" onClick={handleUpdateCoords}>Update Coordinates</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NetworkManager;
