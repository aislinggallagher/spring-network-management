package com.example.springproject;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeedDB {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 1", "athlone", 53.4239, -7.9403);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 2", "dublin", 53.3498, -6.2603);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 3", "cyprus", 35.1264, 33.4299);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 4", "beijing", 39.9042, 116.4074);
    }
}
