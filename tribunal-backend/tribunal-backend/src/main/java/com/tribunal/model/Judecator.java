package com.tribunal.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru judecător.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Judecatori", schema = "dbo")
public class Judecator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDJudecator")
    private Integer idJudecator;

    @Column(name = "NumeJudecator", nullable = false)
    private String nume;

    @Column(name = "PrenumeJudecator", nullable = false)
    private String prenume;

    @Column(name = "Sectie", nullable = false)
    private String sectie;

    @JsonIgnore
    @OneToMany(mappedBy = "judecator")
    private List<Proces> procese;


    public Integer getIdJudecator() {
        return idJudecator;
    }

    public void setIdJudecator(Integer idJudecator) {
        this.idJudecator = idJudecator;
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

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public List<Proces> getProcese() {
        return procese;
    }

    public void setProcese(List<Proces> procese) {
        this.procese = procese;
    }
}
