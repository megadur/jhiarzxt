package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PStatusInfo;
import com.mycompany.myapp.repository.PStatusInfoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PStatusInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PStatusInfoResource {

    private final Logger log = LoggerFactory.getLogger(PStatusInfoResource.class);

    private static final String ENTITY_NAME = "pStatusInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PStatusInfoRepository pStatusInfoRepository;

    public PStatusInfoResource(PStatusInfoRepository pStatusInfoRepository) {
        this.pStatusInfoRepository = pStatusInfoRepository;
    }

    /**
     * {@code POST  /p-status-infos} : Create a new pStatusInfo.
     *
     * @param pStatusInfo the pStatusInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pStatusInfo, or with status {@code 400 (Bad Request)} if the pStatusInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-status-infos")
    public ResponseEntity<PStatusInfo> createPStatusInfo(@RequestBody PStatusInfo pStatusInfo) throws URISyntaxException {
        log.debug("REST request to save PStatusInfo : {}", pStatusInfo);
        if (pStatusInfo.getId() != null) {
            throw new BadRequestAlertException("A new pStatusInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PStatusInfo result = pStatusInfoRepository.save(pStatusInfo);
        return ResponseEntity
            .created(new URI("/api/p-status-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-status-infos/:id} : Updates an existing pStatusInfo.
     *
     * @param id the id of the pStatusInfo to save.
     * @param pStatusInfo the pStatusInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pStatusInfo,
     * or with status {@code 400 (Bad Request)} if the pStatusInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pStatusInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-status-infos/{id}")
    public ResponseEntity<PStatusInfo> updatePStatusInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PStatusInfo pStatusInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PStatusInfo : {}, {}", id, pStatusInfo);
        if (pStatusInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pStatusInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pStatusInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PStatusInfo result = pStatusInfoRepository.save(pStatusInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pStatusInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-status-infos/:id} : Partial updates given fields of an existing pStatusInfo, field will ignore if it is null
     *
     * @param id the id of the pStatusInfo to save.
     * @param pStatusInfo the pStatusInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pStatusInfo,
     * or with status {@code 400 (Bad Request)} if the pStatusInfo is not valid,
     * or with status {@code 404 (Not Found)} if the pStatusInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the pStatusInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-status-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PStatusInfo> partialUpdatePStatusInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PStatusInfo pStatusInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PStatusInfo partially : {}, {}", id, pStatusInfo);
        if (pStatusInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pStatusInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pStatusInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PStatusInfo> result = pStatusInfoRepository
            .findById(pStatusInfo.getId())
            .map(
                existingPStatusInfo -> {
                    if (pStatusInfo.getpStatusInfoId() != null) {
                        existingPStatusInfo.setpStatusInfoId(pStatusInfo.getpStatusInfoId());
                    }
                    if (pStatusInfo.getpRezeptId() != null) {
                        existingPStatusInfo.setpRezeptId(pStatusInfo.getpRezeptId());
                    }
                    if (pStatusInfo.getApoIk() != null) {
                        existingPStatusInfo.setApoIk(pStatusInfo.getApoIk());
                    }
                    if (pStatusInfo.getfCode() != null) {
                        existingPStatusInfo.setfCode(pStatusInfo.getfCode());
                    }
                    if (pStatusInfo.getfHauptFehler() != null) {
                        existingPStatusInfo.setfHauptFehler(pStatusInfo.getfHauptFehler());
                    }
                    if (pStatusInfo.getfKommentar() != null) {
                        existingPStatusInfo.setfKommentar(pStatusInfo.getfKommentar());
                    }
                    if (pStatusInfo.getfKurzText() != null) {
                        existingPStatusInfo.setfKurzText(pStatusInfo.getfKurzText());
                    }
                    if (pStatusInfo.getfLangText() != null) {
                        existingPStatusInfo.setfLangText(pStatusInfo.getfLangText());
                    }
                    if (pStatusInfo.getfStatus() != null) {
                        existingPStatusInfo.setfStatus(pStatusInfo.getfStatus());
                    }
                    if (pStatusInfo.getfTCode() != null) {
                        existingPStatusInfo.setfTCode(pStatusInfo.getfTCode());
                    }
                    if (pStatusInfo.getfWert() != null) {
                        existingPStatusInfo.setfWert(pStatusInfo.getfWert());
                    }
                    if (pStatusInfo.getFaktor() != null) {
                        existingPStatusInfo.setFaktor(pStatusInfo.getFaktor());
                    }
                    if (pStatusInfo.getFristEnde() != null) {
                        existingPStatusInfo.setFristEnde(pStatusInfo.getFristEnde());
                    }
                    if (pStatusInfo.getGesBrutto() != null) {
                        existingPStatusInfo.setGesBrutto(pStatusInfo.getGesBrutto());
                    }
                    if (pStatusInfo.getPosNr() != null) {
                        existingPStatusInfo.setPosNr(pStatusInfo.getPosNr());
                    }
                    if (pStatusInfo.getTaxe() != null) {
                        existingPStatusInfo.setTaxe(pStatusInfo.getTaxe());
                    }
                    if (pStatusInfo.getZeitpunkt() != null) {
                        existingPStatusInfo.setZeitpunkt(pStatusInfo.getZeitpunkt());
                    }
                    if (pStatusInfo.getZuzahlung() != null) {
                        existingPStatusInfo.setZuzahlung(pStatusInfo.getZuzahlung());
                    }

                    return existingPStatusInfo;
                }
            )
            .map(pStatusInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pStatusInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /p-status-infos} : get all the pStatusInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pStatusInfos in body.
     */
    @GetMapping("/p-status-infos")
    public List<PStatusInfo> getAllPStatusInfos() {
        log.debug("REST request to get all PStatusInfos");
        return pStatusInfoRepository.findAll();
    }

    /**
     * {@code GET  /p-status-infos/:id} : get the "id" pStatusInfo.
     *
     * @param id the id of the pStatusInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pStatusInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-status-infos/{id}")
    public ResponseEntity<PStatusInfo> getPStatusInfo(@PathVariable Long id) {
        log.debug("REST request to get PStatusInfo : {}", id);
        Optional<PStatusInfo> pStatusInfo = pStatusInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pStatusInfo);
    }

    /**
     * {@code DELETE  /p-status-infos/:id} : delete the "id" pStatusInfo.
     *
     * @param id the id of the pStatusInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-status-infos/{id}")
    public ResponseEntity<Void> deletePStatusInfo(@PathVariable Long id) {
        log.debug("REST request to delete PStatusInfo : {}", id);
        pStatusInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
