package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EAenderung.
 */
@Entity
@Table(name = "e_aenderung")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EAenderung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "schluessel")
    private Integer schluessel;

    @Column(name = "doku_rezept")
    private String dokuRezept;

    @Column(name = "doku_arzt")
    private String dokuArzt;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne
    @JsonIgnoreProperties(value = { "eStatus", "eAenderungs", "lieferungId" }, allowSetters = true)
    private ERezept eRezept;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EAenderung id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSchluessel() {
        return this.schluessel;
    }

    public EAenderung schluessel(Integer schluessel) {
        this.schluessel = schluessel;
        return this;
    }

    public void setSchluessel(Integer schluessel) {
        this.schluessel = schluessel;
    }

    public String getDokuRezept() {
        return this.dokuRezept;
    }

    public EAenderung dokuRezept(String dokuRezept) {
        this.dokuRezept = dokuRezept;
        return this;
    }

    public void setDokuRezept(String dokuRezept) {
        this.dokuRezept = dokuRezept;
    }

    public String getDokuArzt() {
        return this.dokuArzt;
    }

    public EAenderung dokuArzt(String dokuArzt) {
        this.dokuArzt = dokuArzt;
        return this;
    }

    public void setDokuArzt(String dokuArzt) {
        this.dokuArzt = dokuArzt;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public EAenderung datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public ERezept getERezept() {
        return this.eRezept;
    }

    public EAenderung eRezept(ERezept eRezept) {
        this.setERezept(eRezept);
        return this;
    }

    public void setERezept(ERezept eRezept) {
        this.eRezept = eRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EAenderung)) {
            return false;
        }
        return id != null && id.equals(((EAenderung) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EAenderung{" +
            "id=" + getId() +
            ", schluessel=" + getSchluessel() +
            ", dokuRezept='" + getDokuRezept() + "'" +
            ", dokuArzt='" + getDokuArzt() + "'" +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
