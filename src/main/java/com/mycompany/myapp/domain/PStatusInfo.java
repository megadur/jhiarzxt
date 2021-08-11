package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PStatusInfo.
 */
@Entity
@Table(name = "p_status_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PStatusInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_status_info_id")
    private String pStatusInfoId;

    @Column(name = "p_rezept_id")
    private String pRezeptId;

    @Column(name = "apo_ik")
    private String apoIk;

    @Column(name = "f_code")
    private String fCode;

    @Column(name = "f_haupt_fehler")
    private String fHauptFehler;

    @Column(name = "f_kommentar")
    private String fKommentar;

    @Column(name = "f_kurz_text")
    private String fKurzText;

    @Column(name = "f_lang_text")
    private String fLangText;

    @Column(name = "f_status")
    private String fStatus;

    @Column(name = "f_t_code")
    private String fTCode;

    @Column(name = "f_wert")
    private String fWert;

    @Column(name = "faktor")
    private String faktor;

    @Column(name = "frist_ende")
    private String fristEnde;

    @Column(name = "ges_brutto")
    private String gesBrutto;

    @Column(name = "pos_nr")
    private String posNr;

    @Column(name = "taxe")
    private String taxe;

    @Column(name = "zeitpunkt")
    private String zeitpunkt;

    @Column(name = "zuzahlung")
    private String zuzahlung;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pStatus", "pRezeptId", "pCharges", "pStatusInfos", "lieferungId" }, allowSetters = true)
    private PRezept pStatusInfoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PStatusInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getpStatusInfoId() {
        return this.pStatusInfoId;
    }

    public PStatusInfo pStatusInfoId(String pStatusInfoId) {
        this.pStatusInfoId = pStatusInfoId;
        return this;
    }

    public void setpStatusInfoId(String pStatusInfoId) {
        this.pStatusInfoId = pStatusInfoId;
    }

    public String getpRezeptId() {
        return this.pRezeptId;
    }

    public PStatusInfo pRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
        return this;
    }

    public void setpRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
    }

    public String getApoIk() {
        return this.apoIk;
    }

    public PStatusInfo apoIk(String apoIk) {
        this.apoIk = apoIk;
        return this;
    }

    public void setApoIk(String apoIk) {
        this.apoIk = apoIk;
    }

    public String getfCode() {
        return this.fCode;
    }

    public PStatusInfo fCode(String fCode) {
        this.fCode = fCode;
        return this;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getfHauptFehler() {
        return this.fHauptFehler;
    }

    public PStatusInfo fHauptFehler(String fHauptFehler) {
        this.fHauptFehler = fHauptFehler;
        return this;
    }

    public void setfHauptFehler(String fHauptFehler) {
        this.fHauptFehler = fHauptFehler;
    }

    public String getfKommentar() {
        return this.fKommentar;
    }

    public PStatusInfo fKommentar(String fKommentar) {
        this.fKommentar = fKommentar;
        return this;
    }

    public void setfKommentar(String fKommentar) {
        this.fKommentar = fKommentar;
    }

    public String getfKurzText() {
        return this.fKurzText;
    }

    public PStatusInfo fKurzText(String fKurzText) {
        this.fKurzText = fKurzText;
        return this;
    }

    public void setfKurzText(String fKurzText) {
        this.fKurzText = fKurzText;
    }

    public String getfLangText() {
        return this.fLangText;
    }

    public PStatusInfo fLangText(String fLangText) {
        this.fLangText = fLangText;
        return this;
    }

    public void setfLangText(String fLangText) {
        this.fLangText = fLangText;
    }

    public String getfStatus() {
        return this.fStatus;
    }

    public PStatusInfo fStatus(String fStatus) {
        this.fStatus = fStatus;
        return this;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus;
    }

    public String getfTCode() {
        return this.fTCode;
    }

    public PStatusInfo fTCode(String fTCode) {
        this.fTCode = fTCode;
        return this;
    }

    public void setfTCode(String fTCode) {
        this.fTCode = fTCode;
    }

    public String getfWert() {
        return this.fWert;
    }

    public PStatusInfo fWert(String fWert) {
        this.fWert = fWert;
        return this;
    }

    public void setfWert(String fWert) {
        this.fWert = fWert;
    }

    public String getFaktor() {
        return this.faktor;
    }

    public PStatusInfo faktor(String faktor) {
        this.faktor = faktor;
        return this;
    }

    public void setFaktor(String faktor) {
        this.faktor = faktor;
    }

    public String getFristEnde() {
        return this.fristEnde;
    }

    public PStatusInfo fristEnde(String fristEnde) {
        this.fristEnde = fristEnde;
        return this;
    }

    public void setFristEnde(String fristEnde) {
        this.fristEnde = fristEnde;
    }

    public String getGesBrutto() {
        return this.gesBrutto;
    }

    public PStatusInfo gesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
        return this;
    }

    public void setGesBrutto(String gesBrutto) {
        this.gesBrutto = gesBrutto;
    }

    public String getPosNr() {
        return this.posNr;
    }

    public PStatusInfo posNr(String posNr) {
        this.posNr = posNr;
        return this;
    }

    public void setPosNr(String posNr) {
        this.posNr = posNr;
    }

    public String getTaxe() {
        return this.taxe;
    }

    public PStatusInfo taxe(String taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(String taxe) {
        this.taxe = taxe;
    }

    public String getZeitpunkt() {
        return this.zeitpunkt;
    }

    public PStatusInfo zeitpunkt(String zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
        return this;
    }

    public void setZeitpunkt(String zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public String getZuzahlung() {
        return this.zuzahlung;
    }

    public PStatusInfo zuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
        return this;
    }

    public void setZuzahlung(String zuzahlung) {
        this.zuzahlung = zuzahlung;
    }

    public PRezept getPStatusInfoId() {
        return this.pStatusInfoId;
    }

    public PStatusInfo pStatusInfoId(PRezept pRezept) {
        this.setPStatusInfoId(pRezept);
        return this;
    }

    public void setPStatusInfoId(PRezept pRezept) {
        this.pStatusInfoId = pRezept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PStatusInfo)) {
            return false;
        }
        return id != null && id.equals(((PStatusInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PStatusInfo{" +
            "id=" + getId() +
            ", pStatusInfoId='" + getpStatusInfoId() + "'" +
            ", pRezeptId='" + getpRezeptId() + "'" +
            ", apoIk='" + getApoIk() + "'" +
            ", fCode='" + getfCode() + "'" +
            ", fHauptFehler='" + getfHauptFehler() + "'" +
            ", fKommentar='" + getfKommentar() + "'" +
            ", fKurzText='" + getfKurzText() + "'" +
            ", fLangText='" + getfLangText() + "'" +
            ", fStatus='" + getfStatus() + "'" +
            ", fTCode='" + getfTCode() + "'" +
            ", fWert='" + getfWert() + "'" +
            ", faktor='" + getFaktor() + "'" +
            ", fristEnde='" + getFristEnde() + "'" +
            ", gesBrutto='" + getGesBrutto() + "'" +
            ", posNr='" + getPosNr() + "'" +
            ", taxe='" + getTaxe() + "'" +
            ", zeitpunkt='" + getZeitpunkt() + "'" +
            ", zuzahlung='" + getZuzahlung() + "'" +
            "}";
    }
}
