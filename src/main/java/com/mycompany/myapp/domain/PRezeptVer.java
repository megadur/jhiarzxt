package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PRezeptVer.
 */
@Entity
@Table(name = "p_rezept_ver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PRezeptVer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_rezept_id_9")
    private String pRezeptId9;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "apo_ik_send")
    private String apoIkSend;

    @Column(name = "arb_platz")
    private String arbPlatz;

    @Column(name = "avs_id")
    private String avsId;

    @Column(name = "bediener")
    private String bediener;

    @Column(name = "bvg")
    private String bvg;

    @Column(name = "e_status")
    private String eStatus;

    @Column(name = "erst_zeitpunkt")
    private String erstZeitpunkt;

    @Column(name = "k_name")
    private String kName;

    @Column(name = "kk_ik")
    private String kkIk;

    @Column(name = "la_nr")
    private String laNr;

    @Column(name = "vrs_nr")
    private String vrsNr;

    @Column(name = "vrtrgs_arzt_nr")
    private String vrtrgsArztNr;

    @Column(name = "v_geb")
    private String vGeb;

    @Column(name = "v_stat")
    private String vStat;

    @JsonIgnoreProperties(value = { "pRezeptId", "pRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PRezeptAbg pRezeptId;

    @JsonIgnoreProperties(value = { "pStatus", "pRezeptId", "pCharges", "pStatusInfos", "lieferungId" }, allowSetters = true)
    @OneToOne(mappedBy = "pRezeptId")
    private PRezept pRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PRezeptVer id(Long id) {
        this.id = id;
        return this;
    }

    public String getpRezeptId9() {
        return this.pRezeptId9;
    }

    public PRezeptVer pRezeptId9(String pRezeptId9) {
        this.pRezeptId9 = pRezeptId9;
        return this;
    }

    public void setpRezeptId9(String pRezeptId9) {
        this.pRezeptId9 = pRezeptId9;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public PRezeptVer apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getApoIkSend() {
        return this.apoIkSend;
    }

    public PRezeptVer apoIkSend(String apoIkSend) {
        this.apoIkSend = apoIkSend;
        return this;
    }

    public void setApoIkSend(String apoIkSend) {
        this.apoIkSend = apoIkSend;
    }

    public String getArbPlatz() {
        return this.arbPlatz;
    }

    public PRezeptVer arbPlatz(String arbPlatz) {
        this.arbPlatz = arbPlatz;
        return this;
    }

    public void setArbPlatz(String arbPlatz) {
        this.arbPlatz = arbPlatz;
    }

    public String getAvsId() {
        return this.avsId;
    }

    public PRezeptVer avsId(String avsId) {
        this.avsId = avsId;
        return this;
    }

    public void setAvsId(String avsId) {
        this.avsId = avsId;
    }

    public String getBediener() {
        return this.bediener;
    }

    public PRezeptVer bediener(String bediener) {
        this.bediener = bediener;
        return this;
    }

    public void setBediener(String bediener) {
        this.bediener = bediener;
    }

    public String getBvg() {
        return this.bvg;
    }

    public PRezeptVer bvg(String bvg) {
        this.bvg = bvg;
        return this;
    }

    public void setBvg(String bvg) {
        this.bvg = bvg;
    }

    public String geteStatus() {
        return this.eStatus;
    }

    public PRezeptVer eStatus(String eStatus) {
        this.eStatus = eStatus;
        return this;
    }

    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String getErstZeitpunkt() {
        return this.erstZeitpunkt;
    }

    public PRezeptVer erstZeitpunkt(String erstZeitpunkt) {
        this.erstZeitpunkt = erstZeitpunkt;
        return this;
    }

    public void setErstZeitpunkt(String erstZeitpunkt) {
        this.erstZeitpunkt = erstZeitpunkt;
    }

    public String getkName() {
        return this.kName;
    }

    public PRezeptVer kName(String kName) {
        this.kName = kName;
        return this;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public String getKkIk() {
        return this.kkIk;
    }

    public PRezeptVer kkIk(String kkIk) {
        this.kkIk = kkIk;
        return this;
    }

    public void setKkIk(String kkIk) {
        this.kkIk = kkIk;
    }

    public String getLaNr() {
        return this.laNr;
    }

    public PRezeptVer laNr(String laNr) {
        this.laNr = laNr;
        return this;
    }

    public void setLaNr(String laNr) {
        this.laNr = laNr;
    }

    public String getVrsNr() {
        return this.vrsNr;
    }

    public PRezeptVer vrsNr(String vrsNr) {
        this.vrsNr = vrsNr;
        return this;
    }

    public void setVrsNr(String vrsNr) {
        this.vrsNr = vrsNr;
    }

    public String getVrtrgsArztNr() {
        return this.vrtrgsArztNr;
    }

    public PRezeptVer vrtrgsArztNr(String vrtrgsArztNr) {
        this.vrtrgsArztNr = vrtrgsArztNr;
        return this;
    }

    public void setVrtrgsArztNr(String vrtrgsArztNr) {
        this.vrtrgsArztNr = vrtrgsArztNr;
    }

    public String getvGeb() {
        return this.vGeb;
    }

    public PRezeptVer vGeb(String vGeb) {
        this.vGeb = vGeb;
        return this;
    }

    public void setvGeb(String vGeb) {
        this.vGeb = vGeb;
    }

    public String getvStat() {
        return this.vStat;
    }

    public PRezeptVer vStat(String vStat) {
        this.vStat = vStat;
        return this;
    }

    public void setvStat(String vStat) {
        this.vStat = vStat;
    }

    public PRezeptAbg getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptVer pRezeptId(PRezeptAbg pRezeptAbg) {
        this.setPRezeptId(pRezeptAbg);
        return this;
    }

    public void setPRezeptId(PRezeptAbg pRezeptAbg) {
        this.pRezeptId = pRezeptAbg;
    }

    public PRezept getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptVer pRezeptId(PRezept pRezept) {
        this.setPRezeptId(pRezept);
        return this;
    }

    public void setPRezeptId(PRezept pRezept) {
        if (this.pRezeptId != null) {
            this.pRezeptId.setPRezeptId(null);
        }
        if (pRezept != null) {
            pRezept.setPRezeptId(this);
        }
        this.pRezeptId = pRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PRezeptVer)) {
            return false;
        }
        return id != null && id.equals(((PRezeptVer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PRezeptVer{" +
            "id=" + getId() +
            ", pRezeptId9='" + getpRezeptId9() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            ", apoIkSend='" + getApoIkSend() + "'" +
            ", arbPlatz='" + getArbPlatz() + "'" +
            ", avsId='" + getAvsId() + "'" +
            ", bediener='" + getBediener() + "'" +
            ", bvg='" + getBvg() + "'" +
            ", eStatus='" + geteStatus() + "'" +
            ", erstZeitpunkt='" + getErstZeitpunkt() + "'" +
            ", kName='" + getkName() + "'" +
            ", kkIk='" + getKkIk() + "'" +
            ", laNr='" + getLaNr() + "'" +
            ", vrsNr='" + getVrsNr() + "'" +
            ", vrtrgsArztNr='" + getVrtrgsArztNr() + "'" +
            ", vGeb='" + getvGeb() + "'" +
            ", vStat='" + getvStat() + "'" +
            "}";
    }
}
