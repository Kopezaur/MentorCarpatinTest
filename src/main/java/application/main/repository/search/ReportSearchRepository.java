package application.main.repository.search;

import application.main.domain.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Report} entity.
 */
public interface ReportSearchRepository extends ElasticsearchRepository<Report, Long> {
}
