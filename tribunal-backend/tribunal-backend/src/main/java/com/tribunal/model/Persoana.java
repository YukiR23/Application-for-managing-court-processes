package com.tribunal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru persoană.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Persoane", schema = "dbo")
public class Persoana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPersoana")
    private Integer idPersoana;

    @Column(name = "NumePersoana", nullable = false, length = 50)
    private String nume;

    @Column(name = "PrenumePersoana", nullable = false, length = 50)
    private String prenume;

    @Column(name = "CNP", nullable = false, length = 13, unique = true)
    private String cnp;

    @Column(name = "TipPersoana", length = 20)
    private String tipPersoana;

    @JsonIgnore
    @OneToMany(mappedBy = "persoana")
    private List<Parte> parti = new ArrayList<>();


    public Integer getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(Integer idPersoana) {
        this.idPersoana = idPersoana;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getTipPersoana() {
        return tipPersoana;
    }

    public void setTipPersoana(String tipPersoana) {
        this.tipPersoana = tipPersoana;
    }

    public List<Parte> getParti() {
        return parti;
    }

    public void setParti(List<Parte> parti) {
        this.parti = parti;
    }
}
