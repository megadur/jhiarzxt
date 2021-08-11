package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PRezeptAbr.
 */
@Entity
@Table(name = "p_rezept_abr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PRezeptAbr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "p_rezept_id")
    private String pRezeptId;

    @Column(name = "betrag_rab_a")
    private String betragRabA;

    @Column(name = "betrag_rab_h")
    private String betragRabH;

    @Column(name = "betrag_apo_ausz")
    private String betragApoAusz;

    @JsonIgnoreProperties(value = { "pRezeptId", "pRezeptId" }, allowSetters = true)
    @OneToOne(mappedBy = "pRezeptId")
    private PRezeptAbg pRezeptId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PRezeptAbr id(Long id) {
        this.id = id;
        return this;
    }

    public String getpRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptAbr pRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
        return this;
    }

    public void setpRezeptId(String pRezeptId) {
        this.pRezeptId = pRezeptId;
    }

    public String getBetragRabA() {
        return this.betragRabA;
    }

    public PRezeptAbr betragRabA(String betragRabA) {
        this.betragRabA = betragRabA;
        return this;
    }

    public void setBetragRabA(String betragRabA) {
        this.betragRabA = betragRabA;
    }

    public String getBetragRabH() {
        return this.betragRabH;
    }

    public PRezeptAbr betragRabH(String betragRabH) {
        this.betragRabH = betragRabH;
        return this;
    }

    public void setBetragRabH(String betragRabH) {
        this.betragRabH = betragRabH;
    }

    public String getBetragApoAusz() {
        return this.betragApoAusz;
    }

    public PRezeptAbr betragApoAusz(String betragApoAusz) {
        this.betragApoAusz = betragApoAusz;
        return this;
    }

    public void setBetragApoAusz(String betragApoAusz) {
        this.betragApoAusz = betragApoAusz;
    }

    public PRezeptAbg getPRezeptId() {
        return this.pRezeptId;
    }

    public PRezeptAbr pRezeptId(PRezeptAbg pRezeptAbg) {
        this.setPRezeptId(pRezeptAbg);
        return this;
    }

    public void setPRezeptId(PRezeptAbg pRezeptAbg) {
        if (this.pRezeptId != null) {
            this.pRezeptId.setPRezeptId(null);
        }
        if (pRezeptAbg != null) {
            pRezeptAbg.setPRezeptId(this);
        }
        this.pRezeptId = pRezeptAbg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PRezeptAbr)) {
            return false;
        }
        return id != null && id.equals(((PRezeptAbr) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PRezeptAbr{" +
            "id=" + getId() +
            ", pRezeptId='" + getpRezeptId() + "'" +
            ", betragRabA='" + getBetragRabA() + "'" +
            ", betragRabH='" + getBetragRabH() + "'" +
            ", betragApoAusz='" + getBetragApoAusz() + "'" +
            "}";
    }
}
