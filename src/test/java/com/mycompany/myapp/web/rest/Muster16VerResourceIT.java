package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Muster16Ver;
import com.mycompany.myapp.repository.Muster16VerRepository;
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
 * Integration tests for the {@link Muster16VerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Muster16VerResourceIT {

    private static final String DEFAULT_A_UNFALL = "AAAAAAAAAA";
    private static final String UPDATED_A_UNFALL = "BBBBBBBBBB";

    private static final String DEFAULT_AB_DATUM = "AAAAAAAAAA";
    private static final String UPDATED_AB_DATUM = "BBBBBBBBBB";

    private static final String DEFAULT_VRS_NR = "AAAAAAAAAA";
    private static final String UPDATED_VRS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_VRTRGS_ARZT_NR = "AAAAAAAAAA";
    private static final String UPDATED_VRTRGS_ARZT_NR = "BBBBBBBBBB";

    private static final String DEFAULT_SPR_ST_BEDARF = "AAAAAAAAAA";
    private static final String UPDATED_SPR_ST_BEDARF = "BBBBBBBBBB";

    private static final String DEFAULT_UNFALL = "AAAAAAAAAA";
    private static final String UPDATED_UNFALL = "BBBBBBBBBB";

    private static final String DEFAULT_UNFALL_TAG = "AAAAAAAAAA";
    private static final String UPDATED_UNFALL_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_V_GEB = "AAAAAAAAAA";
    private static final String UPDATED_V_GEB = "BBBBBBBBBB";

    private static final String DEFAULT_V_STAT = "AAAAAAAAAA";
    private static final String UPDATED_V_STAT = "BBBBBBBBBB";

    private static final String DEFAULT_VER_DAT = "AAAAAAAAAA";
    private static final String UPDATED_VER_DAT = "BBBBBBBBBB";

    private static final String DEFAULT_K_NAME = "AAAAAAAAAA";
    private static final String UPDATED_K_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KK_IK = "AAAAAAAAAA";
    private static final String UPDATED_KK_IK = "BBBBBBBBBB";

    private static final String DEFAULT_LA_NR = "AAAAAAAAAA";
    private static final String UPDATED_LA_NR = "BBBBBBBBBB";

    private static final String DEFAULT_NOCTU = "AAAAAAAAAA";
    private static final String UPDATED_NOCTU = "BBBBBBBBBB";

    private static final String DEFAULT_HILF = "AAAAAAAAAA";
    private static final String UPDATED_HILF = "BBBBBBBBBB";

    private static final String DEFAULT_IMPF = "AAAAAAAAAA";
    private static final String UPDATED_IMPF = "BBBBBBBBBB";

    private static final String DEFAULT_K_ART = "AAAAAAAAAA";
    private static final String UPDATED_K_ART = "BBBBBBBBBB";

    private static final String DEFAULT_R_TYP = "AAAAAAAAAA";
    private static final String UPDATED_R_TYP = "BBBBBBBBBB";

    private static final String DEFAULT_REZEPT_TYP = "AAAAAAAAAA";
    private static final String UPDATED_REZEPT_TYP = "BBBBBBBBBB";

    private static final String DEFAULT_BGR_PFL = "AAAAAAAAAA";
    private static final String UPDATED_BGR_PFL = "BBBBBBBBBB";

    private static final String DEFAULT_BVG = "AAAAAAAAAA";
    private static final String UPDATED_BVG = "BBBBBBBBBB";

    private static final String DEFAULT_EIG_BET = "AAAAAAAAAA";
    private static final String UPDATED_EIG_BET = "BBBBBBBBBB";

    private static final String DEFAULT_GEB_FREI = "AAAAAAAAAA";
    private static final String UPDATED_GEB_FREI = "BBBBBBBBBB";

    private static final String DEFAULT_SONSTIGE = "AAAAAAAAAA";
    private static final String UPDATED_SONSTIGE = "BBBBBBBBBB";

    private static final String DEFAULT_VK_GUELTIG_BIS = "AAAAAAAAAA";
    private static final String UPDATED_VK_GUELTIG_BIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/muster-16-vers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Muster16VerRepository muster16VerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuster16VerMockMvc;

    private Muster16Ver muster16Ver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Ver createEntity(EntityManager em) {
        Muster16Ver muster16Ver = new Muster16Ver()
            .aUnfall(DEFAULT_A_UNFALL)
            .abDatum(DEFAULT_AB_DATUM)
            .vrsNr(DEFAULT_VRS_NR)
            .vrtrgsArztNr(DEFAULT_VRTRGS_ARZT_NR)
            .sprStBedarf(DEFAULT_SPR_ST_BEDARF)
            .unfall(DEFAULT_UNFALL)
            .unfallTag(DEFAULT_UNFALL_TAG)
            .vGeb(DEFAULT_V_GEB)
            .vStat(DEFAULT_V_STAT)
            .verDat(DEFAULT_VER_DAT)
            .kName(DEFAULT_K_NAME)
            .kkIk(DEFAULT_KK_IK)
            .laNr(DEFAULT_LA_NR)
            .noctu(DEFAULT_NOCTU)
            .hilf(DEFAULT_HILF)
            .impf(DEFAULT_IMPF)
            .kArt(DEFAULT_K_ART)
            .rTyp(DEFAULT_R_TYP)
            .rezeptTyp(DEFAULT_REZEPT_TYP)
            .bgrPfl(DEFAULT_BGR_PFL)
            .bvg(DEFAULT_BVG)
            .eigBet(DEFAULT_EIG_BET)
            .gebFrei(DEFAULT_GEB_FREI)
            .sonstige(DEFAULT_SONSTIGE)
            .vkGueltigBis(DEFAULT_VK_GUELTIG_BIS);
        return muster16Ver;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16Ver createUpdatedEntity(EntityManager em) {
        Muster16Ver muster16Ver = new Muster16Ver()
            .aUnfall(UPDATED_A_UNFALL)
            .abDatum(UPDATED_AB_DATUM)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .unfall(UPDATED_UNFALL)
            .unfallTag(UPDATED_UNFALL_TAG)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT)
            .verDat(UPDATED_VER_DAT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .noctu(UPDATED_NOCTU)
            .hilf(UPDATED_HILF)
            .impf(UPDATED_IMPF)
            .kArt(UPDATED_K_ART)
            .rTyp(UPDATED_R_TYP)
            .rezeptTyp(UPDATED_REZEPT_TYP)
            .bgrPfl(UPDATED_BGR_PFL)
            .bvg(UPDATED_BVG)
            .eigBet(UPDATED_EIG_BET)
            .gebFrei(UPDATED_GEB_FREI)
            .sonstige(UPDATED_SONSTIGE)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS);
        return muster16Ver;
    }

    @BeforeEach
    public void initTest() {
        muster16Ver = createEntity(em);
    }

    @Test
    @Transactional
    void createMuster16Ver() throws Exception {
        int databaseSizeBeforeCreate = muster16VerRepository.findAll().size();
        // Create the Muster16Ver
        restMuster16VerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Ver)))
            .andExpect(status().isCreated());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeCreate + 1);
        Muster16Ver testMuster16Ver = muster16VerList.get(muster16VerList.size() - 1);
        assertThat(testMuster16Ver.getaUnfall()).isEqualTo(DEFAULT_A_UNFALL);
        assertThat(testMuster16Ver.getAbDatum()).isEqualTo(DEFAULT_AB_DATUM);
        assertThat(testMuster16Ver.getVrsNr()).isEqualTo(DEFAULT_VRS_NR);
        assertThat(testMuster16Ver.getVrtrgsArztNr()).isEqualTo(DEFAULT_VRTRGS_ARZT_NR);
        assertThat(testMuster16Ver.getSprStBedarf()).isEqualTo(DEFAULT_SPR_ST_BEDARF);
        assertThat(testMuster16Ver.getUnfall()).isEqualTo(DEFAULT_UNFALL);
        assertThat(testMuster16Ver.getUnfallTag()).isEqualTo(DEFAULT_UNFALL_TAG);
        assertThat(testMuster16Ver.getvGeb()).isEqualTo(DEFAULT_V_GEB);
        assertThat(testMuster16Ver.getvStat()).isEqualTo(DEFAULT_V_STAT);
        assertThat(testMuster16Ver.getVerDat()).isEqualTo(DEFAULT_VER_DAT);
        assertThat(testMuster16Ver.getkName()).isEqualTo(DEFAULT_K_NAME);
        assertThat(testMuster16Ver.getKkIk()).isEqualTo(DEFAULT_KK_IK);
        assertThat(testMuster16Ver.getLaNr()).isEqualTo(DEFAULT_LA_NR);
        assertThat(testMuster16Ver.getNoctu()).isEqualTo(DEFAULT_NOCTU);
        assertThat(testMuster16Ver.getHilf()).isEqualTo(DEFAULT_HILF);
        assertThat(testMuster16Ver.getImpf()).isEqualTo(DEFAULT_IMPF);
        assertThat(testMuster16Ver.getkArt()).isEqualTo(DEFAULT_K_ART);
        assertThat(testMuster16Ver.getrTyp()).isEqualTo(DEFAULT_R_TYP);
        assertThat(testMuster16Ver.getRezeptTyp()).isEqualTo(DEFAULT_REZEPT_TYP);
        assertThat(testMuster16Ver.getBgrPfl()).isEqualTo(DEFAULT_BGR_PFL);
        assertThat(testMuster16Ver.getBvg()).isEqualTo(DEFAULT_BVG);
        assertThat(testMuster16Ver.getEigBet()).isEqualTo(DEFAULT_EIG_BET);
        assertThat(testMuster16Ver.getGebFrei()).isEqualTo(DEFAULT_GEB_FREI);
        assertThat(testMuster16Ver.getSonstige()).isEqualTo(DEFAULT_SONSTIGE);
        assertThat(testMuster16Ver.getVkGueltigBis()).isEqualTo(DEFAULT_VK_GUELTIG_BIS);
    }

    @Test
    @Transactional
    void createMuster16VerWithExistingId() throws Exception {
        // Create the Muster16Ver with an existing ID
        muster16Ver.setId(1L);

        int databaseSizeBeforeCreate = muster16VerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuster16VerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Ver)))
            .andExpect(status().isBadRequest());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuster16Vers() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        // Get all the muster16VerList
        restMuster16VerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muster16Ver.getId().intValue())))
            .andExpect(jsonPath("$.[*].aUnfall").value(hasItem(DEFAULT_A_UNFALL)))
            .andExpect(jsonPath("$.[*].abDatum").value(hasItem(DEFAULT_AB_DATUM)))
            .andExpect(jsonPath("$.[*].vrsNr").value(hasItem(DEFAULT_VRS_NR)))
            .andExpect(jsonPath("$.[*].vrtrgsArztNr").value(hasItem(DEFAULT_VRTRGS_ARZT_NR)))
            .andExpect(jsonPath("$.[*].sprStBedarf").value(hasItem(DEFAULT_SPR_ST_BEDARF)))
            .andExpect(jsonPath("$.[*].unfall").value(hasItem(DEFAULT_UNFALL)))
            .andExpect(jsonPath("$.[*].unfallTag").value(hasItem(DEFAULT_UNFALL_TAG)))
            .andExpect(jsonPath("$.[*].vGeb").value(hasItem(DEFAULT_V_GEB)))
            .andExpect(jsonPath("$.[*].vStat").value(hasItem(DEFAULT_V_STAT)))
            .andExpect(jsonPath("$.[*].verDat").value(hasItem(DEFAULT_VER_DAT)))
            .andExpect(jsonPath("$.[*].kName").value(hasItem(DEFAULT_K_NAME)))
            .andExpect(jsonPath("$.[*].kkIk").value(hasItem(DEFAULT_KK_IK)))
            .andExpect(jsonPath("$.[*].laNr").value(hasItem(DEFAULT_LA_NR)))
            .andExpect(jsonPath("$.[*].noctu").value(hasItem(DEFAULT_NOCTU)))
            .andExpect(jsonPath("$.[*].hilf").value(hasItem(DEFAULT_HILF)))
            .andExpect(jsonPath("$.[*].impf").value(hasItem(DEFAULT_IMPF)))
            .andExpect(jsonPath("$.[*].kArt").value(hasItem(DEFAULT_K_ART)))
            .andExpect(jsonPath("$.[*].rTyp").value(hasItem(DEFAULT_R_TYP)))
            .andExpect(jsonPath("$.[*].rezeptTyp").value(hasItem(DEFAULT_REZEPT_TYP)))
            .andExpect(jsonPath("$.[*].bgrPfl").value(hasItem(DEFAULT_BGR_PFL)))
            .andExpect(jsonPath("$.[*].bvg").value(hasItem(DEFAULT_BVG)))
            .andExpect(jsonPath("$.[*].eigBet").value(hasItem(DEFAULT_EIG_BET)))
            .andExpect(jsonPath("$.[*].gebFrei").value(hasItem(DEFAULT_GEB_FREI)))
            .andExpect(jsonPath("$.[*].sonstige").value(hasItem(DEFAULT_SONSTIGE)))
            .andExpect(jsonPath("$.[*].vkGueltigBis").value(hasItem(DEFAULT_VK_GUELTIG_BIS)));
    }

    @Test
    @Transactional
    void getMuster16Ver() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        // Get the muster16Ver
        restMuster16VerMockMvc
            .perform(get(ENTITY_API_URL_ID, muster16Ver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(muster16Ver.getId().intValue()))
            .andExpect(jsonPath("$.aUnfall").value(DEFAULT_A_UNFALL))
            .andExpect(jsonPath("$.abDatum").value(DEFAULT_AB_DATUM))
            .andExpect(jsonPath("$.vrsNr").value(DEFAULT_VRS_NR))
            .andExpect(jsonPath("$.vrtrgsArztNr").value(DEFAULT_VRTRGS_ARZT_NR))
            .andExpect(jsonPath("$.sprStBedarf").value(DEFAULT_SPR_ST_BEDARF))
            .andExpect(jsonPath("$.unfall").value(DEFAULT_UNFALL))
            .andExpect(jsonPath("$.unfallTag").value(DEFAULT_UNFALL_TAG))
            .andExpect(jsonPath("$.vGeb").value(DEFAULT_V_GEB))
            .andExpect(jsonPath("$.vStat").value(DEFAULT_V_STAT))
            .andExpect(jsonPath("$.verDat").value(DEFAULT_VER_DAT))
            .andExpect(jsonPath("$.kName").value(DEFAULT_K_NAME))
            .andExpect(jsonPath("$.kkIk").value(DEFAULT_KK_IK))
            .andExpect(jsonPath("$.laNr").value(DEFAULT_LA_NR))
            .andExpect(jsonPath("$.noctu").value(DEFAULT_NOCTU))
            .andExpect(jsonPath("$.hilf").value(DEFAULT_HILF))
            .andExpect(jsonPath("$.impf").value(DEFAULT_IMPF))
            .andExpect(jsonPath("$.kArt").value(DEFAULT_K_ART))
            .andExpect(jsonPath("$.rTyp").value(DEFAULT_R_TYP))
            .andExpect(jsonPath("$.rezeptTyp").value(DEFAULT_REZEPT_TYP))
            .andExpect(jsonPath("$.bgrPfl").value(DEFAULT_BGR_PFL))
            .andExpect(jsonPath("$.bvg").value(DEFAULT_BVG))
            .andExpect(jsonPath("$.eigBet").value(DEFAULT_EIG_BET))
            .andExpect(jsonPath("$.gebFrei").value(DEFAULT_GEB_FREI))
            .andExpect(jsonPath("$.sonstige").value(DEFAULT_SONSTIGE))
            .andExpect(jsonPath("$.vkGueltigBis").value(DEFAULT_VK_GUELTIG_BIS));
    }

    @Test
    @Transactional
    void getNonExistingMuster16Ver() throws Exception {
        // Get the muster16Ver
        restMuster16VerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMuster16Ver() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();

        // Update the muster16Ver
        Muster16Ver updatedMuster16Ver = muster16VerRepository.findById(muster16Ver.getId()).get();
        // Disconnect from session so that the updates on updatedMuster16Ver are not directly saved in db
        em.detach(updatedMuster16Ver);
        updatedMuster16Ver
            .aUnfall(UPDATED_A_UNFALL)
            .abDatum(UPDATED_AB_DATUM)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .unfall(UPDATED_UNFALL)
            .unfallTag(UPDATED_UNFALL_TAG)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT)
            .verDat(UPDATED_VER_DAT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .noctu(UPDATED_NOCTU)
            .hilf(UPDATED_HILF)
            .impf(UPDATED_IMPF)
            .kArt(UPDATED_K_ART)
            .rTyp(UPDATED_R_TYP)
            .rezeptTyp(UPDATED_REZEPT_TYP)
            .bgrPfl(UPDATED_BGR_PFL)
            .bvg(UPDATED_BVG)
            .eigBet(UPDATED_EIG_BET)
            .gebFrei(UPDATED_GEB_FREI)
            .sonstige(UPDATED_SONSTIGE)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS);

        restMuster16VerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuster16Ver.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMuster16Ver))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
        Muster16Ver testMuster16Ver = muster16VerList.get(muster16VerList.size() - 1);
        assertThat(testMuster16Ver.getaUnfall()).isEqualTo(UPDATED_A_UNFALL);
        assertThat(testMuster16Ver.getAbDatum()).isEqualTo(UPDATED_AB_DATUM);
        assertThat(testMuster16Ver.getVrsNr()).isEqualTo(UPDATED_VRS_NR);
        assertThat(testMuster16Ver.getVrtrgsArztNr()).isEqualTo(UPDATED_VRTRGS_ARZT_NR);
        assertThat(testMuster16Ver.getSprStBedarf()).isEqualTo(UPDATED_SPR_ST_BEDARF);
        assertThat(testMuster16Ver.getUnfall()).isEqualTo(UPDATED_UNFALL);
        assertThat(testMuster16Ver.getUnfallTag()).isEqualTo(UPDATED_UNFALL_TAG);
        assertThat(testMuster16Ver.getvGeb()).isEqualTo(UPDATED_V_GEB);
        assertThat(testMuster16Ver.getvStat()).isEqualTo(UPDATED_V_STAT);
        assertThat(testMuster16Ver.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testMuster16Ver.getkName()).isEqualTo(UPDATED_K_NAME);
        assertThat(testMuster16Ver.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testMuster16Ver.getLaNr()).isEqualTo(UPDATED_LA_NR);
        assertThat(testMuster16Ver.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testMuster16Ver.getHilf()).isEqualTo(UPDATED_HILF);
        assertThat(testMuster16Ver.getImpf()).isEqualTo(UPDATED_IMPF);
        assertThat(testMuster16Ver.getkArt()).isEqualTo(UPDATED_K_ART);
        assertThat(testMuster16Ver.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testMuster16Ver.getRezeptTyp()).isEqualTo(UPDATED_REZEPT_TYP);
        assertThat(testMuster16Ver.getBgrPfl()).isEqualTo(UPDATED_BGR_PFL);
        assertThat(testMuster16Ver.getBvg()).isEqualTo(UPDATED_BVG);
        assertThat(testMuster16Ver.getEigBet()).isEqualTo(UPDATED_EIG_BET);
        assertThat(testMuster16Ver.getGebFrei()).isEqualTo(UPDATED_GEB_FREI);
        assertThat(testMuster16Ver.getSonstige()).isEqualTo(UPDATED_SONSTIGE);
        assertThat(testMuster16Ver.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
    }

    @Test
    @Transactional
    void putNonExistingMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, muster16Ver.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Ver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16Ver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16Ver)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuster16VerWithPatch() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();

        // Update the muster16Ver using partial update
        Muster16Ver partialUpdatedMuster16Ver = new Muster16Ver();
        partialUpdatedMuster16Ver.setId(muster16Ver.getId());

        partialUpdatedMuster16Ver
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .unfall(UPDATED_UNFALL)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT)
            .verDat(UPDATED_VER_DAT)
            .kkIk(UPDATED_KK_IK)
            .noctu(UPDATED_NOCTU)
            .hilf(UPDATED_HILF)
            .impf(UPDATED_IMPF)
            .rTyp(UPDATED_R_TYP)
            .rezeptTyp(UPDATED_REZEPT_TYP)
            .bgrPfl(UPDATED_BGR_PFL)
            .eigBet(UPDATED_EIG_BET)
            .gebFrei(UPDATED_GEB_FREI)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS);

        restMuster16VerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Ver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Ver))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
        Muster16Ver testMuster16Ver = muster16VerList.get(muster16VerList.size() - 1);
        assertThat(testMuster16Ver.getaUnfall()).isEqualTo(DEFAULT_A_UNFALL);
        assertThat(testMuster16Ver.getAbDatum()).isEqualTo(DEFAULT_AB_DATUM);
        assertThat(testMuster16Ver.getVrsNr()).isEqualTo(DEFAULT_VRS_NR);
        assertThat(testMuster16Ver.getVrtrgsArztNr()).isEqualTo(DEFAULT_VRTRGS_ARZT_NR);
        assertThat(testMuster16Ver.getSprStBedarf()).isEqualTo(UPDATED_SPR_ST_BEDARF);
        assertThat(testMuster16Ver.getUnfall()).isEqualTo(UPDATED_UNFALL);
        assertThat(testMuster16Ver.getUnfallTag()).isEqualTo(DEFAULT_UNFALL_TAG);
        assertThat(testMuster16Ver.getvGeb()).isEqualTo(UPDATED_V_GEB);
        assertThat(testMuster16Ver.getvStat()).isEqualTo(UPDATED_V_STAT);
        assertThat(testMuster16Ver.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testMuster16Ver.getkName()).isEqualTo(DEFAULT_K_NAME);
        assertThat(testMuster16Ver.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testMuster16Ver.getLaNr()).isEqualTo(DEFAULT_LA_NR);
        assertThat(testMuster16Ver.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testMuster16Ver.getHilf()).isEqualTo(UPDATED_HILF);
        assertThat(testMuster16Ver.getImpf()).isEqualTo(UPDATED_IMPF);
        assertThat(testMuster16Ver.getkArt()).isEqualTo(DEFAULT_K_ART);
        assertThat(testMuster16Ver.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testMuster16Ver.getRezeptTyp()).isEqualTo(UPDATED_REZEPT_TYP);
        assertThat(testMuster16Ver.getBgrPfl()).isEqualTo(UPDATED_BGR_PFL);
        assertThat(testMuster16Ver.getBvg()).isEqualTo(DEFAULT_BVG);
        assertThat(testMuster16Ver.getEigBet()).isEqualTo(UPDATED_EIG_BET);
        assertThat(testMuster16Ver.getGebFrei()).isEqualTo(UPDATED_GEB_FREI);
        assertThat(testMuster16Ver.getSonstige()).isEqualTo(DEFAULT_SONSTIGE);
        assertThat(testMuster16Ver.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
    }

    @Test
    @Transactional
    void fullUpdateMuster16VerWithPatch() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();

        // Update the muster16Ver using partial update
        Muster16Ver partialUpdatedMuster16Ver = new Muster16Ver();
        partialUpdatedMuster16Ver.setId(muster16Ver.getId());

        partialUpdatedMuster16Ver
            .aUnfall(UPDATED_A_UNFALL)
            .abDatum(UPDATED_AB_DATUM)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .sprStBedarf(UPDATED_SPR_ST_BEDARF)
            .unfall(UPDATED_UNFALL)
            .unfallTag(UPDATED_UNFALL_TAG)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT)
            .verDat(UPDATED_VER_DAT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .noctu(UPDATED_NOCTU)
            .hilf(UPDATED_HILF)
            .impf(UPDATED_IMPF)
            .kArt(UPDATED_K_ART)
            .rTyp(UPDATED_R_TYP)
            .rezeptTyp(UPDATED_REZEPT_TYP)
            .bgrPfl(UPDATED_BGR_PFL)
            .bvg(UPDATED_BVG)
            .eigBet(UPDATED_EIG_BET)
            .gebFrei(UPDATED_GEB_FREI)
            .sonstige(UPDATED_SONSTIGE)
            .vkGueltigBis(UPDATED_VK_GUELTIG_BIS);

        restMuster16VerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16Ver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16Ver))
            )
            .andExpect(status().isOk());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
        Muster16Ver testMuster16Ver = muster16VerList.get(muster16VerList.size() - 1);
        assertThat(testMuster16Ver.getaUnfall()).isEqualTo(UPDATED_A_UNFALL);
        assertThat(testMuster16Ver.getAbDatum()).isEqualTo(UPDATED_AB_DATUM);
        assertThat(testMuster16Ver.getVrsNr()).isEqualTo(UPDATED_VRS_NR);
        assertThat(testMuster16Ver.getVrtrgsArztNr()).isEqualTo(UPDATED_VRTRGS_ARZT_NR);
        assertThat(testMuster16Ver.getSprStBedarf()).isEqualTo(UPDATED_SPR_ST_BEDARF);
        assertThat(testMuster16Ver.getUnfall()).isEqualTo(UPDATED_UNFALL);
        assertThat(testMuster16Ver.getUnfallTag()).isEqualTo(UPDATED_UNFALL_TAG);
        assertThat(testMuster16Ver.getvGeb()).isEqualTo(UPDATED_V_GEB);
        assertThat(testMuster16Ver.getvStat()).isEqualTo(UPDATED_V_STAT);
        assertThat(testMuster16Ver.getVerDat()).isEqualTo(UPDATED_VER_DAT);
        assertThat(testMuster16Ver.getkName()).isEqualTo(UPDATED_K_NAME);
        assertThat(testMuster16Ver.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testMuster16Ver.getLaNr()).isEqualTo(UPDATED_LA_NR);
        assertThat(testMuster16Ver.getNoctu()).isEqualTo(UPDATED_NOCTU);
        assertThat(testMuster16Ver.getHilf()).isEqualTo(UPDATED_HILF);
        assertThat(testMuster16Ver.getImpf()).isEqualTo(UPDATED_IMPF);
        assertThat(testMuster16Ver.getkArt()).isEqualTo(UPDATED_K_ART);
        assertThat(testMuster16Ver.getrTyp()).isEqualTo(UPDATED_R_TYP);
        assertThat(testMuster16Ver.getRezeptTyp()).isEqualTo(UPDATED_REZEPT_TYP);
        assertThat(testMuster16Ver.getBgrPfl()).isEqualTo(UPDATED_BGR_PFL);
        assertThat(testMuster16Ver.getBvg()).isEqualTo(UPDATED_BVG);
        assertThat(testMuster16Ver.getEigBet()).isEqualTo(UPDATED_EIG_BET);
        assertThat(testMuster16Ver.getGebFrei()).isEqualTo(UPDATED_GEB_FREI);
        assertThat(testMuster16Ver.getSonstige()).isEqualTo(UPDATED_SONSTIGE);
        assertThat(testMuster16Ver.getVkGueltigBis()).isEqualTo(UPDATED_VK_GUELTIG_BIS);
    }

    @Test
    @Transactional
    void patchNonExistingMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, muster16Ver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Ver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16Ver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuster16Ver() throws Exception {
        int databaseSizeBeforeUpdate = muster16VerRepository.findAll().size();
        muster16Ver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16VerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(muster16Ver))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16Ver in the database
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuster16Ver() throws Exception {
        // Initialize the database
        muster16VerRepository.saveAndFlush(muster16Ver);

        int databaseSizeBeforeDelete = muster16VerRepository.findAll().size();

        // Delete the muster16Ver
        restMuster16VerMockMvc
            .perform(delete(ENTITY_API_URL_ID, muster16Ver.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Muster16Ver> muster16VerList = muster16VerRepository.findAll();
        assertThat(muster16VerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
