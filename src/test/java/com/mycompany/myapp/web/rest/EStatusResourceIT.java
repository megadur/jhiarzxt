package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EStatus;
import com.mycompany.myapp.repository.EStatusRepository;
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
 * Integration tests for the {@link EStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EStatusResourceIT {

    private static final String DEFAULT_WERT = "AAAAAAAAAA";
    private static final String UPDATED_WERT = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHREIBUNG = "AAAAAAAAAA";
    private static final String UPDATED_BESCHREIBUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/e-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EStatusRepository eStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEStatusMockMvc;

    private EStatus eStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EStatus createEntity(EntityManager em) {
        EStatus eStatus = new EStatus().wert(DEFAULT_WERT).beschreibung(DEFAULT_BESCHREIBUNG);
        return eStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EStatus createUpdatedEntity(EntityManager em) {
        EStatus eStatus = new EStatus().wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);
        return eStatus;
    }

    @BeforeEach
    public void initTest() {
        eStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createEStatus() throws Exception {
        int databaseSizeBeforeCreate = eStatusRepository.findAll().size();
        // Create the EStatus
        restEStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eStatus)))
            .andExpect(status().isCreated());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeCreate + 1);
        EStatus testEStatus = eStatusList.get(eStatusList.size() - 1);
        assertThat(testEStatus.getWert()).isEqualTo(DEFAULT_WERT);
        assertThat(testEStatus.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void createEStatusWithExistingId() throws Exception {
        // Create the EStatus with an existing ID
        eStatus.setId(1L);

        int databaseSizeBeforeCreate = eStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eStatus)))
            .andExpect(status().isBadRequest());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEStatuses() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        // Get all the eStatusList
        restEStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].wert").value(hasItem(DEFAULT_WERT)))
            .andExpect(jsonPath("$.[*].beschreibung").value(hasItem(DEFAULT_BESCHREIBUNG)));
    }

    @Test
    @Transactional
    void getEStatus() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        // Get the eStatus
        restEStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, eStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eStatus.getId().intValue()))
            .andExpect(jsonPath("$.wert").value(DEFAULT_WERT))
            .andExpect(jsonPath("$.beschreibung").value(DEFAULT_BESCHREIBUNG));
    }

    @Test
    @Transactional
    void getNonExistingEStatus() throws Exception {
        // Get the eStatus
        restEStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEStatus() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();

        // Update the eStatus
        EStatus updatedEStatus = eStatusRepository.findById(eStatus.getId()).get();
        // Disconnect from session so that the updates on updatedEStatus are not directly saved in db
        em.detach(updatedEStatus);
        updatedEStatus.wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);

        restEStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEStatus))
            )
            .andExpect(status().isOk());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
        EStatus testEStatus = eStatusList.get(eStatusList.size() - 1);
        assertThat(testEStatus.getWert()).isEqualTo(UPDATED_WERT);
        assertThat(testEStatus.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void putNonExistingEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEStatusWithPatch() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();

        // Update the eStatus using partial update
        EStatus partialUpdatedEStatus = new EStatus();
        partialUpdatedEStatus.setId(eStatus.getId());

        restEStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEStatus))
            )
            .andExpect(status().isOk());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
        EStatus testEStatus = eStatusList.get(eStatusList.size() - 1);
        assertThat(testEStatus.getWert()).isEqualTo(DEFAULT_WERT);
        assertThat(testEStatus.getBeschreibung()).isEqualTo(DEFAULT_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void fullUpdateEStatusWithPatch() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();

        // Update the eStatus using partial update
        EStatus partialUpdatedEStatus = new EStatus();
        partialUpdatedEStatus.setId(eStatus.getId());

        partialUpdatedEStatus.wert(UPDATED_WERT).beschreibung(UPDATED_BESCHREIBUNG);

        restEStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEStatus))
            )
            .andExpect(status().isOk());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
        EStatus testEStatus = eStatusList.get(eStatusList.size() - 1);
        assertThat(testEStatus.getWert()).isEqualTo(UPDATED_WERT);
        assertThat(testEStatus.getBeschreibung()).isEqualTo(UPDATED_BESCHREIBUNG);
    }

    @Test
    @Transactional
    void patchNonExistingEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEStatus() throws Exception {
        int databaseSizeBeforeUpdate = eStatusRepository.findAll().size();
        eStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EStatus in the database
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEStatus() throws Exception {
        // Initialize the database
        eStatusRepository.saveAndFlush(eStatus);

        int databaseSizeBeforeDelete = eStatusRepository.findAll().size();

        // Delete the eStatus
        restEStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, eStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EStatus> eStatusList = eStatusRepository.findAll();
        assertThat(eStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
