package com.tribunal.repository;

import com.tribunal.model.Persoana;
import org.springframework.data.jpa.repository.JpaRepository;
/** Interfață repository pentru persoane.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */
public interface PersoanaRepository extends JpaRepository<Persoana, Integer> {
}
