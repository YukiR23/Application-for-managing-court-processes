package com.tribunal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
/** Clasa entitate pentru avocat.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Avocati", schema = "dbo")
public class Avocat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAvocat")
    private Integer idAvocat;

    @Column(name = "NumeAvocat", nullable = false, length = 50)
    private String nume;

    @Column(name = "PrenumeAvocat", nullable = false, length = 50)
    private String prenume;

    @Column(name = "Barou", length = 50)
    private String barou;

    @Column(name = "Telefon", length = 12)
    private String telefon;

    @JsonIgnore
    @OneToMany(mappedBy = "avocat")
    private List<Parte> parti = new ArrayList<>();


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

    public String getBarou() {
        return barou;
    }

    public void setBarou(String barou) {
        this.barou = barou;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Parte> getParti() {
        return parti;
    }

    public void setParti(List<Parte> parti) {
        this.parti = parti;
    }
}
