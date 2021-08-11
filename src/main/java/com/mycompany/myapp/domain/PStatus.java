package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PStatus.
 */
@Entity
@Table(name = "p_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "wert")
    private String wert;

    @Column(name = "beschreibung")
    private String beschreibung;

    @JsonIgnoreProperties(value = { "pStatus", "pRezeptId", "pCharges", "pStatusInfos", "lieferungId" }, allowSetters = true)
    @OneToOne(mappedBy = "pStatus")
    private PRezept pStatusId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PStatus id(Long id) {
        this.id = id;
        return this;
    }

    public String getWert() {
        return this.wert;
    }

    public PStatus wert(String wert) {
        this.wert = wert;
        return this;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public PStatus beschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public PRezept getPStatusId() {
        return this.pStatusId;
    }

    public PStatus pStatusId(PRezept pRezept) {
        this.setPStatusId(pRezept);
        return this;
    }

    public void setPStatusId(PRezept pRezept) {
        if (this.pStatusId != null) {
            this.pStatusId.setPStatus(null);
        }
        if (pRezept != null) {
            pRezept.setPStatus(this);
        }
        this.pStatusId = pRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PStatus)) {
            return false;
        }
        return id != null && id.equals(((PStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PStatus{" +
            "id=" + getId() +
            ", wert='" + getWert() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            "}";
    }
}
