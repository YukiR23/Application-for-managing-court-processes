package com.tribunal.dto;
/** Obiect de transfer date pentru hotarare.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

public class HotarareDTO {

    private Integer idHotarare;
    private Integer idProces;

    private String dataPronuntare;
    private String tipHotarare;

    public Integer getIdHotarare() {
        return idHotarare;
    }

    public void setIdHotarare(Integer idHotarare) {
        this.idHotarare = idHotarare;
    }

    public Integer getIdProces() {
        return idProces;
    }

    public void setIdProces(Integer idProces) {
        this.idProces = idProces;
    }

    public String getDataPronuntare() {
        return dataPronuntare;
    }

    public void setDataPronuntare(String dataPronuntare) {
        this.dataPronuntare = dataPronuntare;
    }

    public String getTipHotarare() {
        return tipHotarare;
    }

    public void setTipHotarare(String tipHotarare) {
        this.tipHotarare = tipHotarare;
    }
}
