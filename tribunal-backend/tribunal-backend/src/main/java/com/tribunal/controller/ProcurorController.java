package com.tribunal.controller;

import com.tribunal.model.Procuror;
import com.tribunal.repository.ProcurorRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Clasa pentru gestionarea procurorilor.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/procurori")
@CrossOrigin
public class ProcurorController {

    private final ProcurorRepository repo;

    public ProcurorController(ProcurorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Procuror> getAll() {
        return repo.findAll();
    }
}
