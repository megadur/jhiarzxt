package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Muster16Ver;
import com.mycompany.myapp.repository.Muster16VerRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Muster16Ver}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Muster16VerResource {

    private final Logger log = LoggerFactory.getLogger(Muster16VerResource.class);

    private static final String ENTITY_NAME = "muster16Ver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Muster16VerRepository muster16VerRepository;

    public Muster16VerResource(Muster16VerRepository muster16VerRepository) {
        this.muster16VerRepository = muster16VerRepository;
    }

    /**
     * {@code POST  /muster-16-vers} : Create a new muster16Ver.
     *
     * @param muster16Ver the muster16Ver to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new muster16Ver, or with status {@code 400 (Bad Request)} if the muster16Ver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muster-16-vers")
    public ResponseEntity<Muster16Ver> createMuster16Ver(@RequestBody Muster16Ver muster16Ver) throws URISyntaxException {
        log.debug("REST request to save Muster16Ver : {}", muster16Ver);
        if (muster16Ver.getId() != null) {
            throw new BadRequestAlertException("A new muster16Ver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Muster16Ver result = muster16VerRepository.save(muster16Ver);
        return ResponseEntity
            .created(new URI("/api/muster-16-vers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muster-16-vers/:id} : Updates an existing muster16Ver.
     *
     * @param id the id of the muster16Ver to save.
     * @param muster16Ver the muster16Ver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Ver,
     * or with status {@code 400 (Bad Request)} if the muster16Ver is not valid,
     * or with status {@code 500 (Internal Server Error)} if the muster16Ver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muster-16-vers/{id}")
    public ResponseEntity<Muster16Ver> updateMuster16Ver(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Ver muster16Ver
    ) throws URISyntaxException {
        log.debug("REST request to update Muster16Ver : {}, {}", id, muster16Ver);
        if (muster16Ver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Ver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16VerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Muster16Ver result = muster16VerRepository.save(muster16Ver);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Ver.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /muster-16-vers/:id} : Partial updates given fields of an existing muster16Ver, field will ignore if it is null
     *
     * @param id the id of the muster16Ver to save.
     * @param muster16Ver the muster16Ver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Ver,
     * or with status {@code 400 (Bad Request)} if the muster16Ver is not valid,
     * or with status {@code 404 (Not Found)} if the muster16Ver is not found,
     * or with status {@code 500 (Internal Server Error)} if the muster16Ver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/muster-16-vers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Muster16Ver> partialUpdateMuster16Ver(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Ver muster16Ver
    ) throws URISyntaxException {
        log.debug("REST request to partial update Muster16Ver partially : {}, {}", id, muster16Ver);
        if (muster16Ver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Ver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16VerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Muster16Ver> result = muster16VerRepository
            .findById(muster16Ver.getId())
            .map(
                existingMuster16Ver -> {
                    if (muster16Ver.getaUnfall() != null) {
                        existingMuster16Ver.setaUnfall(muster16Ver.getaUnfall());
                    }
                    if (muster16Ver.getAbDatum() != null) {
                        existingMuster16Ver.setAbDatum(muster16Ver.getAbDatum());
                    }
                    if (muster16Ver.getVrsNr() != null) {
                        existingMuster16Ver.setVrsNr(muster16Ver.getVrsNr());
                    }
                    if (muster16Ver.getVrtrgsArztNr() != null) {
                        existingMuster16Ver.setVrtrgsArztNr(muster16Ver.getVrtrgsArztNr());
                    }
                    if (muster16Ver.getSprStBedarf() != null) {
                        existingMuster16Ver.setSprStBedarf(muster16Ver.getSprStBedarf());
                    }
                    if (muster16Ver.getUnfall() != null) {
                        existingMuster16Ver.setUnfall(muster16Ver.getUnfall());
                    }
                    if (muster16Ver.getUnfallTag() != null) {
                        existingMuster16Ver.setUnfallTag(muster16Ver.getUnfallTag());
                    }
                    if (muster16Ver.getvGeb() != null) {
                        existingMuster16Ver.setvGeb(muster16Ver.getvGeb());
                    }
                    if (muster16Ver.getvStat() != null) {
                        existingMuster16Ver.setvStat(muster16Ver.getvStat());
                    }
                    if (muster16Ver.getVerDat() != null) {
                        existingMuster16Ver.setVerDat(muster16Ver.getVerDat());
                    }
                    if (muster16Ver.getkName() != null) {
                        existingMuster16Ver.setkName(muster16Ver.getkName());
                    }
                    if (muster16Ver.getKkIk() != null) {
                        existingMuster16Ver.setKkIk(muster16Ver.getKkIk());
                    }
                    if (muster16Ver.getLaNr() != null) {
                        existingMuster16Ver.setLaNr(muster16Ver.getLaNr());
                    }
                    if (muster16Ver.getNoctu() != null) {
                        existingMuster16Ver.setNoctu(muster16Ver.getNoctu());
                    }
                    if (muster16Ver.getHilf() != null) {
                        existingMuster16Ver.setHilf(muster16Ver.getHilf());
                    }
                    if (muster16Ver.getImpf() != null) {
                        existingMuster16Ver.setImpf(muster16Ver.getImpf());
                    }
                    if (muster16Ver.getkArt() != null) {
                        existingMuster16Ver.setkArt(muster16Ver.getkArt());
                    }
                    if (muster16Ver.getrTyp() != null) {
                        existingMuster16Ver.setrTyp(muster16Ver.getrTyp());
                    }
                    if (muster16Ver.getRezeptTyp() != null) {
                        existingMuster16Ver.setRezeptTyp(muster16Ver.getRezeptTyp());
                    }
                    if (muster16Ver.getBgrPfl() != null) {
                        existingMuster16Ver.setBgrPfl(muster16Ver.getBgrPfl());
                    }
                    if (muster16Ver.getBvg() != null) {
                        existingMuster16Ver.setBvg(muster16Ver.getBvg());
                    }
                    if (muster16Ver.getEigBet() != null) {
                        existingMuster16Ver.setEigBet(muster16Ver.getEigBet());
                    }
                    if (muster16Ver.getGebFrei() != null) {
                        existingMuster16Ver.setGebFrei(muster16Ver.getGebFrei());
                    }
                    if (muster16Ver.getSonstige() != null) {
                        existingMuster16Ver.setSonstige(muster16Ver.getSonstige());
                    }
                    if (muster16Ver.getVkGueltigBis() != null) {
                        existingMuster16Ver.setVkGueltigBis(muster16Ver.getVkGueltigBis());
                    }

                    return existingMuster16Ver;
                }
            )
            .map(muster16VerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Ver.getId().toString())
        );
    }

    /**
     * {@code GET  /muster-16-vers} : get all the muster16Vers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of muster16Vers in body.
     */
    @GetMapping("/muster-16-vers")
    public List<Muster16Ver> getAllMuster16Vers(@RequestParam(required = false) String filter) {
        if ("mrezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all Muster16Vers where mRezeptId is null");
            return StreamSupport
                .stream(muster16VerRepository.findAll().spliterator(), false)
                .filter(muster16Ver -> muster16Ver.getMRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Muster16Vers");
        return muster16VerRepository.findAll();
    }

    /**
     * {@code GET  /muster-16-vers/:id} : get the "id" muster16Ver.
     *
     * @param id the id of the muster16Ver to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the muster16Ver, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muster-16-vers/{id}")
    public ResponseEntity<Muster16Ver> getMuster16Ver(@PathVariable Long id) {
        log.debug("REST request to get Muster16Ver : {}", id);
        Optional<Muster16Ver> muster16Ver = muster16VerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(muster16Ver);
    }

    /**
     * {@code DELETE  /muster-16-vers/:id} : delete the "id" muster16Ver.
     *
     * @param id the id of the muster16Ver to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muster-16-vers/{id}")
    public ResponseEntity<Void> deleteMuster16Ver(@PathVariable Long id) {
        log.debug("REST request to delete Muster16Ver : {}", id);
        muster16VerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
