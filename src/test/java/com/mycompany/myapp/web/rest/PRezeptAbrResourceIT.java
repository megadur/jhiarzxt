package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PRezeptAbr;
import com.mycompany.myapp.repository.PRezeptAbrRepository;
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
 * Integration tests for the {@link PRezeptAbrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PRezeptAbrResourceIT {

    private static final String DEFAULT_P_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BETRAG_RAB_A = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_RAB_A = "BBBBBBBBBB";

    private static final String DEFAULT_BETRAG_RAB_H = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_RAB_H = "BBBBBBBBBB";

    private static final String DEFAULT_BETRAG_APO_AUSZ = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_APO_AUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-rezept-abrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PRezeptAbrRepository pRezeptAbrRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPRezeptAbrMockMvc;

    private PRezeptAbr pRezeptAbr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptAbr createEntity(EntityManager em) {
        PRezeptAbr pRezeptAbr = new PRezeptAbr()
            .pRezeptId(DEFAULT_P_REZEPT_ID)
            .betragRabA(DEFAULT_BETRAG_RAB_A)
            .betragRabH(DEFAULT_BETRAG_RAB_H)
            .betragApoAusz(DEFAULT_BETRAG_APO_AUSZ);
        return pRezeptAbr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptAbr createUpdatedEntity(EntityManager em) {
        PRezeptAbr pRezeptAbr = new PRezeptAbr()
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .betragRabA(UPDATED_BETRAG_RAB_A)
            .betragRabH(UPDATED_BETRAG_RAB_H)
            .betragApoAusz(UPDATED_BETRAG_APO_AUSZ);
        return pRezeptAbr;
    }

    @BeforeEach
    public void initTest() {
        pRezeptAbr = createEntity(em);
    }

    @Test
    @Transactional
    void createPRezeptAbr() throws Exception {
        int databaseSizeBeforeCreate = pRezeptAbrRepository.findAll().size();
        // Create the PRezeptAbr
        restPRezeptAbrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbr)))
            .andExpect(status().isCreated());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeCreate + 1);
        PRezeptAbr testPRezeptAbr = pRezeptAbrList.get(pRezeptAbrList.size() - 1);
        assertThat(testPRezeptAbr.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezeptAbr.getBetragRabA()).isEqualTo(DEFAULT_BETRAG_RAB_A);
        assertThat(testPRezeptAbr.getBetragRabH()).isEqualTo(DEFAULT_BETRAG_RAB_H);
        assertThat(testPRezeptAbr.getBetragApoAusz()).isEqualTo(DEFAULT_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void createPRezeptAbrWithExistingId() throws Exception {
        // Create the PRezeptAbr with an existing ID
        pRezeptAbr.setId(1L);

        int databaseSizeBeforeCreate = pRezeptAbrRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPRezeptAbrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbr)))
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPRezeptAbrs() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        // Get all the pRezeptAbrList
        restPRezeptAbrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRezeptAbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].pRezeptId").value(hasItem(DEFAULT_P_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].betragRabA").value(hasItem(DEFAULT_BETRAG_RAB_A)))
            .andExpect(jsonPath("$.[*].betragRabH").value(hasItem(DEFAULT_BETRAG_RAB_H)))
            .andExpect(jsonPath("$.[*].betragApoAusz").value(hasItem(DEFAULT_BETRAG_APO_AUSZ)));
    }

    @Test
    @Transactional
    void getPRezeptAbr() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        // Get the pRezeptAbr
        restPRezeptAbrMockMvc
            .perform(get(ENTITY_API_URL_ID, pRezeptAbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pRezeptAbr.getId().intValue()))
            .andExpect(jsonPath("$.pRezeptId").value(DEFAULT_P_REZEPT_ID))
            .andExpect(jsonPath("$.betragRabA").value(DEFAULT_BETRAG_RAB_A))
            .andExpect(jsonPath("$.betragRabH").value(DEFAULT_BETRAG_RAB_H))
            .andExpect(jsonPath("$.betragApoAusz").value(DEFAULT_BETRAG_APO_AUSZ));
    }

    @Test
    @Transactional
    void getNonExistingPRezeptAbr() throws Exception {
        // Get the pRezeptAbr
        restPRezeptAbrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPRezeptAbr() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();

        // Update the pRezeptAbr
        PRezeptAbr updatedPRezeptAbr = pRezeptAbrRepository.findById(pRezeptAbr.getId()).get();
        // Disconnect from session so that the updates on updatedPRezeptAbr are not directly saved in db
        em.detach(updatedPRezeptAbr);
        updatedPRezeptAbr
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .betragRabA(UPDATED_BETRAG_RAB_A)
            .betragRabH(UPDATED_BETRAG_RAB_H)
            .betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restPRezeptAbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPRezeptAbr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPRezeptAbr))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbr testPRezeptAbr = pRezeptAbrList.get(pRezeptAbrList.size() - 1);
        assertThat(testPRezeptAbr.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezeptAbr.getBetragRabA()).isEqualTo(UPDATED_BETRAG_RAB_A);
        assertThat(testPRezeptAbr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testPRezeptAbr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void putNonExistingPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pRezeptAbr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbr))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbr))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbr)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePRezeptAbrWithPatch() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();

        // Update the pRezeptAbr using partial update
        PRezeptAbr partialUpdatedPRezeptAbr = new PRezeptAbr();
        partialUpdatedPRezeptAbr.setId(pRezeptAbr.getId());

        partialUpdatedPRezeptAbr.betragRabH(UPDATED_BETRAG_RAB_H).betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restPRezeptAbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptAbr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptAbr))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbr testPRezeptAbr = pRezeptAbrList.get(pRezeptAbrList.size() - 1);
        assertThat(testPRezeptAbr.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezeptAbr.getBetragRabA()).isEqualTo(DEFAULT_BETRAG_RAB_A);
        assertThat(testPRezeptAbr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testPRezeptAbr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void fullUpdatePRezeptAbrWithPatch() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();

        // Update the pRezeptAbr using partial update
        PRezeptAbr partialUpdatedPRezeptAbr = new PRezeptAbr();
        partialUpdatedPRezeptAbr.setId(pRezeptAbr.getId());

        partialUpdatedPRezeptAbr
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .betragRabA(UPDATED_BETRAG_RAB_A)
            .betragRabH(UPDATED_BETRAG_RAB_H)
            .betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restPRezeptAbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptAbr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptAbr))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbr testPRezeptAbr = pRezeptAbrList.get(pRezeptAbrList.size() - 1);
        assertThat(testPRezeptAbr.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezeptAbr.getBetragRabA()).isEqualTo(UPDATED_BETRAG_RAB_A);
        assertThat(testPRezeptAbr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testPRezeptAbr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void patchNonExistingPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pRezeptAbr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbr))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbr))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPRezeptAbr() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbrRepository.findAll().size();
        pRezeptAbr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbrMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pRezeptAbr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptAbr in the database
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePRezeptAbr() throws Exception {
        // Initialize the database
        pRezeptAbrRepository.saveAndFlush(pRezeptAbr);

        int databaseSizeBeforeDelete = pRezeptAbrRepository.findAll().size();

        // Delete the pRezeptAbr
        restPRezeptAbrMockMvc
            .perform(delete(ENTITY_API_URL_ID, pRezeptAbr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PRezeptAbr> pRezeptAbrList = pRezeptAbrRepository.findAll();
        assertThat(pRezeptAbrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
