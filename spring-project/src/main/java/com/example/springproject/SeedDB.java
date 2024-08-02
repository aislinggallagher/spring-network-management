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
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 5", "tralee", 52.2713, -9.6999);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 6", "bundoran", 54.4791, -8.2779);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 7", "westport", 53.8011, -9.5222);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 8", "gorey", 52.6753, -6.2959);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 9", "limerick", 52.6638, -8.6267);
        jdbcTemplate.update("insert into NODE(name, location, latitude, longitude) values(?, ?, ?, ?)",
                "node 10", "cork", 51.8985, -8.4756);

    }
}
