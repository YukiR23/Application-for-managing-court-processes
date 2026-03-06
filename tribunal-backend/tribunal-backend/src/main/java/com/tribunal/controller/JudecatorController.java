package com.tribunal.controller;

import com.tribunal.model.Judecator;
import com.tribunal.repository.JudecatorRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Clasa pentru gestionarea judecătorilor.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/judecatori")
@CrossOrigin
public class JudecatorController {

    private final JudecatorRepository repo;

    public JudecatorController(JudecatorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Judecator> getAll() {
        return repo.findAll();
    }
}

