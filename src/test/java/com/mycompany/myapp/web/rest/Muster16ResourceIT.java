package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Muster16;
import com.mycompany.myapp.repository.Muster16Repository;
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
 * Integration tests for the {@link Muster16Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Muster16ResourceIT {

    private static final String DEFAULT_M_REZEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_M_REZEPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LIEFERUNG_ID = "AAAAAAAAAA";
    private static final String UPDATED_LIEFERUNG_ID = "BBBBBBBBBB";

    private static final String DEFAULT_M_16_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_M_16_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_M_MUSTER_16_ID = "AAAAAAAAAA";
    private static final String UPDATED_M_MUSTER_16_ID = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK_SEND = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK_SEND = "BBBBBBBBBB";

    private static final String DEFAULT_APO_IK_SND = "AAAAAAAAAA";
    private static final String UPDATED_APO_IK_SND = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/muster-16-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Muster16Repository muster16Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuster16MockMvc;

    private Muster16 muster16;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16 createEntity(EntityManager em) {
        Muster16 muster16 = new Muster16()
            .mRezeptId(DEFAULT_M_REZEPT_ID)
            .lieferungId(DEFAULT_LIEFERUNG_ID)
            .m16Status(DEFAULT_M_16_STATUS)
            .mMuster16Id(DEFAULT_M_MUSTER_16_ID)
            .apoIkSend(DEFAULT_APO_IK_SEND)
            .apoIkSnd(DEFAULT_APO_IK_SND);
        return muster16;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muster16 createUpdatedEntity(EntityManager em) {
        Muster16 muster16 = new Muster16()
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .m16Status(UPDATED_M_16_STATUS)
            .mMuster16Id(UPDATED_M_MUSTER_16_ID)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .apoIkSnd(UPDATED_APO_IK_SND);
        return muster16;
    }

    @BeforeEach
    public void initTest() {
        muster16 = createEntity(em);
    }

    @Test
    @Transactional
    void createMuster16() throws Exception {
        int databaseSizeBeforeCreate = muster16Repository.findAll().size();
        // Create the Muster16
        restMuster16MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16)))
            .andExpect(status().isCreated());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeCreate + 1);
        Muster16 testMuster16 = muster16List.get(muster16List.size() - 1);
        assertThat(testMuster16.getmRezeptId()).isEqualTo(DEFAULT_M_REZEPT_ID);
        assertThat(testMuster16.getLieferungId()).isEqualTo(DEFAULT_LIEFERUNG_ID);
        assertThat(testMuster16.getm16Status()).isEqualTo(DEFAULT_M_16_STATUS);
        assertThat(testMuster16.getmMuster16Id()).isEqualTo(DEFAULT_M_MUSTER_16_ID);
        assertThat(testMuster16.getApoIkSend()).isEqualTo(DEFAULT_APO_IK_SEND);
        assertThat(testMuster16.getApoIkSnd()).isEqualTo(DEFAULT_APO_IK_SND);
    }

    @Test
    @Transactional
    void createMuster16WithExistingId() throws Exception {
        // Create the Muster16 with an existing ID
        muster16.setId(1L);

        int databaseSizeBeforeCreate = muster16Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuster16MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16)))
            .andExpect(status().isBadRequest());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuster16s() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        // Get all the muster16List
        restMuster16MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muster16.getId().intValue())))
            .andExpect(jsonPath("$.[*].mRezeptId").value(hasItem(DEFAULT_M_REZEPT_ID)))
            .andExpect(jsonPath("$.[*].lieferungId").value(hasItem(DEFAULT_LIEFERUNG_ID)))
            .andExpect(jsonPath("$.[*].m16Status").value(hasItem(DEFAULT_M_16_STATUS)))
            .andExpect(jsonPath("$.[*].mMuster16Id").value(hasItem(DEFAULT_M_MUSTER_16_ID)))
            .andExpect(jsonPath("$.[*].apoIkSend").value(hasItem(DEFAULT_APO_IK_SEND)))
            .andExpect(jsonPath("$.[*].apoIkSnd").value(hasItem(DEFAULT_APO_IK_SND)));
    }

    @Test
    @Transactional
    void getMuster16() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        // Get the muster16
        restMuster16MockMvc
            .perform(get(ENTITY_API_URL_ID, muster16.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(muster16.getId().intValue()))
            .andExpect(jsonPath("$.mRezeptId").value(DEFAULT_M_REZEPT_ID))
            .andExpect(jsonPath("$.lieferungId").value(DEFAULT_LIEFERUNG_ID))
            .andExpect(jsonPath("$.m16Status").value(DEFAULT_M_16_STATUS))
            .andExpect(jsonPath("$.mMuster16Id").value(DEFAULT_M_MUSTER_16_ID))
            .andExpect(jsonPath("$.apoIkSend").value(DEFAULT_APO_IK_SEND))
            .andExpect(jsonPath("$.apoIkSnd").value(DEFAULT_APO_IK_SND));
    }

    @Test
    @Transactional
    void getNonExistingMuster16() throws Exception {
        // Get the muster16
        restMuster16MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMuster16() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();

        // Update the muster16
        Muster16 updatedMuster16 = muster16Repository.findById(muster16.getId()).get();
        // Disconnect from session so that the updates on updatedMuster16 are not directly saved in db
        em.detach(updatedMuster16);
        updatedMuster16
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .m16Status(UPDATED_M_16_STATUS)
            .mMuster16Id(UPDATED_M_MUSTER_16_ID)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .apoIkSnd(UPDATED_APO_IK_SND);

        restMuster16MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuster16.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMuster16))
            )
            .andExpect(status().isOk());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
        Muster16 testMuster16 = muster16List.get(muster16List.size() - 1);
        assertThat(testMuster16.getmRezeptId()).isEqualTo(UPDATED_M_REZEPT_ID);
        assertThat(testMuster16.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testMuster16.getm16Status()).isEqualTo(UPDATED_M_16_STATUS);
        assertThat(testMuster16.getmMuster16Id()).isEqualTo(UPDATED_M_MUSTER_16_ID);
        assertThat(testMuster16.getApoIkSend()).isEqualTo(UPDATED_APO_IK_SEND);
        assertThat(testMuster16.getApoIkSnd()).isEqualTo(UPDATED_APO_IK_SND);
    }

    @Test
    @Transactional
    void putNonExistingMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(
                put(ENTITY_API_URL_ID, muster16.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muster16))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muster16)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuster16WithPatch() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();

        // Update the muster16 using partial update
        Muster16 partialUpdatedMuster16 = new Muster16();
        partialUpdatedMuster16.setId(muster16.getId());

        partialUpdatedMuster16.lieferungId(UPDATED_LIEFERUNG_ID).mMuster16Id(UPDATED_M_MUSTER_16_ID).apoIkSend(UPDATED_APO_IK_SEND);

        restMuster16MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16))
            )
            .andExpect(status().isOk());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
        Muster16 testMuster16 = muster16List.get(muster16List.size() - 1);
        assertThat(testMuster16.getmRezeptId()).isEqualTo(DEFAULT_M_REZEPT_ID);
        assertThat(testMuster16.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testMuster16.getm16Status()).isEqualTo(DEFAULT_M_16_STATUS);
        assertThat(testMuster16.getmMuster16Id()).isEqualTo(UPDATED_M_MUSTER_16_ID);
        assertThat(testMuster16.getApoIkSend()).isEqualTo(UPDATED_APO_IK_SEND);
        assertThat(testMuster16.getApoIkSnd()).isEqualTo(DEFAULT_APO_IK_SND);
    }

    @Test
    @Transactional
    void fullUpdateMuster16WithPatch() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();

        // Update the muster16 using partial update
        Muster16 partialUpdatedMuster16 = new Muster16();
        partialUpdatedMuster16.setId(muster16.getId());

        partialUpdatedMuster16
            .mRezeptId(UPDATED_M_REZEPT_ID)
            .lieferungId(UPDATED_LIEFERUNG_ID)
            .m16Status(UPDATED_M_16_STATUS)
            .mMuster16Id(UPDATED_M_MUSTER_16_ID)
            .apoIkSend(UPDATED_APO_IK_SEND)
            .apoIkSnd(UPDATED_APO_IK_SND);

        restMuster16MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuster16.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuster16))
            )
            .andExpect(status().isOk());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
        Muster16 testMuster16 = muster16List.get(muster16List.size() - 1);
        assertThat(testMuster16.getmRezeptId()).isEqualTo(UPDATED_M_REZEPT_ID);
        assertThat(testMuster16.getLieferungId()).isEqualTo(UPDATED_LIEFERUNG_ID);
        assertThat(testMuster16.getm16Status()).isEqualTo(UPDATED_M_16_STATUS);
        assertThat(testMuster16.getmMuster16Id()).isEqualTo(UPDATED_M_MUSTER_16_ID);
        assertThat(testMuster16.getApoIkSend()).isEqualTo(UPDATED_APO_IK_SEND);
        assertThat(testMuster16.getApoIkSnd()).isEqualTo(UPDATED_APO_IK_SND);
    }

    @Test
    @Transactional
    void patchNonExistingMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, muster16.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muster16))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuster16() throws Exception {
        int databaseSizeBeforeUpdate = muster16Repository.findAll().size();
        muster16.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuster16MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(muster16)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muster16 in the database
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuster16() throws Exception {
        // Initialize the database
        muster16Repository.saveAndFlush(muster16);

        int databaseSizeBeforeDelete = muster16Repository.findAll().size();

        // Delete the muster16
        restMuster16MockMvc
            .perform(delete(ENTITY_API_URL_ID, muster16.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Muster16> muster16List = muster16Repository.findAll();
        assertThat(muster16List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
