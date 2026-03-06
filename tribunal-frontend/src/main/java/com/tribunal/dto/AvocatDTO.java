package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru avocat.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvocatDTO {

    private Integer idAvocat;
    private String nume;
    private String prenume;

    public Integer getIdAvocat() {
        return idAvocat;
    }

    public void setIdAvocat(Integer idAvocat) {
        this.idAvocat = idAvocat;
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
