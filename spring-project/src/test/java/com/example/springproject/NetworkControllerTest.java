package com.example.springproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NetworkControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testInsertNodes() {
        Node node = new Node("node 1", "Athlone", 15, 150);
        ResponseEntity<Node> response = restTemplate.postForEntity("/network", node, Node.class);

        Node responseNode = response.getBody();
        long id = responseNode.getid();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(-1, responseNode.getid());
        assertEquals("node 1", responseNode.getName());
        assertEquals("Athlone", responseNode.getLocation());
        assertEquals(15, responseNode.getLatitude());
        assertEquals(150, responseNode.getLongitude());
    }

    @Test
    public void testGetAllNodes() {
        ResponseEntity<Iterable<Node>> response = restTemplate.exchange("/network",
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Node>>() {});

        Iterable<Node> nodes = response.getBody();
        List<Node> nodeList = StreamSupport.stream(nodes.spliterator(), false).toList();

        assertEquals(4, nodeList.size());
        assertEquals("node 3", nodeList.get(2).getName());
        assertEquals("cyprus", nodeList.get(2).getLocation());
        assertEquals(35, nodeList.get(2).getLatitude());
        assertEquals(33, nodeList.get(2).getLongitude());
    }

    @Test
    public void testGetNodeById() {
        Node responseNode = restTemplate.getForObject("/network/2", Node.class);

        assertEquals(2, responseNode.getid());
        assertEquals("node 2", responseNode.getName());
        assertEquals("dublin", responseNode.getLocation());
        assertEquals(53, responseNode.getLatitude());
        assertEquals(-6, responseNode.getLongitude());
    }

    @Test
    public void deleteNodeById() {
        restTemplate.delete("/network/2");

        ResponseEntity<Iterable<Node>> response = restTemplate.exchange("/network",
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Node>>() {});

        Iterable<Node> nodes = response.getBody();
        List<Node> nodeList = StreamSupport.stream(nodes.spliterator(), false).toList();

        assertNotEquals(4, nodeList.size());
        assertEquals(3, nodeList.size());

        assertNotEquals("node 2", nodeList.get(1).getName());
        assertNotEquals("dublin", nodeList.get(1).getLocation());
        assertNotEquals(53, nodeList.get(1).getLatitude());
        assertNotEquals(-6, nodeList.get(1).getLongitude());

        assertEquals("node 3", nodeList.get(1).getName());
        assertEquals("cyprus", nodeList.get(1).getLocation());
        assertEquals(35, nodeList.get(1).getLatitude());
        assertEquals(33, nodeList.get(1).getLongitude());
    }

    @Test
    public void testUpdateNodeName() {
        Node originalNode = restTemplate.getForEntity("/network/1", Node.class).getBody();
        assertEquals("node 1", originalNode.getName());

        restTemplate.put("/network/updateName/1", "TestUpdateName");
        Node responseNode = restTemplate.getForEntity("/network/1", Node.class).getBody();

        assertEquals("TestUpdateName", responseNode.getName());
    }

    @Test
    public void testUpdateNodeLocation() {
        Node originalNode = restTemplate.getForEntity("/network/1", Node.class).getBody();
        assertEquals("athlone", originalNode.getLocation());

        restTemplate.put("/network/updateLocation/1", "new york");
        Node responseNode = restTemplate.getForEntity("/network/1", Node.class).getBody();

        assertEquals("new york", responseNode.getLocation());
    }

    @Test
    public void testUpdateNodeCoords() {
        Node originalNode = restTemplate.getForEntity("/network/1", Node.class).getBody();
        assertEquals(53, originalNode.getLatitude());
        assertEquals(-7, originalNode.getLongitude());

        CoordinatesDTO coords = new CoordinatesDTO();
        coords.setLatitude(15);
        coords.setLongitude(13);

        restTemplate.put("/network/updateCoords/1", coords);

        Node responseNode = restTemplate.getForEntity("/network/1", Node.class).getBody();
        assertEquals(15, responseNode.getLatitude());
        assertEquals(13, responseNode.getLongitude());
    }

}
