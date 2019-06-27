package application.main.web.rest;

import application.main.domain.RoutePerformance;
import application.main.service.RoutePerformanceService;
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
 * REST controller for managing {@link application.main.domain.RoutePerformance}.
 */
@RestController
@RequestMapping("/api")
public class RoutePerformanceResource {

    private final Logger log = LoggerFactory.getLogger(RoutePerformanceResource.class);

    private static final String ENTITY_NAME = "routePerformance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoutePerformanceService routePerformanceService;

    public RoutePerformanceResource(RoutePerformanceService routePerformanceService) {
        this.routePerformanceService = routePerformanceService;
    }

    /**
     * {@code POST  /route-performances} : Create a new routePerformance.
     *
     * @param routePerformance the routePerformance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new routePerformance, or with status {@code 400 (Bad Request)} if the routePerformance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/route-performances")
    public ResponseEntity<RoutePerformance> createRoutePerformance(@RequestBody RoutePerformance routePerformance) throws URISyntaxException {
        log.debug("REST request to save RoutePerformance : {}", routePerformance);
        if (routePerformance.getId() != null) {
            throw new BadRequestAlertException("A new routePerformance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoutePerformance result = routePerformanceService.save(routePerformance);
        return ResponseEntity.created(new URI("/api/route-performances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /route-performances} : Updates an existing routePerformance.
     *
     * @param routePerformance the routePerformance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated routePerformance,
     * or with status {@code 400 (Bad Request)} if the routePerformance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the routePerformance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/route-performances")
    public ResponseEntity<RoutePerformance> updateRoutePerformance(@RequestBody RoutePerformance routePerformance) throws URISyntaxException {
        log.debug("REST request to update RoutePerformance : {}", routePerformance);
        if (routePerformance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoutePerformance result = routePerformanceService.save(routePerformance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, routePerformance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /route-performances} : get all the routePerformances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routePerformances in body.
     */
    @GetMapping("/route-performances")
    public List<RoutePerformance> getAllRoutePerformances() {
        log.debug("REST request to get all RoutePerformances");
        return routePerformanceService.findAll();
    }

    /**
     * {@code GET  /route-performances/:id} : get the "id" routePerformance.
     *
     * @param id the id of the routePerformance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the routePerformance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/route-performances/{id}")
    public ResponseEntity<RoutePerformance> getRoutePerformance(@PathVariable Long id) {
        log.debug("REST request to get RoutePerformance : {}", id);
        Optional<RoutePerformance> routePerformance = routePerformanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(routePerformance);
    }

    /**
     * {@code DELETE  /route-performances/:id} : delete the "id" routePerformance.
     *
     * @param id the id of the routePerformance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/route-performances/{id}")
    public ResponseEntity<Void> deleteRoutePerformance(@PathVariable Long id) {
        log.debug("REST request to delete RoutePerformance : {}", id);
        routePerformanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/route-performances?query=:query} : search for the routePerformance corresponding
     * to the query.
     *
     * @param query the query of the routePerformance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/route-performances")
    public List<RoutePerformance> searchRoutePerformances(@RequestParam String query) {
        log.debug("REST request to search RoutePerformances for query {}", query);
        return routePerformanceService.search(query);
    }

}
