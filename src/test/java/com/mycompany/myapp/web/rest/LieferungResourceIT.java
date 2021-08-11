package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Lieferung;
import com.mycompany.myapp.repository.LieferungRepository;
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
 * Integration tests for the {@link LieferungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LieferungResourceIT {

    private static final String DEFAULT_I_D = "AAAAAAAAAA";
    private static final String UPDATED_I_D = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lieferungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LieferungRepository lieferungRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLieferungMockMvc;

    private Lieferung lieferung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lieferung createEntity(EntityManager em) {
        Lieferung lieferung = new Lieferung().iD(DEFAULT_I_D).datum(DEFAULT_DATUM).apoIk(DEFAULT_APO_IK);
        return lieferung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lieferung createUpdatedEntity(EntityManager em) {
        Lieferung lieferung = new Lieferung().iD(UPDATED_I_D).datum(UPDATED_DATUM).apoIk(UPDATED_APO_IK);
        return lieferung;
    }

    @BeforeEach
    public void initTest() {
        lieferung = createEntity(em);
    }

    @Test
    @Transactional
    void createLieferung() throws Exception {
        int databaseSizeBeforeCreate = lieferungRepository.findAll().size();
        // Create the Lieferung
        restLieferungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lieferung)))
            .andExpect(status().isCreated());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeCreate + 1);
        Lieferung testLieferung = lieferungList.get(lieferungList.size() - 1);
        assertThat(testLieferung.getiD()).isEqualTo(DEFAULT_I_D);
        assertThat(testLieferung.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testLieferung.getApoIk()).isEqualTo(DEFAULT_APO_IK);
    }

    @Test
    @Transactional
    void createLieferungWithExistingId() throws Exception {
        // Create the Lieferung with an existing ID
        lieferung.setId(1L);

        int databaseSizeBeforeCreate = lieferungRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLieferungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lieferung)))
            .andExpect(status().isBadRequest());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLieferungs() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        // Get all the lieferungList
        restLieferungMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieferung.getId().intValue())))
            .andExpect(jsonPath("$.[*].iD").value(hasItem(DEFAULT_I_D)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)));
    }

    @Test
    @Transactional
    void getLieferung() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        // Get the lieferung
        restLieferungMockMvc
            .perform(get(ENTITY_API_URL_ID, lieferung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lieferung.getId().intValue()))
            .andExpect(jsonPath("$.iD").value(DEFAULT_I_D))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK));
    }

    @Test
    @Transactional
    void getNonExistingLieferung() throws Exception {
        // Get the lieferung
        restLieferungMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLieferung() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();

        // Update the lieferung
        Lieferung updatedLieferung = lieferungRepository.findById(lieferung.getId()).get();
        // Disconnect from session so that the updates on updatedLieferung are not directly saved in db
        em.detach(updatedLieferung);
        updatedLieferung.iD(UPDATED_I_D).datum(UPDATED_DATUM).apoIk(UPDATED_APO_IK);

        restLieferungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLieferung.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLieferung))
            )
            .andExpect(status().isOk());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
        Lieferung testLieferung = lieferungList.get(lieferungList.size() - 1);
        assertThat(testLieferung.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testLieferung.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testLieferung.getApoIk()).isEqualTo(UPDATED_APO_IK);
    }

    @Test
    @Transactional
    void putNonExistingLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lieferung.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lieferung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lieferung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lieferung)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLieferungWithPatch() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();

        // Update the lieferung using partial update
        Lieferung partialUpdatedLieferung = new Lieferung();
        partialUpdatedLieferung.setId(lieferung.getId());

        partialUpdatedLieferung.iD(UPDATED_I_D).apoIk(UPDATED_APO_IK);

        restLieferungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLieferung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLieferung))
            )
            .andExpect(status().isOk());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
        Lieferung testLieferung = lieferungList.get(lieferungList.size() - 1);
        assertThat(testLieferung.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testLieferung.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testLieferung.getApoIk()).isEqualTo(UPDATED_APO_IK);
    }

    @Test
    @Transactional
    void fullUpdateLieferungWithPatch() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();

        // Update the lieferung using partial update
        Lieferung partialUpdatedLieferung = new Lieferung();
        partialUpdatedLieferung.setId(lieferung.getId());

        partialUpdatedLieferung.iD(UPDATED_I_D).datum(UPDATED_DATUM).apoIk(UPDATED_APO_IK);

        restLieferungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLieferung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLieferung))
            )
            .andExpect(status().isOk());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
        Lieferung testLieferung = lieferungList.get(lieferungList.size() - 1);
        assertThat(testLieferung.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testLieferung.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testLieferung.getApoIk()).isEqualTo(UPDATED_APO_IK);
    }

    @Test
    @Transactional
    void patchNonExistingLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lieferung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lieferung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lieferung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLieferung() throws Exception {
        int databaseSizeBeforeUpdate = lieferungRepository.findAll().size();
        lieferung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLieferungMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lieferung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lieferung in the database
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLieferung() throws Exception {
        // Initialize the database
        lieferungRepository.saveAndFlush(lieferung);

        int databaseSizeBeforeDelete = lieferungRepository.findAll().size();

        // Delete the lieferung
        restLieferungMockMvc
            .perform(delete(ENTITY_API_URL_ID, lieferung.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lieferung> lieferungList = lieferungRepository.findAll();
        assertThat(lieferungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
