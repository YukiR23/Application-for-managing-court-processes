package com.tribunal.repository;

import com.tribunal.model.Hotarare;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
/** Interfață repository pentru hotarari.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface HotarareRepository extends JpaRepository<Hotarare, Integer> {

    // SELECT hotărâri după proces
    @Query(
            value = """
            SELECT
                h.IDHotarare,
                h.IDProces,
                h.TipHotarare,
                h.DataPronuntare
            FROM Hotarari h
            WHERE h.IDProces = :idProces
            ORDER BY h.DataPronuntare
        """,
            nativeQuery = true
    )
    List<Object[]> findHotarariByProces(@Param("idProces") Integer idProces);

    @Modifying
    @Transactional
    @Query(
            value = """
            INSERT INTO Hotarari
            (IDProces, TipHotarare, DataPronuntare)
            VALUES
            (:idProces, :tipHotarare, :dataPronuntare)
        """,
            nativeQuery = true
    )
    void insertHotarare(
            @Param("idProces") Integer idProces,
            @Param("tipHotarare") String tipHotarare,
            @Param("dataPronuntare") LocalDateTime dataPronuntare
    );

    @Modifying
    @Transactional
    @Query(
            value = """
            DELETE FROM Hotarari
            WHERE IDHotarare = :idHotarare
        """,
            nativeQuery = true
    )
    void deleteHotarare(@Param("idHotarare") Integer idHotarare);
}
