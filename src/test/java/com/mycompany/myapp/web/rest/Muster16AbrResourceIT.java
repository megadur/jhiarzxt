package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Muster16Abr;
import com.mycompany.myapp.repository.Muster16AbrRepository;
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
 * Integration tests for the {@link Muster16AbrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Muster16AbrResourceIT {

    private static final String DEFAULT_BETRAG_RAB_A = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_RAB_A = "BBBBBBBBBB";

    private static final String DEFAULT_BETRAG_RAB_H = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_RAB_H = "BBBBBBBBBB";

    private static final String DEFAULT_BETRAG_APO_AUSZ = "AAAAAAAAAA";
    private static final String UPDATED_BETRAG_APO_AUSZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/muster-16-abrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Muster16AbrRepository muster16AbrRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuster16AbrMockMvc;

    private Muster16Abr muster16Abr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Abr createEntity(EntityManager em) {
        Muster16Abr muster16Abr = new Muster16Abr()
            .betragRabA(DEFAULT_BETRAG_RAB_A)
            .betragRabH(DEFAULT_BETRAG_RAB_H)
            .betragApoAusz(DEFAULT_BETRAG_APO_AUSZ);
        return muster16Abr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Abr createUpdatedEntity(EntityManager em) {
        Muster16Abr muster16Abr = new Muster16Abr()
            .betragRabA(UPDATED_BETRAG_RAB_A)
            .betragRabH(UPDATED_BETRAG_RAB_H)
            .betragApoAusz(UPDATED_BETRAG_APO_AUSZ);
        return muster16Abr;
    }

    @BeforeEach
    public void initTest() {
        muster16Abr = createEntity(em);
    }

    @Test
    @Transactional
    void createMuster16Abr() throws Exception {
        int databaseSizeBeforeCreate = muster16AbrRepository.findAll().size();
        // Create the Muster16Abr
        restMuster16AbrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abr)))
            .andExpect(status().isCreated());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeCreate + 1);
        Muster16Abr testMuster16Abr = muster16AbrList.get(muster16AbrList.size() - 1);
        assertThat(testMuster16Abr.getBetragRabA()).isEqualTo(DEFAULT_BETRAG_RAB_A);
        assertThat(testMuster16Abr.getBetragRabH()).isEqualTo(DEFAULT_BETRAG_RAB_H);
        assertThat(testMuster16Abr.getBetragApoAusz()).isEqualTo(DEFAULT_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void createMuster16AbrWithExistingId() throws Exception {
        // Create the Muster16Abr with an existing ID
        muster16Abr.setId(1L);

        int databaseSizeBeforeCreate = muster16AbrRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuster16AbrMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abr)))
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuster16Abrs() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        // Get all the muster16AbrList
        restMuster16AbrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muster16Abr.getId().intValue())))
            .andExpect(jsonPath("$.[*].betragRabA").value(hasItem(DEFAULT_BETRAG_RAB_A)))
            .andExpect(jsonPath("$.[*].betragRabH").value(hasItem(DEFAULT_BETRAG_RAB_H)))
            .andExpect(jsonPath("$.[*].betragApoAusz").value(hasItem(DEFAULT_BETRAG_APO_AUSZ)));
    }

    @Test
    @Transactional
    void getMuster16Abr() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        // Get the muster16Abr
        restMuster16AbrMockMvc
            .perform(get(ENTITY_API_URL_ID, muster16Abr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(muster16Abr.getId().intValue()))
            .andExpect(jsonPath("$.betragRabA").value(DEFAULT_BETRAG_RAB_A))
            .andExpect(jsonPath("$.betragRabH").value(DEFAULT_BETRAG_RAB_H))
            .andExpect(jsonPath("$.betragApoAusz").value(DEFAULT_BETRAG_APO_AUSZ));
    }

    @Test
    @Transactional
    void getNonExistingMuster16Abr() throws Exception {
        // Get the muster16Abr
        restMuster16AbrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMuster16Abr() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();

        // Update the muster16Abr
        Muster16Abr updatedMuster16Abr = muster16AbrRepository.findById(muster16Abr.getId()).get();
        // Disconnect from session so that the updates on updatedMuster16Abr are not directly saved in db
        em.detach(updatedMuster16Abr);
        updatedMuster16Abr.betragRabA(UPDATED_BETRAG_RAB_A).betragRabH(UPDATED_BETRAG_RAB_H).betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restMuster16AbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuster16Abr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMuster16Abr))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abr testMuster16Abr = muster16AbrList.get(muster16AbrList.size() - 1);
        assertThat(testMuster16Abr.getBetragRabA()).isEqualTo(UPDATED_BETRAG_RAB_A);
        assertThat(testMuster16Abr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testMuster16Abr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void putNonExistingMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, muster16Abr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Abr)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuster16AbrWithPatch() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();

        // Update the muster16Abr using partial update
        Muster16Abr partialUpdatedMuster16Abr = new Muster16Abr();
        partialUpdatedMuster16Abr.setId(muster16Abr.getId());

        partialUpdatedMuster16Abr.betragRabH(UPDATED_BETRAG_RAB_H).betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restMuster16AbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Abr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Abr))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abr testMuster16Abr = muster16AbrList.get(muster16AbrList.size() - 1);
        assertThat(testMuster16Abr.getBetragRabA()).isEqualTo(DEFAULT_BETRAG_RAB_A);
        assertThat(testMuster16Abr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testMuster16Abr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void fullUpdateMuster16AbrWithPatch() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();

        // Update the muster16Abr using partial update
        Muster16Abr partialUpdatedMuster16Abr = new Muster16Abr();
        partialUpdatedMuster16Abr.setId(muster16Abr.getId());

        partialUpdatedMuster16Abr.betragRabA(UPDATED_BETRAG_RAB_A).betragRabH(UPDATED_BETRAG_RAB_H).betragApoAusz(UPDATED_BETRAG_APO_AUSZ);

        restMuster16AbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Abr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Abr))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
        Muster16Abr testMuster16Abr = muster16AbrList.get(muster16AbrList.size() - 1);
        assertThat(testMuster16Abr.getBetragRabA()).isEqualTo(UPDATED_BETRAG_RAB_A);
        assertThat(testMuster16Abr.getBetragRabH()).isEqualTo(UPDATED_BETRAG_RAB_H);
        assertThat(testMuster16Abr.getBetragApoAusz()).isEqualTo(UPDATED_BETRAG_APO_AUSZ);
    }

    @Test
    @Transactional
    void patchNonExistingMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, muster16Abr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Abr))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuster16Abr() throws Exception {
        int databaseSizeBeforeUpdate = muster16AbrRepository.findAll().size();
        muster16Abr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16AbrMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(muster16Abr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Abr in the database
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuster16Abr() throws Exception {
        // Initialize the database
        muster16AbrRepository.saveAndFlush(muster16Abr);

        int databaseSizeBeforeDelete = muster16AbrRepository.findAll().size();

        // Delete the muster16Abr
        restMuster16AbrMockMvc
            .perform(delete(ENTITY_API_URL_ID, muster16Abr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Muster16Abr> muster16AbrList = muster16AbrRepository.findAll();
        assertThat(muster16AbrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
