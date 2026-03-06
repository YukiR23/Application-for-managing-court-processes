package com.tribunal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
/** Clasa entitate pentru grefier.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Grefieri", schema = "dbo")
public class Grefier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDGrefier")
    private Integer idGrefier;

    @Column(name = "NumeGrefier", nullable = false)
    private String numeGrefier;

    @Column(name = "PrenumeGrefier", nullable = false)
    private String prenumeGrefier;

    @JsonIgnore
    @OneToMany(mappedBy = "grefier")
    private List<Sedinta> sedinte;


    public Integer getIdGrefier() {
        return idGrefier;
    }

    public void setIdGrefier(Integer idGrefier) {
        this.idGrefier = idGrefier;
    }

    public String getNumeGrefier() {
        return numeGrefier;
    }

    public void setNumeGrefier(String numeGrefier) {
        this.numeGrefier = numeGrefier;
    }

    public String getPrenumeGrefier() {
        return prenumeGrefier;
    }

    public void setPrenumeGrefier(String prenumeGrefier) {
        this.prenumeGrefier = prenumeGrefier;
    }

    public List<Sedinta> getSedinte() {
        return sedinte;
    }

    public void setSedinte(List<Sedinta> sedinte) {
        this.sedinte = sedinte;
    }

}
