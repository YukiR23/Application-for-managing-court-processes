package com.tribunal.model;

import jakarta.persistence.*;
/** Clasa entitate pentru utilizatorul aplicației.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Utilizatori")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUtilizator")
    private Integer id;

    @Column(name = "NumeUtilizator")
    private String numeUtilizator;

    @Column(name = "Parola")
    private String parola;

    @Column(name = "Rol")
    private String rol;

    public Integer getId() { return id; }
    public String getNumeUtilizator() { return numeUtilizator; }
    public String getParola() { return parola; }
    public String getRol() { return rol; }
}
