package com.tribunal.controller;

import com.tribunal.model.Grefier;
import com.tribunal.repository.GrefierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/** Clasa pentru gestionarea grefierilor.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */


@RestController
@RequestMapping("/api/grefieri")
@CrossOrigin
public class GrefierController {

    private final GrefierRepository repo;

    public GrefierController(GrefierRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Grefier> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Grefier getById(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Grefier inexistent"));
    }

    @PostMapping
    public Grefier create(@RequestBody Grefier g) {
        return repo.save(g);
    }

}
