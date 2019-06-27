package application.main.repository.search;

import application.main.domain.RoutePerformance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RoutePerformance} entity.
 */
public interface RoutePerformanceSearchRepository extends ElasticsearchRepository<RoutePerformance, Long> {
}
