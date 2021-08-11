package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Rechenzentrum;
import com.mycompany.myapp.repository.RechenzentrumRepository;
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
 * Integration tests for the {@link RechenzentrumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RechenzentrumResourceIT {

    private static final String DEFAULT_I_D = "AAAAAAAAAA";
    private static final String UPDATED_I_D = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rechenzentrums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RechenzentrumRepository rechenzentrumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRechenzentrumMockMvc;

    private Rechenzentrum rechenzentrum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechenzentrum createEntity(EntityManager em) {
        Rechenzentrum rechenzentrum = new Rechenzentrum().iD(DEFAULT_I_D).name(DEFAULT_NAME);
        return rechenzentrum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechenzentrum createUpdatedEntity(EntityManager em) {
        Rechenzentrum rechenzentrum = new Rechenzentrum().iD(UPDATED_I_D).name(UPDATED_NAME);
        return rechenzentrum;
    }

    @BeforeEach
    public void initTest() {
        rechenzentrum = createEntity(em);
    }

    @Test
    @Transactional
    void createRechenzentrum() throws Exception {
        int databaseSizeBeforeCreate = rechenzentrumRepository.findAll().size();
        // Create the Rechenzentrum
        restRechenzentrumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rechenzentrum)))
            .andExpect(status().isCreated());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeCreate + 1);
        Rechenzentrum testRechenzentrum = rechenzentrumList.get(rechenzentrumList.size() - 1);
        assertThat(testRechenzentrum.getiD()).isEqualTo(DEFAULT_I_D);
        assertThat(testRechenzentrum.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRechenzentrumWithExistingId() throws Exception {
        // Create the Rechenzentrum with an existing ID
        rechenzentrum.setId(1L);

        int databaseSizeBeforeCreate = rechenzentrumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechenzentrumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rechenzentrum)))
            .andExpect(status().isBadRequest());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRechenzentrums() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        // Get all the rechenzentrumList
        restRechenzentrumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rechenzentrum.getId().intValue())))
            .andExpect(jsonPath("$.[*].iD").value(hasItem(DEFAULT_I_D)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRechenzentrum() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        // Get the rechenzentrum
        restRechenzentrumMockMvc
            .perform(get(ENTITY_API_URL_ID, rechenzentrum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rechenzentrum.getId().intValue()))
            .andExpect(jsonPath("$.iD").value(DEFAULT_I_D))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRechenzentrum() throws Exception {
        // Get the rechenzentrum
        restRechenzentrumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRechenzentrum() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();

        // Update the rechenzentrum
        Rechenzentrum updatedRechenzentrum = rechenzentrumRepository.findById(rechenzentrum.getId()).get();
        // Disconnect from session so that the updates on updatedRechenzentrum are not directly saved in db
        em.detach(updatedRechenzentrum);
        updatedRechenzentrum.iD(UPDATED_I_D).name(UPDATED_NAME);

        restRechenzentrumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRechenzentrum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRechenzentrum))
            )
            .andExpect(status().isOk());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
        Rechenzentrum testRechenzentrum = rechenzentrumList.get(rechenzentrumList.size() - 1);
        assertThat(testRechenzentrum.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testRechenzentrum.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rechenzentrum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rechenzentrum))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rechenzentrum))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rechenzentrum)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRechenzentrumWithPatch() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();

        // Update the rechenzentrum using partial update
        Rechenzentrum partialUpdatedRechenzentrum = new Rechenzentrum();
        partialUpdatedRechenzentrum.setId(rechenzentrum.getId());

        partialUpdatedRechenzentrum.iD(UPDATED_I_D);

        restRechenzentrumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechenzentrum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRechenzentrum))
            )
            .andExpect(status().isOk());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
        Rechenzentrum testRechenzentrum = rechenzentrumList.get(rechenzentrumList.size() - 1);
        assertThat(testRechenzentrum.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testRechenzentrum.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRechenzentrumWithPatch() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();

        // Update the rechenzentrum using partial update
        Rechenzentrum partialUpdatedRechenzentrum = new Rechenzentrum();
        partialUpdatedRechenzentrum.setId(rechenzentrum.getId());

        partialUpdatedRechenzentrum.iD(UPDATED_I_D).name(UPDATED_NAME);

        restRechenzentrumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechenzentrum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRechenzentrum))
            )
            .andExpect(status().isOk());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
        Rechenzentrum testRechenzentrum = rechenzentrumList.get(rechenzentrumList.size() - 1);
        assertThat(testRechenzentrum.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testRechenzentrum.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rechenzentrum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rechenzentrum))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rechenzentrum))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRechenzentrum() throws Exception {
        int databaseSizeBeforeUpdate = rechenzentrumRepository.findAll().size();
        rechenzentrum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechenzentrumMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rechenzentrum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rechenzentrum in the database
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRechenzentrum() throws Exception {
        // Initialize the database
        rechenzentrumRepository.saveAndFlush(rechenzentrum);

        int databaseSizeBeforeDelete = rechenzentrumRepository.findAll().size();

        // Delete the rechenzentrum
        restRechenzentrumMockMvc
            .perform(delete(ENTITY_API_URL_ID, rechenzentrum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rechenzentrum> rechenzentrumList = rechenzentrumRepository.findAll();
        assertThat(rechenzentrumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
