package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Muster16.
 */
@Entity
@Table(name = "muster_16")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Muster16 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "m_rezept_id")
    private String mRezeptId;

    @Column(name = "lieferung_id")
    private String lieferungId;

    @Column(name = "m_16_status")
    private String m16Status;

    @Column(name = "m_muster_16_id")
    private String mMuster16Id;

    @Column(name = "apo_ik_send")
    private String apoIkSend;

    @Column(name = "apo_ik_snd")
    private String apoIkSnd;

    @JsonIgnoreProperties(value = { "m16StatusId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private M16Status m16Status;

    @JsonIgnoreProperties(value = { "mRezeptId", "mRezeptId" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Muster16Ver mRezeptId;

    @OneToMany(mappedBy = "muster16")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "muster16" }, allowSetters = true)
    private Set<MArtikel> mArtikels = new HashSet<>();

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

    public Muster16 id(Long id) {
        this.id = id;
        return this;
    }

    public String getmRezeptId() {
        return this.mRezeptId;
    }

    public Muster16 mRezeptId(String mRezeptId) {
        this.mRezeptId = mRezeptId;
        return this;
    }

    public void setmRezeptId(String mRezeptId) {
        this.mRezeptId = mRezeptId;
    }

    public String getLieferungId() {
        return this.lieferungId;
    }

    public Muster16 lieferungId(String lieferungId) {
        this.lieferungId = lieferungId;
        return this;
    }

    public void setLieferungId(String lieferungId) {
        this.lieferungId = lieferungId;
    }

    public String getm16Status() {
        return this.m16Status;
    }

    public Muster16 m16Status(String m16Status) {
        this.m16Status = m16Status;
        return this;
    }

    public void setm16Status(String m16Status) {
        this.m16Status = m16Status;
    }

    public String getmMuster16Id() {
        return this.mMuster16Id;
    }

    public Muster16 mMuster16Id(String mMuster16Id) {
        this.mMuster16Id = mMuster16Id;
        return this;
    }

    public void setmMuster16Id(String mMuster16Id) {
        this.mMuster16Id = mMuster16Id;
    }

    public String getApoIkSend() {
        return this.apoIkSend;
    }

    public Muster16 apoIkSend(String apoIkSend) {
        this.apoIkSend = apoIkSend;
        return this;
    }

    public void setApoIkSend(String apoIkSend) {
        this.apoIkSend = apoIkSend;
    }

    public String getApoIkSnd() {
        return this.apoIkSnd;
    }

    public Muster16 apoIkSnd(String apoIkSnd) {
        this.apoIkSnd = apoIkSnd;
        return this;
    }

    public void setApoIkSnd(String apoIkSnd) {
        this.apoIkSnd = apoIkSnd;
    }

    public M16Status getM16Status() {
        return this.m16Status;
    }

    public Muster16 m16Status(M16Status m16Status) {
        this.setM16Status(m16Status);
        return this;
    }

    public void setM16Status(M16Status m16Status) {
        this.m16Status = m16Status;
    }

    public Muster16Ver getMRezeptId() {
        return this.mRezeptId;
    }

    public Muster16 mRezeptId(Muster16Ver muster16Ver) {
        this.setMRezeptId(muster16Ver);
        return this;
    }

    public void setMRezeptId(Muster16Ver muster16Ver) {
        this.mRezeptId = muster16Ver;
    }

    public Set<MArtikel> getMArtikels() {
        return this.mArtikels;
    }

    public Muster16 mArtikels(Set<MArtikel> mArtikels) {
        this.setMArtikels(mArtikels);
        return this;
    }

    public Muster16 addMArtikel(MArtikel mArtikel) {
        this.mArtikels.add(mArtikel);
        mArtikel.setMuster16(this);
        return this;
    }

    public Muster16 removeMArtikel(MArtikel mArtikel) {
        this.mArtikels.remove(mArtikel);
        mArtikel.setMuster16(null);
        return this;
    }

    public void setMArtikels(Set<MArtikel> mArtikels) {
        if (this.mArtikels != null) {
            this.mArtikels.forEach(i -> i.setMuster16(null));
        }
        if (mArtikels != null) {
            mArtikels.forEach(i -> i.setMuster16(this));
        }
        this.mArtikels = mArtikels;
    }

    public Lieferung getLieferungId() {
        return this.lieferungId;
    }

    public Muster16 lieferungId(Lieferung lieferung) {
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
        if (!(o instanceof Muster16)) {
            return false;
        }
        return id != null && id.equals(((Muster16) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Muster16{" +
            "id=" + getId() +
            ", mRezeptId='" + getmRezeptId() + "'" +
            ", lieferungId='" + getLieferungId() + "'" +
            ", m16Status='" + getm16Status() + "'" +
            ", mMuster16Id='" + getmMuster16Id() + "'" +
            ", apoIkSend='" + getApoIkSend() + "'" +
            ", apoIkSnd='" + getApoIkSnd() + "'" +
            "}";
    }
}
