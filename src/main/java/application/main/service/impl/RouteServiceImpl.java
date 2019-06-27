package application.main.service.impl;

import application.main.service.RouteService;
import application.main.domain.Route;
import application.main.repository.RouteRepository;
import application.main.repository.search.RouteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Route}.
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository routeRepository;

    private final RouteSearchRepository routeSearchRepository;

    public RouteServiceImpl(RouteRepository routeRepository, RouteSearchRepository routeSearchRepository) {
        this.routeRepository = routeRepository;
        this.routeSearchRepository = routeSearchRepository;
    }

    /**
     * Save a route.
     *
     * @param route the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Route save(Route route) {
        log.debug("Request to save Route : {}", route);
        Route result = routeRepository.save(route);
        routeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the routes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Route> findAll() {
        log.debug("Request to get all Routes");
        return routeRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the routes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Route> findAllWithEagerRelationships(Pageable pageable) {
        return routeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one route by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Route> findOne(Long id) {
        log.debug("Request to get Route : {}", id);
        return routeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the route by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Route : {}", id);
        routeRepository.deleteById(id);
        routeSearchRepository.deleteById(id);
    }

    /**
     * Search for the route corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Route> search(String query) {
        log.debug("Request to search Routes for query {}", query);
        return StreamSupport
            .stream(routeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
