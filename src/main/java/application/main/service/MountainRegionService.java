package application.main.service;

import application.main.domain.MountainRegion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MountainRegion}.
 */
public interface MountainRegionService {

    /**
     * Save a mountainRegion.
     *
     * @param mountainRegion the entity to save.
     * @return the persisted entity.
     */
    MountainRegion save(MountainRegion mountainRegion);

    /**
     * Get all the mountainRegions.
     *
     * @return the list of entities.
     */
    List<MountainRegion> findAll();


    /**
     * Get the "id" mountainRegion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MountainRegion> findOne(Long id);

    /**
     * Delete the "id" mountainRegion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mountainRegion corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MountainRegion> search(String query);
}
