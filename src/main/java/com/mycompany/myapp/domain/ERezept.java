package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ERezept.
 */
@Entity
@Table(name = "e_rezept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ERezept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "i_d")
    private String iD;

    @Column(name = "dok_ver")
    private String dokVer;

    @Column(name = "abg_info")
    private String abgInfo;

    @Column(name = "abg_datum")
    private LocalDate abgDatum;

    @Column(name = "abg_ges_zuzahl")
    private Float abgGesZuzahl;

    @Column(name = "abg_ges_brutto")
    private Float abgGesBrutto;

    @Column(name = "abg_vertragskz")
    private String abgVertragskz;

    @JsonIgnoreProperties(value = { "eStatusId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private EStatus eStatus;

    @OneToMany(mappedBy = "eRezept")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eRezept" }, allowSetters = true)
    private Set<EAenderung> eAenderungs = new HashSet<>();

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

    public ERezept id(Long id) {
        this.id = id;
        return this;
    }

    public String getiD() {
        return this.iD;
    }

    public ERezept iD(String iD) {
        this.iD = iD;
        return this;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getDokVer() {
        return this.dokVer;
    }

    public ERezept dokVer(String dokVer) {
        this.dokVer = dokVer;
        return this;
    }

    public void setDokVer(String dokVer) {
        this.dokVer = dokVer;
    }

    public String getAbgInfo() {
        return this.abgInfo;
    }

    public ERezept abgInfo(String abgInfo) {
        this.abgInfo = abgInfo;
        return this;
    }

    public void setAbgInfo(String abgInfo) {
        this.abgInfo = abgInfo;
    }

    public LocalDate getAbgDatum() {
        return this.abgDatum;
    }

    public ERezept abgDatum(LocalDate abgDatum) {
        this.abgDatum = abgDatum;
        return this;
    }

    public void setAbgDatum(LocalDate abgDatum) {
        this.abgDatum = abgDatum;
    }

    public Float getAbgGesZuzahl() {
        return this.abgGesZuzahl;
    }

    public ERezept abgGesZuzahl(Float abgGesZuzahl) {
        this.abgGesZuzahl = abgGesZuzahl;
        return this;
    }

    public void setAbgGesZuzahl(Float abgGesZuzahl) {
        this.abgGesZuzahl = abgGesZuzahl;
    }

    public Float getAbgGesBrutto() {
        return this.abgGesBrutto;
    }

    public ERezept abgGesBrutto(Float abgGesBrutto) {
        this.abgGesBrutto = abgGesBrutto;
        return this;
    }

    public void setAbgGesBrutto(Float abgGesBrutto) {
        this.abgGesBrutto = abgGesBrutto;
    }

    public String getAbgVertragskz() {
        return this.abgVertragskz;
    }

    public ERezept abgVertragskz(String abgVertragskz) {
        this.abgVertragskz = abgVertragskz;
        return this;
    }

    public void setAbgVertragskz(String abgVertragskz) {
        this.abgVertragskz = abgVertragskz;
    }

    public EStatus getEStatus() {
        return this.eStatus;
    }

    public ERezept eStatus(EStatus eStatus) {
        this.setEStatus(eStatus);
        return this;
    }

    public void setEStatus(EStatus eStatus) {
        this.eStatus = eStatus;
    }

    public Set<EAenderung> getEAenderungs() {
        return this.eAenderungs;
    }

    public ERezept eAenderungs(Set<EAenderung> eAenderungs) {
        this.setEAenderungs(eAenderungs);
        return this;
    }

    public ERezept addEAenderung(EAenderung eAenderung) {
        this.eAenderungs.add(eAenderung);
        eAenderung.setERezept(this);
        return this;
    }

    public ERezept removeEAenderung(EAenderung eAenderung) {
        this.eAenderungs.remove(eAenderung);
        eAenderung.setERezept(null);
        return this;
    }

    public void setEAenderungs(Set<EAenderung> eAenderungs) {
        if (this.eAenderungs != null) {
            this.eAenderungs.forEach(i -> i.setERezept(null));
        }
        if (eAenderungs != null) {
            eAenderungs.forEach(i -> i.setERezept(this));
        }
        this.eAenderungs = eAenderungs;
    }

    public Lieferung getLieferungId() {
        return this.lieferungId;
    }

    public ERezept lieferungId(Lieferung lieferung) {
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
        if (!(o instanceof ERezept)) {
            return false;
        }
        return id != null && id.equals(((ERezept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ERezept{" +
            "id=" + getId() +
            ", iD='" + getiD() + "'" +
            ", dokVer='" + getDokVer() + "'" +
            ", abgInfo='" + getAbgInfo() + "'" +
            ", abgDatum='" + getAbgDatum() + "'" +
            ", abgGesZuzahl=" + getAbgGesZuzahl() +
            ", abgGesBrutto=" + getAbgGesBrutto() +
            ", abgVertragskz='" + getAbgVertragskz() + "'" +
            "}";
    }
}
