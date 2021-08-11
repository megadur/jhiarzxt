package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Muster16Abg;
import com.mycompany.myapp.repository.Muster16AbgRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Muster16Abg}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Muster16AbgResource {

    private final Logger log = LoggerFactory.getLogger(Muster16AbgResource.class);

    private static final String ENTITY_NAME = "muster16Abg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Muster16AbgRepository muster16AbgRepository;

    public Muster16AbgResource(Muster16AbgRepository muster16AbgRepository) {
        this.muster16AbgRepository = muster16AbgRepository;
    }

    /**
     * {@code POST  /muster-16-abgs} : Create a new muster16Abg.
     *
     * @param muster16Abg the muster16Abg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new muster16Abg, or with status {@code 400 (Bad Request)} if the muster16Abg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muster-16-abgs")
    public ResponseEntity<Muster16Abg> createMuster16Abg(@RequestBody Muster16Abg muster16Abg) throws URISyntaxException {
        log.debug("REST request to save Muster16Abg : {}", muster16Abg);
        if (muster16Abg.getId() != null) {
            throw new BadRequestAlertException("A new muster16Abg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Muster16Abg result = muster16AbgRepository.save(muster16Abg);
        return ResponseEntity
            .created(new URI("/api/muster-16-abgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muster-16-abgs/:id} : Updates an existing muster16Abg.
     *
     * @param id the id of the muster16Abg to save.
     * @param muster16Abg the muster16Abg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Abg,
     * or with status {@code 400 (Bad Request)} if the muster16Abg is not valid,
     * or with status {@code 500 (Internal Server Error)} if the muster16Abg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muster-16-abgs/{id}")
    public ResponseEntity<Muster16Abg> updateMuster16Abg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Abg muster16Abg
    ) throws URISyntaxException {
        log.debug("REST request to update Muster16Abg : {}, {}", id, muster16Abg);
        if (muster16Abg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Abg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16AbgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Muster16Abg result = muster16AbgRepository.save(muster16Abg);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Abg.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /muster-16-abgs/:id} : Partial updates given fields of an existing muster16Abg, field will ignore if it is null
     *
     * @param id the id of the muster16Abg to save.
     * @param muster16Abg the muster16Abg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Abg,
     * or with status {@code 400 (Bad Request)} if the muster16Abg is not valid,
     * or with status {@code 404 (Not Found)} if the muster16Abg is not found,
     * or with status {@code 500 (Internal Server Error)} if the muster16Abg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/muster-16-abgs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Muster16Abg> partialUpdateMuster16Abg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Abg muster16Abg
    ) throws URISyntaxException {
        log.debug("REST request to partial update Muster16Abg partially : {}, {}", id, muster16Abg);
        if (muster16Abg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Abg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16AbgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Muster16Abg> result = muster16AbgRepository
            .findById(muster16Abg.getId())
            .map(
                existingMuster16Abg -> {
                    if (muster16Abg.getApoIk() != null) {
                        existingMuster16Abg.setApoIk(muster16Abg.getApoIk());
                    }
                    if (muster16Abg.getLieferDat() != null) {
                        existingMuster16Abg.setLieferDat(muster16Abg.getLieferDat());
                    }
                    if (muster16Abg.getaPeriode() != null) {
                        existingMuster16Abg.setaPeriode(muster16Abg.getaPeriode());
                    }
                    if (muster16Abg.getArbPlatz() != null) {
                        existingMuster16Abg.setArbPlatz(muster16Abg.getArbPlatz());
                    }
                    if (muster16Abg.getAvsId() != null) {
                        existingMuster16Abg.setAvsId(muster16Abg.getAvsId());
                    }
                    if (muster16Abg.getBediener() != null) {
                        existingMuster16Abg.setBediener(muster16Abg.getBediener());
                    }
                    if (muster16Abg.getZuzahlung() != null) {
                        existingMuster16Abg.setZuzahlung(muster16Abg.getZuzahlung());
                    }
                    if (muster16Abg.getGesBrutto() != null) {
                        existingMuster16Abg.setGesBrutto(muster16Abg.getGesBrutto());
                    }

                    return existingMuster16Abg;
                }
            )
            .map(muster16AbgRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Abg.getId().toString())
        );
    }

    /**
     * {@code GET  /muster-16-abgs} : get all the muster16Abgs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of muster16Abgs in body.
     */
    @GetMapping("/muster-16-abgs")
    public List<Muster16Abg> getAllMuster16Abgs(@RequestParam(required = false) String filter) {
        if ("mrezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all Muster16Abgs where mRezeptId is null");
            return StreamSupport
                .stream(muster16AbgRepository.findAll().spliterator(), false)
                .filter(muster16Abg -> muster16Abg.getMRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Muster16Abgs");
        return muster16AbgRepository.findAll();
    }

    /**
     * {@code GET  /muster-16-abgs/:id} : get the "id" muster16Abg.
     *
     * @param id the id of the muster16Abg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the muster16Abg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muster-16-abgs/{id}")
    public ResponseEntity<Muster16Abg> getMuster16Abg(@PathVariable Long id) {
        log.debug("REST request to get Muster16Abg : {}", id);
        Optional<Muster16Abg> muster16Abg = muster16AbgRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(muster16Abg);
    }

    /**
     * {@code DELETE  /muster-16-abgs/:id} : delete the "id" muster16Abg.
     *
     * @param id the id of the muster16Abg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muster-16-abgs/{id}")
    public ResponseEntity<Void> deleteMuster16Abg(@PathVariable Long id) {
        log.debug("REST request to delete Muster16Abg : {}", id);
        muster16AbgRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
