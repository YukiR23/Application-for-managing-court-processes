package com.tribunal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru hotărâre judecătorească.
 * @author Buterez Daniela-Georgiana
 * @version 11 ianuarie 2026
 */

@Entity
@Table(name = "Hotarari", schema = "dbo")
public class Hotarare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDHotarare")
    private Integer idHotarare;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "IDProces", nullable = true, unique = true)
    private Proces proces;

    @Column(name = "DataPronuntare", nullable = false)
    private LocalDateTime dataPronuntare;

    @Column(name = "TipHotarare", nullable = false, length = 50)
    private String tipHotarare;


    public Integer getIdHotarare() {
        return idHotarare;
    }

    public void setIdHotarare(Integer idHotarare) {
        this.idHotarare = idHotarare;
    }

    public Proces getProces() {
        return proces;
    }

    public void setProces(Proces proces) {
        this.proces = proces;
    }

    public LocalDateTime getDataPronuntare() {
        return dataPronuntare;
    }

    public void setDataPronuntare(LocalDateTime dataPronuntare) {
        this.dataPronuntare = dataPronuntare;
    }

    public String getTipHotarare() {
        return tipHotarare;
    }

    public void setTipHotarare(String tipHotarare) {
        this.tipHotarare = tipHotarare;
    }
}
