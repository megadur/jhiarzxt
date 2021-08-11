package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PRezeptVer;
import com.mycompany.myapp.repository.PRezeptVerRepository;
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
 * Integration tests for the {@link PRezeptVerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PRezeptVerResourceIT {

    private static final String DEFAULT_P_REZEPT_ID_9 = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID_9 = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK_SEND = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK_SEND = "BBBBBBBBBB";

    private static final String DEFAULT_ARB_PLATZ = "AAAAAAAAAA";
    private static final String UPDATED_ARB_PLATZ = "BBBBBBBBBB";

    private static final String DEFAULT_AVS_ID = "AAAAAAAAAA";
    private static final String UPDATED_AVS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIENER = "AAAAAAAAAA";
    private static final String UPDATED_BEDIENER = "BBBBBBBBBB";

    private static final String DEFAULT_BVG = "AAAAAAAAAA";
    private static final String UPDATED_BVG = "BBBBBBBBBB";

    private static final String DEFAULT_E_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_E_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ERST_ZEITPUNKT = "AAAAAAAAAA";
    private static final String UPDATED_ERST_ZEITPUNKT = "BBBBBBBBBB";

    private static final String DEFAULT_K_NAME = "AAAAAAAAAA";
    private static final String UPDATED_K_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KK_IK = "AAAAAAAAAA";
    private static final String UPDATED_KK_IK = "BBBBBBBBBB";

    private static final String DEFAULT_LA_NR = "AAAAAAAAAA";
    private static final String UPDATED_LA_NR = "BBBBBBBBBB";

    private static final String DEFAULT_VRS_NR = "AAAAAAAAAA";
    private static final String UPDATED_VRS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_VRTRGS_ARZT_NR = "AAAAAAAAAA";
    private static final String UPDATED_VRTRGS_ARZT_NR = "BBBBBBBBBB";

    private static final String DEFAULT_V_GEB = "AAAAAAAAAA";
    private static final String UPDATED_V_GEB = "BBBBBBBBBB";

    private static final String DEFAULT_V_STAT = "AAAAAAAAAA";
    private static final String UPDATED_V_STAT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-rezept-vers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PRezeptVerRepository pRezeptVerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPRezeptVerMockMvc;

    private PRezeptVer pRezeptVer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptVer createEntity(EntityManager em) {
        PRezeptVer pRezeptVer = new PRezeptVer()
            .pRezeptId9(DEFAULT_P_REZEPT_ID_9)
            .apoIk(DEFAULT_APO_IK)
            .apoIkSend(DEFAULT_APO_IK_SEND)
            .arbPlatz(DEFAULT_ARB_PLATZ)
            .avsId(DEFAULT_AVS_ID)
            .bediener(DEFAULT_BEDIENER)
            .bvg(DEFAULT_BVG)
            .eStatus(DEFAULT_E_STATUS)
            .erstZeitpunkt(DEFAULT_ERST_ZEITPUNKT)
            .kName(DEFAULT_K_NAME)
            .kkIk(DEFAULT_KK_IK)
            .laNr(DEFAULT_LA_NR)
            .vrsNr(DEFAULT_VRS_NR)
            .vrtrgsArztNr(DEFAULT_VRTRGS_ARZT_NR)
            .vGeb(DEFAULT_V_GEB)
            .vStat(DEFAULT_V_STAT);
        return pRezeptVer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PRezeptVer createUpdatedEntity(EntityManager em) {
        PRezeptVer pRezeptVer = new PRezeptVer()
            .pRezeptId9(UPDATED_P_REZEPT_ID_9)
            .apoIk(UPDATED_APO_IK)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .bvg(UPDATED_BVG)
            .eStatus(UPDATED_E_STATUS)
            .erstZeitpunkt(UPDATED_ERST_ZEITPUNKT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT);
        return pRezeptVer;
    }

    @BeforeEach
    public void initTest() {
        pRezeptVer = createEntity(em);
    }

    @Test
    @Transactional
    void createPRezeptVer() throws Exception {
        int databaseSizeBeforeCreate = pRezeptVerRepository.findAll().size();
        // Create the PRezeptVer
        restPRezeptVerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptVer)))
            .andExpect(status().isCreated());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeCreate + 1);
        PRezeptVer testPRezeptVer = pRezeptVerList.get(pRezeptVerList.size() - 1);
        assertThat(testPRezeptVer.getpRezeptId9()).isEqualTo(DEFAULT_P_REZEPT_ID_9);
        assertThat(testPRezeptVer.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPRezeptVer.getApoIkSend()).isEqualTo(DEFAULT_APO_IK_SEND);
        assertThat(testPRezeptVer.getArbPlatz()).isEqualTo(DEFAULT_ARB_PLATZ);
        assertThat(testPRezeptVer.getAvsId()).isEqualTo(DEFAULT_AVS_ID);
        assertThat(testPRezeptVer.getBediener()).isEqualTo(DEFAULT_BEDIENER);
        assertThat(testPRezeptVer.getBvg()).isEqualTo(DEFAULT_BVG);
        assertThat(testPRezeptVer.geteStatus()).isEqualTo(DEFAULT_E_STATUS);
        assertThat(testPRezeptVer.getErstZeitpunkt()).isEqualTo(DEFAULT_ERST_ZEITPUNKT);
        assertThat(testPRezeptVer.getkName()).isEqualTo(DEFAULT_K_NAME);
        assertThat(testPRezeptVer.getKkIk()).isEqualTo(DEFAULT_KK_IK);
        assertThat(testPRezeptVer.getLaNr()).isEqualTo(DEFAULT_LA_NR);
        assertThat(testPRezeptVer.getVrsNr()).isEqualTo(DEFAULT_VRS_NR);
        assertThat(testPRezeptVer.getVrtrgsArztNr()).isEqualTo(DEFAULT_VRTRGS_ARZT_NR);
        assertThat(testPRezeptVer.getvGeb()).isEqualTo(DEFAULT_V_GEB);
        assertThat(testPRezeptVer.getvStat()).isEqualTo(DEFAULT_V_STAT);
    }

    @Test
    @Transactional
    void createPRezeptVerWithExistingId() throws Exception {
        // Create the PRezeptVer with an existing ID
        pRezeptVer.setId(1L);

        int databaseSizeBeforeCreate = pRezeptVerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPRezeptVerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptVer)))
            .andExpect(status().isBadRequest());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPRezeptVers() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        // Get all the pRezeptVerList
        restPRezeptVerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pRezeptVer.getId().intValue())))
            .andExpect(jsonPath("$.[*].pRezeptId9").value(hasItem(DEFAULT_P_REZEPT_ID_9)))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].apoIkSend").value(hasItem(DEFAULT_APO_IK_SEND)))
            .andExpect(jsonPath("$.[*].arbPlatz").value(hasItem(DEFAULT_ARB_PLATZ)))
            .andExpect(jsonPath("$.[*].avsId").value(hasItem(DEFAULT_AVS_ID)))
            .andExpect(jsonPath("$.[*].bediener").value(hasItem(DEFAULT_BEDIENER)))
            .andExpect(jsonPath("$.[*].bvg").value(hasItem(DEFAULT_BVG)))
            .andExpect(jsonPath("$.[*].eStatus").value(hasItem(DEFAULT_E_STATUS)))
            .andExpect(jsonPath("$.[*].erstZeitpunkt").value(hasItem(DEFAULT_ERST_ZEITPUNKT)))
            .andExpect(jsonPath("$.[*].kName").value(hasItem(DEFAULT_K_NAME)))
            .andExpect(jsonPath("$.[*].kkIk").value(hasItem(DEFAULT_KK_IK)))
            .andExpect(jsonPath("$.[*].laNr").value(hasItem(DEFAULT_LA_NR)))
            .andExpect(jsonPath("$.[*].vrsNr").value(hasItem(DEFAULT_VRS_NR)))
            .andExpect(jsonPath("$.[*].vrtrgsArztNr").value(hasItem(DEFAULT_VRTRGS_ARZT_NR)))
            .andExpect(jsonPath("$.[*].vGeb").value(hasItem(DEFAULT_V_GEB)))
            .andExpect(jsonPath("$.[*].vStat").value(hasItem(DEFAULT_V_STAT)));
    }

    @Test
    @Transactional
    void getPRezeptVer() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        // Get the pRezeptVer
        restPRezeptVerMockMvc
            .perform(get(ENTITY_API_URL_ID, pRezeptVer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pRezeptVer.getId().intValue()))
            .andExpect(jsonPath("$.pRezeptId9").value(DEFAULT_P_REZEPT_ID_9))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.apoIkSend").value(DEFAULT_APO_IK_SEND))
            .andExpect(jsonPath("$.arbPlatz").value(DEFAULT_ARB_PLATZ))
            .andExpect(jsonPath("$.avsId").value(DEFAULT_AVS_ID))
            .andExpect(jsonPath("$.bediener").value(DEFAULT_BEDIENER))
            .andExpect(jsonPath("$.bvg").value(DEFAULT_BVG))
            .andExpect(jsonPath("$.eStatus").value(DEFAULT_E_STATUS))
            .andExpect(jsonPath("$.erstZeitpunkt").value(DEFAULT_ERST_ZEITPUNKT))
            .andExpect(jsonPath("$.kName").value(DEFAULT_K_NAME))
            .andExpect(jsonPath("$.kkIk").value(DEFAULT_KK_IK))
            .andExpect(jsonPath("$.laNr").value(DEFAULT_LA_NR))
            .andExpect(jsonPath("$.vrsNr").value(DEFAULT_VRS_NR))
            .andExpect(jsonPath("$.vrtrgsArztNr").value(DEFAULT_VRTRGS_ARZT_NR))
            .andExpect(jsonPath("$.vGeb").value(DEFAULT_V_GEB))
            .andExpect(jsonPath("$.vStat").value(DEFAULT_V_STAT));
    }

    @Test
    @Transactional
    void getNonExistingPRezeptVer() throws Exception {
        // Get the pRezeptVer
        restPRezeptVerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPRezeptVer() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();

        // Update the pRezeptVer
        PRezeptVer updatedPRezeptVer = pRezeptVerRepository.findById(pRezeptVer.getId()).get();
        // Disconnect from session so that the updates on updatedPRezeptVer are not directly saved in db
        em.detach(updatedPRezeptVer);
        updatedPRezeptVer
            .pRezeptId9(UPDATED_P_REZEPT_ID_9)
            .apoIk(UPDATED_APO_IK)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .bvg(UPDATED_BVG)
            .eStatus(UPDATED_E_STATUS)
            .erstZeitpunkt(UPDATED_ERST_ZEITPUNKT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT);

        restPRezeptVerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPRezeptVer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPRezeptVer))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
        PRezeptVer testPRezeptVer = pRezeptVerList.get(pRezeptVerList.size() - 1);
        assertThat(testPRezeptVer.getpRezeptId9()).isEqualTo(UPDATED_P_REZEPT_ID_9);
        assertThat(testPRezeptVer.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPRezeptVer.getApoIkSend()).isEqualTo(UPDATED_APO_IK_SEND);
        assertThat(testPRezeptVer.getArbPlatz()).isEqualTo(UPDATED_ARB_PLATZ);
        assertThat(testPRezeptVer.getAvsId()).isEqualTo(UPDATED_AVS_ID);
        assertThat(testPRezeptVer.getBediener()).isEqualTo(UPDATED_BEDIENER);
        assertThat(testPRezeptVer.getBvg()).isEqualTo(UPDATED_BVG);
        assertThat(testPRezeptVer.geteStatus()).isEqualTo(UPDATED_E_STATUS);
        assertThat(testPRezeptVer.getErstZeitpunkt()).isEqualTo(UPDATED_ERST_ZEITPUNKT);
        assertThat(testPRezeptVer.getkName()).isEqualTo(UPDATED_K_NAME);
        assertThat(testPRezeptVer.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testPRezeptVer.getLaNr()).isEqualTo(UPDATED_LA_NR);
        assertThat(testPRezeptVer.getVrsNr()).isEqualTo(UPDATED_VRS_NR);
        assertThat(testPRezeptVer.getVrtrgsArztNr()).isEqualTo(UPDATED_VRTRGS_ARZT_NR);
        assertThat(testPRezeptVer.getvGeb()).isEqualTo(UPDATED_V_GEB);
        assertThat(testPRezeptVer.getvStat()).isEqualTo(UPDATED_V_STAT);
    }

    @Test
    @Transactional
    void putNonExistingPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pRezeptVer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptVer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptVer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pRezeptVer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePRezeptVerWithPatch() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();

        // Update the pRezeptVer using partial update
        PRezeptVer partialUpdatedPRezeptVer = new PRezeptVer();
        partialUpdatedPRezeptVer.setId(pRezeptVer.getId());

        partialUpdatedPRezeptVer
            .pRezeptId9(UPDATED_P_REZEPT_ID_9)
            .apoIk(UPDATED_APO_IK)
            .avsId(UPDATED_AVS_ID)
            .bvg(UPDATED_BVG)
            .erstZeitpunkt(UPDATED_ERST_ZEITPUNKT)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR);

        restPRezeptVerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptVer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptVer))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
        PRezeptVer testPRezeptVer = pRezeptVerList.get(pRezeptVerList.size() - 1);
        assertThat(testPRezeptVer.getpRezeptId9()).isEqualTo(UPDATED_P_REZEPT_ID_9);
        assertThat(testPRezeptVer.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPRezeptVer.getApoIkSend()).isEqualTo(DEFAULT_APO_IK_SEND);
        assertThat(testPRezeptVer.getArbPlatz()).isEqualTo(DEFAULT_ARB_PLATZ);
        assertThat(testPRezeptVer.getAvsId()).isEqualTo(UPDATED_AVS_ID);
        assertThat(testPRezeptVer.getBediener()).isEqualTo(DEFAULT_BEDIENER);
        assertThat(testPRezeptVer.getBvg()).isEqualTo(UPDATED_BVG);
        assertThat(testPRezeptVer.geteStatus()).isEqualTo(DEFAULT_E_STATUS);
        assertThat(testPRezeptVer.getErstZeitpunkt()).isEqualTo(UPDATED_ERST_ZEITPUNKT);
        assertThat(testPRezeptVer.getkName()).isEqualTo(DEFAULT_K_NAME);
        assertThat(testPRezeptVer.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testPRezeptVer.getLaNr()).isEqualTo(UPDATED_LA_NR);
        assertThat(testPRezeptVer.getVrsNr()).isEqualTo(UPDATED_VRS_NR);
        assertThat(testPRezeptVer.getVrtrgsArztNr()).isEqualTo(UPDATED_VRTRGS_ARZT_NR);
        assertThat(testPRezeptVer.getvGeb()).isEqualTo(DEFAULT_V_GEB);
        assertThat(testPRezeptVer.getvStat()).isEqualTo(DEFAULT_V_STAT);
    }

    @Test
    @Transactional
    void fullUpdatePRezeptVerWithPatch() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();

        // Update the pRezeptVer using partial update
        PRezeptVer partialUpdatedPRezeptVer = new PRezeptVer();
        partialUpdatedPRezeptVer.setId(pRezeptVer.getId());

        partialUpdatedPRezeptVer
            .pRezeptId9(UPDATED_P_REZEPT_ID_9)
            .apoIk(UPDATED_APO_IK)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .arbPlatz(UPDATED_ARB_PLATZ)
            .avsId(UPDATED_AVS_ID)
            .bediener(UPDATED_BEDIENER)
            .bvg(UPDATED_BVG)
            .eStatus(UPDATED_E_STATUS)
            .erstZeitpunkt(UPDATED_ERST_ZEITPUNKT)
            .kName(UPDATED_K_NAME)
            .kkIk(UPDATED_KK_IK)
            .laNr(UPDATED_LA_NR)
            .vrsNr(UPDATED_VRS_NR)
            .vrtrgsArztNr(UPDATED_VRTRGS_ARZT_NR)
            .vGeb(UPDATED_V_GEB)
            .vStat(UPDATED_V_STAT);

        restPRezeptVerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPRezeptVer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPRezeptVer))
            )
            .andExpect(status().isOk());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
        PRezeptVer testPRezeptVer = pRezeptVerList.get(pRezeptVerList.size() - 1);
        assertThat(testPRezeptVer.getpRezeptId9()).isEqualTo(UPDATED_P_REZEPT_ID_9);
        assertThat(testPRezeptVer.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPRezeptVer.getApoIkSend()).isEqualTo(UPDATED_APO_IK_SEND);
        assertThat(testPRezeptVer.getArbPlatz()).isEqualTo(UPDATED_ARB_PLATZ);
        assertThat(testPRezeptVer.getAvsId()).isEqualTo(UPDATED_AVS_ID);
        assertThat(testPRezeptVer.getBediener()).isEqualTo(UPDATED_BEDIENER);
        assertThat(testPRezeptVer.getBvg()).isEqualTo(UPDATED_BVG);
        assertThat(testPRezeptVer.geteStatus()).isEqualTo(UPDATED_E_STATUS);
        assertThat(testPRezeptVer.getErstZeitpunkt()).isEqualTo(UPDATED_ERST_ZEITPUNKT);
        assertThat(testPRezeptVer.getkName()).isEqualTo(UPDATED_K_NAME);
        assertThat(testPRezeptVer.getKkIk()).isEqualTo(UPDATED_KK_IK);
        assertThat(testPRezeptVer.getLaNr()).isEqualTo(UPDATED_LA_NR);
        assertThat(testPRezeptVer.getVrsNr()).isEqualTo(UPDATED_VRS_NR);
        assertThat(testPRezeptVer.getVrtrgsArztNr()).isEqualTo(UPDATED_VRTRGS_ARZT_NR);
        assertThat(testPRezeptVer.getvGeb()).isEqualTo(UPDATED_V_GEB);
        assertThat(testPRezeptVer.getvStat()).isEqualTo(UPDATED_V_STAT);
    }

    @Test
    @Transactional
    void patchNonExistingPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pRezeptVer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptVer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pRezeptVer))
            )
            .andExpect(status().isBadRequest());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPRezeptVer() throws Exception {
        int databaseSizeBeforeUpdate = pRezeptVerRepository.findAll().size();
        pRezeptVer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPRezeptVerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pRezeptVer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PRezeptVer in the database
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePRezeptVer() throws Exception {
        // Initialize the database
        pRezeptVerRepository.saveAndFlush(pRezeptVer);

        int databaseSizeBeforeDelete = pRezeptVerRepository.findAll().size();

        // Delete the pRezeptVer
        restPRezeptVerMockMvc
            .perform(delete(ENTITY_API_URL_ID, pRezeptVer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PRezeptVer> pRezeptVerList = pRezeptVerRepository.findAll();
        assertThat(pRezeptVerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
