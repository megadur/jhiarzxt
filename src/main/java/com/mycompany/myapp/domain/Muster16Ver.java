package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Muster16Ver.
 */
@Entity
@Table(name = "muster_16_ver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Muster16Ver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "a_unfall")
    private String aUnfall;

    @Column(name = "ab_datum")
    private String abDatum;

    @Column(name = "vrs_nr")
    private String vrsNr;

    @Column(name = "vrtrgs_arzt_nr")
    private String vrtrgsArztNr;

    @Column(name = "spr_st_bedarf")
    private String sprStBedarf;

    @Column(name = "unfall")
    private String unfall;

    @Column(name = "unfall_tag")
    private String unfallTag;

    @Column(name = "v_geb")
    private String vGeb;

    @Column(name = "v_stat")
    private String vStat;

    @Column(name = "ver_dat")
    private String verDat;

    @Column(name = "k_name")
    private String kName;

    @Column(name = "kk_ik")
    private String kkIk;

    @Column(name = "la_nr")
    private String laNr;

    @Column(name = "noctu")
    private String noctu;

    @Column(name = "hilf")
    private String hilf;

    @Column(name = "impf")
    private String impf;

    @Column(name = "k_art")
    private String kArt;

    @Column(name = "r_typ")
    private String rTyp;

    @Column(name = "rezept_typ")
    private String rezeptTyp;

    @Column(name = "bgr_pfl")
    private String bgrPfl;

    @Column(name = "bvg")
    private String bvg;

    @Column(name = "eig_bet")
    private String eigBet;

    @Column(name = "geb_frei")
    private String gebFrei;

    @Column(name = "sonstige")
    private String sonstige;

    @Column(name = "vk_gueltig_bis")
    private String vkGueltigBis;

    @JsonIgnoreProperties(value = { "mRezeptId", "mRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Muster16Abg mRezeptId;

    @JsonIgnoreProperties(value = { "m16Status", "mRezeptId", "mArtikels", "lieferungId" }, allowSetters = true)
    @OneToOne(mappedBy = "mRezeptId")
    private Muster16 mRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Muster16Ver id(Long id) {
        this.id = id;
        return this;
    }

    public String getaUnfall() {
        return this.aUnfall;
    }

    public Muster16Ver aUnfall(String aUnfall) {
        this.aUnfall = aUnfall;
        return this;
    }

    public void setaUnfall(String aUnfall) {
        this.aUnfall = aUnfall;
    }

    public String getAbDatum() {
        return this.abDatum;
    }

    public Muster16Ver abDatum(String abDatum) {
        this.abDatum = abDatum;
        return this;
    }

    public void setAbDatum(String abDatum) {
        this.abDatum = abDatum;
    }

    public String getVrsNr() {
        return this.vrsNr;
    }

    public Muster16Ver vrsNr(String vrsNr) {
        this.vrsNr = vrsNr;
        return this;
    }

    public void setVrsNr(String vrsNr) {
        this.vrsNr = vrsNr;
    }

    public String getVrtrgsArztNr() {
        return this.vrtrgsArztNr;
    }

    public Muster16Ver vrtrgsArztNr(String vrtrgsArztNr) {
        this.vrtrgsArztNr = vrtrgsArztNr;
        return this;
    }

    public void setVrtrgsArztNr(String vrtrgsArztNr) {
        this.vrtrgsArztNr = vrtrgsArztNr;
    }

    public String getSprStBedarf() {
        return this.sprStBedarf;
    }

    public Muster16Ver sprStBedarf(String sprStBedarf) {
        this.sprStBedarf = sprStBedarf;
        return this;
    }

    public void setSprStBedarf(String sprStBedarf) {
        this.sprStBedarf = sprStBedarf;
    }

    public String getUnfall() {
        return this.unfall;
    }

    public Muster16Ver unfall(String unfall) {
        this.unfall = unfall;
        return this;
    }

    public void setUnfall(String unfall) {
        this.unfall = unfall;
    }

    public String getUnfallTag() {
        return this.unfallTag;
    }

    public Muster16Ver unfallTag(String unfallTag) {
        this.unfallTag = unfallTag;
        return this;
    }

    public void setUnfallTag(String unfallTag) {
        this.unfallTag = unfallTag;
    }

    public String getvGeb() {
        return this.vGeb;
    }

    public Muster16Ver vGeb(String vGeb) {
        this.vGeb = vGeb;
        return this;
    }

    public void setvGeb(String vGeb) {
        this.vGeb = vGeb;
    }

    public String getvStat() {
        return this.vStat;
    }

    public Muster16Ver vStat(String vStat) {
        this.vStat = vStat;
        return this;
    }

    public void setvStat(String vStat) {
        this.vStat = vStat;
    }

    public String getVerDat() {
        return this.verDat;
    }

    public Muster16Ver verDat(String verDat) {
        this.verDat = verDat;
        return this;
    }

    public void setVerDat(String verDat) {
        this.verDat = verDat;
    }

    public String getkName() {
        return this.kName;
    }

    public Muster16Ver kName(String kName) {
        this.kName = kName;
        return this;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public String getKkIk() {
        return this.kkIk;
    }

    public Muster16Ver kkIk(String kkIk) {
        this.kkIk = kkIk;
        return this;
    }

    public void setKkIk(String kkIk) {
        this.kkIk = kkIk;
    }

    public String getLaNr() {
        return this.laNr;
    }

    public Muster16Ver laNr(String laNr) {
        this.laNr = laNr;
        return this;
    }

    public void setLaNr(String laNr) {
        this.laNr = laNr;
    }

    public String getNoctu() {
        return this.noctu;
    }

    public Muster16Ver noctu(String noctu) {
        this.noctu = noctu;
        return this;
    }

    public void setNoctu(String noctu) {
        this.noctu = noctu;
    }

    public String getHilf() {
        return this.hilf;
    }

    public Muster16Ver hilf(String hilf) {
        this.hilf = hilf;
        return this;
    }

    public void setHilf(String hilf) {
        this.hilf = hilf;
    }

    public String getImpf() {
        return this.impf;
    }

    public Muster16Ver impf(String impf) {
        this.impf = impf;
        return this;
    }

    public void setImpf(String impf) {
        this.impf = impf;
    }

    public String getkArt() {
        return this.kArt;
    }

    public Muster16Ver kArt(String kArt) {
        this.kArt = kArt;
        return this;
    }

    public void setkArt(String kArt) {
        this.kArt = kArt;
    }

    public String getrTyp() {
        return this.rTyp;
    }

    public Muster16Ver rTyp(String rTyp) {
        this.rTyp = rTyp;
        return this;
    }

    public void setrTyp(String rTyp) {
        this.rTyp = rTyp;
    }

    public String getRezeptTyp() {
        return this.rezeptTyp;
    }

    public Muster16Ver rezeptTyp(String rezeptTyp) {
        this.rezeptTyp = rezeptTyp;
        return this;
    }

    public void setRezeptTyp(String rezeptTyp) {
        this.rezeptTyp = rezeptTyp;
    }

    public String getBgrPfl() {
        return this.bgrPfl;
    }

    public Muster16Ver bgrPfl(String bgrPfl) {
        this.bgrPfl = bgrPfl;
        return this;
    }

    public void setBgrPfl(String bgrPfl) {
        this.bgrPfl = bgrPfl;
    }

    public String getBvg() {
        return this.bvg;
    }

    public Muster16Ver bvg(String bvg) {
        this.bvg = bvg;
        return this;
    }

    public void setBvg(String bvg) {
        this.bvg = bvg;
    }

    public String getEigBet() {
        return this.eigBet;
    }

    public Muster16Ver eigBet(String eigBet) {
        this.eigBet = eigBet;
        return this;
    }

    public void setEigBet(String eigBet) {
        this.eigBet = eigBet;
    }

    public String getGebFrei() {
        return this.gebFrei;
    }

    public Muster16Ver gebFrei(String gebFrei) {
        this.gebFrei = gebFrei;
        return this;
    }

    public void setGebFrei(String gebFrei) {
        this.gebFrei = gebFrei;
    }

    public String getSonstige() {
        return this.sonstige;
    }

    public Muster16Ver sonstige(String sonstige) {
        this.sonstige = sonstige;
        return this;
    }

    public void setSonstige(String sonstige) {
        this.sonstige = sonstige;
    }

    public String getVkGueltigBis() {
        return this.vkGueltigBis;
    }

    public Muster16Ver vkGueltigBis(String vkGueltigBis) {
        this.vkGueltigBis = vkGueltigBis;
        return this;
    }

    public void setVkGueltigBis(String vkGueltigBis) {
        this.vkGueltigBis = vkGueltigBis;
    }

    public Muster16Abg getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16Ver mRezeptId(Muster16Abg muster16Abg) {
        this.setMRezeptId(muster16Abg);
        return this;
    }

    public void setMRezeptId(Muster16Abg muster16Abg) {
        this.mRezeptId = muster16Abg;
    }

    public Muster16 getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16Ver mRezeptId(Muster16 muster16) {
        this.setMRezeptId(muster16);
        return this;
    }

    public void setMRezeptId(Muster16 muster16) {
        if (this.mRezeptId != null) {
            this.mRezeptId.setMRezeptId(null);
        }
        if (muster16 != null) {
            muster16.setMRezeptId(this);
        }
        this.mRezeptId = muster16;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Muster16Ver)) {
            return false;
        }
        return id != null && id.equals(((Muster16Ver) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Muster16Ver{" +
            "id=" + getId() +
            ", aUnfall='" + getaUnfall() + "'" +
            ", abDatum='" + getAbDatum() + "'" +
            ", vrsNr='" + getVrsNr() + "'" +
            ", vrtrgsArztNr='" + getVrtrgsArztNr() + "'" +
            ", sprStBedarf='" + getSprStBedarf() + "'" +
            ", unfall='" + getUnfall() + "'" +
            ", unfallTag='" + getUnfallTag() + "'" +
            ", vGeb='" + getvGeb() + "'" +
            ", vStat='" + getvStat() + "'" +
            ", verDat='" + getVerDat() + "'" +
            ", kName='" + getkName() + "'" +
            ", kkIk='" + getKkIk() + "'" +
            ", laNr='" + getLaNr() + "'" +
            ", noctu='" + getNoctu() + "'" +
            ", hilf='" + getHilf() + "'" +
            ", impf='" + getImpf() + "'" +
            ", kArt='" + getkArt() + "'" +
            ", rTyp='" + getrTyp() + "'" +
            ", rezeptTyp='" + getRezeptTyp() + "'" +
            ", bgrPfl='" + getBgrPfl() + "'" +
            ", bvg='" + getBvg() + "'" +
            ", eigBet='" + getEigBet() + "'" +
            ", gebFrei='" + getGebFrei() + "'" +
            ", sonstige='" + getSonstige() + "'" +
            ", vkGueltigBis='" + getVkGueltigBis() + "'" +
            "}";
    }
}
