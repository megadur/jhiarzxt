package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PStatus;
import com.mycompany.myapp.repository.PStatusRepository;
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
 * Integration tests for the {@link PStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PStatusResourceIT {

    private static final String DEFAULT_WERT = "AAAAAAAAAA";
    private static final String UPDATED_WERT = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PStatusRepository pStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPStatusMockMvc;

    private PStatus pStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PStatus createEntity(EntityManager em) {
        PStatus pStatus = new PStatus().wert(DEFAULT_WERT).beschreibung(DEFAULT_BESCHREIBUNG);
        return pStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PStatus createUpdatedEntity(EntityManager em) {
        PStatus pStatus = new PStatus().wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);
        return pStatus;
    }

    @BeforeEach
    public void initTest() {
        pStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createPStatus() throws Exception {
        int databaseSizeBeforeCreate = pStatusRepository.findAll().size();
        // Create the PStatus
        restPStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatus)))
            .andExpect(status().isCreated());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PStatus testPStatus = pStatusList.get(pStatusList.size() - 1);
        assertThat(testPStatus.getWert()).isEqualTo(DEFAULT_WERT);
        assertThat(testPStatus.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void createPStatusWithExistingId() throws Exception {
        // Create the PStatus with an existing ID
        pStatus.setId(1L);

        int databaseSizeBeforeCreate = pStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPStatuses() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        // Get all the pStatusList
        restPStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].wert").value(hasItem(DEFAULT_WERT)))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG)));
    }

    @Test
    @Transactional
    void getPStatus() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        // Get the pStatus
        restPStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, pStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pStatus.getId().intValue()))
            .andExpect(jsonPath("$.wert").value(DEFAULT_WERT))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG));
    }

    @Test
    @Transactional
    void getNonExistingPStatus() throws Exception {
        // Get the pStatus
        restPStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPStatus() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();

        // Update the pStatus
        PStatus updatedPStatus = pStatusRepository.findById(pStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPStatus are not directly saved in db
        em.detach(updatedPStatus);
        updatedPStatus.wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);

        restPStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPStatus))
            )
            .andExpect(status().isOk());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
        PStatus testPStatus = pStatusList.get(pStatusList.size() - 1);
        assertThat(testPStatus.getWert()).isEqualTo(UPDATED_WERT);
        assertThat(testPStatus.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void putNonExistingPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePStatusWithPatch() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();

        // Update the pStatus using partial update
        PStatus partialUpdatedPStatus = new PStatus();
        partialUpdatedPStatus.setId(pStatus.getId());

        partialUpdatedPStatus.beschreibung(UPDATED_BESCHREIBUNG);

        restPStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPStatus))
            )
            .andExpect(status().isOk());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
        PStatus testPStatus = pStatusList.get(pStatusList.size() - 1);
        assertThat(testPStatus.getWert()).isEqualTo(DEFAULT_WERT);
        assertThat(testPStatus.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void fullUpdatePStatusWithPatch() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();

        // Update the pStatus using partial update
        PStatus partialUpdatedPStatus = new PStatus();
        partialUpdatedPStatus.setId(pStatus.getId());

        partialUpdatedPStatus.wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);

        restPStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPStatus))
            )
            .andExpect(status().isOk());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
        PStatus testPStatus = pStatusList.get(pStatusList.size() - 1);
        assertThat(testPStatus.getWert()).isEqualTo(UPDATED_WERT);
        assertThat(testPStatus.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void patchNonExistingPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPStatus() throws Exception {
        int databaseSizeBeforeUpdate = pStatusRepository.findAll().size();
        pStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PStatus in the database
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePStatus() throws Exception {
        // Initialize the database
        pStatusRepository.saveAndFlush(pStatus);

        int databaseSizeBeforeDelete = pStatusRepository.findAll().size();

        // Delete the pStatus
        restPStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, pStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PStatus> pStatusList = pStatusRepository.findAll();
        assertThat(pStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
