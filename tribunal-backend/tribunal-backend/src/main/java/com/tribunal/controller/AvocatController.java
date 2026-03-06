package com.tribunal.controller;

import com.tribunal.model.Avocat;
import com.tribunal.repository.AvocatRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Clasa pentru gestionarea avocaților prin API.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */


@RestController
@RequestMapping("/api/avocati")
@CrossOrigin
public class AvocatController {

    private final AvocatRepository repo;

    public AvocatController(AvocatRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Avocat> getAll() {
        return repo.findAll();
    }
}

