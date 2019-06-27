package application.main.repository.search;

import application.main.domain.MountainRegion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MountainRegion} entity.
 */
public interface MountainRegionSearchRepository extends ElasticsearchRepository<MountainRegion, Long> {
}
