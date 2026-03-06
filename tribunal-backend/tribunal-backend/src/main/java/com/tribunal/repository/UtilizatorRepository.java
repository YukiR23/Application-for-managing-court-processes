package com.tribunal.repository;

import com.tribunal.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
/** Interfață repository pentru utilizatori.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface UtilizatorRepository extends JpaRepository<Utilizator, Integer> {

    @Query(
            value = """
        SELECT
            IDUtilizator,
            NumeUtilizator,
            Parola,
            Rol
        FROM Utilizatori
        WHERE NumeUtilizator = :user
          AND Parola = :pass
    """,
            nativeQuery = true
    )
    Optional<Object[]> loginSql(
            @Param("user") String username,
            @Param("pass") String password
    );

}
