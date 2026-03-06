package com.tribunal.controller;

import com.tribunal.model.Persoana;
import com.tribunal.repository.PersoanaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Clasa pentru gestionarea persoanelor implicate în procese.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/persoane")
@CrossOrigin
public class PersoanaController {

    private final PersoanaRepository repo;

    public PersoanaController(PersoanaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Persoana> getAll() {
        return repo.findAll();
    }
}

