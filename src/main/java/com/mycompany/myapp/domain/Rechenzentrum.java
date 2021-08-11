package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rechenzentrum.
 */
@Entity
@Table(name = "rechenzentrum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rechenzentrum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "i_d")
    private String iD;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "rechenzentrums")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lieferungs", "rechenzentrums" }, allowSetters = true)
    private Set<Apotheke> apothekes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rechenzentrum id(Long id) {
        this.id = id;
        return this;
    }

    public String getiD() {
        return this.iD;
    }

    public Rechenzentrum iD(String iD) {
        this.iD = iD;
        return this;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return this.name;
    }

    public Rechenzentrum name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Apotheke> getApothekes() {
        return this.apothekes;
    }

    public Rechenzentrum apothekes(Set<Apotheke> apothekes) {
        this.setApothekes(apothekes);
        return this;
    }

    public Rechenzentrum addApotheke(Apotheke apotheke) {
        this.apothekes.add(apotheke);
        apotheke.getRechenzentrums().add(this);
        return this;
    }

    public Rechenzentrum removeApotheke(Apotheke apotheke) {
        this.apothekes.remove(apotheke);
        apotheke.getRechenzentrums().remove(this);
        return this;
    }

    public void setApothekes(Set<Apotheke> apothekes) {
        if (this.apothekes != null) {
            this.apothekes.forEach(i -> i.removeRechenzentrum(this));
        }
        if (apothekes != null) {
            apothekes.forEach(i -> i.addRechenzentrum(this));
        }
        this.apothekes = apothekes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rechenzentrum)) {
            return false;
        }
        return id != null && id.equals(((Rechenzentrum) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rechenzentrum{" +
            "id=" + getId() +
            ", iD='" + getiD() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
