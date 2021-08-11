package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.M16Status;
import com.mycompany.myapp.repository.M16StatusRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.M16Status}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class M16StatusResource {

    private final Logger log = LoggerFactory.getLogger(M16StatusResource.class);

    private static final String ENTITY_NAME = "m16Status";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final M16StatusRepository m16StatusRepository;

    public M16StatusResource(M16StatusRepository m16StatusRepository) {
        this.m16StatusRepository = m16StatusRepository;
    }

    /**
     * {@code POST  /m-16-statuses} : Create a new m16Status.
     *
     * @param m16Status the m16Status to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new m16Status, or with status {@code 400 (Bad Request)} if the m16Status has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-16-statuses")
    public ResponseEntity<M16Status> createM16Status(@RequestBody M16Status m16Status) throws URISyntaxException {
        log.debug("REST request to save M16Status : {}", m16Status);
        if (m16Status.getId() != null) {
            throw new BadRequestAlertException("A new m16Status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M16Status result = m16StatusRepository.save(m16Status);
        return ResponseEntity
            .created(new URI("/api/m-16-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-16-statuses/:id} : Updates an existing m16Status.
     *
     * @param id the id of the m16Status to save.
     * @param m16Status the m16Status to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m16Status,
     * or with status {@code 400 (Bad Request)} if the m16Status is not valid,
     * or with status {@code 500 (Internal Server Error)} if the m16Status couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-16-statuses/{id}")
    public ResponseEntity<M16Status> updateM16Status(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody M16Status m16Status
    ) throws URISyntaxException {
        log.debug("REST request to update M16Status : {}, {}", id, m16Status);
        if (m16Status.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, m16Status.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!m16StatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        M16Status result = m16StatusRepository.save(m16Status);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, m16Status.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /m-16-statuses/:id} : Partial updates given fields of an existing m16Status, field will ignore if it is null
     *
     * @param id the id of the m16Status to save.
     * @param m16Status the m16Status to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m16Status,
     * or with status {@code 400 (Bad Request)} if the m16Status is not valid,
     * or with status {@code 404 (Not Found)} if the m16Status is not found,
     * or with status {@code 500 (Internal Server Error)} if the m16Status couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/m-16-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<M16Status> partialUpdateM16Status(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody M16Status m16Status
    ) throws URISyntaxException {
        log.debug("REST request to partial update M16Status partially : {}, {}", id, m16Status);
        if (m16Status.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, m16Status.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!m16StatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<M16Status> result = m16StatusRepository
            .findById(m16Status.getId())
            .map(
                existingM16Status -> {
                    if (m16Status.getm16StatusId() != null) {
                        existingM16Status.setm16StatusId(m16Status.getm16StatusId());
                    }
                    if (m16Status.getStatus() != null) {
                        existingM16Status.setStatus(m16Status.getStatus());
                    }

                    return existingM16Status;
                }
            )
            .map(m16StatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, m16Status.getId().toString())
        );
    }

    /**
     * {@code GET  /m-16-statuses} : get all the m16Statuses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of m16Statuses in body.
     */
    @GetMapping("/m-16-statuses")
    public List<M16Status> getAllM16Statuses(@RequestParam(required = false) String filter) {
        if ("m16statusid-is-null".equals(filter)) {
            log.debug("REST request to get all M16Statuss where m16StatusId is null");
            return StreamSupport
                .stream(m16StatusRepository.findAll().spliterator(), false)
                .filter(m16Status -> m16Status.getM16StatusId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all M16Statuses");
        return m16StatusRepository.findAll();
    }

    /**
     * {@code GET  /m-16-statuses/:id} : get the "id" m16Status.
     *
     * @param id the id of the m16Status to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the m16Status, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-16-statuses/{id}")
    public ResponseEntity<M16Status> getM16Status(@PathVariable Long id) {
        log.debug("REST request to get M16Status : {}", id);
        Optional<M16Status> m16Status = m16StatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(m16Status);
    }

    /**
     * {@code DELETE  /m-16-statuses/:id} : delete the "id" m16Status.
     *
     * @param id the id of the m16Status to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-16-statuses/{id}")
    public ResponseEntity<Void> deleteM16Status(@PathVariable Long id) {
        log.debug("REST request to delete M16Status : {}", id);
        m16StatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
