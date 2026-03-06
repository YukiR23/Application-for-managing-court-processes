package com.tribunal.repository;

import com.tribunal.model.Avocat;
import org.springframework.data.jpa.repository.JpaRepository;
/** Interfață repository pentru  avocați.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public interface AvocatRepository extends JpaRepository<Avocat, Integer> {
}
