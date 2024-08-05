import React, { useEffect } from 'react';
import Map, { Marker } from 'react-map-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import mapboxgl from 'mapbox-gl';

// Set your Mapbox token here
mapboxgl.accessToken = 'pk.eyJ1IjoiYWlzbGluZ2dhbCIsImEiOiJjbHpjaXk0MWUwNm9wMmlzOWF4ZzlhY3J5In0.kiSoYQhHSJfiqcuwwrCLTQ';

const MapView = ({ nodes, onMarkerClick }) => {
    useEffect(() => {
        // Initialize the map
        const map = new mapboxgl.Map({
            container: 'map', // ID of the container in CSS
            style: 'mapbox://styles/mapbox/standard-satellite',
            center: [-7.5029786, 53.4494762], // Default center, can be adjusted
            zoom: 6, // Default zoom level
        });

        // Add markers for each node
        nodes.forEach(node => {
            new mapboxgl.Marker()
                .setLngLat([node.longitude, node.latitude])
                .setPopup(new mapboxgl.Popup({ offset: 25 }).setText(node.name))
                .setLngLat([node.longitude, node.latitude])
                .addTo(map)
                .getElement().addEventListener('click', () => onMarkerClick(node.id));
        });

        // Clean up on unmount
        return () => map.remove();
    }, [nodes, onMarkerClick]);

    return <div id="map" className="map-container"></div>;
};

export default MapView;
