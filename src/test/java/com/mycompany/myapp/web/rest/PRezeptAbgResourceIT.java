package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PRezeptAbg;
import com.mycompany.myapp.repository.PRezeptAbgRepository;
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
 * Integration tests for the {@link PRezeptAbgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PRezeptAbgResourceIT {

    private static final String DEFAULT_P_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GEB_FREI = "AAAAAAAAAA";
    private static final String UPDATED_GEB_FREI = "BBBBBBBBBB";

    private static final String DEFAULT_GES_BRUTTO = "AAAAAAAAAA";
    private static final String UPDATED_GES_BRUTTO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_K_ART = "AAAAAAAAAA";
    private static final String UPDATED_K_ART = "BBBBBBBBBB";

    private static final String DEFAULT_NOCTU = "AAAAAAAAAA";
    private static final String UPDATED_NOCTU = "BBBBBBBBBB";

    private static final String DEFAULT_P_REZEPT_TYP = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_TYP = "BBBBBBBBBB";

    private static final String DEFAULT_R_TYP = "AAAAAAAAAA";
    private static final String UPDATED_R_TYP = "BBBBBBBBBB";

    private static final String DEFAULT_SONSTIGE = "AAAAAAAAAA";
    private static final String UPDATED_SONSTIGE = "BBBBBBBBBB";

    private static final String DEFAULT_SPR_ST_BEDARF = "AAAAAAAAAA";
    private static final String UPDATED_SPR_ST_BEDARF = "BBBBBBBBBB";

    private static final String DEFAULT_VER_DAT = "AAAAAAAAAA";
    private static final String UPDATED_VER_DAT = "BBBBBBBBBB";

    private static final String DEFAULT_VK_GUELTIG_BIS = "AAAAAAAAAA";
    private static final String UPDATED_VK_GUELTIG_BIS = "BBBBBBBBBB";

    private static final String DEFAULT_ZUZAHLUNG = "AAAAAAAAAA";
    private static final String UPDATED_ZUZAHLUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-rezept-abgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PRezeptAbgRepository pRezeptAbgRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPRezeptAbgMockMvc;

    private PRezeptAbg pRezeptAbg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptAbg createEntity(EntityManager em) {
        PRezeptAbg pRezeptAbg = new PRezeptAbg()
            .pRezeptId(DEFAULT_P_REZEPT_ID)
            .gebFrei(DEFAULT_GEB_FREI)
            .gesBrutto(DEFAULT_GES_BRUTTO)
            .hashCode(DEFAULT_HASH_CODE)
            .kArt(DEFAULT_K_ART)
            .noctu(DEFAULT_NOCTU)
            .pRezeptTyp(DEFAULT_P_REZEPT_TYP)
            .rTyp(DEFAULT_R_TYP)
            .sonstige(DEFAULT_SONSTIGE)
            .sprStBedarf(DEFAULT_SPR_ST_BEDARF)
            .verDat(DEFAULT_VER_DAT)
            .vkGueltigBis(DEFAULT_VK_GUELTIG_BIS)
            .zuzahlung(DEFAULT_ZUZAHLUNG);
        return pRezeptAbg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptAbg createUpdatedEntity(EntityManager em) {
        PRezeptAbg pRezeptAbg = new PRezeptAbg()
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .gebFrei(UPDATED_GEB_FREI)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .hashCode(UPDATED_HASH_CODE)
            .kArt(UPDATED_K_ART)
            .noctu(UPDATED_NOCTU)
            .pRezeptTyp(UPDATED_P_REZEPT_TYP)
            .rTyp(UPDATED_R_TYP)
            .sonstige(UPDATED_SONSTIGE)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .verDat(UPDATED_VER_DAT)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS)
            .zuzahlung(UPDATED_ZUZAHLUNG);
        return pRezeptAbg;
    }

    @BeforeEach
    public void initTest() {
        pRezeptAbg = createEntity(em);
    }

    @Test
    @Transactional
    void createPRezeptAbg() throws Exception {
        int databaseSizeBeforeCreate = pRezeptAbgRepository.findAll().size();
        // Create the PRezeptAbg
        restPRezeptAbgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbg)))
            .andExpect(status().isCreated());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeCreate + 1);
        PRezeptAbg testPRezeptAbg = pRezeptAbgList.get(pRezeptAbgList.size() - 1);
        assertThat(testPRezeptAbg.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezeptAbg.getGebFrei()).isEqualTo(DEFAULT_GEB_FREI);
        assertThat(testPRezeptAbg.getGesBrutto()).isEqualTo(DEFAULT_GES_BRUTTO);
        assertThat(testPRezeptAbg.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
        assertThat(testPRezeptAbg.getkArt()).isEqualTo(DEFAULT_K_ART);
        assertThat(testPRezeptAbg.getNoctu()).isEqualTo(DEFAULT_NOCTU);
        assertThat(testPRezeptAbg.getpRezeptTyp()).isEqualTo(DEFAULT_P_REZEPT_TYP);
        assertThat(testPRezeptAbg.getrTyp()).isEqualTo(DEFAULT_R_TYP);
        assertThat(testPRezeptAbg.getSonstige()).isEqualTo(DEFAULT_SONSTIGE);
        assertThat(testPRezeptAbg.getSprStBedarf()).isEqualTo(DEFAULT_SPR_ST_BEDARF);
        assertThat(testPRezeptAbg.getVerDat()).isEqualTo(DEFAULT_VER_DAT);
        assertThat(testPRezeptAbg.getVkGueltigBis()).isEqualTo(DEFAULT_VK_GUELTIG_BIS);
        assertThat(testPRezeptAbg.getZuzahlung()).isEqualTo(DEFAULT_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void createPRezeptAbgWithExistingId() throws Exception {
        // Create the PRezeptAbg with an existing ID
        pRezeptAbg.setId(1L);

        int databaseSizeBeforeCreate = pRezeptAbgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPRezeptAbgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbg)))
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPRezeptAbgs() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        // Get all the pRezeptAbgList
        restPRezeptAbgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRezeptAbg.getId().intValue())))
            .andExpect(jsonPath("$.[*].pRezeptId").value(hasItem(DEFAULT_P_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].gebFrei").value(hasItem(DEFAULT_GEB_FREI)))
            .andExpect(jsonPath("$.[*].gesBrutto").value(hasItem(DEFAULT_GES_BRUTTO)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].kArt").value(hasItem(DEFAULT_K_ART)))
            .andExpect(jsonPath("$.[*].noctu").value(hasItem(DEFAULT_NOCTU)))
            .andExpect(jsonPath("$.[*].pRezeptTyp").value(hasItem(DEFAULT_P_REZEPT_TYP)))
            .andExpect(jsonPath("$.[*].rTyp").value(hasItem(DEFAULT_R_TYP)))
            .andExpect(jsonPath("$.[*].sonstige").value(hasItem(DEFAULT_SONSTIGE)))
            .andExpect(jsonPath("$.[*].sprStBedarf").value(hasItem(DEFAULT_SPR_ST_BEDARF)))
            .andExpect(jsonPath("$.[*].verDat").value(hasItem(DEFAULT_VER_DAT)))
            .andExpect(jsonPath("$.[*].vkGueltigBis").value(hasItem(DEFAULT_VK_GUELTIG_BIS)))
            .andExpect(jsonPath("$.[*].zuzahlung").value(hasItem(DEFAULT_ZUZAHLUNG)));
    }

    @Test
    @Transactional
    void getPRezeptAbg() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        // Get the pRezeptAbg
        restPRezeptAbgMockMvc
            .perform(get(ENTITY_API_URL_ID, pRezeptAbg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pRezeptAbg.getId().intValue()))
            .andExpect(jsonPath("$.pRezeptId").value(DEFAULT_P_REZEPT_ID))
            .andExpect(jsonPath("$.gebFrei").value(DEFAULT_GEB_FREI))
            .andExpect(jsonPath("$.gesBrutto").value(DEFAULT_GES_BRUTTO))
            .andExpect(jsonPath("$.hashCode").value(DEFAULT_HASH_CODE))
            .andExpect(jsonPath("$.kArt").value(DEFAULT_K_ART))
            .andExpect(jsonPath("$.noctu").value(DEFAULT_NOCTU))
            .andExpect(jsonPath("$.pRezeptTyp").value(DEFAULT_P_REZEPT_TYP))
            .andExpect(jsonPath("$.rTyp").value(DEFAULT_R_TYP))
            .andExpect(jsonPath("$.sonstige").value(DEFAULT_SONSTIGE))
            .andExpect(jsonPath("$.sprStBedarf").value(DEFAULT_SPR_ST_BEDARF))
            .andExpect(jsonPath("$.verDat").value(DEFAULT_VER_DAT))
            .andExpect(jsonPath("$.vkGueltigBis").value(DEFAULT_VK_GUELTIG_BIS))
            .andExpect(jsonPath("$.zuzahlung").value(DEFAULT_ZUZAHLUNG));
    }

    @Test
    @Transactional
    void getNonExistingPRezeptAbg() throws Exception {
        // Get the pRezeptAbg
        restPRezeptAbgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPRezeptAbg() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();

        // Update the pRezeptAbg
        PRezeptAbg updatedPRezeptAbg = pRezeptAbgRepository.findById(pRezeptAbg.getId()).get();
        // Disconnect from session so that the updates on updatedPRezeptAbg are not directly saved in db
        em.detach(updatedPRezeptAbg);
        updatedPRezeptAbg
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .gebFrei(UPDATED_GEB_FREI)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .hashCode(UPDATED_HASH_CODE)
            .kArt(UPDATED_K_ART)
            .noctu(UPDATED_NOCTU)
            .pRezeptTyp(UPDATED_P_REZEPT_TYP)
            .rTyp(UPDATED_R_TYP)
            .sonstige(UPDATED_SONSTIGE)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .verDat(UPDATED_VER_DAT)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS)
            .zuzahlung(UPDATED_ZUZAHLUNG);

        restPRezeptAbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPRezeptAbg.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPRezeptAbg))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbg testPRezeptAbg = pRezeptAbgList.get(pRezeptAbgList.size() - 1);
        assertThat(testPRezeptAbg.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezeptAbg.getGebFrei()).isEqualTo(UPDATED_GEB_FREI);
        assertThat(testPRezeptAbg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
        assertThat(testPRezeptAbg.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testPRezeptAbg.getkArt()).isEqualTo(UPDATED_K_ART);
        assertThat(testPRezeptAbg.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testPRezeptAbg.getpRezeptTyp()).isEqualTo(UPDATED_P_REZEPT_TYP);
        assertThat(testPRezeptAbg.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testPRezeptAbg.getSonstige()).isEqualTo(UPDATED_SONSTIGE);
        assertThat(testPRezeptAbg.getSprStBedarf()).isEqualTo(UPDATED_SPR_ST_BEDARF);
        assertThat(testPRezeptAbg.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testPRezeptAbg.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
        assertThat(testPRezeptAbg.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void putNonExistingPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pRezeptAbg.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbg))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbg))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptAbg)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePRezeptAbgWithPatch() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();

        // Update the pRezeptAbg using partial update
        PRezeptAbg partialUpdatedPRezeptAbg = new PRezeptAbg();
        partialUpdatedPRezeptAbg.setId(pRezeptAbg.getId());

        partialUpdatedPRezeptAbg
            .gesBrutto(UPDATED_GES_BRUTTO)
            .noctu(UPDATED_NOCTU)
            .pRezeptTyp(UPDATED_P_REZEPT_TYP)
            .rTyp(UPDATED_R_TYP)
            .sonstige(UPDATED_SONSTIGE)
            .verDat(UPDATED_VER_DAT)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS);

        restPRezeptAbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptAbg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptAbg))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbg testPRezeptAbg = pRezeptAbgList.get(pRezeptAbgList.size() - 1);
        assertThat(testPRezeptAbg.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPRezeptAbg.getGebFrei()).isEqualTo(DEFAULT_GEB_FREI);
        assertThat(testPRezeptAbg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
        assertThat(testPRezeptAbg.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
        assertThat(testPRezeptAbg.getkArt()).isEqualTo(DEFAULT_K_ART);
        assertThat(testPRezeptAbg.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testPRezeptAbg.getpRezeptTyp()).isEqualTo(UPDATED_P_REZEPT_TYP);
        assertThat(testPRezeptAbg.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testPRezeptAbg.getSonstige()).isEqualTo(UPDATED_SONSTIGE);
        assertThat(testPRezeptAbg.getSprStBedarf()).isEqualTo(DEFAULT_SPR_ST_BEDARF);
        assertThat(testPRezeptAbg.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testPRezeptAbg.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
        assertThat(testPRezeptAbg.getZuzahlung()).isEqualTo(DEFAULT_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void fullUpdatePRezeptAbgWithPatch() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();

        // Update the pRezeptAbg using partial update
        PRezeptAbg partialUpdatedPRezeptAbg = new PRezeptAbg();
        partialUpdatedPRezeptAbg.setId(pRezeptAbg.getId());

        partialUpdatedPRezeptAbg
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .gebFrei(UPDATED_GEB_FREI)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .hashCode(UPDATED_HASH_CODE)
            .kArt(UPDATED_K_ART)
            .noctu(UPDATED_NOCTU)
            .pRezeptTyp(UPDATED_P_REZEPT_TYP)
            .rTyp(UPDATED_R_TYP)
            .sonstige(UPDATED_SONSTIGE)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .verDat(UPDATED_VER_DAT)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS)
            .zuzahlung(UPDATED_ZUZAHLUNG);

        restPRezeptAbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptAbg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptAbg))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
        PRezeptAbg testPRezeptAbg = pRezeptAbgList.get(pRezeptAbgList.size() - 1);
        assertThat(testPRezeptAbg.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPRezeptAbg.getGebFrei()).isEqualTo(UPDATED_GEB_FREI);
        assertThat(testPRezeptAbg.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
        assertThat(testPRezeptAbg.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testPRezeptAbg.getkArt()).isEqualTo(UPDATED_K_ART);
        assertThat(testPRezeptAbg.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testPRezeptAbg.getpRezeptTyp()).isEqualTo(UPDATED_P_REZEPT_TYP);
        assertThat(testPRezeptAbg.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testPRezeptAbg.getSonstige()).isEqualTo(UPDATED_SONSTIGE);
        assertThat(testPRezeptAbg.getSprStBedarf()).isEqualTo(UPDATED_SPR_ST_BEDARF);
        assertThat(testPRezeptAbg.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testPRezeptAbg.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
        assertThat(testPRezeptAbg.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void patchNonExistingPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pRezeptAbg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbg))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptAbg))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPRezeptAbg() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptAbgRepository.findAll().size();
        pRezeptAbg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptAbgMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pRezeptAbg))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptAbg in the database
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePRezeptAbg() throws Exception {
        // Initialize the database
        pRezeptAbgRepository.saveAndFlush(pRezeptAbg);

        int databaseSizeBeforeDelete = pRezeptAbgRepository.findAll().size();

        // Delete the pRezeptAbg
        restPRezeptAbgMockMvc
            .perform(delete(ENTITY_API_URL_ID, pRezeptAbg.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PRezeptAbg> pRezeptAbgList = pRezeptAbgRepository.findAll();
        assertThat(pRezeptAbgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
