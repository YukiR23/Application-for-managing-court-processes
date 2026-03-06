package com.tribunal.dto;
/** Obiect de transfer date pentru proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class ProcesDTO {

    private Integer idProces;

    private String nrDosar;
    private String materieJuridica;
    private String stadiuProces;
    private String dataInregistrare;

    private Integer idJudecator;
    private Integer idProcuror;

    public Integer getIdProces() {
        return idProces;
    }

    public void setIdProces(Integer idProces) {
        this.idProces = idProces;
    }

    public String getNrDosar() {
        return nrDosar;
    }

    public void setNrDosar(String nrDosar) {
        this.nrDosar = nrDosar;
    }

    public String getMaterieJuridica() {
        return materieJuridica;
    }

    public void setMaterieJuridica(String materieJuridica) {
        this.materieJuridica = materieJuridica;
    }

    public String getStadiuProces() {
        return stadiuProces;
    }

    public void setStadiuProces(String stadiuProces) {
        this.stadiuProces = stadiuProces;
    }

    public String getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(String dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }

    public Integer getIdJudecator() {
        return idJudecator;
    }

    public void setIdJudecator(Integer idJudecator) {
        this.idJudecator = idJudecator;
    }

    public Integer getIdProcuror() {
        return idProcuror;
    }

    public void setIdProcuror(Integer idProcuror) {
        this.idProcuror = idProcuror;
    }
}
