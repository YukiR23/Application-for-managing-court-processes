package com.tribunal.repository;

import com.tribunal.model.Proces;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/** Interfață repository pentru procese.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface ProcesRepository extends JpaRepository<Proces, Integer> {

    @Query(
            value = """
        SELECT
            p.IDProces,
            p.NrDosar,
            CAST(p.DataInregistrare AS DATE) AS DataInregistrare,
            p.StadiuProces,
            p.MaterieJuridica,
            p.IDJudecator,
            p.IDProcuror
        FROM Procese p
        ORDER BY p.DataInregistrare DESC
        """,
            nativeQuery = true
    )
    List<Object[]> findAllProcese();

    @Modifying
    @Transactional
    @Query(
            value = """
    INSERT INTO Procese (
        NrDosar,
        MaterieJuridica,
        StadiuProces,
        DataInregistrare,
        IDJudecator,
        IDProcuror
    )
    VALUES (
        :nrDosar,
        :materie,
        :stadiu,
        :data,
        :idJudecator,
        :idProcuror
    )
    """,
            nativeQuery = true
    )
    void insertProces(
            @Param("nrDosar") String nrDosar,
            @Param("materie") String materie,
            @Param("stadiu") String stadiu,
            @Param("data") LocalDate data,
            @Param("idJudecator") Integer idJudecator,
            @Param("idProcuror") Integer idProcuror
    );


    @Modifying
    @Transactional
    @Query(
            value = """
    UPDATE Procese
    SET
        NrDosar = :nrDosar,
        MaterieJuridica = :materie,
        StadiuProces = :stadiu,
        DataInregistrare = :data,
        IDJudecator = :idJudecator,
        IDProcuror = :idProcuror
    WHERE IDProces = :idProces
    """,
            nativeQuery = true
    )
    void updateProces(
            @Param("idProces") Integer idProces,
            @Param("nrDosar") String nrDosar,
            @Param("materie") String materie,
            @Param("stadiu") String stadiu,
            @Param("data") LocalDate data,
            @Param("idJudecator") Integer idJudecator,
            @Param("idProcuror") Integer idProcuror
    );

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM Procese WHERE IDProces = :idProces",
            nativeQuery = true
    )
    void deleteProces(@Param("idProces") Integer idProces);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM Hotarari WHERE IDProces = :idProces",
            nativeQuery = true
    )
    void deleteHotarareByProces(@Param("idProces") Integer idProces);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM Sedinte WHERE IDProces = :idProces",
            nativeQuery = true
    )
    void deleteSedinteByProces(@Param("idProces") Integer idProces);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM Parti WHERE IDProces = :idProces",
            nativeQuery = true
    )
    void deletePartiByProces(@Param("idProces") Integer idProces);

    @Query(
            value = """
        SELECT j.NumeJudecator, j.PrenumeJudecator, j.Sectie
        FROM Procese p
        JOIN Judecatori j ON p.IDJudecator = j.IDJudecator
        WHERE p.IDProces = :idProces
    """,
            nativeQuery = true
    )
    Optional<Object[]> findJudecatorByProces(@Param("idProces") Integer idProces);


    @Query(
            value = """
        SELECT pr.NumeProcuror, pr.PrenumeProcuror, pr.Parchet
        FROM Procese p
        JOIN Procurori pr ON p.IDProcuror = pr.IDProcuror
        WHERE p.IDProces = :idProces
    """,
            nativeQuery = true
    )
    Optional<Object[]> findProcurorByProces(@Param("idProces") Integer idProces);


    @Query(
            value = """
        SELECT
            p.IDProces,
            p.NrDosar
        FROM Procese p
        WHERE (
            SELECT COUNT(*)
            FROM Sedinte s
            WHERE s.IDProces = p.IDProces
        ) >= :min
    """,
            nativeQuery = true
    )
    List<Object[]> proceseCuMinSedinte(@Param("min") int min);

}
