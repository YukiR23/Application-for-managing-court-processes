package com.tribunal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/** Obiect de transfer date pentru toate sedintele.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SedintaToateDTO {

    private Integer idSedinta;
    private String dataTermen;
    private String ora;
    private String sala;
    private String rezultat;
    private String nrDosar;
    private String grefier;

    public Integer getIdSedinta() { return idSedinta; }
    public void setIdSedinta(Integer idSedinta) { this.idSedinta = idSedinta; }

    public String getDataTermen() { return dataTermen; }
    public void setDataTermen(String dataTermen) { this.dataTermen = dataTermen; }

    public String getOra() { return ora; }
    public void setOra(String ora) { this.ora = ora; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getRezultat() { return rezultat; }
    public void setRezultat(String rezultat) { this.rezultat = rezultat; }

    public String getNrDosar() { return nrDosar; }
    public void setNrDosar(String nrDosar) { this.nrDosar = nrDosar; }

    public String getGrefier() { return grefier; }
    public void setGrefier(String grefier) { this.grefier = grefier; }
}
