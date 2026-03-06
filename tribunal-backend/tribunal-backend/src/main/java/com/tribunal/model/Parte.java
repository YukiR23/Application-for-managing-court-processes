package com.tribunal.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru partea din proces.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Parti", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDParte")
    private Integer idParte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProces", nullable = false)
    private Proces proces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPersoana", nullable = false)
    private Persoana persoana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAvocat")
    private Avocat avocat; // nullable

    @Column(name = "TipParte", nullable = false)
    private String tipParte;


    public Integer getIdParte() {
        return idParte;
    }

    public void setIdParte(Integer idParte) {
        this.idParte = idParte;
    }

    public Proces getProces() {
        return proces;
    }

    public void setProces(Proces proces) {
        this.proces = proces;
    }

    public Persoana getPersoana() {
        return persoana;
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    public Avocat getAvocat() {
        return avocat;
    }

    public void setAvocat(Avocat avocat) {
        this.avocat = avocat;
    }

    public String getTipParte() {
        return tipParte;
    }

    public void setTipParte(String tipParte) {
        this.tipParte = tipParte;
    }
}

