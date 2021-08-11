package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MArtikel;
import com.mycompany.myapp.repository.MArtikelRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MArtikelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MArtikelResourceIT {

    private static final String DEFAULT_M_ARTIKEL_ID = "AAAAAAAAAA";
    private static final String UPDATED_M_ARTIKEL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_M_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_M_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_AUTIDEM = "AAAAAAAAAA";
    private static final String UPDATED_AUTIDEM = "BBBBBBBBBB";

    private static final String DEFAULT_FAKTOR = "AAAAAAAAAA";
    private static final String UPDATED_FAKTOR = "BBBBBBBBBB";

    private static final String DEFAULT_HILFSMITTEL_NR = "AAAAAAAAAA";
    private static final String UPDATED_HILFSMITTEL_NR = "BBBBBBBBBB";

    private static final String DEFAULT_MUSTER_16_ID = "AAAAAAAAAA";
    private static final String UPDATED_MUSTER_16_ID = "BBBBBBBBBB";

    private static final String DEFAULT_POS_NR = "AAAAAAAAAA";
    private static final String UPDATED_POS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_PZN = "AAAAAAAAAA";
    private static final String UPDATED_PZN = "BBBBBBBBBB";

    private static final String DEFAULT_TAXE = "AAAAAAAAAA";
    private static final String UPDATED_TAXE = "BBBBBBBBBB";

    private static final String DEFAULT_V_ZEILE = "AAAAAAAAAA";
    private static final String UPDATED_V_ZEILE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/m-artikels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MArtikelRepository mArtikelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMArtikelMockMvc;

    private MArtikel mArtikel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MArtikel createEntity(EntityManager em) {
        MArtikel mArtikel = new MArtikel()
            .mArtikelId(DEFAULT_M_ARTIKEL_ID)
            .mRezeptId(DEFAULT_M_REZEPT_ID)
            .apoIk(DEFAULT_APO_IK)
            .autidem(DEFAULT_AUTIDEM)
            .faktor(DEFAULT_FAKTOR)
            .hilfsmittelNr(DEFAULT_HILFSMITTEL_NR)
            .muster16Id(DEFAULT_MUSTER_16_ID)
            .posNr(DEFAULT_POS_NR)
            .pzn(DEFAULT_PZN)
            .taxe(DEFAULT_TAXE)
            .vZeile(DEFAULT_V_ZEILE);
        return mArtikel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MArtikel createUpdatedEntity(EntityManager em) {
        MArtikel mArtikel = new MArtikel()
            .mArtikelId(UPDATED_M_ARTIKEL_ID)
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .autidem(UPDATED_AUTIDEM)
            .faktor(UPDATED_FAKTOR)
            .hilfsmittelNr(UPDATED_HILFSMITTEL_NR)
            .muster16Id(UPDATED_MUSTER_16_ID)
            .posNr(UPDATED_POS_NR)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .vZeile(UPDATED_V_ZEILE);
        return mArtikel;
    }

    @BeforeEach
    public void initTest() {
        mArtikel = createEntity(em);
    }

    @Test
    @Transactional
    void createMArtikel() throws Exception {
        int databaseSizeBeforeCreate = mArtikelRepository.findAll().size();
        // Create the MArtikel
        restMArtikelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mArtikel)))
            .andExpect(status().isCreated());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeCreate + 1);
        MArtikel testMArtikel = mArtikelList.get(mArtikelList.size() - 1);
        assertThat(testMArtikel.getmArtikelId()).isEqualTo(DEFAULT_M_ARTIKEL_ID);
        assertThat(testMArtikel.getmRezeptId()).isEqualTo(DEFAULT_M_REZEPT_ID);
        assertThat(testMArtikel.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testMArtikel.getAutidem()).isEqualTo(DEFAULT_AUTIDEM);
        assertThat(testMArtikel.getFaktor()).isEqualTo(DEFAULT_FAKTOR);
        assertThat(testMArtikel.getHilfsmittelNr()).isEqualTo(DEFAULT_HILFSMITTEL_NR);
        assertThat(testMArtikel.getMuster16Id()).isEqualTo(DEFAULT_MUSTER_16_ID);
        assertThat(testMArtikel.getPosNr()).isEqualTo(DEFAULT_POS_NR);
        assertThat(testMArtikel.getPzn()).isEqualTo(DEFAULT_PZN);
        assertThat(testMArtikel.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testMArtikel.getvZeile()).isEqualTo(DEFAULT_V_ZEILE);
    }

    @Test
    @Transactional
    void createMArtikelWithExistingId() throws Exception {
        // Create the MArtikel with an existing ID
        mArtikel.setId(1L);

        int databaseSizeBeforeCreate = mArtikelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMArtikelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mArtikel)))
            .andExpect(status().isBadRequest());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMArtikels() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        // Get all the mArtikelList
        restMArtikelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mArtikel.getId().intValue())))
            .andExpect(jsonPath("$.[*].mArtikelId").value(hasItem(DEFAULT_M_ARTIKEL_ID)))
            .andExpect(jsonPath("$.[*].mRezeptId").value(hasItem(DEFAULT_M_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].autidem").value(hasItem(DEFAULT_AUTIDEM)))
            .andExpect(jsonPath("$.[*].faktor").value(hasItem(DEFAULT_FAKTOR)))
            .andExpect(jsonPath("$.[*].hilfsmittelNr").value(hasItem(DEFAULT_HILFSMITTEL_NR)))
            .andExpect(jsonPath("$.[*].muster16Id").value(hasItem(DEFAULT_MUSTER_16_ID)))
            .andExpect(jsonPath("$.[*].posNr").value(hasItem(DEFAULT_POS_NR)))
            .andExpect(jsonPath("$.[*].pzn").value(hasItem(DEFAULT_PZN)))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE)))
            .andExpect(jsonPath("$.[*].vZeile").value(hasItem(DEFAULT_V_ZEILE)));
    }

    @Test
    @Transactional
    void getMArtikel() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        // Get the mArtikel
        restMArtikelMockMvc
            .perform(get(ENTITY_API_URL_ID, mArtikel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mArtikel.getId().intValue()))
            .andExpect(jsonPath("$.mArtikelId").value(DEFAULT_M_ARTIKEL_ID))
            .andExpect(jsonPath("$.mRezeptId").value(DEFAULT_M_REZEPT_ID))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.autidem").value(DEFAULT_AUTIDEM))
            .andExpect(jsonPath("$.faktor").value(DEFAULT_FAKTOR))
            .andExpect(jsonPath("$.hilfsmittelNr").value(DEFAULT_HILFSMITTEL_NR))
            .andExpect(jsonPath("$.muster16Id").value(DEFAULT_MUSTER_16_ID))
            .andExpect(jsonPath("$.posNr").value(DEFAULT_POS_NR))
            .andExpect(jsonPath("$.pzn").value(DEFAULT_PZN))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE))
            .andExpect(jsonPath("$.vZeile").value(DEFAULT_V_ZEILE));
    }

    @Test
    @Transactional
    void getNonExistingMArtikel() throws Exception {
        // Get the mArtikel
        restMArtikelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMArtikel() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();

        // Update the mArtikel
        MArtikel updatedMArtikel = mArtikelRepository.findById(mArtikel.getId()).get();
        // Disconnect from session so that the updates on updatedMArtikel are not directly saved in db
        em.detach(updatedMArtikel);
        updatedMArtikel
            .mArtikelId(UPDATED_M_ARTIKEL_ID)
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .autidem(UPDATED_AUTIDEM)
            .faktor(UPDATED_FAKTOR)
            .hilfsmittelNr(UPDATED_HILFSMITTEL_NR)
            .muster16Id(UPDATED_MUSTER_16_ID)
            .posNr(UPDATED_POS_NR)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .vZeile(UPDATED_V_ZEILE);

        restMArtikelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMArtikel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMArtikel))
            )
            .andExpect(status().isOk());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
        MArtikel testMArtikel = mArtikelList.get(mArtikelList.size() - 1);
        assertThat(testMArtikel.getmArtikelId()).isEqualTo(UPDATED_M_ARTIKEL_ID);
        assertThat(testMArtikel.getmRezeptId()).isEqualTo(UPDATED_M_REZEPT_ID);
        assertThat(testMArtikel.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testMArtikel.getAutidem()).isEqualTo(UPDATED_AUTIDEM);
        assertThat(testMArtikel.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testMArtikel.getHilfsmittelNr()).isEqualTo(UPDATED_HILFSMITTEL_NR);
        assertThat(testMArtikel.getMuster16Id()).isEqualTo(UPDATED_MUSTER_16_ID);
        assertThat(testMArtikel.getPosNr()).isEqualTo(UPDATED_POS_NR);
        assertThat(testMArtikel.getPzn()).isEqualTo(UPDATED_PZN);
        assertThat(testMArtikel.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testMArtikel.getvZeile()).isEqualTo(UPDATED_V_ZEILE);
    }

    @Test
    @Transactional
    void putNonExistingMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mArtikel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mArtikel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mArtikel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mArtikel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMArtikelWithPatch() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();

        // Update the mArtikel using partial update
        MArtikel partialUpdatedMArtikel = new MArtikel();
        partialUpdatedMArtikel.setId(mArtikel.getId());

        partialUpdatedMArtikel.faktor(UPDATED_FAKTOR).posNr(UPDATED_POS_NR);

        restMArtikelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMArtikel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMArtikel))
            )
            .andExpect(status().isOk());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
        MArtikel testMArtikel = mArtikelList.get(mArtikelList.size() - 1);
        assertThat(testMArtikel.getmArtikelId()).isEqualTo(DEFAULT_M_ARTIKEL_ID);
        assertThat(testMArtikel.getmRezeptId()).isEqualTo(DEFAULT_M_REZEPT_ID);
        assertThat(testMArtikel.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testMArtikel.getAutidem()).isEqualTo(DEFAULT_AUTIDEM);
        assertThat(testMArtikel.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testMArtikel.getHilfsmittelNr()).isEqualTo(DEFAULT_HILFSMITTEL_NR);
        assertThat(testMArtikel.getMuster16Id()).isEqualTo(DEFAULT_MUSTER_16_ID);
        assertThat(testMArtikel.getPosNr()).isEqualTo(UPDATED_POS_NR);
        assertThat(testMArtikel.getPzn()).isEqualTo(DEFAULT_PZN);
        assertThat(testMArtikel.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testMArtikel.getvZeile()).isEqualTo(DEFAULT_V_ZEILE);
    }

    @Test
    @Transactional
    void fullUpdateMArtikelWithPatch() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();

        // Update the mArtikel using partial update
        MArtikel partialUpdatedMArtikel = new MArtikel();
        partialUpdatedMArtikel.setId(mArtikel.getId());

        partialUpdatedMArtikel
            .mArtikelId(UPDATED_M_ARTIKEL_ID)
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .autidem(UPDATED_AUTIDEM)
            .faktor(UPDATED_FAKTOR)
            .hilfsmittelNr(UPDATED_HILFSMITTEL_NR)
            .muster16Id(UPDATED_MUSTER_16_ID)
            .posNr(UPDATED_POS_NR)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .vZeile(UPDATED_V_ZEILE);

        restMArtikelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMArtikel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMArtikel))
            )
            .andExpect(status().isOk());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
        MArtikel testMArtikel = mArtikelList.get(mArtikelList.size() - 1);
        assertThat(testMArtikel.getmArtikelId()).isEqualTo(UPDATED_M_ARTIKEL_ID);
        assertThat(testMArtikel.getmRezeptId()).isEqualTo(UPDATED_M_REZEPT_ID);
        assertThat(testMArtikel.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testMArtikel.getAutidem()).isEqualTo(UPDATED_AUTIDEM);
        assertThat(testMArtikel.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testMArtikel.getHilfsmittelNr()).isEqualTo(UPDATED_HILFSMITTEL_NR);
        assertThat(testMArtikel.getMuster16Id()).isEqualTo(UPDATED_MUSTER_16_ID);
        assertThat(testMArtikel.getPosNr()).isEqualTo(UPDATED_POS_NR);
        assertThat(testMArtikel.getPzn()).isEqualTo(UPDATED_PZN);
        assertThat(testMArtikel.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testMArtikel.getvZeile()).isEqualTo(UPDATED_V_ZEILE);
    }

    @Test
    @Transactional
    void patchNonExistingMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mArtikel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mArtikel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mArtikel))
            )
            .andExpect(status().isBadRequest());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMArtikel() throws Exception {
        int databaseSizeBeforeUpdate = mArtikelRepository.findAll().size();
        mArtikel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMArtikelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mArtikel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MArtikel in the database
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMArtikel() throws Exception {
        // Initialize the database
        mArtikelRepository.saveAndFlush(mArtikel);

        int databaseSizeBeforeDelete = mArtikelRepository.findAll().size();

        // Delete the mArtikel
        restMArtikelMockMvc
            .perform(delete(ENTITY_API_URL_ID, mArtikel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MArtikel> mArtikelList = mArtikelRepository.findAll();
        assertThat(mArtikelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
