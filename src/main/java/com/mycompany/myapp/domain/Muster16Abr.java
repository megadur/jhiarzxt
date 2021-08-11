package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Muster16Abr.
 */
@Entity
@Table(name = "muster_16_abr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Muster16Abr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "betrag_rab_a")
    private String betragRabA;

    @Column(name = "betrag_rab_h")
    private String betragRabH;

    @Column(name = "betrag_apo_ausz")
    private String betragApoAusz;

    @JsonIgnoreProperties(value = { "mRezeptId", "mRezeptId" }, allowSetters = true)
    @OneToOne(mappedBy = "mRezeptId")
    private Muster16Abg mRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Muster16Abr id(Long id) {
        this.id = id;
        return this;
    }

    public String getBetragRabA() {
        return this.betragRabA;
    }

    public Muster16Abr betragRabA(String betragRabA) {
        this.betragRabA = betragRabA;
        return this;
    }

    public void setBetragRabA(String betragRabA) {
        this.betragRabA = betragRabA;
    }

    public String getBetragRabH() {
        return this.betragRabH;
    }

    public Muster16Abr betragRabH(String betragRabH) {
        this.betragRabH = betragRabH;
        return this;
    }

    public void setBetragRabH(String betragRabH) {
        this.betragRabH = betragRabH;
    }

    public String getBetragApoAusz() {
        return this.betragApoAusz;
    }

    public Muster16Abr betragApoAusz(String betragApoAusz) {
        this.betragApoAusz = betragApoAusz;
        return this;
    }

    public void setBetragApoAusz(String betragApoAusz) {
        this.betragApoAusz = betragApoAusz;
    }

    public Muster16Abg getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16Abr mRezeptId(Muster16Abg muster16Abg) {
        this.setMRezeptId(muster16Abg);
        return this;
    }

    public void setMRezeptId(Muster16Abg muster16Abg) {
        if (this.mRezeptId != null) {
            this.mRezeptId.setMRezeptId(null);
        }
        if (muster16Abg != null) {
            muster16Abg.setMRezeptId(this);
        }
        this.mRezeptId = muster16Abg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Muster16Abr)) {
            return false;
        }
        return id != null && id.equals(((Muster16Abr) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Muster16Abr{" +
            "id=" + getId() +
            ", betragRabA='" + getBetragRabA() + "'" +
            ", betragRabH='" + getBetragRabH() + "'" +
            ", betragApoAusz='" + getBetragApoAusz() + "'" +
            "}";
    }
}
