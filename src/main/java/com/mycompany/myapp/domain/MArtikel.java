package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MArtikel.
 */
@Entity
@Table(name = "m_artikel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MArtikel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "m_artikel_id")
    private String mArtikelId;

    @Column(name = "m_rezept_id")
    private String mRezeptId;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "autidem")
    private String autidem;

    @Column(name = "faktor")
    private String faktor;

    @Column(name = "hilfsmittel_nr")
    private String hilfsmittelNr;

    @Column(name = "muster_16_id")
    private String muster16Id;

    @Column(name = "pos_nr")
    private String posNr;

    @Column(name = "pzn")
    private String pzn;

    @Column(name = "taxe")
    private String taxe;

    @Column(name = "v_zeile")
    private String vZeile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "m16Status", "mRezeptId", "mArtikels", "lieferungId" }, allowSetters = true)
    private Muster16 muster16;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MArtikel id(Long id) {
        this.id = id;
        return this;
    }

    public String getmArtikelId() {
        return this.mArtikelId;
    }

    public MArtikel mArtikelId(String mArtikelId) {
        this.mArtikelId = mArtikelId;
        return this;
    }

    public void setmArtikelId(String mArtikelId) {
        this.mArtikelId = mArtikelId;
    }

    public String getmRezeptId() {
        return this.mRezeptId;
    }

    public MArtikel mRezeptId(String mRezeptId) {
        this.mRezeptId = mRezeptId;
        return this;
    }

    public void setmRezeptId(String mRezeptId) {
        this.mRezeptId = mRezeptId;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public MArtikel apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getAutidem() {
        return this.autidem;
    }

    public MArtikel autidem(String autidem) {
        this.autidem = autidem;
        return this;
    }

    public void setAutidem(String autidem) {
        this.autidem = autidem;
    }

    public String getFaktor() {
        return this.faktor;
    }

    public MArtikel faktor(String faktor) {
        this.faktor = faktor;
        return this;
    }

    public void setFaktor(String faktor) {
        this.faktor = faktor;
    }

    public String getHilfsmittelNr() {
        return this.hilfsmittelNr;
    }

    public MArtikel hilfsmittelNr(String hilfsmittelNr) {
        this.hilfsmittelNr = hilfsmittelNr;
        return this;
    }

    public void setHilfsmittelNr(String hilfsmittelNr) {
        this.hilfsmittelNr = hilfsmittelNr;
    }

    public String getMuster16Id() {
        return this.muster16Id;
    }

    public MArtikel muster16Id(String muster16Id) {
        this.muster16Id = muster16Id;
        return this;
    }

    public void setMuster16Id(String muster16Id) {
        this.muster16Id = muster16Id;
    }

    public String getPosNr() {
        return this.posNr;
    }

    public MArtikel posNr(String posNr) {
        this.posNr = posNr;
        return this;
    }

    public void setPosNr(String posNr) {
        this.posNr = posNr;
    }

    public String getPzn() {
        return this.pzn;
    }

    public MArtikel pzn(String pzn) {
        this.pzn = pzn;
        return this;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getTaxe() {
        return this.taxe;
    }

    public MArtikel taxe(String taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(String taxe) {
        this.taxe = taxe;
    }

    public String getvZeile() {
        return this.vZeile;
    }

    public MArtikel vZeile(String vZeile) {
        this.vZeile = vZeile;
        return this;
    }

    public void setvZeile(String vZeile) {
        this.vZeile = vZeile;
    }

    public Muster16 getMuster16() {
        return this.muster16;
    }

    public MArtikel muster16(Muster16 muster16) {
        this.setMuster16(muster16);
        return this;
    }

    public void setMuster16(Muster16 muster16) {
        this.muster16 = muster16;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MArtikel)) {
            return false;
        }
        return id != null && id.equals(((MArtikel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MArtikel{" +
            "id=" + getId() +
            ", mArtikelId='" + getmArtikelId() + "'" +
            ", mRezeptId='" + getmRezeptId() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            ", autidem='" + getAutidem() + "'" +
            ", faktor='" + getFaktor() + "'" +
            ", hilfsmittelNr='" + getHilfsmittelNr() + "'" +
            ", muster16Id='" + getMuster16Id() + "'" +
            ", posNr='" + getPosNr() + "'" +
            ", pzn='" + getPzn() + "'" +
            ", taxe='" + getTaxe() + "'" +
            ", vZeile='" + getvZeile() + "'" +
            "}";
    }
}
