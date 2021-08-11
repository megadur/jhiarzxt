package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Muster16Abg;
import com.mycompany.myapp.repository.Muster16AbgRepository;
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
 * Integration tests for the {@link Muster16AbgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Muster16AbgResourceIT {

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_LIEFER_DAT = "AAAAAAAAAA";
    private static final String UPDATED_LIEFER_DAT = "BBBBBBBBBB";

    private static final String DEFAULT_A_PERIODE = "AAAAAAAAAA";
    private static final String UPDATED_A_PERIODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARB_PLATZ = "AAAAAAAAAA";
    private static final String UPDATED_ARB_PLATZ = "BBBBBBBBBB";

    private static final String DEFAULT_AVS_ID = "AAAAAAAAAA";
    private static final String UPDATED_AVS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIENER = "AAAAAAAAAA";
    private static final String UPDATED_BEDIENER = "BBBBBBBBBB";

    private static final String DEFAULT_ZUZAHLUNG = "AAAAAAAAAA";
    private static final String UPDATED_ZUZAHLUNG = "BBBBBBBBBB";

    private static final String DEFAULT_GES_BRUTTO = "AAAAAAAAAA";
    private static final String UPDATED_GES_BRUTTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/muster-16-abgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Muster16AbgRepository muster16AbgRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuster16AbgMockMvc;

    private Muster16Abg muster16Abg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Abg createEntity(EntityManager em) {
        Muster16Abg muster16Abg = new Muster16Abg()
            .apoIk(DEFAULT_APO_IK)
            .lieferDat(DEFAULT_LIEFER_DAT)
            .aPeriode(DEFAULT_A_PERIODE)
            .arbPlatz(DEFAULT_ARB_PLATZ)
            .avsId(DEFAULT_AVS_ID)
            .bediener(DEFAULT_BEDIENER)
            .zuzahlung(DEFAULT_ZUZAHLUNG)
            .gesBrutto(DEFAULT_GES_BRUTTO);
        return muster16Abg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Abg createUpdatedEntity(EntityManager em) {
        Muster16Abg muster16Abg = new Muster16Abg()
            .apoIk(UPDATED_APO_IK)
            .lieferDat(UPDATED_LIEFER_DAT)
            .aPeriode(UPDATED_A_PERIODE)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .zuzahlung(UPDATED_ZUZAHLUNG)
            .gesBrutto(UPDATED_GES_BRUTTO);
        return muster16Abg;
    }

    @BeforeEach
    public void initTest() {
        muster16Abg = createEntity(em);
    }

    @Test
    @Transactional
    void createMuster16Abg() throws Exception {
        int databaseSizeBeforeCreate = muster16AbgRepository.findAll().size();
        // Create the Muster16Abg
        restMuster16AbgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abg)))
            .andExpect(status().isCreated());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeCreate + 1);
        Muster16Abg testMuster16Abg = muster16AbgList.get(muster16AbgList.size() - 1);
        assertThat(testMuster16Abg.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testMuster16Abg.getLieferDat()).isEqualTo(DEFAULT_LIEFER_DAT);
        assertThat(testMuster16Abg.getaPeriode()).isEqualTo(DEFAULT_A_PERIODE);
        assertThat(testMuster16Abg.getArbPlatz()).isEqualTo(DEFAULT_ARB_PLATZ);
        assertThat(testMuster16Abg.getAvsId()).isEqualTo(DEFAULT_AVS_ID);
        assertThat(testMuster16Abg.getBediener()).isEqualTo(DEFAULT_BEDIENER);
        assertThat(testMuster16Abg.getZuzahlung()).isEqualTo(DEFAULT_ZUZAHLUNG);
        assertThat(testMuster16Abg.getGesBrutto()).isEqualTo(DEFAULT_GES_BRUTTO);
    }

    @Test
    @Transactional
    void createMuster16AbgWithExistingId() throws Exception {
        // Create the Muster16Abg with an existing ID
        muster16Abg.setId(1L);

        int databaseSizeBeforeCreate = muster16AbgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuster16AbgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abg)))
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuster16Abgs() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        // Get all the muster16AbgList
        restMuster16AbgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muster16Abg.getId().intValue())))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].lieferDat").value(hasItem(DEFAULT_LIEFER_DAT)))
            .andExpect(jsonPath("$.[*].aPeriode").value(hasItem(DEFAULT_A_PERIODE)))
            .andExpect(jsonPath("$.[*].arbPlatz").value(hasItem(DEFAULT_ARB_PLATZ)))
            .andExpect(jsonPath("$.[*].avsId").value(hasItem(DEFAULT_AVS_ID)))
            .andExpect(jsonPath("$.[*].bediener").value(hasItem(DEFAULT_BEDIENER)))
            .andExpect(jsonPath("$.[*].zuzahlung").value(hasItem(DEFAULT_ZUZAHLUNG)))
            .andExpect(jsonPath("$.[*].gesBrutto").value(hasItem(DEFAULT_GES_BRUTTO)));
    }

    @Test
    @Transactional
    void getMuster16Abg() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        // Get the muster16Abg
        restMuster16AbgMockMvc
            .perform(get(ENTITY_API_URL_ID, muster16Abg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(muster16Abg.getId().intValue()))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.lieferDat").value(DEFAULT_LIEFER_DAT))
            .andExpect(jsonPath("$.aPeriode").value(DEFAULT_A_PERIODE))
            .andExpect(jsonPath("$.arbPlatz").value(DEFAULT_ARB_PLATZ))
            .andExpect(jsonPath("$.avsId").value(DEFAULT_AVS_ID))
            .andExpect(jsonPath("$.bediener").value(DEFAULT_BEDIENER))
            .andExpect(jsonPath("$.zuzahlung").value(DEFAULT_ZUZAHLUNG))
            .andExpect(jsonPath("$.gesBrutto").value(DEFAULT_GES_BRUTTO));
    }

    @Test
    @Transactional
    void getNonExistingMuster16Abg() throws Exception {
        // Get the muster16Abg
        restMuster16AbgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMuster16Abg() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();

        // Update the muster16Abg
        Muster16Abg updatedMuster16Abg = muster16AbgRepository.findById(muster16Abg.getId()).get();
        // Disconnect from session so that the updates on updatedMuster16Abg are not directly saved in db
        em.detach(updatedMuster16Abg);
        updatedMuster16Abg
            .apoIk(UPDATED_APO_IK)
            .lieferDat(UPDATED_LIEFER_DAT)
            .aPeriode(UPDATED_A_PERIODE)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .zuzahlung(UPDATED_ZUZAHLUNG)
            .gesBrutto(UPDATED_GES_BRUTTO);

        restMuster16AbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuster16Abg.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMuster16Abg))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abg testMuster16Abg = muster16AbgList.get(muster16AbgList.size() - 1);
        assertThat(testMuster16Abg.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testMuster16Abg.getLieferDat()).isEqualTo(UPDATED_LIEFER_DAT);
        assertThat(testMuster16Abg.getaPeriode()).isEqualTo(UPDATED_A_PERIODE);
        assertThat(testMuster16Abg.getArbPlatz()).isEqualTo(UPDATED_ARB_PLATZ);
        assertThat(testMuster16Abg.getAvsId()).isEqualTo(UPDATED_AVS_ID);
        assertThat(testMuster16Abg.getBediener()).isEqualTo(UPDATED_BEDIENER);
        assertThat(testMuster16Abg.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
        assertThat(testMuster16Abg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
    }

    @Test
    @Transactional
    void putNonExistingMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, muster16Abg.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abg)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuster16AbgWithPatch() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();

        // Update the muster16Abg using partial update
        Muster16Abg partialUpdatedMuster16Abg = new Muster16Abg();
        partialUpdatedMuster16Abg.setId(muster16Abg.getId());

        partialUpdatedMuster16Abg.lieferDat(UPDATED_LIEFER_DAT).arbPlatz(UPDATED_ARB_PLATZ).gesBrutto(UPDATED_GES_BRUTTO);

        restMuster16AbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Abg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Abg))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abg testMuster16Abg = muster16AbgList.get(muster16AbgList.size() - 1);
        assertThat(testMuster16Abg.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testMuster16Abg.getLieferDat()).isEqualTo(UPDATED_LIEFER_DAT);
        assertThat(testMuster16Abg.getaPeriode()).isEqualTo(DEFAULT_A_PERIODE);
        assertThat(testMuster16Abg.getArbPlatz()).isEqualTo(UPDATED_ARB_PLATZ);
        assertThat(testMuster16Abg.getAvsId()).isEqualTo(DEFAULT_AVS_ID);
        assertThat(testMuster16Abg.getBediener()).isEqualTo(DEFAULT_BEDIENER);
        assertThat(testMuster16Abg.getZuzahlung()).isEqualTo(DEFAULT_ZUZAHLUNG);
        assertThat(testMuster16Abg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
    }

    @Test
    @Transactional
    void fullUpdateMuster16AbgWithPatch() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();

        // Update the muster16Abg using partial update
        Muster16Abg partialUpdatedMuster16Abg = new Muster16Abg();
        partialUpdatedMuster16Abg.setId(muster16Abg.getId());

        partialUpdatedMuster16Abg
            .apoIk(UPDATED_APO_IK)
            .lieferDat(UPDATED_LIEFER_DAT)
            .aPeriode(UPDATED_A_PERIODE)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .zuzahlung(UPDATED_ZUZAHLUNG)
            .gesBrutto(UPDATED_GES_BRUTTO);

        restMuster16AbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Abg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Abg))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abg testMuster16Abg = muster16AbgList.get(muster16AbgList.size() - 1);
        assertThat(testMuster16Abg.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testMuster16Abg.getLieferDat()).isEqualTo(UPDATED_LIEFER_DAT);
        assertThat(testMuster16Abg.getaPeriode()).isEqualTo(UPDATED_A_PERIODE);
        assertThat(testMuster16Abg.getArbPlatz()).isEqualTo(UPDATED_ARB_PLATZ);
        assertThat(testMuster16Abg.getAvsId()).isEqualTo(UPDATED_AVS_ID);
        assertThat(testMuster16Abg.getBediener()).isEqualTo(UPDATED_BEDIENER);
        assertThat(testMuster16Abg.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
        assertThat(testMuster16Abg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
    }

    @Test
    @Transactional
    void patchNonExistingMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, muster16Abg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuster16Abg() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbgRepository.findAll().size();
        muster16Abg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbgMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(muster16Abg))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Abg in the database
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuster16Abg() throws Exception {
        // Initialize the database
        muster16AbgRepository.saveAndFlush(muster16Abg);

        int databaseSizeBeforeDelete = muster16AbgRepository.findAll().size();

        // Delete the muster16Abg
        restMuster16AbgMockMvc
            .perform(delete(ENTITY_API_URL_ID, muster16Abg.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Muster16Abg> muster16AbgList = muster16AbgRepository.findAll();
        assertThat(muster16AbgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
