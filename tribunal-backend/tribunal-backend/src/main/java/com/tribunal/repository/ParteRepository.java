package com.tribunal.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tribunal.model.Parte;

import java.util.List;
/** Interfață repository pentru parți.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface ParteRepository extends JpaRepository<Parte, Integer> {

    //  Părți pentru un proces
        @Query(
                value = """
            SELECT
                pa.IDParte,
                pa.TipParte,

                pe.IDPersoana,
                pe.NumePersoana,
                pe.PrenumePersoana,

                av.IDAvocat,
                av.NumeAvocat,
                av.PrenumeAvocat
            FROM Parti pa
            JOIN Persoane pe ON pa.IDPersoana = pe.IDPersoana
            LEFT JOIN Avocati av ON pa.IDAvocat = av.IDAvocat
            WHERE pa.IDProces = :idProces
            ORDER BY pa.IDParte
        """,
                nativeQuery = true
        )
        List<Object[]> findPartiByProces(@Param("idProces") Integer idProces);

    @Modifying
    @Transactional
    @Query(
            value = """
        INSERT INTO Parti
            (IDProces, IDPersoana, IDAvocat, TipParte)
        VALUES
            (:idProces, :idPersoana, :idAvocat, :tipParte)
        """,
            nativeQuery = true
    )
    void insertParte(
            @Param("idProces") Integer idProces,
            @Param("idPersoana") Integer idPersoana,
            @Param("idAvocat") Integer idAvocat,
            @Param("tipParte") String tipParte
    );

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM Parti WHERE IDParte = :id",
            nativeQuery = true
    )
    void deleteParte(@Param("id") Integer id);
}
