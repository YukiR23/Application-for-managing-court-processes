package com.tribunal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru ședință de judecată.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Sedinte", schema = "dbo")
public class Sedinta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSedinta")
    private Integer idSedinta;

    @ManyToOne
    @JoinColumn(name = "IDGrefier", nullable = false)
    private Grefier grefier;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProces", nullable = false)
    private Proces proces;

    @Column(name = "DataTermen", nullable = false)
    private LocalDate dataTermen;

    @Column(name = "Ora", nullable = false)
    private LocalTime ora;

    @Column(name = "Sala")
    private String sala;

    @Column(name = "Rezultat")
    private String rezultat;

    public Integer getIdSedinta() {
        return idSedinta;
    }

    public void setIdSedinta(Integer idSedinta) {
        this.idSedinta = idSedinta;
    }

    public Grefier getGrefier() {
        return grefier;
    }

    public void setGrefier(Grefier grefier) {
        this.grefier = grefier;
    }

    public Proces getProces() {
        return proces;
    }

    public void setProces(Proces proces) {
        this.proces = proces;
    }

    public LocalDate getDataTermen() {
        return dataTermen;
    }

    public void setDataTermen(LocalDate dataTermen) {
        this.dataTermen = dataTermen;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }
}
