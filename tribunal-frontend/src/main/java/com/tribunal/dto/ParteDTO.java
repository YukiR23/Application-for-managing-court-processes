package com.tribunal.dto;

/** Obiect de transfer date pentru proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ParteDTO {

    private Integer idParte;

    private Integer idProces;
    private Integer idPersoana;
    private Integer idAvocat; // nullable

    private String numePersoana;
    private String prenumePersoana;

    private String numeAvocat;
    private String prenumeAvocat;

    private String tipParte;


    public Integer getIdParte() {
        return idParte;
    }

    public void setIdParte(Integer idParte) {
        this.idParte = idParte;
    }

    public Integer getIdProces() {
        return idProces;
    }

    public void setIdProces(Integer idProces) {
        this.idProces = idProces;
    }

    public Integer getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(Integer idPersoana) {
        this.idPersoana = idPersoana;
    }

    public Integer getIdAvocat() {
        return idAvocat;
    }

    public void setIdAvocat(Integer idAvocat) {
        this.idAvocat = idAvocat;
    }

    public String getNumePersoana() {
        return numePersoana;
    }

    public void setNumePersoana(String numePersoana) {
        this.numePersoana = numePersoana;
    }

    public String getPrenumePersoana() {
        return prenumePersoana;
    }

    public void setPrenumePersoana(String prenumePersoana) {
        this.prenumePersoana = prenumePersoana;
    }

    public String getNumeAvocat() {
        return numeAvocat;
    }

    public void setNumeAvocat(String numeAvocat) {
        this.numeAvocat = numeAvocat;
    }

    public String getPrenumeAvocat() {
        return prenumeAvocat;
    }

    public void setPrenumeAvocat(String prenumeAvocat) {
        this.prenumeAvocat = prenumeAvocat;
    }

    public String getTipParte() {
        return tipParte;
    }

    public void setTipParte(String tipParte) {
        this.tipParte = tipParte;
    }
}

