package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Lieferung.
 */
@Entity
@Table(name = "lieferung")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lieferung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "i_d")
    private String iD;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "apo_ik")
    private String apoIk;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lieferungs", "rechenzentrums" }, allowSetters = true)
    private Apotheke apotheke;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lieferung id(Long id) {
        this.id = id;
        return this;
    }

    public String getiD() {
        return this.iD;
    }

    public Lieferung iD(String iD) {
        this.iD = iD;
        return this;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Lieferung datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public Lieferung apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public Apotheke getApotheke() {
        return this.apotheke;
    }

    public Lieferung apotheke(Apotheke apotheke) {
        this.setApotheke(apotheke);
        return this;
    }

    public void setApotheke(Apotheke apotheke) {
        this.apotheke = apotheke;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lieferung)) {
            return false;
        }
        return id != null && id.equals(((Lieferung) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lieferung{" +
            "id=" + getId() +
            ", iD='" + getiD() + "'" +
            ", datum='" + getDatum() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            "}";
    }
}
