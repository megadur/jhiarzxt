package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PRezept.
 */
@Entity
@Table(name = "p_rezept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PRezept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_rezept_id")
    private String pRezeptId;

    @Column(name = "lieferdat")
    private String lieferdat;

    @Column(name = "lieferung_id")
    private String lieferungId;

    @Column(name = "a_periode")
    private String aPeriode;

    @Column(name = "ab_datum")
    private String abDatum;

    @JsonIgnoreProperties(value = { "pStatusId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PStatus pStatus;

    @JsonIgnoreProperties(value = { "pRezeptId", "pRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PRezeptVer pRezeptId;

    @OneToMany(mappedBy = "pRezept")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pWirkstoffs", "pRezept" }, allowSetters = true)
    private Set<PCharge> pCharges = new HashSet<>();

    @OneToMany(mappedBy = "pStatusInfoId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pStatusInfoId" }, allowSetters = true)
    private Set<PStatusInfo> pStatusInfos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "apotheke" }, allowSetters = true)
    private Lieferung lieferungId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PRezept id(Long id) {
        this.id = id;
        return this;
    }

    public String getpRezeptId() {
        return this.pRezeptId;
    }

    public PRezept pRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
        return this;
    }

    public void setpRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
    }

    public String getLieferdat() {
        return this.lieferdat;
    }

    public PRezept lieferdat(String lieferdat) {
        this.lieferdat = lieferdat;
        return this;
    }

    public void setLieferdat(String lieferdat) {
        this.lieferdat = lieferdat;
    }

    public String getLieferungId() {
        return this.lieferungId;
    }

    public PRezept lieferungId(String lieferungId) {
        this.lieferungId = lieferungId;
        return this;
    }

    public void setLieferungId(String lieferungId) {
        this.lieferungId = lieferungId;
    }

    public String getaPeriode() {
        return this.aPeriode;
    }

    public PRezept aPeriode(String aPeriode) {
        this.aPeriode = aPeriode;
        return this;
    }

    public void setaPeriode(String aPeriode) {
        this.aPeriode = aPeriode;
    }

    public String getAbDatum() {
        return this.abDatum;
    }

    public PRezept abDatum(String abDatum) {
        this.abDatum = abDatum;
        return this;
    }

    public void setAbDatum(String abDatum) {
        this.abDatum = abDatum;
    }

    public PStatus getPStatus() {
        return this.pStatus;
    }

    public PRezept pStatus(PStatus pStatus) {
        this.setPStatus(pStatus);
        return this;
    }

    public void setPStatus(PStatus pStatus) {
        this.pStatus = pStatus;
    }

    public PRezeptVer getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezept pRezeptId(PRezeptVer pRezeptVer) {
        this.setPRezeptId(pRezeptVer);
        return this;
    }

    public void setPRezeptId(PRezeptVer pRezeptVer) {
        this.pRezeptId = pRezeptVer;
    }

    public Set<PCharge> getPCharges() {
        return this.pCharges;
    }

    public PRezept pCharges(Set<PCharge> pCharges) {
        this.setPCharges(pCharges);
        return this;
    }

    public PRezept addPCharge(PCharge pCharge) {
        this.pCharges.add(pCharge);
        pCharge.setPRezept(this);
        return this;
    }

    public PRezept removePCharge(PCharge pCharge) {
        this.pCharges.remove(pCharge);
        pCharge.setPRezept(null);
        return this;
    }

    public void setPCharges(Set<PCharge> pCharges) {
        if (this.pCharges != null) {
            this.pCharges.forEach(i -> i.setPRezept(null));
        }
        if (pCharges != null) {
            pCharges.forEach(i -> i.setPRezept(this));
        }
        this.pCharges = pCharges;
    }

    public Set<PStatusInfo> getPStatusInfos() {
        return this.pStatusInfos;
    }

    public PRezept pStatusInfos(Set<PStatusInfo> pStatusInfos) {
        this.setPStatusInfos(pStatusInfos);
        return this;
    }

    public PRezept addPStatusInfo(PStatusInfo pStatusInfo) {
        this.pStatusInfos.add(pStatusInfo);
        pStatusInfo.setPStatusInfoId(this);
        return this;
    }

    public PRezept removePStatusInfo(PStatusInfo pStatusInfo) {
        this.pStatusInfos.remove(pStatusInfo);
        pStatusInfo.setPStatusInfoId(null);
        return this;
    }

    public void setPStatusInfos(Set<PStatusInfo> pStatusInfos) {
        if (this.pStatusInfos != null) {
            this.pStatusInfos.forEach(i -> i.setPStatusInfoId(null));
        }
        if (pStatusInfos != null) {
            pStatusInfos.forEach(i -> i.setPStatusInfoId(this));
        }
        this.pStatusInfos = pStatusInfos;
    }

    public Lieferung getLieferungId() {
        return this.lieferungId;
    }

    public PRezept lieferungId(Lieferung lieferung) {
        this.setLieferungId(lieferung);
        return this;
    }

    public void setLieferungId(Lieferung lieferung) {
        this.lieferungId = lieferung;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PRezept)) {
            return false;
        }
        return id != null && id.equals(((PRezept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PRezept{" +
            "id=" + getId() +
            ", pRezeptId='" + getpRezeptId() + "'" +
            ", lieferdat='" + getLieferdat() + "'" +
            ", lieferungId='" + getLieferungId() + "'" +
            ", aPeriode='" + getaPeriode() + "'" +
            ", abDatum='" + getAbDatum() + "'" +
            "}";
    }
}
