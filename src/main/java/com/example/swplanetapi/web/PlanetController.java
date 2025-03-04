package com.example.swplanetapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.domain.PlanetService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
  @Autowired
  private PlanetService planetService;

  @PostMapping
  public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet) {
    Planet planetCreated = planetService.create(planet);
    return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> get(@PathVariable("id") Long id) {
    return planetService.get(id).map(planet -> ResponseEntity.ok(planet))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Planet> getByName(@PathVariable("name") String name) {
    return planetService.getByName(name).map(planet -> ResponseEntity.ok(planet))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Planet>> list(@RequestParam(required = false) String terrain,
                                           @RequestParam(required = false) String climate) {
    List<Planet> list = planetService.list(terrain, climate);
    return ResponseEntity.ok(list);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
    planetService.remove(id);
    return ResponseEntity.noContent().build();
  }
}
