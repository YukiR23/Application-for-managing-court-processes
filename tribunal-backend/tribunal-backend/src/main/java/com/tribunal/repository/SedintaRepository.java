package com.tribunal.repository;

import com.tribunal.model.Sedinta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
/** Interfață repository pentru sedinte.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface SedintaRepository extends JpaRepository<Sedinta, Integer> {

    //  Ședințe pentru un proces
    @Query(
            value = """
    SELECT
        s.IDSedinta,
        CAST(s.DataTermen AS DATE) AS DataTermen,
        s.Ora,
        s.Sala,
        s.Rezultat,
        g.IDGrefier,
        g.NumeGrefier,
        g.PrenumeGrefier
    FROM Sedinte s
    JOIN Grefieri g ON s.IDGrefier = g.IDGrefier
    WHERE s.IDProces = :idProces
    ORDER BY s.DataTermen
    """,
            nativeQuery = true
    )
    List<Object[]> findSedinteByProces(@Param("idProces") Integer idProces);


    @Modifying
    @Transactional
    @Query(
            value = """
        INSERT INTO Sedinte
        (DataTermen, Ora, Sala, Rezultat, IDProces, IDGrefier)
        VALUES
        (:dataTermen, :ora, :sala, :rezultat, :idProces, :idGrefier)
    """,
            nativeQuery = true
    )
    void insertSedinta(
            @Param("dataTermen") LocalDate dataTermen,
            @Param("ora") LocalTime ora,
            @Param("sala") String sala,
            @Param("rezultat") String rezultat,
            @Param("idProces") Integer idProces,
            @Param("idGrefier") Integer idGrefier
    );

    @Modifying
    @Transactional
    @Query(
            value = """
        UPDATE Sedinte
        SET
            DataTermen = :dataTermen,
            Ora = :ora,
            Sala = :sala,
            Rezultat = :rezultat,
            IDGrefier = :idGrefier
        WHERE IDSedinta = :idSedinta
    """,
            nativeQuery = true
    )
    void updateSedinta(
            @Param("idSedinta") Integer idSedinta,
            @Param("dataTermen") LocalDate dataTermen,
            @Param("ora") LocalTime ora,
            @Param("sala") String sala,
            @Param("rezultat") String rezultat,
            @Param("idGrefier") Integer idGrefier
    );

    @Modifying
    @Transactional
    @Query(
            value = """
        DELETE FROM Sedinte
        WHERE IDSedinta = :id
    """,
            nativeQuery = true
    )
    void deleteSedinta(@Param("id") Integer id);


    @Query(value = """
    SELECT
        s.IDSedinta,
        s.DataTermen,
        s.Ora,
        s.Sala,
        s.Rezultat,
        p.NrDosar,
        g.NumeGrefier,
        g.PrenumeGrefier
    FROM Sedinte s
    JOIN Procese p ON s.IDProces = p.IDProces
    JOIN Grefieri g ON s.IDGrefier = g.IDGrefier
    WHERE
        (:data IS NULL OR s.DataTermen = :data)
        AND (:nrDosar IS NULL OR p.NrDosar LIKE CONCAT('%', :nrDosar, '%'))
        AND (
            :faraRezultat = 0
            OR s.Rezultat <> 'finalizata'
        )
    ORDER BY s.DataTermen, s.Ora
""", nativeQuery = true)
    List<Object[]> findAllSedinteFiltrate(
            @Param("data") LocalDate data,
            @Param("nrDosar") String nrDosar,
            @Param("faraRezultat") boolean faraRezultat
    );


    @Query(
            value = """
        SELECT p.NrDosar, COUNT(s.IDSedinta) AS nrSedinte
        FROM Procese p
        JOIN Sedinte s ON p.IDProces = s.IDProces
        GROUP BY p.NrDosar
    """,
            nativeQuery = true
    )
    List<Object[]> countSedintePerProces();


    @Query(value = """
    SELECT
        s.IDSedinta,
        CAST(s.DataTermen AS DATE),
        s.Ora,
        s.Sala,
        p.NrDosar,
        g.NumeGrefier,
        g.PrenumeGrefier
    FROM Sedinte s
    JOIN Procese p ON s.IDProces = p.IDProces
    JOIN Grefieri g ON s.IDGrefier = g.IDGrefier
    WHERE s.IDSedinta IN (
        SELECT s2.IDSedinta
        FROM Sedinte s2
        WHERE s2.Rezultat IS NULL
    )
    ORDER BY s.DataTermen, s.Ora
""", nativeQuery = true)
    List<Object[]> findSedinteFaraRezultat();

    @Query(
            value = """
        SELECT
            s.IDSedinta,
            s.DataTermen,
            s.Ora,
            s.Sala,
            p.NrDosar
        FROM Sedinte s
        JOIN Procese p ON s.IDProces = p.IDProces
        WHERE s.DataTermen = :data
        AND EXISTS (
            SELECT 1
            FROM Hotarari h
            WHERE h.IDProces = p.IDProces
        )
    """,
            nativeQuery = true
    )
    List<Object[]> sedinteImportante(@Param("data") LocalDate data);

}
