package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.M16Status;
import com.mycompany.myapp.repository.M16StatusRepository;
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
 * Integration tests for the {@link M16StatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class M16StatusResourceIT {

    private static final String DEFAULT_M_16_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_M_16_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/m-16-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private M16StatusRepository m16StatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restM16StatusMockMvc;

    private M16Status m16Status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M16Status createEntity(EntityManager em) {
        M16Status m16Status = new M16Status().m16StatusId(DEFAULT_M_16_STATUS_ID).status(DEFAULT_STATUS);
        return m16Status;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M16Status createUpdatedEntity(EntityManager em) {
        M16Status m16Status = new M16Status().m16StatusId(UPDATED_M_16_STATUS_ID).status(UPDATED_STATUS);
        return m16Status;
    }

    @BeforeEach
    public void initTest() {
        m16Status = createEntity(em);
    }

    @Test
    @Transactional
    void createM16Status() throws Exception {
        int databaseSizeBeforeCreate = m16StatusRepository.findAll().size();
        // Create the M16Status
        restM16StatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(m16Status)))
            .andExpect(status().isCreated());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeCreate + 1);
        M16Status testM16Status = m16StatusList.get(m16StatusList.size() - 1);
        assertThat(testM16Status.getm16StatusId()).isEqualTo(DEFAULT_M_16_STATUS_ID);
        assertThat(testM16Status.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createM16StatusWithExistingId() throws Exception {
        // Create the M16Status with an existing ID
        m16Status.setId(1L);

        int databaseSizeBeforeCreate = m16StatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restM16StatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(m16Status)))
            .andExpect(status().isBadRequest());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllM16Statuses() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        // Get all the m16StatusList
        restM16StatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m16Status.getId().intValue())))
            .andExpect(jsonPath("$.[*].m16StatusId").value(hasItem(DEFAULT_M_16_STATUS_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getM16Status() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        // Get the m16Status
        restM16StatusMockMvc
            .perform(get(ENTITY_API_URL_ID, m16Status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(m16Status.getId().intValue()))
            .andExpect(jsonPath("$.m16StatusId").value(DEFAULT_M_16_STATUS_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingM16Status() throws Exception {
        // Get the m16Status
        restM16StatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewM16Status() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();

        // Update the m16Status
        M16Status updatedM16Status = m16StatusRepository.findById(m16Status.getId()).get();
        // Disconnect from session so that the updates on updatedM16Status are not directly saved in db
        em.detach(updatedM16Status);
        updatedM16Status.m16StatusId(UPDATED_M_16_STATUS_ID).status(UPDATED_STATUS);

        restM16StatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedM16Status.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedM16Status))
            )
            .andExpect(status().isOk());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
        M16Status testM16Status = m16StatusList.get(m16StatusList.size() - 1);
        assertThat(testM16Status.getm16StatusId()).isEqualTo(UPDATED_M_16_STATUS_ID);
        assertThat(testM16Status.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, m16Status.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(m16Status))
            )
            .andExpect(status().isBadRequest());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(m16Status))
            )
            .andExpect(status().isBadRequest());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(m16Status)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateM16StatusWithPatch() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();

        // Update the m16Status using partial update
        M16Status partialUpdatedM16Status = new M16Status();
        partialUpdatedM16Status.setId(m16Status.getId());

        partialUpdatedM16Status.status(UPDATED_STATUS);

        restM16StatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedM16Status.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedM16Status))
            )
            .andExpect(status().isOk());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
        M16Status testM16Status = m16StatusList.get(m16StatusList.size() - 1);
        assertThat(testM16Status.getm16StatusId()).isEqualTo(DEFAULT_M_16_STATUS_ID);
        assertThat(testM16Status.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateM16StatusWithPatch() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();

        // Update the m16Status using partial update
        M16Status partialUpdatedM16Status = new M16Status();
        partialUpdatedM16Status.setId(m16Status.getId());

        partialUpdatedM16Status.m16StatusId(UPDATED_M_16_STATUS_ID).status(UPDATED_STATUS);

        restM16StatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedM16Status.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedM16Status))
            )
            .andExpect(status().isOk());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
        M16Status testM16Status = m16StatusList.get(m16StatusList.size() - 1);
        assertThat(testM16Status.getm16StatusId()).isEqualTo(UPDATED_M_16_STATUS_ID);
        assertThat(testM16Status.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, m16Status.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(m16Status))
            )
            .andExpect(status().isBadRequest());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(m16Status))
            )
            .andExpect(status().isBadRequest());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamM16Status() throws Exception {
        int databaseSizeBeforeUpdate = m16StatusRepository.findAll().size();
        m16Status.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restM16StatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(m16Status))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the M16Status in the database
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteM16Status() throws Exception {
        // Initialize the database
        m16StatusRepository.saveAndFlush(m16Status);

        int databaseSizeBeforeDelete = m16StatusRepository.findAll().size();

        // Delete the m16Status
        restM16StatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, m16Status.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<M16Status> m16StatusList = m16StatusRepository.findAll();
        assertThat(m16StatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
