package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru judecator.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class JudecatorDTO {
    private Integer idJudecator;
    private String nume;
    private String prenume;

    public Integer getIdJudecator() { return idJudecator; }
    public void setIdJudecator(Integer idJudecator) { this.idJudecator = idJudecator; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }

}

