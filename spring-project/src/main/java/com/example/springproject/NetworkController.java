package com.example.springproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/network")
@CrossOrigin
public class NetworkController {
    //create methods for CRUD operations
    @Autowired
    private NetworkCrudRepository networkCrudRepository;

    @GetMapping
    public ResponseEntity<Iterable<Node>> getNodes() {
        Iterable<Node> nodes = networkCrudRepository.findAll();
        return ResponseEntity.ok().body(nodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Node> getNode(@PathVariable Long id) {
        Node node = networkCrudRepository.findById(id).orElse(null);
        return ResponseEntity.ok().body(node);
    }

    @PostMapping
    public ResponseEntity<Node> createNode(@RequestBody Node node) {
        Node newNode = networkCrudRepository.save(node);
        return ResponseEntity.ok().body(newNode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        networkCrudRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateName/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String name) {
        networkCrudRepository.updateName(name, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateLocation/{id}")
    public ResponseEntity<Void> updateLocation(@PathVariable Long id, @RequestBody String location) {
        networkCrudRepository.updateLocation(location, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateCoords/{id}")
    public ResponseEntity<Void> updateCoords(@PathVariable Long id, @RequestBody CoordinatesDTO coords) {
        networkCrudRepository.updateCoords(coords.getLatitude(), coords.getLongitude(), id);
        return ResponseEntity.noContent().build();
    }
}
