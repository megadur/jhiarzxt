package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PCharge;
import com.mycompany.myapp.repository.PChargeRepository;
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
 * Integration tests for the {@link PChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PChargeResourceIT {

    private static final String DEFAULT_P_CHARGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_CHARGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_P_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ANZAHL_APPLIKATIONEN = "AAAAAAAAAA";
    private static final String UPDATED_ANZAHL_APPLIKATIONEN = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_CHARGEN_NR = "AAAAAAAAAA";
    private static final String UPDATED_CHARGEN_NR = "BBBBBBBBBB";

    private static final String DEFAULT_HERSTELLER_NR = "AAAAAAAAAA";
    private static final String UPDATED_HERSTELLER_NR = "BBBBBBBBBB";

    private static final String DEFAULT_HERSTELLER_SCHLUESSEL = "AAAAAAAAAA";
    private static final String UPDATED_HERSTELLER_SCHLUESSEL = "BBBBBBBBBB";

    private static final String DEFAULT_HERSTELLUNGS_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_HERSTELLUNGS_DATUM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PChargeRepository pChargeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPChargeMockMvc;

    private PCharge pCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PCharge createEntity(EntityManager em) {
        PCharge pCharge = new PCharge()
            .pChargeId(DEFAULT_P_CHARGE_ID)
            .pRezeptId(DEFAULT_P_REZEPT_ID)
            .anzahlApplikationen(DEFAULT_ANZAHL_APPLIKATIONEN)
            .apoIk(DEFAULT_APO_IK)
            .chargenNr(DEFAULT_CHARGEN_NR)
            .herstellerNr(DEFAULT_HERSTELLER_NR)
            .herstellerSchluessel(DEFAULT_HERSTELLER_SCHLUESSEL)
            .herstellungsDatum(DEFAULT_HERSTELLUNGS_DATUM);
        return pCharge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PCharge createUpdatedEntity(EntityManager em) {
        PCharge pCharge = new PCharge()
            .pChargeId(UPDATED_P_CHARGE_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .anzahlApplikationen(UPDATED_ANZAHL_APPLIKATIONEN)
            .apoIk(UPDATED_APO_IK)
            .chargenNr(UPDATED_CHARGEN_NR)
            .herstellerNr(UPDATED_HERSTELLER_NR)
            .herstellerSchluessel(UPDATED_HERSTELLER_SCHLUESSEL)
            .herstellungsDatum(UPDATED_HERSTELLUNGS_DATUM);
        return pCharge;
    }

    @BeforeEach
    public void initTest() {
        pCharge = createEntity(em);
    }

    @Test
    @Transactional
    void createPCharge() throws Exception {
        int databaseSizeBeforeCreate = pChargeRepository.findAll().size();
        // Create the PCharge
        restPChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCharge)))
            .andExpect(status().isCreated());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeCreate + 1);
        PCharge testPCharge = pChargeList.get(pChargeList.size() - 1);
        assertThat(testPCharge.getpChargeId()).isEqualTo(DEFAULT_P_CHARGE_ID);
        assertThat(testPCharge.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPCharge.getAnzahlApplikationen()).isEqualTo(DEFAULT_ANZAHL_APPLIKATIONEN);
        assertThat(testPCharge.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPCharge.getChargenNr()).isEqualTo(DEFAULT_CHARGEN_NR);
        assertThat(testPCharge.getHerstellerNr()).isEqualTo(DEFAULT_HERSTELLER_NR);
        assertThat(testPCharge.getHerstellerSchluessel()).isEqualTo(DEFAULT_HERSTELLER_SCHLUESSEL);
        assertThat(testPCharge.getHerstellungsDatum()).isEqualTo(DEFAULT_HERSTELLUNGS_DATUM);
    }

    @Test
    @Transactional
    void createPChargeWithExistingId() throws Exception {
        // Create the PCharge with an existing ID
        pCharge.setId(1L);

        int databaseSizeBeforeCreate = pChargeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCharge)))
            .andExpect(status().isBadRequest());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPCharges() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        // Get all the pChargeList
        restPChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].pChargeId").value(hasItem(DEFAULT_P_CHARGE_ID)))
            .andExpect(jsonPath("$.[*].pRezeptId").value(hasItem(DEFAULT_P_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].anzahlApplikationen").value(hasItem(DEFAULT_ANZAHL_APPLIKATIONEN)))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].chargenNr").value(hasItem(DEFAULT_CHARGEN_NR)))
            .andExpect(jsonPath("$.[*].herstellerNr").value(hasItem(DEFAULT_HERSTELLER_NR)))
            .andExpect(jsonPath("$.[*].herstellerSchluessel").value(hasItem(DEFAULT_HERSTELLER_SCHLUESSEL)))
            .andExpect(jsonPath("$.[*].herstellungsDatum").value(hasItem(DEFAULT_HERSTELLUNGS_DATUM)));
    }

    @Test
    @Transactional
    void getPCharge() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        // Get the pCharge
        restPChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, pCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pCharge.getId().intValue()))
            .andExpect(jsonPath("$.pChargeId").value(DEFAULT_P_CHARGE_ID))
            .andExpect(jsonPath("$.pRezeptId").value(DEFAULT_P_REZEPT_ID))
            .andExpect(jsonPath("$.anzahlApplikationen").value(DEFAULT_ANZAHL_APPLIKATIONEN))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.chargenNr").value(DEFAULT_CHARGEN_NR))
            .andExpect(jsonPath("$.herstellerNr").value(DEFAULT_HERSTELLER_NR))
            .andExpect(jsonPath("$.herstellerSchluessel").value(DEFAULT_HERSTELLER_SCHLUESSEL))
            .andExpect(jsonPath("$.herstellungsDatum").value(DEFAULT_HERSTELLUNGS_DATUM));
    }

    @Test
    @Transactional
    void getNonExistingPCharge() throws Exception {
        // Get the pCharge
        restPChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPCharge() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();

        // Update the pCharge
        PCharge updatedPCharge = pChargeRepository.findById(pCharge.getId()).get();
        // Disconnect from session so that the updates on updatedPCharge are not directly saved in db
        em.detach(updatedPCharge);
        updatedPCharge
            .pChargeId(UPDATED_P_CHARGE_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .anzahlApplikationen(UPDATED_ANZAHL_APPLIKATIONEN)
            .apoIk(UPDATED_APO_IK)
            .chargenNr(UPDATED_CHARGEN_NR)
            .herstellerNr(UPDATED_HERSTELLER_NR)
            .herstellerSchluessel(UPDATED_HERSTELLER_SCHLUESSEL)
            .herstellungsDatum(UPDATED_HERSTELLUNGS_DATUM);

        restPChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPCharge))
            )
            .andExpect(status().isOk());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
        PCharge testPCharge = pChargeList.get(pChargeList.size() - 1);
        assertThat(testPCharge.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPCharge.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPCharge.getAnzahlApplikationen()).isEqualTo(UPDATED_ANZAHL_APPLIKATIONEN);
        assertThat(testPCharge.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPCharge.getChargenNr()).isEqualTo(UPDATED_CHARGEN_NR);
        assertThat(testPCharge.getHerstellerNr()).isEqualTo(UPDATED_HERSTELLER_NR);
        assertThat(testPCharge.getHerstellerSchluessel()).isEqualTo(UPDATED_HERSTELLER_SCHLUESSEL);
        assertThat(testPCharge.getHerstellungsDatum()).isEqualTo(UPDATED_HERSTELLUNGS_DATUM);
    }

    @Test
    @Transactional
    void putNonExistingPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePChargeWithPatch() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();

        // Update the pCharge using partial update
        PCharge partialUpdatedPCharge = new PCharge();
        partialUpdatedPCharge.setId(pCharge.getId());

        partialUpdatedPCharge.pChargeId(UPDATED_P_CHARGE_ID).herstellerSchluessel(UPDATED_HERSTELLER_SCHLUESSEL);

        restPChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPCharge))
            )
            .andExpect(status().isOk());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
        PCharge testPCharge = pChargeList.get(pChargeList.size() - 1);
        assertThat(testPCharge.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPCharge.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPCharge.getAnzahlApplikationen()).isEqualTo(DEFAULT_ANZAHL_APPLIKATIONEN);
        assertThat(testPCharge.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPCharge.getChargenNr()).isEqualTo(DEFAULT_CHARGEN_NR);
        assertThat(testPCharge.getHerstellerNr()).isEqualTo(DEFAULT_HERSTELLER_NR);
        assertThat(testPCharge.getHerstellerSchluessel()).isEqualTo(UPDATED_HERSTELLER_SCHLUESSEL);
        assertThat(testPCharge.getHerstellungsDatum()).isEqualTo(DEFAULT_HERSTELLUNGS_DATUM);
    }

    @Test
    @Transactional
    void fullUpdatePChargeWithPatch() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();

        // Update the pCharge using partial update
        PCharge partialUpdatedPCharge = new PCharge();
        partialUpdatedPCharge.setId(pCharge.getId());

        partialUpdatedPCharge
            .pChargeId(UPDATED_P_CHARGE_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .anzahlApplikationen(UPDATED_ANZAHL_APPLIKATIONEN)
            .apoIk(UPDATED_APO_IK)
            .chargenNr(UPDATED_CHARGEN_NR)
            .herstellerNr(UPDATED_HERSTELLER_NR)
            .herstellerSchluessel(UPDATED_HERSTELLER_SCHLUESSEL)
            .herstellungsDatum(UPDATED_HERSTELLUNGS_DATUM);

        restPChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPCharge))
            )
            .andExpect(status().isOk());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
        PCharge testPCharge = pChargeList.get(pChargeList.size() - 1);
        assertThat(testPCharge.getpChargeId()).isEqualTo(UPDATED_P_CHARGE_ID);
        assertThat(testPCharge.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPCharge.getAnzahlApplikationen()).isEqualTo(UPDATED_ANZAHL_APPLIKATIONEN);
        assertThat(testPCharge.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPCharge.getChargenNr()).isEqualTo(UPDATED_CHARGEN_NR);
        assertThat(testPCharge.getHerstellerNr()).isEqualTo(UPDATED_HERSTELLER_NR);
        assertThat(testPCharge.getHerstellerSchluessel()).isEqualTo(UPDATED_HERSTELLER_SCHLUESSEL);
        assertThat(testPCharge.getHerstellungsDatum()).isEqualTo(UPDATED_HERSTELLUNGS_DATUM);
    }

    @Test
    @Transactional
    void patchNonExistingPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPCharge() throws Exception {
        int databaseSizeBeforeUpdate = pChargeRepository.findAll().size();
        pCharge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPChargeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PCharge in the database
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePCharge() throws Exception {
        // Initialize the database
        pChargeRepository.saveAndFlush(pCharge);

        int databaseSizeBeforeDelete = pChargeRepository.findAll().size();

        // Delete the pCharge
        restPChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, pCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PCharge> pChargeList = pChargeRepository.findAll();
        assertThat(pChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
