package application.main.service.impl;

import application.main.service.ReportService;
import application.main.domain.Report;
import application.main.repository.ReportRepository;
import application.main.repository.search.ReportSearchRepository;
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
 * Service Implementation for managing {@link Report}.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private final ReportSearchRepository reportSearchRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ReportSearchRepository reportSearchRepository) {
        this.reportRepository = reportRepository;
        this.reportSearchRepository = reportSearchRepository;
    }

    /**
     * Save a report.
     *
     * @param report the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Report save(Report report) {
        log.debug("Request to save Report : {}", report);
        Report result = reportRepository.save(report);
        reportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the reports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Report> findAll() {
        log.debug("Request to get all Reports");
        return reportRepository.findAll();
    }


    /**
     * Get one report by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return reportRepository.findById(id);
    }

    /**
     * Delete the report by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.deleteById(id);
        reportSearchRepository.deleteById(id);
    }

    /**
     * Search for the report corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Report> search(String query) {
        log.debug("Request to search Reports for query {}", query);
        return StreamSupport
            .stream(reportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
