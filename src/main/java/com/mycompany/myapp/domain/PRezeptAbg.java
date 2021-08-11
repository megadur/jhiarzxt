package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PRezeptAbg.
 */
@Entity
@Table(name = "p_rezept_abg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PRezeptAbg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_rezept_id")
    private String pRezeptId;

    @Column(name = "geb_frei")
    private String gebFrei;

    @Column(name = "ges_brutto")
    private String gesBrutto;

    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "k_art")
    private String kArt;

    @Column(name = "noctu")
    private String noctu;

    @Column(name = "p_rezept_typ")
    private String pRezeptTyp;

    @Column(name = "r_typ")
    private String rTyp;

    @Column(name = "sonstige")
    private String sonstige;

    @Column(name = "spr_st_bedarf")
    private String sprStBedarf;

    @Column(name = "ver_dat")
    private String verDat;

    @Column(name = "vk_gueltig_bis")
    private String vkGueltigBis;

    @Column(name = "zuzahlung")
    private String zuzahlung;

    @JsonIgnoreProperties(value = { "pRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PRezeptAbr pRezeptId;

    @JsonIgnoreProperties(value = { "pRezeptId", "pRezeptId" }, allowSetters = true)
    @OneToOne(mappedBy = "pRezeptId")
    private PRezeptVer pRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PRezeptAbg id(Long id) {
        this.id = id;
        return this;
    }

    public String getpRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptAbg pRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
        return this;
    }

    public void setpRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
    }

    public String getGebFrei() {
        return this.gebFrei;
    }

    public PRezeptAbg gebFrei(String gebFrei) {
        this.gebFrei = gebFrei;
        return this;
    }

    public void setGebFrei(String gebFrei) {
        this.gebFrei = gebFrei;
    }

    public String getGesBrutto() {
        return this.gesBrutto;
    }

    public PRezeptAbg gesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
        return this;
    }

    public void setGesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public PRezeptAbg hashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getkArt() {
        return this.kArt;
    }

    public PRezeptAbg kArt(String kArt) {
        this.kArt = kArt;
        return this;
    }

    public void setkArt(String kArt) {
        this.kArt = kArt;
    }

    public String getNoctu() {
        return this.noctu;
    }

    public PRezeptAbg noctu(String noctu) {
        this.noctu = noctu;
        return this;
    }

    public void setNoctu(String noctu) {
        this.noctu = noctu;
    }

    public String getpRezeptTyp() {
        return this.pRezeptTyp;
    }

    public PRezeptAbg pRezeptTyp(String pRezeptTyp) {
        this.pRezeptTyp = pRezeptTyp;
        return this;
    }

    public void setpRezeptTyp(String pRezeptTyp) {
        this.pRezeptTyp = pRezeptTyp;
    }

    public String getrTyp() {
        return this.rTyp;
    }

    public PRezeptAbg rTyp(String rTyp) {
        this.rTyp = rTyp;
        return this;
    }

    public void setrTyp(String rTyp) {
        this.rTyp = rTyp;
    }

    public String getSonstige() {
        return this.sonstige;
    }

    public PRezeptAbg sonstige(String sonstige) {
        this.sonstige = sonstige;
        return this;
    }

    public void setSonstige(String sonstige) {
        this.sonstige = sonstige;
    }

    public String getSprStBedarf() {
        return this.sprStBedarf;
    }

    public PRezeptAbg sprStBedarf(String sprStBedarf) {
        this.sprStBedarf = sprStBedarf;
        return this;
    }

    public void setSprStBedarf(String sprStBedarf) {
        this.sprStBedarf = sprStBedarf;
    }

    public String getVerDat() {
        return this.verDat;
    }

    public PRezeptAbg verDat(String verDat) {
        this.verDat = verDat;
        return this;
    }

    public void setVerDat(String verDat) {
        this.verDat = verDat;
    }

    public String getVkGueltigBis() {
        return this.vkGueltigBis;
    }

    public PRezeptAbg vkGueltigBis(String vkGueltigBis) {
        this.vkGueltigBis = vkGueltigBis;
        return this;
    }

    public void setVkGueltigBis(String vkGueltigBis) {
        this.vkGueltigBis = vkGueltigBis;
    }

    public String getZuzahlung() {
        return this.zuzahlung;
    }

    public PRezeptAbg zuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
        return this;
    }

    public void setZuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
    }

    public PRezeptAbr getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptAbg pRezeptId(PRezeptAbr pRezeptAbr) {
        this.setPRezeptId(pRezeptAbr);
        return this;
    }

    public void setPRezeptId(PRezeptAbr pRezeptAbr) {
        this.pRezeptId = pRezeptAbr;
    }

    public PRezeptVer getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptAbg pRezeptId(PRezeptVer pRezeptVer) {
        this.setPRezeptId(pRezeptVer);
        return this;
    }

    public void setPRezeptId(PRezeptVer pRezeptVer) {
        if (this.pRezeptId != null) {
            this.pRezeptId.setPRezeptId(null);
        }
        if (pRezeptVer != null) {
            pRezeptVer.setPRezeptId(this);
        }
        this.pRezeptId = pRezeptVer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PRezeptAbg)) {
            return false;
        }
        return id != null && id.equals(((PRezeptAbg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PRezeptAbg{" +
            "id=" + getId() +
            ", pRezeptId='" + getpRezeptId() + "'" +
            ", gebFrei='" + getGebFrei() + "'" +
            ", gesBrutto='" + getGesBrutto() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            ", kArt='" + getkArt() + "'" +
            ", noctu='" + getNoctu() + "'" +
            ", pRezeptTyp='" + getpRezeptTyp() + "'" +
            ", rTyp='" + getrTyp() + "'" +
            ", sonstige='" + getSonstige() + "'" +
            ", sprStBedarf='" + getSprStBedarf() + "'" +
            ", verDat='" + getVerDat() + "'" +
            ", vkGueltigBis='" + getVkGueltigBis() + "'" +
            ", zuzahlung='" + getZuzahlung() + "'" +
            "}";
    }
}
