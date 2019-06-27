package application.main.service.impl;

import application.main.service.RescueServiceService;
import application.main.domain.RescueService;
import application.main.repository.RescueServiceRepository;
import application.main.repository.search.RescueServiceSearchRepository;
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
 * Service Implementation for managing {@link RescueService}.
 */
@Service
@Transactional
public class RescueServiceServiceImpl implements RescueServiceService {

    private final Logger log = LoggerFactory.getLogger(RescueServiceServiceImpl.class);

    private final RescueServiceRepository rescueServiceRepository;

    private final RescueServiceSearchRepository rescueServiceSearchRepository;

    public RescueServiceServiceImpl(RescueServiceRepository rescueServiceRepository, RescueServiceSearchRepository rescueServiceSearchRepository) {
        this.rescueServiceRepository = rescueServiceRepository;
        this.rescueServiceSearchRepository = rescueServiceSearchRepository;
    }

    /**
     * Save a rescueService.
     *
     * @param rescueService the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RescueService save(RescueService rescueService) {
        log.debug("Request to save RescueService : {}", rescueService);
        RescueService result = rescueServiceRepository.save(rescueService);
        rescueServiceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the rescueServices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RescueService> findAll() {
        log.debug("Request to get all RescueServices");
        return rescueServiceRepository.findAll();
    }


    /**
     * Get one rescueService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RescueService> findOne(Long id) {
        log.debug("Request to get RescueService : {}", id);
        return rescueServiceRepository.findById(id);
    }

    /**
     * Delete the rescueService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RescueService : {}", id);
        rescueServiceRepository.deleteById(id);
        rescueServiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the rescueService corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RescueService> search(String query) {
        log.debug("Request to search RescueServices for query {}", query);
        return StreamSupport
            .stream(rescueServiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
