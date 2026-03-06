package com.tribunal.service;

import com.tribunal.model.Utilizator;
import com.tribunal.repository.UtilizatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
/** Clasa service pentru logica de business a utilizatorilor.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Service
@RequiredArgsConstructor
public class UtilizatorService {

    private final UtilizatorRepository repo;

    public Optional<String> autentifica(String user, String parola) {

        return repo.loginSql(user, parola)
                .map(row -> (String) row[3]); // Rol
    }
}

