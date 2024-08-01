package com.example.springproject;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NetworkCrudRepository extends CrudRepository<Node, Long> {
    //update methods needed for attributes
    @Query("update Node n set n.name = ?1 where n.id = ?2")
    @Modifying(clearAutomatically = true)
    @Transactional
    void updateName(String name, Long id);

    @Query("update Node n set n.location = ?1 where n.id = ?2")
    @Modifying(clearAutomatically = true)
    @Transactional
    void updateLocation(String location, Long id);

    @Query("UPDATE Node n SET n.latitude = ?1, n.longitude = ?2 WHERE n.id = ?3")
    @Modifying(clearAutomatically = true)
    @Transactional
    void updateCoords(int latitude, int longitude, Long id);
}
