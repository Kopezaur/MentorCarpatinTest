package application.main.repository.search;

import application.main.domain.Route;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Route} entity.
 */
public interface RouteSearchRepository extends ElasticsearchRepository<Route, Long> {
}
