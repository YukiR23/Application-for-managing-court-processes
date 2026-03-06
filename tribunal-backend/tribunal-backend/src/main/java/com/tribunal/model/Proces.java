package com.tribunal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
/** Clasa entitate pentru proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Procese", schema = "dbo")
public class Proces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDProces")
    private Integer idProces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDJudecator", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Judecator judecator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProcuror")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Procuror procuror;

    @OneToOne(mappedBy = "proces")
    private Hotarare hotarare;

    @OneToMany(mappedBy = "proces")
    @JsonIgnore
    private List<Sedinta> sedinte;

    @OneToMany(mappedBy = "proces")
    @JsonIgnore
    private List<Parte> parti;

    @Column(name = "NrDosar", nullable = false, unique = true)
    private String nrDosar;

    @Column(name = "DataInregistrare", nullable = false)
    private LocalDate dataInregistrare;

    @Column(name = "StadiuProces", nullable = false)
    private String stadiuProces;

    @Column(name = "MaterieJuridica", nullable = false)
    private String materieJuridica;

    public Integer getIdProces() {
        return idProces;
    }

    public void setIdProces(Integer idProces) {
        this.idProces = idProces;
    }

    public Judecator getJudecator() {
        return judecator;
    }

    public void setJudecator(Judecator judecator) {
        this.judecator = judecator;
    }

    public Procuror getProcuror() {
        return procuror;
    }

    public void setProcuror(Procuror procuror) {
        this.procuror = procuror;
    }

    public List<Sedinta> getSedinte() {
        return sedinte;
    }

    public void setSedinte(List<Sedinta> sedinte) {
        this.sedinte = sedinte;
    }

    public Hotarare getHotarare() {
        return hotarare;
    }

    public void setHotarare(Hotarare hotarare) {
        this.hotarare = hotarare;
    }

    public List<Parte> getParti() {
        return parti;
    }

    public void setParti(List<Parte> parti) {
        this.parti = parti;
    }

    public String getNrDosar() {
        return nrDosar;
    }

    public void setNrDosar(String nrDosar) {
        this.nrDosar = nrDosar;
    }

    public LocalDate getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(LocalDate dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }

    public String getStadiuProces() {
        return stadiuProces;
    }

    public void setStadiuProces(String stadiuProces) {
        this.stadiuProces = stadiuProces;
    }

    public String getMaterieJuridica() {
        return materieJuridica;
    }

    public void setMaterieJuridica(String materieJuridica) {
        this.materieJuridica = materieJuridica;
    }
}


