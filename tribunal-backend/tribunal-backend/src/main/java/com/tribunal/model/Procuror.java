package com.tribunal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
/** Clasa entitate pentru procuror.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Entity
@Table(name = "Procurori", schema = "dbo")
public class Procuror {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDProcuror")
    private Integer idProcuror;

    @Column(name = "NumeProcuror", nullable = false)
    private String nume;

    @Column(name = "PrenumeProcuror", nullable = false)
    private String prenume;

    @Column(name = "Parchet")
    private String parchet;

    @JsonIgnore
    @OneToMany(mappedBy = "procuror")
    private List<Proces> procese;

    public Integer getIdProcuror() {
        return idProcuror;
    }

    public void setIdProcuror(Integer idProcuror) {
        this.idProcuror = idProcuror;
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

    public String getParchet() {
        return parchet;
    }

    public void setParchet(String parchet) {
        this.parchet = parchet;
    }

    public List<Proces> getProcese() {
        return procese;
    }

    public void setProcese(List<Proces> procese) {
        this.procese = procese;
    }
}
