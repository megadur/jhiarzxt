package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PWirkstoff;
import com.mycompany.myapp.repository.PWirkstoffRepository;
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
 * Integration tests for the {@link PWirkstoffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PWirkstoffResourceIT {

    private static final String DEFAULT_P_WIRKSTOFF_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_WIRKSTOFF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_P_CHARGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_CHARGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_FAKTOR = "AAAAAAAAAA";
    private static final String UPDATED_FAKTOR = "BBBBBBBBBB";

    private static final String DEFAULT_FAKTOR_KENNZEICHEN = "AAAAAAAAAA";
    private static final String UPDATED_FAKTOR_KENNZEICHEN = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIZ = "AAAAAAAAAA";
    private static final String UPDATED_NOTIZ = "BBBBBBBBBB";

    private static final String DEFAULT_P_POS_NR = "AAAAAAAAAA";
    private static final String UPDATED_P_POS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_PREIS_KENNZEICHEN = "AAAAAAAAAA";
    private static final String UPDATED_PREIS_KENNZEICHEN = "BBBBBBBBBB";

    private static final String DEFAULT_PZN = "AAAAAAAAAA";
    private static final String UPDATED_PZN = "BBBBBBBBBB";

    private static final String DEFAULT_TAXE = "AAAAAAAAAA";
    private static final String UPDATED_TAXE = "BBBBBBBBBB";

    private static final String DEFAULT_WIRKSTOFF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WIRKSTOFF_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-wirkstoffs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PWirkstoffRepository pWirkstoffRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPWirkstoffMockMvc;

    private PWirkstoff pWirkstoff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PWirkstoff createEntity(EntityManager em) {
        PWirkstoff pWirkstoff = new PWirkstoff()
            .pWirkstoffId(DEFAULT_P_WIRKSTOFF_ID)
            .pChargeId(DEFAULT_P_CHARGE_ID)
            .apoIk(DEFAULT_APO_IK)
            .faktor(DEFAULT_FAKTOR)
            .faktorKennzeichen(DEFAULT_FAKTOR_KENNZEICHEN)
            .notiz(DEFAULT_NOTIZ)
            .pPosNr(DEFAULT_P_POS_NR)
            .preisKennzeichen(DEFAULT_PREIS_KENNZEICHEN)
            .pzn(DEFAULT_PZN)
            .taxe(DEFAULT_TAXE)
            .wirkstoffName(DEFAULT_WIRKSTOFF_NAME);
        return pWirkstoff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PWirkstoff createUpdatedEntity(EntityManager em) {
        PWirkstoff pWirkstoff = new PWirkstoff()
            .pWirkstoffId(UPDATED_P_WIRKSTOFF_ID)
            .pChargeId(UPDATED_P_CHARGE_ID)
            .apoIk(UPDATED_APO_IK)
            .faktor(UPDATED_FAKTOR)
            .faktorKennzeichen(UPDATED_FAKTOR_KENNZEICHEN)
            .notiz(UPDATED_NOTIZ)
            .pPosNr(UPDATED_P_POS_NR)
            .preisKennzeichen(UPDATED_PREIS_KENNZEICHEN)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .wirkstoffName(UPDATED_WIRKSTOFF_NAME);
        return pWirkstoff;
    }

    @BeforeEach
    public void initTest() {
        pWirkstoff = createEntity(em);
    }

    @Test
    @Transactional
    void createPWirkstoff() throws Exception {
        int databaseSizeBeforeCreate = pWirkstoffRepository.findAll().size();
        // Create the PWirkstoff
        restPWirkstoffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pWirkstoff)))
            .andExpect(status().isCreated());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeCreate + 1);
        PWirkstoff testPWirkstoff = pWirkstoffList.get(pWirkstoffList.size() - 1);
        assertThat(testPWirkstoff.getpWirkstoffId()).isEqualTo(DEFAULT_P_WIRKSTOFF_ID);
        assertThat(testPWirkstoff.getpChargeId()).isEqualTo(DEFAULT_P_CHARGE_ID);
        assertThat(testPWirkstoff.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPWirkstoff.getFaktor()).isEqualTo(DEFAULT_FAKTOR);
        assertThat(testPWirkstoff.getFaktorKennzeichen()).isEqualTo(DEFAULT_FAKTOR_KENNZEICHEN);
        assertThat(testPWirkstoff.getNotiz()).isEqualTo(DEFAULT_NOTIZ);
        assertThat(testPWirkstoff.getpPosNr()).isEqualTo(DEFAULT_P_POS_NR);
        assertThat(testPWirkstoff.getPreisKennzeichen()).isEqualTo(DEFAULT_PREIS_KENNZEICHEN);
        assertThat(testPWirkstoff.getPzn()).isEqualTo(DEFAULT_PZN);
        assertThat(testPWirkstoff.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testPWirkstoff.getWirkstoffName()).isEqualTo(DEFAULT_WIRKSTOFF_NAME);
    }

    @Test
    @Transactional
    void createPWirkstoffWithExistingId() throws Exception {
        // Create the PWirkstoff with an existing ID
        pWirkstoff.setId(1L);

        int databaseSizeBeforeCreate = pWirkstoffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPWirkstoffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pWirkstoff)))
            .andExpect(status().isBadRequest());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPWirkstoffs() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        // Get all the pWirkstoffList
        restPWirkstoffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pWirkstoff.getId().intValue())))
            .andExpect(jsonPath("$.[*].pWirkstoffId").value(hasItem(DEFAULT_P_WIRKSTOFF_ID)))
            .andExpect(jsonPath("$.[*].pChargeId").value(hasItem(DEFAULT_P_CHARGE_ID)))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].faktor").value(hasItem(DEFAULT_FAKTOR)))
            .andExpect(jsonPath("$.[*].faktorKennzeichen").value(hasItem(DEFAULT_FAKTOR_KENNZEICHEN)))
            .andExpect(jsonPath("$.[*].notiz").value(hasItem(DEFAULT_NOTIZ)))
            .andExpect(jsonPath("$.[*].pPosNr").value(hasItem(DEFAULT_P_POS_NR)))
            .andExpect(jsonPath("$.[*].preisKennzeichen").value(hasItem(DEFAULT_PREIS_KENNZEICHEN)))
            .andExpect(jsonPath("$.[*].pzn").value(hasItem(DEFAULT_PZN)))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE)))
            .andExpect(jsonPath("$.[*].wirkstoffName").value(hasItem(DEFAULT_WIRKSTOFF_NAME)));
    }

    @Test
    @Transactional
    void getPWirkstoff() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        // Get the pWirkstoff
        restPWirkstoffMockMvc
            .perform(get(ENTITY_API_URL_ID, pWirkstoff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pWirkstoff.getId().intValue()))
            .andExpect(jsonPath("$.pWirkstoffId").value(DEFAULT_P_WIRKSTOFF_ID))
            .andExpect(jsonPath("$.pChargeId").value(DEFAULT_P_CHARGE_ID))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.faktor").value(DEFAULT_FAKTOR))
            .andExpect(jsonPath("$.faktorKennzeichen").value(DEFAULT_FAKTOR_KENNZEICHEN))
            .andExpect(jsonPath("$.notiz").value(DEFAULT_NOTIZ))
            .andExpect(jsonPath("$.pPosNr").value(DEFAULT_P_POS_NR))
            .andExpect(jsonPath("$.preisKennzeichen").value(DEFAULT_PREIS_KENNZEICHEN))
            .andExpect(jsonPath("$.pzn").value(DEFAULT_PZN))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE))
            .andExpect(jsonPath("$.wirkstoffName").value(DEFAULT_WIRKSTOFF_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPWirkstoff() throws Exception {
        // Get the pWirkstoff
        restPWirkstoffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPWirkstoff() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();

        // Update the pWirkstoff
        PWirkstoff updatedPWirkstoff = pWirkstoffRepository.findById(pWirkstoff.getId()).get();
        // Disconnect from session so that the updates on updatedPWirkstoff are not directly saved in db
        em.detach(updatedPWirkstoff);
        updatedPWirkstoff
            .pWirkstoffId(UPDATED_P_WIRKSTOFF_ID)
            .pChargeId(UPDATED_P_CHARGE_ID)
            .apoIk(UPDATED_APO_IK)
            .faktor(UPDATED_FAKTOR)
            .faktorKennzeichen(UPDATED_FAKTOR_KENNZEICHEN)
            .notiz(UPDATED_NOTIZ)
            .pPosNr(UPDATED_P_POS_NR)
            .preisKennzeichen(UPDATED_PREIS_KENNZEICHEN)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .wirkstoffName(UPDATED_WIRKSTOFF_NAME);

        restPWirkstoffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPWirkstoff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPWirkstoff))
            )
            .andExpect(status().isOk());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
        PWirkstoff testPWirkstoff = pWirkstoffList.get(pWirkstoffList.size() - 1);
        assertThat(testPWirkstoff.getpWirkstoffId()).isEqualTo(UPDATED_P_WIRKSTOFF_ID);
        assertThat(testPWirkstoff.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPWirkstoff.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPWirkstoff.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPWirkstoff.getFaktorKennzeichen()).isEqualTo(UPDATED_FAKTOR_KENNZEICHEN);
        assertThat(testPWirkstoff.getNotiz()).isEqualTo(UPDATED_NOTIZ);
        assertThat(testPWirkstoff.getpPosNr()).isEqualTo(UPDATED_P_POS_NR);
        assertThat(testPWirkstoff.getPreisKennzeichen()).isEqualTo(UPDATED_PREIS_KENNZEICHEN);
        assertThat(testPWirkstoff.getPzn()).isEqualTo(UPDATED_PZN);
        assertThat(testPWirkstoff.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testPWirkstoff.getWirkstoffName()).isEqualTo(UPDATED_WIRKSTOFF_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pWirkstoff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pWirkstoff))
            )
            .andExpect(status().isBadRequest());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pWirkstoff))
            )
            .andExpect(status().isBadRequest());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pWirkstoff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePWirkstoffWithPatch() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();

        // Update the pWirkstoff using partial update
        PWirkstoff partialUpdatedPWirkstoff = new PWirkstoff();
        partialUpdatedPWirkstoff.setId(pWirkstoff.getId());

        partialUpdatedPWirkstoff
            .pChargeId(UPDATED_P_CHARGE_ID)
            .faktor(UPDATED_FAKTOR)
            .faktorKennzeichen(UPDATED_FAKTOR_KENNZEICHEN)
            .notiz(UPDATED_NOTIZ)
            .preisKennzeichen(UPDATED_PREIS_KENNZEICHEN)
            .wirkstoffName(UPDATED_WIRKSTOFF_NAME);

        restPWirkstoffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPWirkstoff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPWirkstoff))
            )
            .andExpect(status().isOk());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
        PWirkstoff testPWirkstoff = pWirkstoffList.get(pWirkstoffList.size() - 1);
        assertThat(testPWirkstoff.getpWirkstoffId()).isEqualTo(DEFAULT_P_WIRKSTOFF_ID);
        assertThat(testPWirkstoff.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPWirkstoff.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPWirkstoff.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPWirkstoff.getFaktorKennzeichen()).isEqualTo(UPDATED_FAKTOR_KENNZEICHEN);
        assertThat(testPWirkstoff.getNotiz()).isEqualTo(UPDATED_NOTIZ);
        assertThat(testPWirkstoff.getpPosNr()).isEqualTo(DEFAULT_P_POS_NR);
        assertThat(testPWirkstoff.getPreisKennzeichen()).isEqualTo(UPDATED_PREIS_KENNZEICHEN);
        assertThat(testPWirkstoff.getPzn()).isEqualTo(DEFAULT_PZN);
        assertThat(testPWirkstoff.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testPWirkstoff.getWirkstoffName()).isEqualTo(UPDATED_WIRKSTOFF_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePWirkstoffWithPatch() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();

        // Update the pWirkstoff using partial update
        PWirkstoff partialUpdatedPWirkstoff = new PWirkstoff();
        partialUpdatedPWirkstoff.setId(pWirkstoff.getId());

        partialUpdatedPWirkstoff
            .pWirkstoffId(UPDATED_P_WIRKSTOFF_ID)
            .pChargeId(UPDATED_P_CHARGE_ID)
            .apoIk(UPDATED_APO_IK)
            .faktor(UPDATED_FAKTOR)
            .faktorKennzeichen(UPDATED_FAKTOR_KENNZEICHEN)
            .notiz(UPDATED_NOTIZ)
            .pPosNr(UPDATED_P_POS_NR)
            .preisKennzeichen(UPDATED_PREIS_KENNZEICHEN)
            .pzn(UPDATED_PZN)
            .taxe(UPDATED_TAXE)
            .wirkstoffName(UPDATED_WIRKSTOFF_NAME);

        restPWirkstoffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPWirkstoff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPWirkstoff))
            )
            .andExpect(status().isOk());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
        PWirkstoff testPWirkstoff = pWirkstoffList.get(pWirkstoffList.size() - 1);
        assertThat(testPWirkstoff.getpWirkstoffId()).isEqualTo(UPDATED_P_WIRKSTOFF_ID);
        assertThat(testPWirkstoff.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPWirkstoff.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPWirkstoff.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPWirkstoff.getFaktorKennzeichen()).isEqualTo(UPDATED_FAKTOR_KENNZEICHEN);
        assertThat(testPWirkstoff.getNotiz()).isEqualTo(UPDATED_NOTIZ);
        assertThat(testPWirkstoff.getpPosNr()).isEqualTo(UPDATED_P_POS_NR);
        assertThat(testPWirkstoff.getPreisKennzeichen()).isEqualTo(UPDATED_PREIS_KENNZEICHEN);
        assertThat(testPWirkstoff.getPzn()).isEqualTo(UPDATED_PZN);
        assertThat(testPWirkstoff.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testPWirkstoff.getWirkstoffName()).isEqualTo(UPDATED_WIRKSTOFF_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pWirkstoff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pWirkstoff))
            )
            .andExpect(status().isBadRequest());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pWirkstoff))
            )
            .andExpect(status().isBadRequest());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPWirkstoff() throws Exception {
        int databaseSizeBeforeUpdate = pWirkstoffRepository.findAll().size();
        pWirkstoff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWirkstoffMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pWirkstoff))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PWirkstoff in the database
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePWirkstoff() throws Exception {
        // Initialize the database
        pWirkstoffRepository.saveAndFlush(pWirkstoff);

        int databaseSizeBeforeDelete = pWirkstoffRepository.findAll().size();

        // Delete the pWirkstoff
        restPWirkstoffMockMvc
            .perform(delete(ENTITY_API_URL_ID, pWirkstoff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PWirkstoff> pWirkstoffList = pWirkstoffRepository.findAll();
        assertThat(pWirkstoffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
