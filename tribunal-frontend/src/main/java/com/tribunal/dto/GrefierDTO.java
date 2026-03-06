package com.tribunal.dto;
/** Obiect de transfer date pentru grefier.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class GrefierDTO {
    private Integer idGrefier;
    private String numeGrefier;
    private String prenumeGrefier;

    public Integer getIdGrefier() { return idGrefier; }
    public void setIdGrefier(Integer idGrefier) { this.idGrefier = idGrefier; }

    public String getNumeGrefier() { return numeGrefier; }
    public void setNumeGrefier(String numeGrefier) { this.numeGrefier = numeGrefier; }

    public String getPrenumeGrefier() { return prenumeGrefier; }
    public void setPrenumeGrefier(String prenumeGrefier) { this.prenumeGrefier = prenumeGrefier; }

    @Override
    public String toString() {
        return numeGrefier + " " + prenumeGrefier;
    }
}
