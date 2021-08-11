package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EAenderung;
import com.mycompany.myapp.repository.EAenderungRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EAenderungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EAenderungResourceIT {

    private static final Integer DEFAULT_SCHLUESSEL = 1;
    private static final Integer UPDATED_SCHLUESSEL = 2;

    private static final String DEFAULT_DOKU_REZEPT = "AAAAAAAAAA";
    private static final String UPDATED_DOKU_REZEPT = "BBBBBBBBBB";

    private static final String DEFAULT_DOKU_ARZT = "AAAAAAAAAA";
    private static final String UPDATED_DOKU_ARZT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/e-aenderungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EAenderungRepository eAenderungRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEAenderungMockMvc;

    private EAenderung eAenderung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EAenderung createEntity(EntityManager em) {
        EAenderung eAenderung = new EAenderung()
            .schluessel(DEFAULT_SCHLUESSEL)
            .dokuRezept(DEFAULT_DOKU_REZEPT)
            .dokuArzt(DEFAULT_DOKU_ARZT)
            .datum(DEFAULT_DATUM);
        return eAenderung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EAenderung createUpdatedEntity(EntityManager em) {
        EAenderung eAenderung = new EAenderung()
            .schluessel(UPDATED_SCHLUESSEL)
            .dokuRezept(UPDATED_DOKU_REZEPT)
            .dokuArzt(UPDATED_DOKU_ARZT)
            .datum(UPDATED_DATUM);
        return eAenderung;
    }

    @BeforeEach
    public void initTest() {
        eAenderung = createEntity(em);
    }

    @Test
    @Transactional
    void createEAenderung() throws Exception {
        int databaseSizeBeforeCreate = eAenderungRepository.findAll().size();
        // Create the EAenderung
        restEAenderungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eAenderung)))
            .andExpect(status().isCreated());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeCreate + 1);
        EAenderung testEAenderung = eAenderungList.get(eAenderungList.size() - 1);
        assertThat(testEAenderung.getSchluessel()).isEqualTo(DEFAULT_SCHLUESSEL);
        assertThat(testEAenderung.getDokuRezept()).isEqualTo(DEFAULT_DOKU_REZEPT);
        assertThat(testEAenderung.getDokuArzt()).isEqualTo(DEFAULT_DOKU_ARZT);
        assertThat(testEAenderung.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    void createEAenderungWithExistingId() throws Exception {
        // Create the EAenderung with an existing ID
        eAenderung.setId(1L);

        int databaseSizeBeforeCreate = eAenderungRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEAenderungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eAenderung)))
            .andExpect(status().isBadRequest());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEAenderungs() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        // Get all the eAenderungList
        restEAenderungMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eAenderung.getId().intValue())))
            .andExpect(jsonPath("$.[*].schluessel").value(hasItem(DEFAULT_SCHLUESSEL)))
            .andExpect(jsonPath("$.[*].dokuRezept").value(hasItem(DEFAULT_DOKU_REZEPT)))
            .andExpect(jsonPath("$.[*].dokuArzt").value(hasItem(DEFAULT_DOKU_ARZT)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())));
    }

    @Test
    @Transactional
    void getEAenderung() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        // Get the eAenderung
        restEAenderungMockMvc
            .perform(get(ENTITY_API_URL_ID, eAenderung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eAenderung.getId().intValue()))
            .andExpect(jsonPath("$.schluessel").value(DEFAULT_SCHLUESSEL))
            .andExpect(jsonPath("$.dokuRezept").value(DEFAULT_DOKU_REZEPT))
            .andExpect(jsonPath("$.dokuArzt").value(DEFAULT_DOKU_ARZT))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEAenderung() throws Exception {
        // Get the eAenderung
        restEAenderungMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEAenderung() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();

        // Update the eAenderung
        EAenderung updatedEAenderung = eAenderungRepository.findById(eAenderung.getId()).get();
        // Disconnect from session so that the updates on updatedEAenderung are not directly saved in db
        em.detach(updatedEAenderung);
        updatedEAenderung.schluessel(UPDATED_SCHLUESSEL).dokuRezept(UPDATED_DOKU_REZEPT).dokuArzt(UPDATED_DOKU_ARZT).datum(UPDATED_DATUM);

        restEAenderungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEAenderung.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEAenderung))
            )
            .andExpect(status().isOk());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
        EAenderung testEAenderung = eAenderungList.get(eAenderungList.size() - 1);
        assertThat(testEAenderung.getSchluessel()).isEqualTo(UPDATED_SCHLUESSEL);
        assertThat(testEAenderung.getDokuRezept()).isEqualTo(UPDATED_DOKU_REZEPT);
        assertThat(testEAenderung.getDokuArzt()).isEqualTo(UPDATED_DOKU_ARZT);
        assertThat(testEAenderung.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void putNonExistingEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eAenderung.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eAenderung))
            )
            .andExpect(status().isBadRequest());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eAenderung))
            )
            .andExpect(status().isBadRequest());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eAenderung)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEAenderungWithPatch() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();

        // Update the eAenderung using partial update
        EAenderung partialUpdatedEAenderung = new EAenderung();
        partialUpdatedEAenderung.setId(eAenderung.getId());

        partialUpdatedEAenderung.datum(UPDATED_DATUM);

        restEAenderungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEAenderung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEAenderung))
            )
            .andExpect(status().isOk());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
        EAenderung testEAenderung = eAenderungList.get(eAenderungList.size() - 1);
        assertThat(testEAenderung.getSchluessel()).isEqualTo(DEFAULT_SCHLUESSEL);
        assertThat(testEAenderung.getDokuRezept()).isEqualTo(DEFAULT_DOKU_REZEPT);
        assertThat(testEAenderung.getDokuArzt()).isEqualTo(DEFAULT_DOKU_ARZT);
        assertThat(testEAenderung.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void fullUpdateEAenderungWithPatch() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();

        // Update the eAenderung using partial update
        EAenderung partialUpdatedEAenderung = new EAenderung();
        partialUpdatedEAenderung.setId(eAenderung.getId());

        partialUpdatedEAenderung
            .schluessel(UPDATED_SCHLUESSEL)
            .dokuRezept(UPDATED_DOKU_REZEPT)
            .dokuArzt(UPDATED_DOKU_ARZT)
            .datum(UPDATED_DATUM);

        restEAenderungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEAenderung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEAenderung))
            )
            .andExpect(status().isOk());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
        EAenderung testEAenderung = eAenderungList.get(eAenderungList.size() - 1);
        assertThat(testEAenderung.getSchluessel()).isEqualTo(UPDATED_SCHLUESSEL);
        assertThat(testEAenderung.getDokuRezept()).isEqualTo(UPDATED_DOKU_REZEPT);
        assertThat(testEAenderung.getDokuArzt()).isEqualTo(UPDATED_DOKU_ARZT);
        assertThat(testEAenderung.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    void patchNonExistingEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eAenderung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eAenderung))
            )
            .andExpect(status().isBadRequest());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eAenderung))
            )
            .andExpect(status().isBadRequest());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEAenderung() throws Exception {
        int databaseSizeBeforeUpdate = eAenderungRepository.findAll().size();
        eAenderung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEAenderungMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eAenderung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EAenderung in the database
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEAenderung() throws Exception {
        // Initialize the database
        eAenderungRepository.saveAndFlush(eAenderung);

        int databaseSizeBeforeDelete = eAenderungRepository.findAll().size();

        // Delete the eAenderung
        restEAenderungMockMvc
            .perform(delete(ENTITY_API_URL_ID, eAenderung.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EAenderung> eAenderungList = eAenderungRepository.findAll();
        assertThat(eAenderungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
