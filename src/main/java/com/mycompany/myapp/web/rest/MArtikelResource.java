package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MArtikel;
import com.mycompany.myapp.repository.MArtikelRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MArtikel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MArtikelResource {

    private final Logger log = LoggerFactory.getLogger(MArtikelResource.class);

    private static final String ENTITY_NAME = "mArtikel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MArtikelRepository mArtikelRepository;

    public MArtikelResource(MArtikelRepository mArtikelRepository) {
        this.mArtikelRepository = mArtikelRepository;
    }

    /**
     * {@code POST  /m-artikels} : Create a new mArtikel.
     *
     * @param mArtikel the mArtikel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mArtikel, or with status {@code 400 (Bad Request)} if the mArtikel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-artikels")
    public ResponseEntity<MArtikel> createMArtikel(@RequestBody MArtikel mArtikel) throws URISyntaxException {
        log.debug("REST request to save MArtikel : {}", mArtikel);
        if (mArtikel.getId() != null) {
            throw new BadRequestAlertException("A new mArtikel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MArtikel result = mArtikelRepository.save(mArtikel);
        return ResponseEntity
            .created(new URI("/api/m-artikels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-artikels/:id} : Updates an existing mArtikel.
     *
     * @param id the id of the mArtikel to save.
     * @param mArtikel the mArtikel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mArtikel,
     * or with status {@code 400 (Bad Request)} if the mArtikel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mArtikel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-artikels/{id}")
    public ResponseEntity<MArtikel> updateMArtikel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MArtikel mArtikel
    ) throws URISyntaxException {
        log.debug("REST request to update MArtikel : {}, {}", id, mArtikel);
        if (mArtikel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mArtikel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mArtikelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MArtikel result = mArtikelRepository.save(mArtikel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mArtikel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /m-artikels/:id} : Partial updates given fields of an existing mArtikel, field will ignore if it is null
     *
     * @param id the id of the mArtikel to save.
     * @param mArtikel the mArtikel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mArtikel,
     * or with status {@code 400 (Bad Request)} if the mArtikel is not valid,
     * or with status {@code 404 (Not Found)} if the mArtikel is not found,
     * or with status {@code 500 (Internal Server Error)} if the mArtikel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/m-artikels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MArtikel> partialUpdateMArtikel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MArtikel mArtikel
    ) throws URISyntaxException {
        log.debug("REST request to partial update MArtikel partially : {}, {}", id, mArtikel);
        if (mArtikel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mArtikel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mArtikelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MArtikel> result = mArtikelRepository
            .findById(mArtikel.getId())
            .map(
                existingMArtikel -> {
                    if (mArtikel.getmArtikelId() != null) {
                        existingMArtikel.setmArtikelId(mArtikel.getmArtikelId());
                    }
                    if (mArtikel.getmRezeptId() != null) {
                        existingMArtikel.setmRezeptId(mArtikel.getmRezeptId());
                    }
                    if (mArtikel.getApoIk() != null) {
                        existingMArtikel.setApoIk(mArtikel.getApoIk());
                    }
                    if (mArtikel.getAutidem() != null) {
                        existingMArtikel.setAutidem(mArtikel.getAutidem());
                    }
                    if (mArtikel.getFaktor() != null) {
                        existingMArtikel.setFaktor(mArtikel.getFaktor());
                    }
                    if (mArtikel.getHilfsmittelNr() != null) {
                        existingMArtikel.setHilfsmittelNr(mArtikel.getHilfsmittelNr());
                    }
                    if (mArtikel.getMuster16Id() != null) {
                        existingMArtikel.setMuster16Id(mArtikel.getMuster16Id());
                    }
                    if (mArtikel.getPosNr() != null) {
                        existingMArtikel.setPosNr(mArtikel.getPosNr());
                    }
                    if (mArtikel.getPzn() != null) {
                        existingMArtikel.setPzn(mArtikel.getPzn());
                    }
                    if (mArtikel.getTaxe() != null) {
                        existingMArtikel.setTaxe(mArtikel.getTaxe());
                    }
                    if (mArtikel.getvZeile() != null) {
                        existingMArtikel.setvZeile(mArtikel.getvZeile());
                    }

                    return existingMArtikel;
                }
            )
            .map(mArtikelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mArtikel.getId().toString())
        );
    }

    /**
     * {@code GET  /m-artikels} : get all the mArtikels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mArtikels in body.
     */
    @GetMapping("/m-artikels")
    public List<MArtikel> getAllMArtikels() {
        log.debug("REST request to get all MArtikels");
        return mArtikelRepository.findAll();
    }

    /**
     * {@code GET  /m-artikels/:id} : get the "id" mArtikel.
     *
     * @param id the id of the mArtikel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mArtikel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-artikels/{id}")
    public ResponseEntity<MArtikel> getMArtikel(@PathVariable Long id) {
        log.debug("REST request to get MArtikel : {}", id);
        Optional<MArtikel> mArtikel = mArtikelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mArtikel);
    }

    /**
     * {@code DELETE  /m-artikels/:id} : delete the "id" mArtikel.
     *
     * @param id the id of the mArtikel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-artikels/{id}")
    public ResponseEntity<Void> deleteMArtikel(@PathVariable Long id) {
        log.debug("REST request to delete MArtikel : {}", id);
        mArtikelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
