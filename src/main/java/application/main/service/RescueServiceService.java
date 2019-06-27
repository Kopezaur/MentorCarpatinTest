package application.main.service;

import application.main.domain.RescueService;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RescueService}.
 */
public interface RescueServiceService {

    /**
     * Save a rescueService.
     *
     * @param rescueService the entity to save.
     * @return the persisted entity.
     */
    RescueService save(RescueService rescueService);

    /**
     * Get all the rescueServices.
     *
     * @return the list of entities.
     */
    List<RescueService> findAll();


    /**
     * Get the "id" rescueService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RescueService> findOne(Long id);

    /**
     * Delete the "id" rescueService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the rescueService corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<RescueService> search(String query);
}
