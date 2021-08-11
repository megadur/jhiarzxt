package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ERezept;
import com.mycompany.myapp.repository.ERezeptRepository;
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
 * Integration tests for the {@link ERezeptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ERezeptResourceIT {

    private static final String DEFAULT_I_D = "AAAAAAAAAA";
    private static final String UPDATED_I_D = "BBBBBBBBBB";

    private static final String DEFAULT_DOK_VER = "AAAAAAAAAA";
    private static final String UPDATED_DOK_VER = "BBBBBBBBBB";

    private static final String DEFAULT_ABG_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ABG_INFO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ABG_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ABG_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_ABG_GES_ZUZAHL = 1F;
    private static final Float UPDATED_ABG_GES_ZUZAHL = 2F;

    private static final Float DEFAULT_ABG_GES_BRUTTO = 1F;
    private static final Float UPDATED_ABG_GES_BRUTTO = 2F;

    private static final String DEFAULT_ABG_VERTRAGSKZ = "AAAAAAAAAA";
    private static final String UPDATED_ABG_VERTRAGSKZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/e-rezepts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ERezeptRepository eRezeptRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restERezeptMockMvc;

    private ERezept eRezept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ERezept createEntity(EntityManager em) {
        ERezept eRezept = new ERezept()
            .iD(DEFAULT_I_D)
            .dokVer(DEFAULT_DOK_VER)
            .abgInfo(DEFAULT_ABG_INFO)
            .abgDatum(DEFAULT_ABG_DATUM)
            .abgGesZuzahl(DEFAULT_ABG_GES_ZUZAHL)
            .abgGesBrutto(DEFAULT_ABG_GES_BRUTTO)
            .abgVertragskz(DEFAULT_ABG_VERTRAGSKZ);
        return eRezept;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ERezept createUpdatedEntity(EntityManager em) {
        ERezept eRezept = new ERezept()
            .iD(UPDATED_I_D)
            .dokVer(UPDATED_DOK_VER)
            .abgInfo(UPDATED_ABG_INFO)
            .abgDatum(UPDATED_ABG_DATUM)
            .abgGesZuzahl(UPDATED_ABG_GES_ZUZAHL)
            .abgGesBrutto(UPDATED_ABG_GES_BRUTTO)
            .abgVertragskz(UPDATED_ABG_VERTRAGSKZ);
        return eRezept;
    }

    @BeforeEach
    public void initTest() {
        eRezept = createEntity(em);
    }

    @Test
    @Transactional
    void createERezept() throws Exception {
        int databaseSizeBeforeCreate = eRezeptRepository.findAll().size();
        // Create the ERezept
        restERezeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eRezept)))
            .andExpect(status().isCreated());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeCreate + 1);
        ERezept testERezept = eRezeptList.get(eRezeptList.size() - 1);
        assertThat(testERezept.getiD()).isEqualTo(DEFAULT_I_D);
        assertThat(testERezept.getDokVer()).isEqualTo(DEFAULT_DOK_VER);
        assertThat(testERezept.getAbgInfo()).isEqualTo(DEFAULT_ABG_INFO);
        assertThat(testERezept.getAbgDatum()).isEqualTo(DEFAULT_ABG_DATUM);
        assertThat(testERezept.getAbgGesZuzahl()).isEqualTo(DEFAULT_ABG_GES_ZUZAHL);
        assertThat(testERezept.getAbgGesBrutto()).isEqualTo(DEFAULT_ABG_GES_BRUTTO);
        assertThat(testERezept.getAbgVertragskz()).isEqualTo(DEFAULT_ABG_VERTRAGSKZ);
    }

    @Test
    @Transactional
    void createERezeptWithExistingId() throws Exception {
        // Create the ERezept with an existing ID
        eRezept.setId(1L);

        int databaseSizeBeforeCreate = eRezeptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restERezeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eRezept)))
            .andExpect(status().isBadRequest());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllERezepts() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        // Get all the eRezeptList
        restERezeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eRezept.getId().intValue())))
            .andExpect(jsonPath("$.[*].iD").value(hasItem(DEFAULT_I_D)))
            .andExpect(jsonPath("$.[*].dokVer").value(hasItem(DEFAULT_DOK_VER)))
            .andExpect(jsonPath("$.[*].abgInfo").value(hasItem(DEFAULT_ABG_INFO)))
            .andExpect(jsonPath("$.[*].abgDatum").value(hasItem(DEFAULT_ABG_DATUM.toString())))
            .andExpect(jsonPath("$.[*].abgGesZuzahl").value(hasItem(DEFAULT_ABG_GES_ZUZAHL.doubleValue())))
            .andExpect(jsonPath("$.[*].abgGesBrutto").value(hasItem(DEFAULT_ABG_GES_BRUTTO.doubleValue())))
            .andExpect(jsonPath("$.[*].abgVertragskz").value(hasItem(DEFAULT_ABG_VERTRAGSKZ)));
    }

    @Test
    @Transactional
    void getERezept() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        // Get the eRezept
        restERezeptMockMvc
            .perform(get(ENTITY_API_URL_ID, eRezept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eRezept.getId().intValue()))
            .andExpect(jsonPath("$.iD").value(DEFAULT_I_D))
            .andExpect(jsonPath("$.dokVer").value(DEFAULT_DOK_VER))
            .andExpect(jsonPath("$.abgInfo").value(DEFAULT_ABG_INFO))
            .andExpect(jsonPath("$.abgDatum").value(DEFAULT_ABG_DATUM.toString()))
            .andExpect(jsonPath("$.abgGesZuzahl").value(DEFAULT_ABG_GES_ZUZAHL.doubleValue()))
            .andExpect(jsonPath("$.abgGesBrutto").value(DEFAULT_ABG_GES_BRUTTO.doubleValue()))
            .andExpect(jsonPath("$.abgVertragskz").value(DEFAULT_ABG_VERTRAGSKZ));
    }

    @Test
    @Transactional
    void getNonExistingERezept() throws Exception {
        // Get the eRezept
        restERezeptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewERezept() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();

        // Update the eRezept
        ERezept updatedERezept = eRezeptRepository.findById(eRezept.getId()).get();
        // Disconnect from session so that the updates on updatedERezept are not directly saved in db
        em.detach(updatedERezept);
        updatedERezept
            .iD(UPDATED_I_D)
            .dokVer(UPDATED_DOK_VER)
            .abgInfo(UPDATED_ABG_INFO)
            .abgDatum(UPDATED_ABG_DATUM)
            .abgGesZuzahl(UPDATED_ABG_GES_ZUZAHL)
            .abgGesBrutto(UPDATED_ABG_GES_BRUTTO)
            .abgVertragskz(UPDATED_ABG_VERTRAGSKZ);

        restERezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedERezept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedERezept))
            )
            .andExpect(status().isOk());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
        ERezept testERezept = eRezeptList.get(eRezeptList.size() - 1);
        assertThat(testERezept.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testERezept.getDokVer()).isEqualTo(UPDATED_DOK_VER);
        assertThat(testERezept.getAbgInfo()).isEqualTo(UPDATED_ABG_INFO);
        assertThat(testERezept.getAbgDatum()).isEqualTo(UPDATED_ABG_DATUM);
        assertThat(testERezept.getAbgGesZuzahl()).isEqualTo(UPDATED_ABG_GES_ZUZAHL);
        assertThat(testERezept.getAbgGesBrutto()).isEqualTo(UPDATED_ABG_GES_BRUTTO);
        assertThat(testERezept.getAbgVertragskz()).isEqualTo(UPDATED_ABG_VERTRAGSKZ);
    }

    @Test
    @Transactional
    void putNonExistingERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eRezept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eRezept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateERezeptWithPatch() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();

        // Update the eRezept using partial update
        ERezept partialUpdatedERezept = new ERezept();
        partialUpdatedERezept.setId(eRezept.getId());

        partialUpdatedERezept.abgInfo(UPDATED_ABG_INFO).abgGesZuzahl(UPDATED_ABG_GES_ZUZAHL).abgGesBrutto(UPDATED_ABG_GES_BRUTTO);

        restERezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedERezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedERezept))
            )
            .andExpect(status().isOk());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
        ERezept testERezept = eRezeptList.get(eRezeptList.size() - 1);
        assertThat(testERezept.getiD()).isEqualTo(DEFAULT_I_D);
        assertThat(testERezept.getDokVer()).isEqualTo(DEFAULT_DOK_VER);
        assertThat(testERezept.getAbgInfo()).isEqualTo(UPDATED_ABG_INFO);
        assertThat(testERezept.getAbgDatum()).isEqualTo(DEFAULT_ABG_DATUM);
        assertThat(testERezept.getAbgGesZuzahl()).isEqualTo(UPDATED_ABG_GES_ZUZAHL);
        assertThat(testERezept.getAbgGesBrutto()).isEqualTo(UPDATED_ABG_GES_BRUTTO);
        assertThat(testERezept.getAbgVertragskz()).isEqualTo(DEFAULT_ABG_VERTRAGSKZ);
    }

    @Test
    @Transactional
    void fullUpdateERezeptWithPatch() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();

        // Update the eRezept using partial update
        ERezept partialUpdatedERezept = new ERezept();
        partialUpdatedERezept.setId(eRezept.getId());

        partialUpdatedERezept
            .iD(UPDATED_I_D)
            .dokVer(UPDATED_DOK_VER)
            .abgInfo(UPDATED_ABG_INFO)
            .abgDatum(UPDATED_ABG_DATUM)
            .abgGesZuzahl(UPDATED_ABG_GES_ZUZAHL)
            .abgGesBrutto(UPDATED_ABG_GES_BRUTTO)
            .abgVertragskz(UPDATED_ABG_VERTRAGSKZ);

        restERezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedERezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedERezept))
            )
            .andExpect(status().isOk());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
        ERezept testERezept = eRezeptList.get(eRezeptList.size() - 1);
        assertThat(testERezept.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testERezept.getDokVer()).isEqualTo(UPDATED_DOK_VER);
        assertThat(testERezept.getAbgInfo()).isEqualTo(UPDATED_ABG_INFO);
        assertThat(testERezept.getAbgDatum()).isEqualTo(UPDATED_ABG_DATUM);
        assertThat(testERezept.getAbgGesZuzahl()).isEqualTo(UPDATED_ABG_GES_ZUZAHL);
        assertThat(testERezept.getAbgGesBrutto()).isEqualTo(UPDATED_ABG_GES_BRUTTO);
        assertThat(testERezept.getAbgVertragskz()).isEqualTo(UPDATED_ABG_VERTRAGSKZ);
    }

    @Test
    @Transactional
    void patchNonExistingERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eRezept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eRezept))
            )
            .andExpect(status().isBadRequest());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamERezept() throws Exception {
        int databaseSizeBeforeUpdate = eRezeptRepository.findAll().size();
        eRezept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restERezeptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eRezept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ERezept in the database
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteERezept() throws Exception {
        // Initialize the database
        eRezeptRepository.saveAndFlush(eRezept);

        int databaseSizeBeforeDelete = eRezeptRepository.findAll().size();

        // Delete the eRezept
        restERezeptMockMvc
            .perform(delete(ENTITY_API_URL_ID, eRezept.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ERezept> eRezeptList = eRezeptRepository.findAll();
        assertThat(eRezeptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
