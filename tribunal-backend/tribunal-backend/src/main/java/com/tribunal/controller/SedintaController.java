package com.tribunal.controller;

import com.tribunal.model.*;
import com.tribunal.repository.GrefierRepository;
import com.tribunal.repository.ProcesRepository;
import com.tribunal.repository.SedintaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** Clasa pentru gestionarea ședințelor de judecată.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/sedinte")
@CrossOrigin
public class SedintaController {

    private final SedintaRepository sedintaRepository;
    private final ProcesRepository procesRepository;
    private final GrefierRepository grefierRepository;

    public SedintaController(
            SedintaRepository sedintaRepository,
            ProcesRepository procesRepository,
            GrefierRepository grefierRepository
    ) {
        this.sedintaRepository = sedintaRepository;
        this.procesRepository = procesRepository;
        this.grefierRepository = grefierRepository;
    }


    @GetMapping
    public List<Sedinta> getAll() {
        return sedintaRepository.findAll();
    }

    @GetMapping("/proces/{idProces}")
    public List<Map<String, Object>> geSedintetByProces(@PathVariable Integer idProces) {

        return sedintaRepository.findSedinteByProces(idProces)
                .stream()
                .map(r -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("idSedinta", r[0]);
                    m.put("dataTermen", r[1].toString());
                    m.put("ora", r[2].toString());
                    m.put("sala", r[3]);
                    m.put("rezultat", r[4]);
                    m.put("idGrefier", r[5]);
                    m.put("numeGrefier", r[6]);
                    m.put("prenumeGrefier", r[7]);
                    return m;
                })
                .toList();
    }

    @GetMapping("/toate")
    public List<Map<String, Object>> getSedinteToate(
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) String nrDosar,
            @RequestParam(defaultValue = "false") boolean faraRezultat
    ) {
        List<Object[]> rows =
                sedintaRepository.findAllSedinteFiltrate(
                        data,
                        nrDosar,
                        faraRezultat
                );

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("idSedinta", r[0]);
            m.put("dataTermen", r[1].toString());
            m.put("ora", r[2].toString());
            m.put("sala", r[3]);
            m.put("rezultat", r[4]);
            m.put("nrDosar", r[5]);
            m.put("grefier", r[6] + " " + r[7]);
            result.add(m);
        }

        return result;
    }

    @GetMapping("/nr-sedinte-per-proces")
    public List<Map<String, Object>> getNrSedintePerProces() {

        List<Object[]> rows = sedintaRepository.countSedintePerProces();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            result.add(Map.of(
                    "nrDosar", r[0],
                    "nrSedinte", r[1]
            ));
        }
        return result;
    }


    @GetMapping("/procese-cu-min-sedinte")
    public List<Map<String, Object>> getProceseCuMinSedinte(
            @RequestParam int min
    ) {
        List<Object[]> rows =
                procesRepository.proceseCuMinSedinte(min);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            result.add(Map.of(
                    "idProces", r[0],
                    "nrDosar", r[1]
            ));
        }

        return result;
    }

    @GetMapping("/fara-rezultat")
    public List<Map<String, Object>> getSedinteFaraRezultat() {

        List<Object[]> rows = sedintaRepository.findSedinteFaraRezultat();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            result.add(Map.of(
                    "idSedinta", r[0],
                    "dataTermen", r[1].toString(),
                    "ora", r[2].toString(),
                    "sala", r[3],
                    "nrDosar", r[4],
                    "grefier", r[5] + " " + r[6],
                    "rezultat", ""
            ));
        }
        return result;
    }

    @GetMapping("/sedinte-importante")
    public List<Map<String, Object>> getSedinteImportante(
            @RequestParam LocalDate data
    ) {
        List<Object[]> rows = sedintaRepository.sedinteImportante(data);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("idSedinta", r[0]);
            m.put("dataTermen", r[1].toString());
            m.put("ora", r[2].toString());
            m.put("sala", r[3]);
            m.put("nrDosar", r[4]);
            result.add(m);
        }


        return result;
    }

    @PostMapping
    public Sedinta create(@RequestBody Sedinta s) {

        if (s.getProces() == null || s.getProces().getIdProces() == null) {
            throw new RuntimeException("Sedinta trebuie sa apartina unui proces");
        }

        Proces proces = procesRepository
                .findById(s.getProces().getIdProces())
                .orElseThrow(() -> new RuntimeException("Proces inexistent"));

        if (s.getGrefier() == null || s.getGrefier().getIdGrefier() == null) {
            throw new RuntimeException("Sedinta trebuie sa aiba grefier");
        }

        Grefier grefier = grefierRepository
                .findById(s.getGrefier().getIdGrefier())
                .orElseThrow(() -> new RuntimeException("Grefier inexistent"));

        s.setProces(proces);
        s.setGrefier(grefier);

        return sedintaRepository.save(s);
    }

    @PostMapping("/proces/{idProces}")
    @Transactional
    public ResponseEntity<?> addSedinta(
            @PathVariable Integer idProces,
            @RequestBody Map<String, Object> body
    ) {
        sedintaRepository.insertSedinta(
                LocalDate.parse((String) body.get("dataTermen")),
                LocalTime.parse((String) body.get("ora")),
                (String) body.get("sala"),
                (String) body.get("rezultat"),
                idProces,
                (Integer) body.get("idGrefier")
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public void update(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body
    ) {
        sedintaRepository.updateSedinta(
                id,
                LocalDate.parse((String) body.get("dataTermen")),
                LocalTime.parse((String) body.get("ora")),
                (String) body.get("sala"),
                (String) body.get("rezultat"),
                (Integer) body.get("idGrefier")
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Integer id) {
        sedintaRepository.deleteSedinta(id);
    }

}
