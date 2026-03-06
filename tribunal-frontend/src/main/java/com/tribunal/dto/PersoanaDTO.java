package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru persoana.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersoanaDTO {

    private Integer idPersoana;
    private String nume;
    private String prenume;

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
}
