package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A M16Status.
 */
@Entity
@Table(name = "m_16_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class M16Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "m_16_status_id")
    private String m16StatusId;

    @Column(name = "status")
    private String status;

    @JsonIgnoreProperties(value = { "m16Status", "mRezeptId", "mArtikels", "lieferungId" }, allowSetters = true)
    @OneToOne(mappedBy = "m16Status")
    private Muster16 m16StatusId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public M16Status id(Long id) {
        this.id = id;
        return this;
    }

    public String getm16StatusId() {
        return this.m16StatusId;
    }

    public M16Status m16StatusId(String m16StatusId) {
        this.m16StatusId = m16StatusId;
        return this;
    }

    public void setm16StatusId(String m16StatusId) {
        this.m16StatusId = m16StatusId;
    }

    public String getStatus() {
        return this.status;
    }

    public M16Status status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Muster16 getM16StatusId() {
        return this.m16StatusId;
    }

    public M16Status m16StatusId(Muster16 muster16) {
        this.setM16StatusId(muster16);
        return this;
    }

    public void setM16StatusId(Muster16 muster16) {
        if (this.m16StatusId != null) {
            this.m16StatusId.setM16Status(null);
        }
        if (muster16 != null) {
            muster16.setM16Status(this);
        }
        this.m16StatusId = muster16;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof M16Status)) {
            return false;
        }
        return id != null && id.equals(((M16Status) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "M16Status{" +
            "id=" + getId() +
            ", m16StatusId='" + getm16StatusId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
