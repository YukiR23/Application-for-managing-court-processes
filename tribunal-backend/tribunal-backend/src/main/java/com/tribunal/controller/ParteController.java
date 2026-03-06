package com.tribunal.controller;

import com.tribunal.model.Avocat;
import com.tribunal.model.Parte;
import com.tribunal.model.Persoana;
import com.tribunal.model.Proces;
import com.tribunal.repository.AvocatRepository;
import com.tribunal.repository.ParteRepository;
import com.tribunal.repository.PersoanaRepository;
import com.tribunal.repository.ProcesRepository;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** Clasa pentru gestionarea părților dintr-un proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */


@RestController
@RequestMapping("/api/parti")
@CrossOrigin
public class ParteController {

    private final ParteRepository parteRepository;

    public ParteController(ParteRepository parteRepository) {
        this.parteRepository = parteRepository;
    }

    @GetMapping("/proces/{idProces}")
    public List<Map<String, Object>> getPartiByProces(
            @PathVariable Integer idProces
    ) {
        List<Object[]> rows = parteRepository.findPartiByProces(idProces);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();

            m.put("idParte", r[0]);
            m.put("tipParte", r[1]);

            m.put("idPersoana", r[2]);
            m.put("numePersoana", r[3]);
            m.put("prenumePersoana", r[4]);

            m.put("idAvocat", r[5]);
            m.put("numeAvocat", r[6]);
            m.put("prenumeAvocat", r[7]);

            result.add(m);
        }

        return result;
    }

    @PostMapping
    public void addParte(@RequestBody Map<String, Object> body) {

        parteRepository.insertParte(
                (Integer) body.get("idProces"),
                (Integer) body.get("idPersoana"),
                (Integer) body.get("idAvocat"),
                (String) body.get("tipParte")
        );
    }

    @DeleteMapping("/{id}")
    public void deleteParte(@PathVariable Integer id) {
        parteRepository.deleteParte(id);
    }
}

