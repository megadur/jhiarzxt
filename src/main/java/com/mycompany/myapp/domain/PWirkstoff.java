package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PWirkstoff.
 */
@Entity
@Table(name = "p_wirkstoff")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PWirkstoff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_wirkstoff_id")
    private String pWirkstoffId;

    @Column(name = "p_charge_id")
    private String pChargeId;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "faktor")
    private String faktor;

    @Column(name = "faktor_kennzeichen")
    private String faktorKennzeichen;

    @Column(name = "notiz")
    private String notiz;

    @Column(name = "p_pos_nr")
    private String pPosNr;

    @Column(name = "preis_kennzeichen")
    private String preisKennzeichen;

    @Column(name = "pzn")
    private String pzn;

    @Column(name = "taxe")
    private String taxe;

    @Column(name = "wirkstoff_name")
    private String wirkstoffName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pWirkstoffs", "pRezept" }, allowSetters = true)
    private PCharge pCharge;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PWirkstoff id(Long id) {
        this.id = id;
        return this;
    }

    public String getpWirkstoffId() {
        return this.pWirkstoffId;
    }

    public PWirkstoff pWirkstoffId(String pWirkstoffId) {
        this.pWirkstoffId = pWirkstoffId;
        return this;
    }

    public void setpWirkstoffId(String pWirkstoffId) {
        this.pWirkstoffId = pWirkstoffId;
    }

    public String getpChargeId() {
        return this.pChargeId;
    }

    public PWirkstoff pChargeId(String pChargeId) {
        this.pChargeId = pChargeId;
        return this;
    }

    public void setpChargeId(String pChargeId) {
        this.pChargeId = pChargeId;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public PWirkstoff apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getFaktor() {
        return this.faktor;
    }

    public PWirkstoff faktor(String faktor) {
        this.faktor = faktor;
        return this;
    }

    public void setFaktor(String faktor) {
        this.faktor = faktor;
    }

    public String getFaktorKennzeichen() {
        return this.faktorKennzeichen;
    }

    public PWirkstoff faktorKennzeichen(String faktorKennzeichen) {
        this.faktorKennzeichen = faktorKennzeichen;
        return this;
    }

    public void setFaktorKennzeichen(String faktorKennzeichen) {
        this.faktorKennzeichen = faktorKennzeichen;
    }

    public String getNotiz() {
        return this.notiz;
    }

    public PWirkstoff notiz(String notiz) {
        this.notiz = notiz;
        return this;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public String getpPosNr() {
        return this.pPosNr;
    }

    public PWirkstoff pPosNr(String pPosNr) {
        this.pPosNr = pPosNr;
        return this;
    }

    public void setpPosNr(String pPosNr) {
        this.pPosNr = pPosNr;
    }

    public String getPreisKennzeichen() {
        return this.preisKennzeichen;
    }

    public PWirkstoff preisKennzeichen(String preisKennzeichen) {
        this.preisKennzeichen = preisKennzeichen;
        return this;
    }

    public void setPreisKennzeichen(String preisKennzeichen) {
        this.preisKennzeichen = preisKennzeichen;
    }

    public String getPzn() {
        return this.pzn;
    }

    public PWirkstoff pzn(String pzn) {
        this.pzn = pzn;
        return this;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getTaxe() {
        return this.taxe;
    }

    public PWirkstoff taxe(String taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(String taxe) {
        this.taxe = taxe;
    }

    public String getWirkstoffName() {
        return this.wirkstoffName;
    }

    public PWirkstoff wirkstoffName(String wirkstoffName) {
        this.wirkstoffName = wirkstoffName;
        return this;
    }

    public void setWirkstoffName(String wirkstoffName) {
        this.wirkstoffName = wirkstoffName;
    }

    public PCharge getPCharge() {
        return this.pCharge;
    }

    public PWirkstoff pCharge(PCharge pCharge) {
        this.setPCharge(pCharge);
        return this;
    }

    public void setPCharge(PCharge pCharge) {
        this.pCharge = pCharge;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PWirkstoff)) {
            return false;
        }
        return id != null && id.equals(((PWirkstoff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PWirkstoff{" +
            "id=" + getId() +
            ", pWirkstoffId='" + getpWirkstoffId() + "'" +
            ", pChargeId='" + getpChargeId() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            ", faktor='" + getFaktor() + "'" +
            ", faktorKennzeichen='" + getFaktorKennzeichen() + "'" +
            ", notiz='" + getNotiz() + "'" +
            ", pPosNr='" + getpPosNr() + "'" +
            ", preisKennzeichen='" + getPreisKennzeichen() + "'" +
            ", pzn='" + getPzn() + "'" +
            ", taxe='" + getTaxe() + "'" +
            ", wirkstoffName='" + getWirkstoffName() + "'" +
            "}";
    }
}
