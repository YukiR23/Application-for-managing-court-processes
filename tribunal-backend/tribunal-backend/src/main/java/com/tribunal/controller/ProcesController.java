package com.tribunal.controller;

import com.tribunal.model.Judecator;
import com.tribunal.model.Proces;
import com.tribunal.model.Procuror;
import com.tribunal.repository.JudecatorRepository;
import com.tribunal.repository.ProcesRepository;
import com.tribunal.repository.ProcurorRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Clasa pentru gestionarea proceselor în aplicație.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/procese")
@CrossOrigin
public class ProcesController {

    private final ProcesRepository procesRepository;
    private final JudecatorRepository judecatorRepository;
    private final ProcurorRepository procurorRepository;

    public ProcesController(
            ProcesRepository procesRepository,
            JudecatorRepository judecatorRepository,
            ProcurorRepository procurorRepository
    ) {
        this.procesRepository = procesRepository;
        this.judecatorRepository = judecatorRepository;
        this.procurorRepository = procurorRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return procesRepository.findAllProcese()
                .stream()
                .map(r -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("idProces", r[0]);
                    m.put("nrDosar", r[1]);
                    m.put("dataInregistrare", r[2].toString());
                    m.put("stadiuProces", r[3]);
                    m.put("materieJuridica", r[4]);
                    m.put("idJudecator", r[5]);
                    m.put("idProcuror", r[6]);
                    return m;
                })
                .toList();
    }

    @GetMapping("/{idProces}/judecator")
    public Map<String, Object> getJudecatorByProces(
            @PathVariable Integer idProces
    ) {
        Optional<Object[]> result =
                procesRepository.findJudecatorByProces(idProces);

        if (result.isEmpty()) {
            return Map.of();
        }

        Object[] row = (Object[]) result.get()[0];

        Map<String, Object> m = new HashMap<>();
        m.put("nume", row[0]);
        m.put("prenume", row[1]);
        m.put("sectie", row[2]);

        return m;
    }

    @GetMapping("/{idProces}/procuror")
    public Map<String, Object> getProcurorByProces(
            @PathVariable Integer idProces
    ) {

        Optional<Object[]> result =
                procesRepository.findProcurorByProces(idProces);

        if (result.isEmpty()) {
            return Map.of();
        }

        Object[] row = (Object[]) result.get()[0];

        Map<String, Object> m = new HashMap<>();
        m.put("nume", row[0]);
        m.put("prenume", row[1]);
        m.put("parchet", row[2]);

        return m;
    }

    @PostMapping
    public ResponseEntity<?> addProces(@RequestBody Map<String, Object> body) {

        Map<String, Object> jud = (Map<String, Object>) body.get("judecator");
        Map<String, Object> proc = (Map<String, Object>) body.get("procuror");

        procesRepository.insertProces(
                (String) body.get("nrDosar"),
                (String) body.get("materieJuridica"),
                (String) body.get("stadiuProces"),
                LocalDate.parse((String) body.get("dataInregistrare")),
                (Integer) jud.get("idJudecator"),
                proc != null ? (Integer) proc.get("idProcuror") : null
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProces(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body
    ) {
        Map<String, Object> jud = (Map<String, Object>) body.get("judecator");
        Map<String, Object> proc = (Map<String, Object>) body.get("procuror");

        procesRepository.updateProces(
                id,
                (String) body.get("nrDosar"),
                (String) body.get("materieJuridica"),
                (String) body.get("stadiuProces"),
                LocalDate.parse((String) body.get("dataInregistrare")),
                (Integer) jud.get("idJudecator"),
                proc != null ? (Integer) proc.get("idProcuror") : null
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        procesRepository.deleteSedinteByProces(id);
        procesRepository.deleteHotarareByProces(id);
        procesRepository.deletePartiByProces(id);
        procesRepository.deleteProces(id);

        return ResponseEntity.noContent().build();
    }

}
