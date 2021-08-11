package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Muster16Abg.
 */
@Entity
@Table(name = "muster_16_abg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Muster16Abg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "liefer_dat")
    private String lieferDat;

    @Column(name = "a_periode")
    private String aPeriode;

    @Column(name = "arb_platz")
    private String arbPlatz;

    @Column(name = "avs_id")
    private String avsId;

    @Column(name = "bediener")
    private String bediener;

    @Column(name = "zuzahlung")
    private String zuzahlung;

    @Column(name = "ges_brutto")
    private String gesBrutto;

    @JsonIgnoreProperties(value = { "mRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Muster16Abr mRezeptId;

    @JsonIgnoreProperties(value = { "mRezeptId", "mRezeptId" }, allowSetters = true)
    @OneToOne(mappedBy = "mRezeptId")
    private Muster16Ver mRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Muster16Abg id(Long id) {
        this.id = id;
        return this;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public Muster16Abg apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getLieferDat() {
        return this.lieferDat;
    }

    public Muster16Abg lieferDat(String lieferDat) {
        this.lieferDat = lieferDat;
        return this;
    }

    public void setLieferDat(String lieferDat) {
        this.lieferDat = lieferDat;
    }

    public String getaPeriode() {
        return this.aPeriode;
    }

    public Muster16Abg aPeriode(String aPeriode) {
        this.aPeriode = aPeriode;
        return this;
    }

    public void setaPeriode(String aPeriode) {
        this.aPeriode = aPeriode;
    }

    public String getArbPlatz() {
        return this.arbPlatz;
    }

    public Muster16Abg arbPlatz(String arbPlatz) {
        this.arbPlatz = arbPlatz;
        return this;
    }

    public void setArbPlatz(String arbPlatz) {
        this.arbPlatz = arbPlatz;
    }

    public String getAvsId() {
        return this.avsId;
    }

    public Muster16Abg avsId(String avsId) {
        this.avsId = avsId;
        return this;
    }

    public void setAvsId(String avsId) {
        this.avsId = avsId;
    }

    public String getBediener() {
        return this.bediener;
    }

    public Muster16Abg bediener(String bediener) {
        this.bediener = bediener;
        return this;
    }

    public void setBediener(String bediener) {
        this.bediener = bediener;
    }

    public String getZuzahlung() {
        return this.zuzahlung;
    }

    public Muster16Abg zuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
        return this;
    }

    public void setZuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
    }

    public String getGesBrutto() {
        return this.gesBrutto;
    }

    public Muster16Abg gesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
        return this;
    }

    public void setGesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
    }

    public Muster16Abr getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16Abg mRezeptId(Muster16Abr muster16Abr) {
        this.setMRezeptId(muster16Abr);
        return this;
    }

    public void setMRezeptId(Muster16Abr muster16Abr) {
        this.mRezeptId = muster16Abr;
    }

    public Muster16Ver getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16Abg mRezeptId(Muster16Ver muster16Ver) {
        this.setMRezeptId(muster16Ver);
        return this;
    }

    public void setMRezeptId(Muster16Ver muster16Ver) {
        if (this.mRezeptId != null) {
            this.mRezeptId.setMRezeptId(null);
        }
        if (muster16Ver != null) {
            muster16Ver.setMRezeptId(this);
        }
        this.mRezeptId = muster16Ver;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Muster16Abg)) {
            return false;
        }
        return id != null && id.equals(((Muster16Abg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Muster16Abg{" +
            "id=" + getId() +
            ", apoIk='" + getApoIk() + "'" +
            ", lieferDat='" + getLieferDat() + "'" +
            ", aPeriode='" + getaPeriode() + "'" +
            ", arbPlatz='" + getArbPlatz() + "'" +
            ", avsId='" + getAvsId() + "'" +
            ", bediener='" + getBediener() + "'" +
            ", zuzahlung='" + getZuzahlung() + "'" +
            ", gesBrutto='" + getGesBrutto() + "'" +
            "}";
    }
}
