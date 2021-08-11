package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PRezeptVer;
import com.mycompany.myapp.repository.PRezeptVerRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PRezeptVer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PRezeptVerResource {

    private final Logger log = LoggerFactory.getLogger(PRezeptVerResource.class);

    private static final String ENTITY_NAME = "pRezeptVer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PRezeptVerRepository pRezeptVerRepository;

    public PRezeptVerResource(PRezeptVerRepository pRezeptVerRepository) {
        this.pRezeptVerRepository = pRezeptVerRepository;
    }

    /**
     * {@code POST  /p-rezept-vers} : Create a new pRezeptVer.
     *
     * @param pRezeptVer the pRezeptVer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pRezeptVer, or with status {@code 400 (Bad Request)} if the pRezeptVer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-rezept-vers")
    public ResponseEntity<PRezeptVer> createPRezeptVer(@RequestBody PRezeptVer pRezeptVer) throws URISyntaxException {
        log.debug("REST request to save PRezeptVer : {}", pRezeptVer);
        if (pRezeptVer.getId() != null) {
            throw new BadRequestAlertException("A new pRezeptVer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PRezeptVer result = pRezeptVerRepository.save(pRezeptVer);
        return ResponseEntity
            .created(new URI("/api/p-rezept-vers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-rezept-vers/:id} : Updates an existing pRezeptVer.
     *
     * @param id the id of the pRezeptVer to save.
     * @param pRezeptVer the pRezeptVer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptVer,
     * or with status {@code 400 (Bad Request)} if the pRezeptVer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptVer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-rezept-vers/{id}")
    public ResponseEntity<PRezeptVer> updatePRezeptVer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptVer pRezeptVer
    ) throws URISyntaxException {
        log.debug("REST request to update PRezeptVer : {}, {}", id, pRezeptVer);
        if (pRezeptVer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptVer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptVerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PRezeptVer result = pRezeptVerRepository.save(pRezeptVer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptVer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-rezept-vers/:id} : Partial updates given fields of an existing pRezeptVer, field will ignore if it is null
     *
     * @param id the id of the pRezeptVer to save.
     * @param pRezeptVer the pRezeptVer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptVer,
     * or with status {@code 400 (Bad Request)} if the pRezeptVer is not valid,
     * or with status {@code 404 (Not Found)} if the pRezeptVer is not found,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptVer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-rezept-vers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PRezeptVer> partialUpdatePRezeptVer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptVer pRezeptVer
    ) throws URISyntaxException {
        log.debug("REST request to partial update PRezeptVer partially : {}, {}", id, pRezeptVer);
        if (pRezeptVer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptVer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptVerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PRezeptVer> result = pRezeptVerRepository
            .findById(pRezeptVer.getId())
            .map(
                existingPRezeptVer -> {
                    if (pRezeptVer.getpRezeptId9() != null) {
                        existingPRezeptVer.setpRezeptId9(pRezeptVer.getpRezeptId9());
                    }
                    if (pRezeptVer.getApoIk() != null) {
                        existingPRezeptVer.setApoIk(pRezeptVer.getApoIk());
                    }
                    if (pRezeptVer.getApoIkSend() != null) {
                        existingPRezeptVer.setApoIkSend(pRezeptVer.getApoIkSend());
                    }
                    if (pRezeptVer.getArbPlatz() != null) {
                        existingPRezeptVer.setArbPlatz(pRezeptVer.getArbPlatz());
                    }
                    if (pRezeptVer.getAvsId() != null) {
                        existingPRezeptVer.setAvsId(pRezeptVer.getAvsId());
                    }
                    if (pRezeptVer.getBediener() != null) {
                        existingPRezeptVer.setBediener(pRezeptVer.getBediener());
                    }
                    if (pRezeptVer.getBvg() != null) {
                        existingPRezeptVer.setBvg(pRezeptVer.getBvg());
                    }
                    if (pRezeptVer.geteStatus() != null) {
                        existingPRezeptVer.seteStatus(pRezeptVer.geteStatus());
                    }
                    if (pRezeptVer.getErstZeitpunkt() != null) {
                        existingPRezeptVer.setErstZeitpunkt(pRezeptVer.getErstZeitpunkt());
                    }
                    if (pRezeptVer.getkName() != null) {
                        existingPRezeptVer.setkName(pRezeptVer.getkName());
                    }
                    if (pRezeptVer.getKkIk() != null) {
                        existingPRezeptVer.setKkIk(pRezeptVer.getKkIk());
                    }
                    if (pRezeptVer.getLaNr() != null) {
                        existingPRezeptVer.setLaNr(pRezeptVer.getLaNr());
                    }
                    if (pRezeptVer.getVrsNr() != null) {
                        existingPRezeptVer.setVrsNr(pRezeptVer.getVrsNr());
                    }
                    if (pRezeptVer.getVrtrgsArztNr() != null) {
                        existingPRezeptVer.setVrtrgsArztNr(pRezeptVer.getVrtrgsArztNr());
                    }
                    if (pRezeptVer.getvGeb() != null) {
                        existingPRezeptVer.setvGeb(pRezeptVer.getvGeb());
                    }
                    if (pRezeptVer.getvStat() != null) {
                        existingPRezeptVer.setvStat(pRezeptVer.getvStat());
                    }

                    return existingPRezeptVer;
                }
            )
            .map(pRezeptVerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptVer.getId().toString())
        );
    }

    /**
     * {@code GET  /p-rezept-vers} : get all the pRezeptVers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pRezeptVers in body.
     */
    @GetMapping("/p-rezept-vers")
    public List<PRezeptVer> getAllPRezeptVers(@RequestParam(required = false) String filter) {
        if ("prezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all PRezeptVers where pRezeptId is null");
            return StreamSupport
                .stream(pRezeptVerRepository.findAll().spliterator(), false)
                .filter(pRezeptVer -> pRezeptVer.getPRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PRezeptVers");
        return pRezeptVerRepository.findAll();
    }

    /**
     * {@code GET  /p-rezept-vers/:id} : get the "id" pRezeptVer.
     *
     * @param id the id of the pRezeptVer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pRezeptVer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-rezept-vers/{id}")
    public ResponseEntity<PRezeptVer> getPRezeptVer(@PathVariable Long id) {
        log.debug("REST request to get PRezeptVer : {}", id);
        Optional<PRezeptVer> pRezeptVer = pRezeptVerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pRezeptVer);
    }

    /**
     * {@code DELETE  /p-rezept-vers/:id} : delete the "id" pRezeptVer.
     *
     * @param id the id of the pRezeptVer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-rezept-vers/{id}")
    public ResponseEntity<Void> deletePRezeptVer(@PathVariable Long id) {
        log.debug("REST request to delete PRezeptVer : {}", id);
        pRezeptVerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
