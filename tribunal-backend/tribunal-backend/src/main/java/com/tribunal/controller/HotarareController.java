package com.tribunal.controller;

import com.tribunal.model.Hotarare;
import com.tribunal.model.Proces;
import com.tribunal.repository.HotarareRepository;
import com.tribunal.repository.ProcesRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Clasa pentru gestionarea hotărârilor judecătorești.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/hotarari")
@CrossOrigin
public class HotarareController {

    private final HotarareRepository hotarareRepository;

    public HotarareController(HotarareRepository hotarareRepository) {
        this.hotarareRepository = hotarareRepository;
    }

    @GetMapping("/proces/{idProces}")
    public List<Map<String, Object>> getByProces(
            @PathVariable Integer idProces
    ) {
        return hotarareRepository.findHotarariByProces(idProces)
                .stream()
                .map(r -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("idHotarare", r[0]);
                    m.put("idProces", r[1]);
                    m.put("tipHotarare", r[2]);
                    m.put("dataPronuntare", r[3].toString());
                    return m;
                })
                .toList();
    }

    @PostMapping
    public void create(@RequestBody Map<String, Object> body) {

        Integer idProces =
                (Integer) ((Map<String, Object>) body.get("proces")).get("idProces");

        hotarareRepository.insertHotarare(
                idProces,
                (String) body.get("tipHotarare"),
                LocalDateTime.parse((String) body.get("dataPronuntare"))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        hotarareRepository.deleteHotarare(id);
    }
}



