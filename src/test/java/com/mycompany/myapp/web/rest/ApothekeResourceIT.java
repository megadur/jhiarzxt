package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Apotheke;
import com.mycompany.myapp.repository.ApothekeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ApothekeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ApothekeResourceIT {

    private static final Integer DEFAULT_I_K = 1;
    private static final Integer UPDATED_I_K = 2;

    private static final String DEFAULT_INHABER = "AAAAAAAAAA";
    private static final String UPDATED_INHABER = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PLZ = "AAAAAAAAAA";
    private static final String UPDATED_PLZ = "BBBBBBBBBB";

    private static final String DEFAULT_ORT = "AAAAAAAAAA";
    private static final String UPDATED_ORT = "BBBBBBBBBB";

    private static final String DEFAULT_STR = "AAAAAAAAAA";
    private static final String UPDATED_STR = "BBBBBBBBBB";

    private static final String DEFAULT_HAUS_NR = "AAAAAAAAAA";
    private static final String UPDATED_HAUS_NR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_ZUSATZ = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_ZUSATZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apothekes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApothekeRepository apothekeRepository;

    @Mock
    private ApothekeRepository apothekeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApothekeMockMvc;

    private Apotheke apotheke;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apotheke createEntity(EntityManager em) {
        Apotheke apotheke = new Apotheke()
            .iK(DEFAULT_I_K)
            .inhaber(DEFAULT_INHABER)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .plz(DEFAULT_PLZ)
            .ort(DEFAULT_ORT)
            .str(DEFAULT_STR)
            .hausNr(DEFAULT_HAUS_NR)
            .addrZusatz(DEFAULT_ADDR_ZUSATZ);
        return apotheke;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apotheke createUpdatedEntity(EntityManager em) {
        Apotheke apotheke = new Apotheke()
            .iK(UPDATED_I_K)
            .inhaber(UPDATED_INHABER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .plz(UPDATED_PLZ)
            .ort(UPDATED_ORT)
            .str(UPDATED_STR)
            .hausNr(UPDATED_HAUS_NR)
            .addrZusatz(UPDATED_ADDR_ZUSATZ);
        return apotheke;
    }

    @BeforeEach
    public void initTest() {
        apotheke = createEntity(em);
    }

    @Test
    @Transactional
    void createApotheke() throws Exception {
        int databaseSizeBeforeCreate = apothekeRepository.findAll().size();
        // Create the Apotheke
        restApothekeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apotheke)))
            .andExpect(status().isCreated());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeCreate + 1);
        Apotheke testApotheke = apothekeList.get(apothekeList.size() - 1);
        assertThat(testApotheke.getiK()).isEqualTo(DEFAULT_I_K);
        assertThat(testApotheke.getInhaber()).isEqualTo(DEFAULT_INHABER);
        assertThat(testApotheke.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testApotheke.getPlz()).isEqualTo(DEFAULT_PLZ);
        assertThat(testApotheke.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testApotheke.getStr()).isEqualTo(DEFAULT_STR);
        assertThat(testApotheke.getHausNr()).isEqualTo(DEFAULT_HAUS_NR);
        assertThat(testApotheke.getAddrZusatz()).isEqualTo(DEFAULT_ADDR_ZUSATZ);
    }

    @Test
    @Transactional
    void createApothekeWithExistingId() throws Exception {
        // Create the Apotheke with an existing ID
        apotheke.setId(1L);

        int databaseSizeBeforeCreate = apothekeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApothekeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apotheke)))
            .andExpect(status().isBadRequest());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApothekes() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        // Get all the apothekeList
        restApothekeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apotheke.getId().intValue())))
            .andExpect(jsonPath("$.[*].iK").value(hasItem(DEFAULT_I_K)))
            .andExpect(jsonPath("$.[*].inhaber").value(hasItem(DEFAULT_INHABER)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].plz").value(hasItem(DEFAULT_PLZ)))
            .andExpect(jsonPath("$.[*].ort").value(hasItem(DEFAULT_ORT)))
            .andExpect(jsonPath("$.[*].str").value(hasItem(DEFAULT_STR)))
            .andExpect(jsonPath("$.[*].hausNr").value(hasItem(DEFAULT_HAUS_NR)))
            .andExpect(jsonPath("$.[*].addrZusatz").value(hasItem(DEFAULT_ADDR_ZUSATZ)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApothekesWithEagerRelationshipsIsEnabled() throws Exception {
        when(apothekeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApothekeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(apothekeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllApothekesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(apothekeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restApothekeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(apothekeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getApotheke() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        // Get the apotheke
        restApothekeMockMvc
            .perform(get(ENTITY_API_URL_ID, apotheke.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apotheke.getId().intValue()))
            .andExpect(jsonPath("$.iK").value(DEFAULT_I_K))
            .andExpect(jsonPath("$.inhaber").value(DEFAULT_INHABER))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.plz").value(DEFAULT_PLZ))
            .andExpect(jsonPath("$.ort").value(DEFAULT_ORT))
            .andExpect(jsonPath("$.str").value(DEFAULT_STR))
            .andExpect(jsonPath("$.hausNr").value(DEFAULT_HAUS_NR))
            .andExpect(jsonPath("$.addrZusatz").value(DEFAULT_ADDR_ZUSATZ));
    }

    @Test
    @Transactional
    void getNonExistingApotheke() throws Exception {
        // Get the apotheke
        restApothekeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApotheke() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();

        // Update the apotheke
        Apotheke updatedApotheke = apothekeRepository.findById(apotheke.getId()).get();
        // Disconnect from session so that the updates on updatedApotheke are not directly saved in db
        em.detach(updatedApotheke);
        updatedApotheke
            .iK(UPDATED_I_K)
            .inhaber(UPDATED_INHABER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .plz(UPDATED_PLZ)
            .ort(UPDATED_ORT)
            .str(UPDATED_STR)
            .hausNr(UPDATED_HAUS_NR)
            .addrZusatz(UPDATED_ADDR_ZUSATZ);

        restApothekeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApotheke.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApotheke))
            )
            .andExpect(status().isOk());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
        Apotheke testApotheke = apothekeList.get(apothekeList.size() - 1);
        assertThat(testApotheke.getiK()).isEqualTo(UPDATED_I_K);
        assertThat(testApotheke.getInhaber()).isEqualTo(UPDATED_INHABER);
        assertThat(testApotheke.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testApotheke.getPlz()).isEqualTo(UPDATED_PLZ);
        assertThat(testApotheke.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testApotheke.getStr()).isEqualTo(UPDATED_STR);
        assertThat(testApotheke.getHausNr()).isEqualTo(UPDATED_HAUS_NR);
        assertThat(testApotheke.getAddrZusatz()).isEqualTo(UPDATED_ADDR_ZUSATZ);
    }

    @Test
    @Transactional
    void putNonExistingApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apotheke.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apotheke))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apotheke))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apotheke)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApothekeWithPatch() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();

        // Update the apotheke using partial update
        Apotheke partialUpdatedApotheke = new Apotheke();
        partialUpdatedApotheke.setId(apotheke.getId());

        partialUpdatedApotheke.countryCode(UPDATED_COUNTRY_CODE).plz(UPDATED_PLZ).ort(UPDATED_ORT).str(UPDATED_STR).hausNr(UPDATED_HAUS_NR);

        restApothekeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApotheke.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApotheke))
            )
            .andExpect(status().isOk());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
        Apotheke testApotheke = apothekeList.get(apothekeList.size() - 1);
        assertThat(testApotheke.getiK()).isEqualTo(DEFAULT_I_K);
        assertThat(testApotheke.getInhaber()).isEqualTo(DEFAULT_INHABER);
        assertThat(testApotheke.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testApotheke.getPlz()).isEqualTo(UPDATED_PLZ);
        assertThat(testApotheke.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testApotheke.getStr()).isEqualTo(UPDATED_STR);
        assertThat(testApotheke.getHausNr()).isEqualTo(UPDATED_HAUS_NR);
        assertThat(testApotheke.getAddrZusatz()).isEqualTo(DEFAULT_ADDR_ZUSATZ);
    }

    @Test
    @Transactional
    void fullUpdateApothekeWithPatch() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();

        // Update the apotheke using partial update
        Apotheke partialUpdatedApotheke = new Apotheke();
        partialUpdatedApotheke.setId(apotheke.getId());

        partialUpdatedApotheke
            .iK(UPDATED_I_K)
            .inhaber(UPDATED_INHABER)
            .countryCode(UPDATED_COUNTRY_CODE)
            .plz(UPDATED_PLZ)
            .ort(UPDATED_ORT)
            .str(UPDATED_STR)
            .hausNr(UPDATED_HAUS_NR)
            .addrZusatz(UPDATED_ADDR_ZUSATZ);

        restApothekeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApotheke.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApotheke))
            )
            .andExpect(status().isOk());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
        Apotheke testApotheke = apothekeList.get(apothekeList.size() - 1);
        assertThat(testApotheke.getiK()).isEqualTo(UPDATED_I_K);
        assertThat(testApotheke.getInhaber()).isEqualTo(UPDATED_INHABER);
        assertThat(testApotheke.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testApotheke.getPlz()).isEqualTo(UPDATED_PLZ);
        assertThat(testApotheke.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testApotheke.getStr()).isEqualTo(UPDATED_STR);
        assertThat(testApotheke.getHausNr()).isEqualTo(UPDATED_HAUS_NR);
        assertThat(testApotheke.getAddrZusatz()).isEqualTo(UPDATED_ADDR_ZUSATZ);
    }

    @Test
    @Transactional
    void patchNonExistingApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apotheke.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apotheke))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apotheke))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApotheke() throws Exception {
        int databaseSizeBeforeUpdate = apothekeRepository.findAll().size();
        apotheke.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApothekeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apotheke)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apotheke in the database
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApotheke() throws Exception {
        // Initialize the database
        apothekeRepository.saveAndFlush(apotheke);

        int databaseSizeBeforeDelete = apothekeRepository.findAll().size();

        // Delete the apotheke
        restApothekeMockMvc
            .perform(delete(ENTITY_API_URL_ID, apotheke.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apotheke> apothekeList = apothekeRepository.findAll();
        assertThat(apothekeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
