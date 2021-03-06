package application.main.web.rest;

import application.main.domain.Route;
import application.main.service.RouteService;
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
 * REST controller for managing {@link application.main.domain.Route}.
 */
@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    private static final String ENTITY_NAME = "route";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RouteService routeService;

    public RouteResource(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * {@code POST  /routes} : Create a new route.
     *
     * @param route the route to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new route, or with status {@code 400 (Bad Request)} if the route has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/routes")
    public ResponseEntity<Route> createRoute(@RequestBody Route route) throws URISyntaxException {
        log.debug("REST request to save Route : {}", route);
        if (route.getId() != null) {
            throw new BadRequestAlertException("A new route cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Route result = routeService.save(route);
        return ResponseEntity.created(new URI("/api/routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /routes} : Updates an existing route.
     *
     * @param route the route to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated route,
     * or with status {@code 400 (Bad Request)} if the route is not valid,
     * or with status {@code 500 (Internal Server Error)} if the route couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/routes")
    public ResponseEntity<Route> updateRoute(@RequestBody Route route) throws URISyntaxException {
        log.debug("REST request to update Route : {}", route);
        if (route.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Route result = routeService.save(route);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, route.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /routes} : get all the routes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routes in body.
     */
    @GetMapping("/routes")
    public List<Route> getAllRoutes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Routes");
        return routeService.findAll();
    }

    /**
     * {@code GET  /routes/:id} : get the "id" route.
     *
     * @param id the id of the route to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the route, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/routes/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        log.debug("REST request to get Route : {}", id);
        Optional<Route> route = routeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(route);
    }

    /**
     * {@code DELETE  /routes/:id} : delete the "id" route.
     *
     * @param id the id of the route to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.debug("REST request to delete Route : {}", id);
        routeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/routes?query=:query} : search for the route corresponding
     * to the query.
     *
     * @param query the query of the route search.
     * @return the result of the search.
     */
    @GetMapping("/_search/routes")
    public List<Route> searchRoutes(@RequestParam String query) {
        log.debug("REST request to search Routes for query {}", query);
        return routeService.search(query);
    }

}
