package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PCharge.
 */
@Entity
@Table(name = "p_charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_charge_id")
    private String pChargeId;

    @Column(name = "p_rezept_id")
    private String pRezeptId;

    @Column(name = "anzahl_applikationen")
    private String anzahlApplikationen;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "chargen_nr")
    private String chargenNr;

    @Column(name = "hersteller_nr")
    private String herstellerNr;

    @Column(name = "hersteller_schluessel")
    private String herstellerSchluessel;

    @Column(name = "herstellungs_datum")
    private String herstellungsDatum;

    @OneToMany(mappedBy = "pCharge")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pCharge" }, allowSetters = true)
    private Set<PWirkstoff> pWirkstoffs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pStatus", "pRezeptId", "pCharges", "pStatusInfos", "lieferungId" }, allowSetters = true)
    private PRezept pRezept;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PCharge id(Long id) {
        this.id = id;
        return this;
    }

    public String getpChargeId() {
        return this.pChargeId;
    }

    public PCharge pChargeId(String pChargeId) {
        this.pChargeId = pChargeId;
        return this;
    }

    public void setpChargeId(String pChargeId) {
        this.pChargeId = pChargeId;
    }

    public String getpRezeptId() {
        return this.pRezeptId;
    }

    public PCharge pRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
        return this;
    }

    public void setpRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
    }

    public String getAnzahlApplikationen() {
        return this.anzahlApplikationen;
    }

    public PCharge anzahlApplikationen(String anzahlApplikationen) {
        this.anzahlApplikationen = anzahlApplikationen;
        return this;
    }

    public void setAnzahlApplikationen(String anzahlApplikationen) {
        this.anzahlApplikationen = anzahlApplikationen;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public PCharge apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getChargenNr() {
        return this.chargenNr;
    }

    public PCharge chargenNr(String chargenNr) {
        this.chargenNr = chargenNr;
        return this;
    }

    public void setChargenNr(String chargenNr) {
        this.chargenNr = chargenNr;
    }

    public String getHerstellerNr() {
        return this.herstellerNr;
    }

    public PCharge herstellerNr(String herstellerNr) {
        this.herstellerNr = herstellerNr;
        return this;
    }

    public void setHerstellerNr(String herstellerNr) {
        this.herstellerNr = herstellerNr;
    }

    public String getHerstellerSchluessel() {
        return this.herstellerSchluessel;
    }

    public PCharge herstellerSchluessel(String herstellerSchluessel) {
        this.herstellerSchluessel = herstellerSchluessel;
        return this;
    }

    public void setHerstellerSchluessel(String herstellerSchluessel) {
        this.herstellerSchluessel = herstellerSchluessel;
    }

    public String getHerstellungsDatum() {
        return this.herstellungsDatum;
    }

    public PCharge herstellungsDatum(String herstellungsDatum) {
        this.herstellungsDatum = herstellungsDatum;
        return this;
    }

    public void setHerstellungsDatum(String herstellungsDatum) {
        this.herstellungsDatum = herstellungsDatum;
    }

    public Set<PWirkstoff> getPWirkstoffs() {
        return this.pWirkstoffs;
    }

    public PCharge pWirkstoffs(Set<PWirkstoff> pWirkstoffs) {
        this.setPWirkstoffs(pWirkstoffs);
        return this;
    }

    public PCharge addPWirkstoff(PWirkstoff pWirkstoff) {
        this.pWirkstoffs.add(pWirkstoff);
        pWirkstoff.setPCharge(this);
        return this;
    }

    public PCharge removePWirkstoff(PWirkstoff pWirkstoff) {
        this.pWirkstoffs.remove(pWirkstoff);
        pWirkstoff.setPCharge(null);
        return this;
    }

    public void setPWirkstoffs(Set<PWirkstoff> pWirkstoffs) {
        if (this.pWirkstoffs != null) {
            this.pWirkstoffs.forEach(i -> i.setPCharge(null));
        }
        if (pWirkstoffs != null) {
            pWirkstoffs.forEach(i -> i.setPCharge(this));
        }
        this.pWirkstoffs = pWirkstoffs;
    }

    public PRezept getPRezept() {
        return this.pRezept;
    }

    public PCharge pRezept(PRezept pRezept) {
        this.setPRezept(pRezept);
        return this;
    }

    public void setPRezept(PRezept pRezept) {
        this.pRezept = pRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PCharge)) {
            return false;
        }
        return id != null && id.equals(((PCharge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PCharge{" +
            "id=" + getId() +
            ", pChargeId='" + getpChargeId() + "'" +
            ", pRezeptId='" + getpRezeptId() + "'" +
            ", anzahlApplikationen='" + getAnzahlApplikationen() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            ", chargenNr='" + getChargenNr() + "'" +
            ", herstellerNr='" + getHerstellerNr() + "'" +
            ", herstellerSchluessel='" + getHerstellerSchluessel() + "'" +
            ", herstellungsDatum='" + getHerstellungsDatum() + "'" +
            "}";
    }
}
