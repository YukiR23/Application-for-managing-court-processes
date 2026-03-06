package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru procuror.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcurorDTO {
    private Integer idProcuror;
    private String nume;
    private String prenume;

    public Integer getIdProcuror() { return idProcuror; }
    public void setIdProcuror(Integer idProcuror) { this.idProcuror = idProcuror; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }

}
