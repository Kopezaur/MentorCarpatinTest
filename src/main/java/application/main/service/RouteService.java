package application.main.service;

import application.main.domain.Route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Route}.
 */
public interface RouteService {

    /**
     * Save a route.
     *
     * @param route the entity to save.
     * @return the persisted entity.
     */
    Route save(Route route);

    /**
     * Get all the routes.
     *
     * @return the list of entities.
     */
    List<Route> findAll();

    /**
     * Get all the routes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Route> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" route.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Route> findOne(Long id);

    /**
     * Delete the "id" route.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the route corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Route> search(String query);
}
