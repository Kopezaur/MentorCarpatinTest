package application.main.service.impl;

import application.main.service.MountainRegionService;
import application.main.domain.MountainRegion;
import application.main.repository.MountainRegionRepository;
import application.main.repository.search.MountainRegionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MountainRegion}.
 */
@Service
@Transactional
public class MountainRegionServiceImpl implements MountainRegionService {

    private final Logger log = LoggerFactory.getLogger(MountainRegionServiceImpl.class);

    private final MountainRegionRepository mountainRegionRepository;

    private final MountainRegionSearchRepository mountainRegionSearchRepository;

    public MountainRegionServiceImpl(MountainRegionRepository mountainRegionRepository, MountainRegionSearchRepository mountainRegionSearchRepository) {
        this.mountainRegionRepository = mountainRegionRepository;
        this.mountainRegionSearchRepository = mountainRegionSearchRepository;
    }

    /**
     * Save a mountainRegion.
     *
     * @param mountainRegion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MountainRegion save(MountainRegion mountainRegion) {
        log.debug("Request to save MountainRegion : {}", mountainRegion);
        MountainRegion result = mountainRegionRepository.save(mountainRegion);
        mountainRegionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the mountainRegions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MountainRegion> findAll() {
        log.debug("Request to get all MountainRegions");
        return mountainRegionRepository.findAll();
    }


    /**
     * Get one mountainRegion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MountainRegion> findOne(Long id) {
        log.debug("Request to get MountainRegion : {}", id);
        return mountainRegionRepository.findById(id);
    }

    /**
     * Delete the mountainRegion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MountainRegion : {}", id);
        mountainRegionRepository.deleteById(id);
        mountainRegionSearchRepository.deleteById(id);
    }

    /**
     * Search for the mountainRegion corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MountainRegion> search(String query) {
        log.debug("Request to search MountainRegions for query {}", query);
        return StreamSupport
            .stream(mountainRegionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
