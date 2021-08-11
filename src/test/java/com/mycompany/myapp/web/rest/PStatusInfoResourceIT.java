package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PStatusInfo;
import com.mycompany.myapp.repository.PStatusInfoRepository;
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
 * Integration tests for the {@link PStatusInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PStatusInfoResourceIT {

    private static final String DEFAULT_P_STATUS_INFO_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_STATUS_INFO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_P_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_P_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK = "BBBBBBBBBB";

    private static final String DEFAULT_F_CODE = "AAAAAAAAAA";
    private static final String UPDATED_F_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_F_HAUPT_FEHLER = "AAAAAAAAAA";
    private static final String UPDATED_F_HAUPT_FEHLER = "BBBBBBBBBB";

    private static final String DEFAULT_F_KOMMENTAR = "AAAAAAAAAA";
    private static final String UPDATED_F_KOMMENTAR = "BBBBBBBBBB";

    private static final String DEFAULT_F_KURZ_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_F_KURZ_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_F_LANG_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_F_LANG_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_F_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_F_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_F_T_CODE = "AAAAAAAAAA";
    private static final String UPDATED_F_T_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_F_WERT = "AAAAAAAAAA";
    private static final String UPDATED_F_WERT = "BBBBBBBBBB";

    private static final String DEFAULT_FAKTOR = "AAAAAAAAAA";
    private static final String UPDATED_FAKTOR = "BBBBBBBBBB";

    private static final String DEFAULT_FRIST_ENDE = "AAAAAAAAAA";
    private static final String UPDATED_FRIST_ENDE = "BBBBBBBBBB";

    private static final String DEFAULT_GES_BRUTTO = "AAAAAAAAAA";
    private static final String UPDATED_GES_BRUTTO = "BBBBBBBBBB";

    private static final String DEFAULT_POS_NR = "AAAAAAAAAA";
    private static final String UPDATED_POS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_TAXE = "AAAAAAAAAA";
    private static final String UPDATED_TAXE = "BBBBBBBBBB";

    private static final String DEFAULT_ZEITPUNKT = "AAAAAAAAAA";
    private static final String UPDATED_ZEITPUNKT = "BBBBBBBBBB";

    private static final String DEFAULT_ZUZAHLUNG = "AAAAAAAAAA";
    private static final String UPDATED_ZUZAHLUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/p-status-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PStatusInfoRepository pStatusInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPStatusInfoMockMvc;

    private PStatusInfo pStatusInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PStatusInfo createEntity(EntityManager em) {
        PStatusInfo pStatusInfo = new PStatusInfo()
            .pStatusInfoId(DEFAULT_P_STATUS_INFO_ID)
            .pRezeptId(DEFAULT_P_REZEPT_ID)
            .apoIk(DEFAULT_APO_IK)
            .fCode(DEFAULT_F_CODE)
            .fHauptFehler(DEFAULT_F_HAUPT_FEHLER)
            .fKommentar(DEFAULT_F_KOMMENTAR)
            .fKurzText(DEFAULT_F_KURZ_TEXT)
            .fLangText(DEFAULT_F_LANG_TEXT)
            .fStatus(DEFAULT_F_STATUS)
            .fTCode(DEFAULT_F_T_CODE)
            .fWert(DEFAULT_F_WERT)
            .faktor(DEFAULT_FAKTOR)
            .fristEnde(DEFAULT_FRIST_ENDE)
            .gesBrutto(DEFAULT_GES_BRUTTO)
            .posNr(DEFAULT_POS_NR)
            .taxe(DEFAULT_TAXE)
            .zeitpunkt(DEFAULT_ZEITPUNKT)
            .zuzahlung(DEFAULT_ZUZAHLUNG);
        return pStatusInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PStatusInfo createUpdatedEntity(EntityManager em) {
        PStatusInfo pStatusInfo = new PStatusInfo()
            .pStatusInfoId(UPDATED_P_STATUS_INFO_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .fCode(UPDATED_F_CODE)
            .fHauptFehler(UPDATED_F_HAUPT_FEHLER)
            .fKommentar(UPDATED_F_KOMMENTAR)
            .fKurzText(UPDATED_F_KURZ_TEXT)
            .fLangText(UPDATED_F_LANG_TEXT)
            .fStatus(UPDATED_F_STATUS)
            .fTCode(UPDATED_F_T_CODE)
            .fWert(UPDATED_F_WERT)
            .faktor(UPDATED_FAKTOR)
            .fristEnde(UPDATED_FRIST_ENDE)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .posNr(UPDATED_POS_NR)
            .taxe(UPDATED_TAXE)
            .zeitpunkt(UPDATED_ZEITPUNKT)
            .zuzahlung(UPDATED_ZUZAHLUNG);
        return pStatusInfo;
    }

    @BeforeEach
    public void initTest() {
        pStatusInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPStatusInfo() throws Exception {
        int databaseSizeBeforeCreate = pStatusInfoRepository.findAll().size();
        // Create the PStatusInfo
        restPStatusInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatusInfo)))
            .andExpect(status().isCreated());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PStatusInfo testPStatusInfo = pStatusInfoList.get(pStatusInfoList.size() - 1);
        assertThat(testPStatusInfo.getpStatusInfoId()).isEqualTo(DEFAULT_P_STATUS_INFO_ID);
        assertThat(testPStatusInfo.getpRezeptId()).isEqualTo(DEFAULT_P_REZEPT_ID);
        assertThat(testPStatusInfo.getApoIk()).isEqualTo(DEFAULT_APO_IK);
        assertThat(testPStatusInfo.getfCode()).isEqualTo(DEFAULT_F_CODE);
        assertThat(testPStatusInfo.getfHauptFehler()).isEqualTo(DEFAULT_F_HAUPT_FEHLER);
        assertThat(testPStatusInfo.getfKommentar()).isEqualTo(DEFAULT_F_KOMMENTAR);
        assertThat(testPStatusInfo.getfKurzText()).isEqualTo(DEFAULT_F_KURZ_TEXT);
        assertThat(testPStatusInfo.getfLangText()).isEqualTo(DEFAULT_F_LANG_TEXT);
        assertThat(testPStatusInfo.getfStatus()).isEqualTo(DEFAULT_F_STATUS);
        assertThat(testPStatusInfo.getfTCode()).isEqualTo(DEFAULT_F_T_CODE);
        assertThat(testPStatusInfo.getfWert()).isEqualTo(DEFAULT_F_WERT);
        assertThat(testPStatusInfo.getFaktor()).isEqualTo(DEFAULT_FAKTOR);
        assertThat(testPStatusInfo.getFristEnde()).isEqualTo(DEFAULT_FRIST_ENDE);
        assertThat(testPStatusInfo.getGesBrutto()).isEqualTo(DEFAULT_GES_BRUTTO);
        assertThat(testPStatusInfo.getPosNr()).isEqualTo(DEFAULT_POS_NR);
        assertThat(testPStatusInfo.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testPStatusInfo.getZeitpunkt()).isEqualTo(DEFAULT_ZEITPUNKT);
        assertThat(testPStatusInfo.getZuzahlung()).isEqualTo(DEFAULT_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void createPStatusInfoWithExistingId() throws Exception {
        // Create the PStatusInfo with an existing ID
        pStatusInfo.setId(1L);

        int databaseSizeBeforeCreate = pStatusInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPStatusInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatusInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPStatusInfos() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        // Get all the pStatusInfoList
        restPStatusInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pStatusInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].pStatusInfoId").value(hasItem(DEFAULT_P_STATUS_INFO_ID)))
            .andExpect(jsonPath("$.[*].pRezeptId").value(hasItem(DEFAULT_P_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].apoIk").value(hasItem(DEFAULT_APO_IK)))
            .andExpect(jsonPath("$.[*].fCode").value(hasItem(DEFAULT_F_CODE)))
            .andExpect(jsonPath("$.[*].fHauptFehler").value(hasItem(DEFAULT_F_HAUPT_FEHLER)))
            .andExpect(jsonPath("$.[*].fKommentar").value(hasItem(DEFAULT_F_KOMMENTAR)))
            .andExpect(jsonPath("$.[*].fKurzText").value(hasItem(DEFAULT_F_KURZ_TEXT)))
            .andExpect(jsonPath("$.[*].fLangText").value(hasItem(DEFAULT_F_LANG_TEXT)))
            .andExpect(jsonPath("$.[*].fStatus").value(hasItem(DEFAULT_F_STATUS)))
            .andExpect(jsonPath("$.[*].fTCode").value(hasItem(DEFAULT_F_T_CODE)))
            .andExpect(jsonPath("$.[*].fWert").value(hasItem(DEFAULT_F_WERT)))
            .andExpect(jsonPath("$.[*].faktor").value(hasItem(DEFAULT_FAKTOR)))
            .andExpect(jsonPath("$.[*].fristEnde").value(hasItem(DEFAULT_FRIST_ENDE)))
            .andExpect(jsonPath("$.[*].gesBrutto").value(hasItem(DEFAULT_GES_BRUTTO)))
            .andExpect(jsonPath("$.[*].posNr").value(hasItem(DEFAULT_POS_NR)))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE)))
            .andExpect(jsonPath("$.[*].zeitpunkt").value(hasItem(DEFAULT_ZEITPUNKT)))
            .andExpect(jsonPath("$.[*].zuzahlung").value(hasItem(DEFAULT_ZUZAHLUNG)));
    }

    @Test
    @Transactional
    void getPStatusInfo() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        // Get the pStatusInfo
        restPStatusInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, pStatusInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pStatusInfo.getId().intValue()))
            .andExpect(jsonPath("$.pStatusInfoId").value(DEFAULT_P_STATUS_INFO_ID))
            .andExpect(jsonPath("$.pRezeptId").value(DEFAULT_P_REZEPT_ID))
            .andExpect(jsonPath("$.apoIk").value(DEFAULT_APO_IK))
            .andExpect(jsonPath("$.fCode").value(DEFAULT_F_CODE))
            .andExpect(jsonPath("$.fHauptFehler").value(DEFAULT_F_HAUPT_FEHLER))
            .andExpect(jsonPath("$.fKommentar").value(DEFAULT_F_KOMMENTAR))
            .andExpect(jsonPath("$.fKurzText").value(DEFAULT_F_KURZ_TEXT))
            .andExpect(jsonPath("$.fLangText").value(DEFAULT_F_LANG_TEXT))
            .andExpect(jsonPath("$.fStatus").value(DEFAULT_F_STATUS))
            .andExpect(jsonPath("$.fTCode").value(DEFAULT_F_T_CODE))
            .andExpect(jsonPath("$.fWert").value(DEFAULT_F_WERT))
            .andExpect(jsonPath("$.faktor").value(DEFAULT_FAKTOR))
            .andExpect(jsonPath("$.fristEnde").value(DEFAULT_FRIST_ENDE))
            .andExpect(jsonPath("$.gesBrutto").value(DEFAULT_GES_BRUTTO))
            .andExpect(jsonPath("$.posNr").value(DEFAULT_POS_NR))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE))
            .andExpect(jsonPath("$.zeitpunkt").value(DEFAULT_ZEITPUNKT))
            .andExpect(jsonPath("$.zuzahlung").value(DEFAULT_ZUZAHLUNG));
    }

    @Test
    @Transactional
    void getNonExistingPStatusInfo() throws Exception {
        // Get the pStatusInfo
        restPStatusInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPStatusInfo() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();

        // Update the pStatusInfo
        PStatusInfo updatedPStatusInfo = pStatusInfoRepository.findById(pStatusInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPStatusInfo are not directly saved in db
        em.detach(updatedPStatusInfo);
        updatedPStatusInfo
            .pStatusInfoId(UPDATED_P_STATUS_INFO_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .fCode(UPDATED_F_CODE)
            .fHauptFehler(UPDATED_F_HAUPT_FEHLER)
            .fKommentar(UPDATED_F_KOMMENTAR)
            .fKurzText(UPDATED_F_KURZ_TEXT)
            .fLangText(UPDATED_F_LANG_TEXT)
            .fStatus(UPDATED_F_STATUS)
            .fTCode(UPDATED_F_T_CODE)
            .fWert(UPDATED_F_WERT)
            .faktor(UPDATED_FAKTOR)
            .fristEnde(UPDATED_FRIST_ENDE)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .posNr(UPDATED_POS_NR)
            .taxe(UPDATED_TAXE)
            .zeitpunkt(UPDATED_ZEITPUNKT)
            .zuzahlung(UPDATED_ZUZAHLUNG);

        restPStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPStatusInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPStatusInfo))
            )
            .andExpect(status().isOk());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        PStatusInfo testPStatusInfo = pStatusInfoList.get(pStatusInfoList.size() - 1);
        assertThat(testPStatusInfo.getpStatusInfoId()).isEqualTo(UPDATED_P_STATUS_INFO_ID);
        assertThat(testPStatusInfo.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPStatusInfo.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPStatusInfo.getfCode()).isEqualTo(UPDATED_F_CODE);
        assertThat(testPStatusInfo.getfHauptFehler()).isEqualTo(UPDATED_F_HAUPT_FEHLER);
        assertThat(testPStatusInfo.getfKommentar()).isEqualTo(UPDATED_F_KOMMENTAR);
        assertThat(testPStatusInfo.getfKurzText()).isEqualTo(UPDATED_F_KURZ_TEXT);
        assertThat(testPStatusInfo.getfLangText()).isEqualTo(UPDATED_F_LANG_TEXT);
        assertThat(testPStatusInfo.getfStatus()).isEqualTo(UPDATED_F_STATUS);
        assertThat(testPStatusInfo.getfTCode()).isEqualTo(UPDATED_F_T_CODE);
        assertThat(testPStatusInfo.getfWert()).isEqualTo(UPDATED_F_WERT);
        assertThat(testPStatusInfo.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPStatusInfo.getFristEnde()).isEqualTo(UPDATED_FRIST_ENDE);
        assertThat(testPStatusInfo.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
        assertThat(testPStatusInfo.getPosNr()).isEqualTo(UPDATED_POS_NR);
        assertThat(testPStatusInfo.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testPStatusInfo.getZeitpunkt()).isEqualTo(UPDATED_ZEITPUNKT);
        assertThat(testPStatusInfo.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void putNonExistingPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pStatusInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pStatusInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pStatusInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pStatusInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePStatusInfoWithPatch() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();

        // Update the pStatusInfo using partial update
        PStatusInfo partialUpdatedPStatusInfo = new PStatusInfo();
        partialUpdatedPStatusInfo.setId(pStatusInfo.getId());

        partialUpdatedPStatusInfo
            .pStatusInfoId(UPDATED_P_STATUS_INFO_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .fHauptFehler(UPDATED_F_HAUPT_FEHLER)
            .faktor(UPDATED_FAKTOR)
            .taxe(UPDATED_TAXE)
            .zuzahlung(UPDATED_ZUZAHLUNG);

        restPStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPStatusInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPStatusInfo))
            )
            .andExpect(status().isOk());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        PStatusInfo testPStatusInfo = pStatusInfoList.get(pStatusInfoList.size() - 1);
        assertThat(testPStatusInfo.getpStatusInfoId()).isEqualTo(UPDATED_P_STATUS_INFO_ID);
        assertThat(testPStatusInfo.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPStatusInfo.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPStatusInfo.getfCode()).isEqualTo(DEFAULT_F_CODE);
        assertThat(testPStatusInfo.getfHauptFehler()).isEqualTo(UPDATED_F_HAUPT_FEHLER);
        assertThat(testPStatusInfo.getfKommentar()).isEqualTo(DEFAULT_F_KOMMENTAR);
        assertThat(testPStatusInfo.getfKurzText()).isEqualTo(DEFAULT_F_KURZ_TEXT);
        assertThat(testPStatusInfo.getfLangText()).isEqualTo(DEFAULT_F_LANG_TEXT);
        assertThat(testPStatusInfo.getfStatus()).isEqualTo(DEFAULT_F_STATUS);
        assertThat(testPStatusInfo.getfTCode()).isEqualTo(DEFAULT_F_T_CODE);
        assertThat(testPStatusInfo.getfWert()).isEqualTo(DEFAULT_F_WERT);
        assertThat(testPStatusInfo.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPStatusInfo.getFristEnde()).isEqualTo(DEFAULT_FRIST_ENDE);
        assertThat(testPStatusInfo.getGesBrutto()).isEqualTo(DEFAULT_GES_BRUTTO);
        assertThat(testPStatusInfo.getPosNr()).isEqualTo(DEFAULT_POS_NR);
        assertThat(testPStatusInfo.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testPStatusInfo.getZeitpunkt()).isEqualTo(DEFAULT_ZEITPUNKT);
        assertThat(testPStatusInfo.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void fullUpdatePStatusInfoWithPatch() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();

        // Update the pStatusInfo using partial update
        PStatusInfo partialUpdatedPStatusInfo = new PStatusInfo();
        partialUpdatedPStatusInfo.setId(pStatusInfo.getId());

        partialUpdatedPStatusInfo
            .pStatusInfoId(UPDATED_P_STATUS_INFO_ID)
            .pRezeptId(UPDATED_P_REZEPT_ID)
            .apoIk(UPDATED_APO_IK)
            .fCode(UPDATED_F_CODE)
            .fHauptFehler(UPDATED_F_HAUPT_FEHLER)
            .fKommentar(UPDATED_F_KOMMENTAR)
            .fKurzText(UPDATED_F_KURZ_TEXT)
            .fLangText(UPDATED_F_LANG_TEXT)
            .fStatus(UPDATED_F_STATUS)
            .fTCode(UPDATED_F_T_CODE)
            .fWert(UPDATED_F_WERT)
            .faktor(UPDATED_FAKTOR)
            .fristEnde(UPDATED_FRIST_ENDE)
            .gesBrutto(UPDATED_GES_BRUTTO)
            .posNr(UPDATED_POS_NR)
            .taxe(UPDATED_TAXE)
            .zeitpunkt(UPDATED_ZEITPUNKT)
            .zuzahlung(UPDATED_ZUZAHLUNG);

        restPStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPStatusInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPStatusInfo))
            )
            .andExpect(status().isOk());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
        PStatusInfo testPStatusInfo = pStatusInfoList.get(pStatusInfoList.size() - 1);
        assertThat(testPStatusInfo.getpStatusInfoId()).isEqualTo(UPDATED_P_STATUS_INFO_ID);
        assertThat(testPStatusInfo.getpRezeptId()).isEqualTo(UPDATED_P_REZEPT_ID);
        assertThat(testPStatusInfo.getApoIk()).isEqualTo(UPDATED_APO_IK);
        assertThat(testPStatusInfo.getfCode()).isEqualTo(UPDATED_F_CODE);
        assertThat(testPStatusInfo.getfHauptFehler()).isEqualTo(UPDATED_F_HAUPT_FEHLER);
        assertThat(testPStatusInfo.getfKommentar()).isEqualTo(UPDATED_F_KOMMENTAR);
        assertThat(testPStatusInfo.getfKurzText()).isEqualTo(UPDATED_F_KURZ_TEXT);
        assertThat(testPStatusInfo.getfLangText()).isEqualTo(UPDATED_F_LANG_TEXT);
        assertThat(testPStatusInfo.getfStatus()).isEqualTo(UPDATED_F_STATUS);
        assertThat(testPStatusInfo.getfTCode()).isEqualTo(UPDATED_F_T_CODE);
        assertThat(testPStatusInfo.getfWert()).isEqualTo(UPDATED_F_WERT);
        assertThat(testPStatusInfo.getFaktor()).isEqualTo(UPDATED_FAKTOR);
        assertThat(testPStatusInfo.getFristEnde()).isEqualTo(UPDATED_FRIST_ENDE);
        assertThat(testPStatusInfo.getGesBrutto()).isEqualTo(UPDATED_GES_BRUTTO);
        assertThat(testPStatusInfo.getPosNr()).isEqualTo(UPDATED_POS_NR);
        assertThat(testPStatusInfo.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testPStatusInfo.getZeitpunkt()).isEqualTo(UPDATED_ZEITPUNKT);
        assertThat(testPStatusInfo.getZuzahlung()).isEqualTo(UPDATED_ZUZAHLUNG);
    }

    @Test
    @Transactional
    void patchNonExistingPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pStatusInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pStatusInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pStatusInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPStatusInfo() throws Exception {
        int databaseSizeBeforeUpdate = pStatusInfoRepository.findAll().size();
        pStatusInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPStatusInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pStatusInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PStatusInfo in the database
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePStatusInfo() throws Exception {
        // Initialize the database
        pStatusInfoRepository.saveAndFlush(pStatusInfo);

        int databaseSizeBeforeDelete = pStatusInfoRepository.findAll().size();

        // Delete the pStatusInfo
        restPStatusInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pStatusInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PStatusInfo> pStatusInfoList = pStatusInfoRepository.findAll();
        assertThat(pStatusInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
