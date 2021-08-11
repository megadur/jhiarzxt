package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PRezept;
import com.mycompany.myapp.repository.PRezeptRepository;
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
 * Integration tests for the {@link PRezeptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PRezeptResourceIT {

    private static final String DEFAULT_P_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LIEFERDAT = "AAAAAAAAAA";
    private static final String UPDATED_LIEFERDAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIEFERUNG_ID = "AAAAAAAAAA";
    private static final String UPDATED_LIEFERUNG_ID = "BBBBBBBBBB";

    private static final String DEFAULT_A_PERIODE = "AAAAAAAAAA";
    private static final String UPDATED_A_PERIODE = "BBBBBBBBBB";

    private static final String DEFAULT_AB_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_AB_DATUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-rezepts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PRezeptRepository pRezeptRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPRezeptMockMvc;

    private PRezept pRezept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezept createEntity(EntityManager em) {
        PRezept pRezept = new PRezept()
            .pRezeptId(DEFAULT_P_REZEPT_ID)
            .lieferdat(DEFAULT_LIEFERDAT)
            .lieferungId(DEFAULT_LIEFERUNG_ID)
            .aPeriode(DEFAULT_A_PERIODE)
            .abDatum(DEFAULT_AB_DATUM);
        return pRezept;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezept createUpdatedEntity(EntityManager em) {
        PRezept pRezept = new PRezept()
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .lieferdat(UPDATED_LIEFERDAT)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .aPeriode(UPDATED_A_PERIODE)
            .abDatum(UPDATED_AB_DATUM);
        return pRezept;
    }

    @BeforeEach
    public void initTest() {
        pRezept = createEntity(em);
    }

    @Test
    @Transactional
    void createPRezept() throws Exception {
        int databaseSizeBeforeCreate = pRezeptRepository.findAll().size();
        // Create the PRezept
        restPRezeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezept)))
            .andExpect(status().isCreated());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeCreate + 1);
        PRezept testPRezept = pRezeptList.get(pRezeptList.size() - 1);
        assertThat(testPRezept.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezept.getLieferdat()).isEqualTo(DEFAULT_LIEFERDAT);
        assertThat(testPRezept.getLieferungId()).isEqualTo(DEFAULT_LIEFERUNG_ID);
        assertThat(testPRezept.getaPeriode()).isEqualTo(DEFAULT_A_PERIODE);
        assertThat(testPRezept.getAbDatum()).isEqualTo(DEFAULT_AB_DATUM);
    }

    @Test
    @Transactional
    void createPRezeptWithExistingId() throws Exception {
        // Create the PRezept with an existing ID
        pRezept.setId(1L);

        int databaseSizeBeforeCreate = pRezeptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPRezeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezept)))
            .andExpect(status().isBadRequest());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPRezepts() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        // Get all the pRezeptList
        restPRezeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRezept.getId().intValue())))
            .andExpect(jsonPath("$.[*].pRezeptId").value(hasItem(DEFAULT_P_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].lieferdat").value(hasItem(DEFAULT_LIEFERDAT)))
            .andExpect(jsonPath("$.[*].lieferungId").value(hasItem(DEFAULT_LIEFERUNG_ID)))
            .andExpect(jsonPath("$.[*].aPeriode").value(hasItem(DEFAULT_A_PERIODE)))
            .andExpect(jsonPath("$.[*].abDatum").value(hasItem(DEFAULT_AB_DATUM)));
    }

    @Test
    @Transactional
    void getPRezept() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        // Get the pRezept
        restPRezeptMockMvc
            .perform(get(ENTITY_API_URL_ID, pRezept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pRezept.getId().intValue()))
            .andExpect(jsonPath("$.pRezeptId").value(DEFAULT_P_REZEPT_ID))
            .andExpect(jsonPath("$.lieferdat").value(DEFAULT_LIEFERDAT))
            .andExpect(jsonPath("$.lieferungId").value(DEFAULT_LIEFERUNG_ID))
            .andExpect(jsonPath("$.aPeriode").value(DEFAULT_A_PERIODE))
            .andExpect(jsonPath("$.abDatum").value(DEFAULT_AB_DATUM));
    }

    @Test
    @Transactional
    void getNonExistingPRezept() throws Exception {
        // Get the pRezept
        restPRezeptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPRezept() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();

        // Update the pRezept
        PRezept updatedPRezept = pRezeptRepository.findById(pRezept.getId()).get();
        // Disconnect from session so that the updates on updatedPRezept are not directly saved in db
        em.detach(updatedPRezept);
        updatedPRezept
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .lieferdat(UPDATED_LIEFERDAT)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .aPeriode(UPDATED_A_PERIODE)
            .abDatum(UPDATED_AB_DATUM);

        restPRezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPRezept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPRezept))
            )
            .andExpect(status().isOk());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
        PRezept testPRezept = pRezeptList.get(pRezeptList.size() - 1);
        assertThat(testPRezept.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezept.getLieferdat()).isEqualTo(UPDATED_LIEFERDAT);
        assertThat(testPRezept.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testPRezept.getaPeriode()).isEqualTo(UPDATED_A_PERIODE);
        assertThat(testPRezept.getAbDatum()).isEqualTo(UPDATED_AB_DATUM);
    }

    @Test
    @Transactional
    void putNonExistingPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pRezept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePRezeptWithPatch() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();

        // Update the pRezept using partial update
        PRezept partialUpdatedPRezept = new PRezept();
        partialUpdatedPRezept.setId(pRezept.getId());

        partialUpdatedPRezept.lieferungId(UPDATED_LIEFERUNG_ID).aPeriode(UPDATED_A_PERIODE).abDatum(UPDATED_AB_DATUM);

        restPRezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezept))
            )
            .andExpect(status().isOk());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
        PRezept testPRezept = pRezeptList.get(pRezeptList.size() - 1);
        assertThat(testPRezept.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezept.getLieferdat()).isEqualTo(DEFAULT_LIEFERDAT);
        assertThat(testPRezept.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testPRezept.getaPeriode()).isEqualTo(UPDATED_A_PERIODE);
        assertThat(testPRezept.getAbDatum()).isEqualTo(UPDATED_AB_DATUM);
    }

    @Test
    @Transactional
    void fullUpdatePRezeptWithPatch() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();

        // Update the pRezept using partial update
        PRezept partialUpdatedPRezept = new PRezept();
        partialUpdatedPRezept.setId(pRezept.getId());

        partialUpdatedPRezept
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .lieferdat(UPDATED_LIEFERDAT)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .aPeriode(UPDATED_A_PERIODE)
            .abDatum(UPDATED_AB_DATUM);

        restPRezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezept))
            )
            .andExpect(status().isOk());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
        PRezept testPRezept = pRezeptList.get(pRezeptList.size() - 1);
        assertThat(testPRezept.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezept.getLieferdat()).isEqualTo(UPDATED_LIEFERDAT);
        assertThat(testPRezept.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testPRezept.getaPeriode()).isEqualTo(UPDATED_A_PERIODE);
        assertThat(testPRezept.getAbDatum()).isEqualTo(UPDATED_AB_DATUM);
    }

    @Test
    @Transactional
    void patchNonExistingPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pRezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPRezept() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptRepository.findAll().size();
        pRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pRezept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezept in the database
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePRezept() throws Exception {
        // Initialize the database
        pRezeptRepository.saveAndFlush(pRezept);

        int databaseSizeBeforeDelete = pRezeptRepository.findAll().size();

        // Delete the pRezept
        restPRezeptMockMvc
            .perform(delete(ENTITY_API_URL_ID, pRezept.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PRezept> pRezeptList = pRezeptRepository.findAll();
        assertThat(pRezeptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
