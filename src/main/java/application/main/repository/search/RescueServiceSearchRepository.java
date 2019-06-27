package application.main.repository.search;

import application.main.domain.RescueService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RescueService} entity.
 */
public interface RescueServiceSearchRepository extends ElasticsearchRepository<RescueService, Long> {
}
