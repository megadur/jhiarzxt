package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apotheke.
 */
@Entity
@Table(name = "apotheke")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Apotheke implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "i_k")
    private Integer iK;

    @Column(name = "inhaber")
    private String inhaber;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "plz")
    private String plz;

    @Column(name = "ort")
    private String ort;

    @Column(name = "str")
    private String str;

    @Column(name = "haus_nr")
    private String hausNr;

    @Column(name = "addr_zusatz")
    private String addrZusatz;

    @OneToMany(mappedBy = "apotheke")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apotheke" }, allowSetters = true)
    private Set<Lieferung> lieferungs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_apotheke__rechenzentrum",
        joinColumns = @JoinColumn(name = "apotheke_id"),
        inverseJoinColumns = @JoinColumn(name = "rechenzentrum_id")
    )
    @JsonIgnoreProperties(value = { "apothekes" }, allowSetters = true)
    private Set<Rechenzentrum> rechenzentrums = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apotheke id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getiK() {
        return this.iK;
    }

    public Apotheke iK(Integer iK) {
        this.iK = iK;
        return this;
    }

    public void setiK(Integer iK) {
        this.iK = iK;
    }

    public String getInhaber() {
        return this.inhaber;
    }

    public Apotheke inhaber(String inhaber) {
        this.inhaber = inhaber;
        return this;
    }

    public void setInhaber(String inhaber) {
        this.inhaber = inhaber;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Apotheke countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPlz() {
        return this.plz;
    }

    public Apotheke plz(String plz) {
        this.plz = plz;
        return this;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return this.ort;
    }

    public Apotheke ort(String ort) {
        this.ort = ort;
        return this;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStr() {
        return this.str;
    }

    public Apotheke str(String str) {
        this.str = str;
        return this;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getHausNr() {
        return this.hausNr;
    }

    public Apotheke hausNr(String hausNr) {
        this.hausNr = hausNr;
        return this;
    }

    public void setHausNr(String hausNr) {
        this.hausNr = hausNr;
    }

    public String getAddrZusatz() {
        return this.addrZusatz;
    }

    public Apotheke addrZusatz(String addrZusatz) {
        this.addrZusatz = addrZusatz;
        return this;
    }

    public void setAddrZusatz(String addrZusatz) {
        this.addrZusatz = addrZusatz;
    }

    public Set<Lieferung> getLieferungs() {
        return this.lieferungs;
    }

    public Apotheke lieferungs(Set<Lieferung> lieferungs) {
        this.setLieferungs(lieferungs);
        return this;
    }

    public Apotheke addLieferung(Lieferung lieferung) {
        this.lieferungs.add(lieferung);
        lieferung.setApotheke(this);
        return this;
    }

    public Apotheke removeLieferung(Lieferung lieferung) {
        this.lieferungs.remove(lieferung);
        lieferung.setApotheke(null);
        return this;
    }

    public void setLieferungs(Set<Lieferung> lieferungs) {
        if (this.lieferungs != null) {
            this.lieferungs.forEach(i -> i.setApotheke(null));
        }
        if (lieferungs != null) {
            lieferungs.forEach(i -> i.setApotheke(this));
        }
        this.lieferungs = lieferungs;
    }

    public Set<Rechenzentrum> getRechenzentrums() {
        return this.rechenzentrums;
    }

    public Apotheke rechenzentrums(Set<Rechenzentrum> rechenzentrums) {
        this.setRechenzentrums(rechenzentrums);
        return this;
    }

    public Apotheke addRechenzentrum(Rechenzentrum rechenzentrum) {
        this.rechenzentrums.add(rechenzentrum);
        rechenzentrum.getApothekes().add(this);
        return this;
    }

    public Apotheke removeRechenzentrum(Rechenzentrum rechenzentrum) {
        this.rechenzentrums.remove(rechenzentrum);
        rechenzentrum.getApothekes().remove(this);
        return this;
    }

    public void setRechenzentrums(Set<Rechenzentrum> rechenzentrums) {
        this.rechenzentrums = rechenzentrums;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apotheke)) {
            return false;
        }
        return id != null && id.equals(((Apotheke) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apotheke{" +
            "id=" + getId() +
            ", iK=" + getiK() +
            ", inhaber='" + getInhaber() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", plz='" + getPlz() + "'" +
            ", ort='" + getOrt() + "'" +
            ", str='" + getStr() + "'" +
            ", hausNr='" + getHausNr() + "'" +
            ", addrZusatz='" + getAddrZusatz() + "'" +
            "}";
    }
}
