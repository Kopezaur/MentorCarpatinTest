package application.main.service.impl;

import application.main.service.RoutePerformanceService;
import application.main.domain.RoutePerformance;
import application.main.repository.RoutePerformanceRepository;
import application.main.repository.search.RoutePerformanceSearchRepository;
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
 * Service Implementation for managing {@link RoutePerformance}.
 */
@Service
@Transactional
public class RoutePerformanceServiceImpl implements RoutePerformanceService {

    private final Logger log = LoggerFactory.getLogger(RoutePerformanceServiceImpl.class);

    private final RoutePerformanceRepository routePerformanceRepository;

    private final RoutePerformanceSearchRepository routePerformanceSearchRepository;

    public RoutePerformanceServiceImpl(RoutePerformanceRepository routePerformanceRepository, RoutePerformanceSearchRepository routePerformanceSearchRepository) {
        this.routePerformanceRepository = routePerformanceRepository;
        this.routePerformanceSearchRepository = routePerformanceSearchRepository;
    }

    /**
     * Save a routePerformance.
     *
     * @param routePerformance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RoutePerformance save(RoutePerformance routePerformance) {
        log.debug("Request to save RoutePerformance : {}", routePerformance);
        RoutePerformance result = routePerformanceRepository.save(routePerformance);
        routePerformanceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the routePerformances.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoutePerformance> findAll() {
        log.debug("Request to get all RoutePerformances");
        return routePerformanceRepository.findAll();
    }


    /**
     * Get one routePerformance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoutePerformance> findOne(Long id) {
        log.debug("Request to get RoutePerformance : {}", id);
        return routePerformanceRepository.findById(id);
    }

    /**
     * Delete the routePerformance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoutePerformance : {}", id);
        routePerformanceRepository.deleteById(id);
        routePerformanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the routePerformance corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoutePerformance> search(String query) {
        log.debug("Request to search RoutePerformances for query {}", query);
        return StreamSupport
            .stream(routePerformanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
