package application.main.web.rest;

import application.main.domain.MountainRegion;
import application.main.service.MountainRegionService;
import application.main.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link application.main.domain.MountainRegion}.
 */
@RestController
@RequestMapping("/api")
public class MountainRegionResource {

    private final Logger log = LoggerFactory.getLogger(MountainRegionResource.class);

    private static final String ENTITY_NAME = "mountainRegion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MountainRegionService mountainRegionService;

    public MountainRegionResource(MountainRegionService mountainRegionService) {
        this.mountainRegionService = mountainRegionService;
    }

    /**
     * {@code POST  /mountain-regions} : Create a new mountainRegion.
     *
     * @param mountainRegion the mountainRegion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mountainRegion, or with status {@code 400 (Bad Request)} if the mountainRegion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mountain-regions")
    public ResponseEntity<MountainRegion> createMountainRegion(@RequestBody MountainRegion mountainRegion) throws URISyntaxException {
        log.debug("REST request to save MountainRegion : {}", mountainRegion);
        if (mountainRegion.getId() != null) {
            throw new BadRequestAlertException("A new mountainRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MountainRegion result = mountainRegionService.save(mountainRegion);
        return ResponseEntity.created(new URI("/api/mountain-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mountain-regions} : Updates an existing mountainRegion.
     *
     * @param mountainRegion the mountainRegion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mountainRegion,
     * or with status {@code 400 (Bad Request)} if the mountainRegion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mountainRegion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mountain-regions")
    public ResponseEntity<MountainRegion> updateMountainRegion(@RequestBody MountainRegion mountainRegion) throws URISyntaxException {
        log.debug("REST request to update MountainRegion : {}", mountainRegion);
        if (mountainRegion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MountainRegion result = mountainRegionService.save(mountainRegion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mountainRegion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mountain-regions} : get all the mountainRegions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mountainRegions in body.
     */
    @GetMapping("/mountain-regions")
    public List<MountainRegion> getAllMountainRegions() {
        log.debug("REST request to get all MountainRegions");
        return mountainRegionService.findAll();
    }

    /**
     * {@code GET  /mountain-regions/:id} : get the "id" mountainRegion.
     *
     * @param id the id of the mountainRegion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mountainRegion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mountain-regions/{id}")
    public ResponseEntity<MountainRegion> getMountainRegion(@PathVariable Long id) {
        log.debug("REST request to get MountainRegion : {}", id);
        Optional<MountainRegion> mountainRegion = mountainRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mountainRegion);
    }

    /**
     * {@code DELETE  /mountain-regions/:id} : delete the "id" mountainRegion.
     *
     * @param id the id of the mountainRegion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mountain-regions/{id}")
    public ResponseEntity<Void> deleteMountainRegion(@PathVariable Long id) {
        log.debug("REST request to delete MountainRegion : {}", id);
        mountainRegionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mountain-regions?query=:query} : search for the mountainRegion corresponding
     * to the query.
     *
     * @param query the query of the mountainRegion search.
     * @return the result of the search.
     */
    @GetMapping("/_search/mountain-regions")
    public List<MountainRegion> searchMountainRegions(@RequestParam String query) {
        log.debug("REST request to search MountainRegions for query {}", query);
        return mountainRegionService.search(query);
    }

}
