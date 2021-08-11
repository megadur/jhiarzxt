package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EStatus.
 */
@Entity
@Table(name = "e_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "wert")
    private String wert;

    @Column(name = "beschreibung")
    private String beschreibung;

    @JsonIgnoreProperties(value = { "eStatus", "eAenderungs", "lieferungId" }, allowSetters = true)
    @OneToOne(mappedBy = "eStatus")
    private ERezept eStatusId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EStatus id(Long id) {
        this.id = id;
        return this;
    }

    public String getWert() {
        return this.wert;
    }

    public EStatus wert(String wert) {
        this.wert = wert;
        return this;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public EStatus beschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public ERezept getEStatusId() {
        return this.eStatusId;
    }

    public EStatus eStatusId(ERezept eRezept) {
        this.setEStatusId(eRezept);
        return this;
    }

    public void setEStatusId(ERezept eRezept) {
        if (this.eStatusId != null) {
            this.eStatusId.setEStatus(null);
        }
        if (eRezept != null) {
            eRezept.setEStatus(this);
        }
        this.eStatusId = eRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EStatus)) {
            return false;
        }
        return id != null && id.equals(((EStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EStatus{" +
            "id=" + getId() +
            ", wert='" + getWert() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            "}";
    }
}
