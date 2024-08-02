package com.example.springproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NetworkCrudRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NetworkCrudRepository networkCrudRepository;

    @Test
    public void testUpdateName() {
        Node node = entityManager.persist(new Node("testNode", "athlone", 10, 11));
        long id = node.getid();

        networkCrudRepository.updateName("updatedNode", id);

        Node updatedNode = networkCrudRepository.findById(id).get();
        assertEquals("updatedNode", updatedNode.getName());
    }

    @Test
    public void testUpdateLocation() {
        Node node = entityManager.persist(new Node("testNode", "athlone", 10, 11));
        long id = node.getid();

        networkCrudRepository.updateLocation("Athlone", id);

        Node updatedNode = networkCrudRepository.findById(id).get();
        assertEquals("Athlone", updatedNode.getLocation());
    }

    @Test
    public void testUpdateCoords() {
        Node node = entityManager.persist(new Node("testNode", "athlone", 10, 11));
        long id = node.getid();

        networkCrudRepository.updateCoords(100, 150, id);

        Node updatedNode = networkCrudRepository.findById(id).get();
        assertEquals(100, updatedNode.getLatitude());
        assertEquals(150, updatedNode.getLongitude());
    }


}
