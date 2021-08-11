package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PRezeptAbg;
import com.mycompany.myapp.repository.PRezeptAbgRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PRezeptAbg}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PRezeptAbgResource {

    private final Logger log = LoggerFactory.getLogger(PRezeptAbgResource.class);

    private static final String ENTITY_NAME = "pRezeptAbg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PRezeptAbgRepository pRezeptAbgRepository;

    public PRezeptAbgResource(PRezeptAbgRepository pRezeptAbgRepository) {
        this.pRezeptAbgRepository = pRezeptAbgRepository;
    }

    /**
     * {@code POST  /p-rezept-abgs} : Create a new pRezeptAbg.
     *
     * @param pRezeptAbg the pRezeptAbg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pRezeptAbg, or with status {@code 400 (Bad Request)} if the pRezeptAbg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-rezept-abgs")
    public ResponseEntity<PRezeptAbg> createPRezeptAbg(@RequestBody PRezeptAbg pRezeptAbg) throws URISyntaxException {
        log.debug("REST request to save PRezeptAbg : {}", pRezeptAbg);
        if (pRezeptAbg.getId() != null) {
            throw new BadRequestAlertException("A new pRezeptAbg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PRezeptAbg result = pRezeptAbgRepository.save(pRezeptAbg);
        return ResponseEntity
            .created(new URI("/api/p-rezept-abgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-rezept-abgs/:id} : Updates an existing pRezeptAbg.
     *
     * @param id the id of the pRezeptAbg to save.
     * @param pRezeptAbg the pRezeptAbg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptAbg,
     * or with status {@code 400 (Bad Request)} if the pRezeptAbg is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptAbg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-rezept-abgs/{id}")
    public ResponseEntity<PRezeptAbg> updatePRezeptAbg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptAbg pRezeptAbg
    ) throws URISyntaxException {
        log.debug("REST request to update PRezeptAbg : {}, {}", id, pRezeptAbg);
        if (pRezeptAbg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptAbg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptAbgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PRezeptAbg result = pRezeptAbgRepository.save(pRezeptAbg);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptAbg.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-rezept-abgs/:id} : Partial updates given fields of an existing pRezeptAbg, field will ignore if it is null
     *
     * @param id the id of the pRezeptAbg to save.
     * @param pRezeptAbg the pRezeptAbg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptAbg,
     * or with status {@code 400 (Bad Request)} if the pRezeptAbg is not valid,
     * or with status {@code 404 (Not Found)} if the pRezeptAbg is not found,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptAbg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-rezept-abgs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PRezeptAbg> partialUpdatePRezeptAbg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptAbg pRezeptAbg
    ) throws URISyntaxException {
        log.debug("REST request to partial update PRezeptAbg partially : {}, {}", id, pRezeptAbg);
        if (pRezeptAbg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptAbg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptAbgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PRezeptAbg> result = pRezeptAbgRepository
            .findById(pRezeptAbg.getId())
            .map(
                existingPRezeptAbg -> {
                    if (pRezeptAbg.getpRezeptId() != null) {
                        existingPRezeptAbg.setpRezeptId(pRezeptAbg.getpRezeptId());
                    }
                    if (pRezeptAbg.getGebFrei() != null) {
                        existingPRezeptAbg.setGebFrei(pRezeptAbg.getGebFrei());
                    }
                    if (pRezeptAbg.getGesBrutto() != null) {
                        existingPRezeptAbg.setGesBrutto(pRezeptAbg.getGesBrutto());
                    }
                    if (pRezeptAbg.getHashCode() != null) {
                        existingPRezeptAbg.setHashCode(pRezeptAbg.getHashCode());
                    }
                    if (pRezeptAbg.getkArt() != null) {
                        existingPRezeptAbg.setkArt(pRezeptAbg.getkArt());
                    }
                    if (pRezeptAbg.getNoctu() != null) {
                        existingPRezeptAbg.setNoctu(pRezeptAbg.getNoctu());
                    }
                    if (pRezeptAbg.getpRezeptTyp() != null) {
                        existingPRezeptAbg.setpRezeptTyp(pRezeptAbg.getpRezeptTyp());
                    }
                    if (pRezeptAbg.getrTyp() != null) {
                        existingPRezeptAbg.setrTyp(pRezeptAbg.getrTyp());
                    }
                    if (pRezeptAbg.getSonstige() != null) {
                        existingPRezeptAbg.setSonstige(pRezeptAbg.getSonstige());
                    }
                    if (pRezeptAbg.getSprStBedarf() != null) {
                        existingPRezeptAbg.setSprStBedarf(pRezeptAbg.getSprStBedarf());
                    }
                    if (pRezeptAbg.getVerDat() != null) {
                        existingPRezeptAbg.setVerDat(pRezeptAbg.getVerDat());
                    }
                    if (pRezeptAbg.getVkGueltigBis() != null) {
                        existingPRezeptAbg.setVkGueltigBis(pRezeptAbg.getVkGueltigBis());
                    }
                    if (pRezeptAbg.getZuzahlung() != null) {
                        existingPRezeptAbg.setZuzahlung(pRezeptAbg.getZuzahlung());
                    }

                    return existingPRezeptAbg;
                }
            )
            .map(pRezeptAbgRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptAbg.getId().toString())
        );
    }

    /**
     * {@code GET  /p-rezept-abgs} : get all the pRezeptAbgs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pRezeptAbgs in body.
     */
    @GetMapping("/p-rezept-abgs")
    public List<PRezeptAbg> getAllPRezeptAbgs(@RequestParam(required = false) String filter) {
        if ("prezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all PRezeptAbgs where pRezeptId is null");
            return StreamSupport
                .stream(pRezeptAbgRepository.findAll().spliterator(), false)
                .filter(pRezeptAbg -> pRezeptAbg.getPRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PRezeptAbgs");
        return pRezeptAbgRepository.findAll();
    }

    /**
     * {@code GET  /p-rezept-abgs/:id} : get the "id" pRezeptAbg.
     *
     * @param id the id of the pRezeptAbg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pRezeptAbg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-rezept-abgs/{id}")
    public ResponseEntity<PRezeptAbg> getPRezeptAbg(@PathVariable Long id) {
        log.debug("REST request to get PRezeptAbg : {}", id);
        Optional<PRezeptAbg> pRezeptAbg = pRezeptAbgRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pRezeptAbg);
    }

    /**
     * {@code DELETE  /p-rezept-abgs/:id} : delete the "id" pRezeptAbg.
     *
     * @param id the id of the pRezeptAbg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-rezept-abgs/{id}")
    public ResponseEntity<Void> deletePRezeptAbg(@PathVariable Long id) {
        log.debug("REST request to delete PRezeptAbg : {}", id);
        pRezeptAbgRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
