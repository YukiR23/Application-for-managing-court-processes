package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru sedinta.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SedintaDTO {

    private Integer idSedinta;
    private Integer idProces;
    private Integer idGrefier;
    private String numeGrefier;
    private String prenumeGrefier;
    private String dataTermen;
    private String ora;
    private String sala;
    private String rezultat;

    public Integer getIdSedinta() {
        return idSedinta;
    }

    public void setIdSedinta(Integer idSedinta) {
        this.idSedinta = idSedinta;
    }

    public Integer getIdProces() {
        return idProces;
    }

    public void setIdProces(Integer idProces) {
        this.idProces = idProces;
    }


    public Integer getIdGrefier() {
        return idGrefier;
    }

    public void setIdGrefier(Integer idGrefier) {
        this.idGrefier = idGrefier;

    }
    public String getNumeGrefier() { return numeGrefier; }
    public void setNumeGrefier(String numeGrefier) { this.numeGrefier = numeGrefier; }

    public String getPrenumeGrefier() { return prenumeGrefier; }
    public void setPrenumeGrefier(String prenumeGrefier) { this.prenumeGrefier = prenumeGrefier; }

    public String getDataTermen() {
        return dataTermen;
    }

    public void setDataTermen(String dataTermen) {
        this.dataTermen = dataTermen;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
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

    @Override
    public String toString() {
        return dataTermen + " " + ora + " (" + sala + ")";
    }


}
