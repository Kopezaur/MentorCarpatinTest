package application.main.service;

import application.main.domain.RoutePerformance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RoutePerformance}.
 */
public interface RoutePerformanceService {

    /**
     * Save a routePerformance.
     *
     * @param routePerformance the entity to save.
     * @return the persisted entity.
     */
    RoutePerformance save(RoutePerformance routePerformance);

    /**
     * Get all the routePerformances.
     *
     * @return the list of entities.
     */
    List<RoutePerformance> findAll();


    /**
     * Get the "id" routePerformance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoutePerformance> findOne(Long id);

    /**
     * Delete the "id" routePerformance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the routePerformance corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<RoutePerformance> search(String query);
}
