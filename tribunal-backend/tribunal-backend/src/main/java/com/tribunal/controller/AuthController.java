package com.tribunal.controller;

import com.tribunal.repository.UtilizatorRepository;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/** Clasa pentru autentificarea utilizatorilor și gestionarea login-ului.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilizatorRepository repo;

    public AuthController(UtilizatorRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {

        String user = req.get("username");
        String pass = req.get("password");

        return repo.loginSql(user, pass)
                .map(r -> {
                    Object[] row = (Object[]) r[0];

                    return Map.of(
                            "success", (Object) true,
                            "rol", row[3].toString()
                    );
                })
                .orElse(Map.of(
                        "success", (Object) false
                ));
    }
}
