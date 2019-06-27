package application.main.web.rest;

import application.main.MentorCarpatinApp;
import application.main.domain.RoutePerformance;
import application.main.repository.RoutePerformanceRepository;
import application.main.repository.search.RoutePerformanceSearchRepository;
import application.main.service.RoutePerformanceService;
import application.main.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static application.main.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RoutePerformanceResource} REST controller.
 */
@SpringBootTest(classes = MentorCarpatinApp.class)
public class RoutePerformanceResourceIT {

    private static final Integer DEFAULT_TIME = 1;
    private static final Integer UPDATED_TIME = 2;

    private static final Double DEFAULT_PACE = 1D;
    private static final Double UPDATED_PACE = 2D;

    private static final Double DEFAULT_SPEED = 1D;
    private static final Double UPDATED_SPEED = 2D;

    private static final Double DEFAULT_PERFORMANCE_COEFFICIENT = 1D;
    private static final Double UPDATED_PERFORMANCE_COEFFICIENT = 2D;

    @Autowired
    private RoutePerformanceRepository routePerformanceRepository;

    @Autowired
    private RoutePerformanceService routePerformanceService;

    /**
     * This repository is mocked in the application.main.repository.search test package.
     *
     * @see application.main.repository.search.RoutePerformanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private RoutePerformanceSearchRepository mockRoutePerformanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRoutePerformanceMockMvc;

    private RoutePerformance routePerformance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoutePerformanceResource routePerformanceResource = new RoutePerformanceResource(routePerformanceService);
        this.restRoutePerformanceMockMvc = MockMvcBuilders.standaloneSetup(routePerformanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoutePerformance createEntity(EntityManager em) {
        RoutePerformance routePerformance = new RoutePerformance()
            .time(DEFAULT_TIME)
            .pace(DEFAULT_PACE)
            .speed(DEFAULT_SPEED)
            .performanceCoefficient(DEFAULT_PERFORMANCE_COEFFICIENT);
        return routePerformance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoutePerformance createUpdatedEntity(EntityManager em) {
        RoutePerformance routePerformance = new RoutePerformance()
            .time(UPDATED_TIME)
            .pace(UPDATED_PACE)
            .speed(UPDATED_SPEED)
            .performanceCoefficient(UPDATED_PERFORMANCE_COEFFICIENT);
        return routePerformance;
    }

    @BeforeEach
    public void initTest() {
        routePerformance = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoutePerformance() throws Exception {
        int databaseSizeBeforeCreate = routePerformanceRepository.findAll().size();

        // Create the RoutePerformance
        restRoutePerformanceMockMvc.perform(post("/api/route-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routePerformance)))
            .andExpect(status().isCreated());

        // Validate the RoutePerformance in the database
        List<RoutePerformance> routePerformanceList = routePerformanceRepository.findAll();
        assertThat(routePerformanceList).hasSize(databaseSizeBeforeCreate + 1);
        RoutePerformance testRoutePerformance = routePerformanceList.get(routePerformanceList.size() - 1);
        assertThat(testRoutePerformance.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testRoutePerformance.getPace()).isEqualTo(DEFAULT_PACE);
        assertThat(testRoutePerformance.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testRoutePerformance.getPerformanceCoefficient()).isEqualTo(DEFAULT_PERFORMANCE_COEFFICIENT);

        // Validate the RoutePerformance in Elasticsearch
        verify(mockRoutePerformanceSearchRepository, times(1)).save(testRoutePerformance);
    }

    @Test
    @Transactional
    public void createRoutePerformanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = routePerformanceRepository.findAll().size();

        // Create the RoutePerformance with an existing ID
        routePerformance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoutePerformanceMockMvc.perform(post("/api/route-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routePerformance)))
            .andExpect(status().isBadRequest());

        // Validate the RoutePerformance in the database
        List<RoutePerformance> routePerformanceList = routePerformanceRepository.findAll();
        assertThat(routePerformanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the RoutePerformance in Elasticsearch
        verify(mockRoutePerformanceSearchRepository, times(0)).save(routePerformance);
    }


    @Test
    @Transactional
    public void getAllRoutePerformances() throws Exception {
        // Initialize the database
        routePerformanceRepository.saveAndFlush(routePerformance);

        // Get all the routePerformanceList
        restRoutePerformanceMockMvc.perform(get("/api/route-performances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routePerformance.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].pace").value(hasItem(DEFAULT_PACE.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceCoefficient").value(hasItem(DEFAULT_PERFORMANCE_COEFFICIENT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRoutePerformance() throws Exception {
        // Initialize the database
        routePerformanceRepository.saveAndFlush(routePerformance);

        // Get the routePerformance
        restRoutePerformanceMockMvc.perform(get("/api/route-performances/{id}", routePerformance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(routePerformance.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.pace").value(DEFAULT_PACE.doubleValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.doubleValue()))
            .andExpect(jsonPath("$.performanceCoefficient").value(DEFAULT_PERFORMANCE_COEFFICIENT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRoutePerformance() throws Exception {
        // Get the routePerformance
        restRoutePerformanceMockMvc.perform(get("/api/route-performances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoutePerformance() throws Exception {
        // Initialize the database
        routePerformanceService.save(routePerformance);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRoutePerformanceSearchRepository);

        int databaseSizeBeforeUpdate = routePerformanceRepository.findAll().size();

        // Update the routePerformance
        RoutePerformance updatedRoutePerformance = routePerformanceRepository.findById(routePerformance.getId()).get();
        // Disconnect from session so that the updates on updatedRoutePerformance are not directly saved in db
        em.detach(updatedRoutePerformance);
        updatedRoutePerformance
            .time(UPDATED_TIME)
            .pace(UPDATED_PACE)
            .speed(UPDATED_SPEED)
            .performanceCoefficient(UPDATED_PERFORMANCE_COEFFICIENT);

        restRoutePerformanceMockMvc.perform(put("/api/route-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoutePerformance)))
            .andExpect(status().isOk());

        // Validate the RoutePerformance in the database
        List<RoutePerformance> routePerformanceList = routePerformanceRepository.findAll();
        assertThat(routePerformanceList).hasSize(databaseSizeBeforeUpdate);
        RoutePerformance testRoutePerformance = routePerformanceList.get(routePerformanceList.size() - 1);
        assertThat(testRoutePerformance.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testRoutePerformance.getPace()).isEqualTo(UPDATED_PACE);
        assertThat(testRoutePerformance.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testRoutePerformance.getPerformanceCoefficient()).isEqualTo(UPDATED_PERFORMANCE_COEFFICIENT);

        // Validate the RoutePerformance in Elasticsearch
        verify(mockRoutePerformanceSearchRepository, times(1)).save(testRoutePerformance);
    }

    @Test
    @Transactional
    public void updateNonExistingRoutePerformance() throws Exception {
        int databaseSizeBeforeUpdate = routePerformanceRepository.findAll().size();

        // Create the RoutePerformance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoutePerformanceMockMvc.perform(put("/api/route-performances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(routePerformance)))
            .andExpect(status().isBadRequest());

        // Validate the RoutePerformance in the database
        List<RoutePerformance> routePerformanceList = routePerformanceRepository.findAll();
        assertThat(routePerformanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RoutePerformance in Elasticsearch
        verify(mockRoutePerformanceSearchRepository, times(0)).save(routePerformance);
    }

    @Test
    @Transactional
    public void deleteRoutePerformance() throws Exception {
        // Initialize the database
        routePerformanceService.save(routePerformance);

        int databaseSizeBeforeDelete = routePerformanceRepository.findAll().size();

        // Delete the routePerformance
        restRoutePerformanceMockMvc.perform(delete("/api/route-performances/{id}", routePerformance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoutePerformance> routePerformanceList = routePerformanceRepository.findAll();
        assertThat(routePerformanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RoutePerformance in Elasticsearch
        verify(mockRoutePerformanceSearchRepository, times(1)).deleteById(routePerformance.getId());
    }

    @Test
    @Transactional
    public void searchRoutePerformance() throws Exception {
        // Initialize the database
        routePerformanceService.save(routePerformance);
        when(mockRoutePerformanceSearchRepository.search(queryStringQuery("id:" + routePerformance.getId())))
            .thenReturn(Collections.singletonList(routePerformance));
        // Search the routePerformance
        restRoutePerformanceMockMvc.perform(get("/api/_search/route-performances?query=id:" + routePerformance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routePerformance.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].pace").value(hasItem(DEFAULT_PACE.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceCoefficient").value(hasItem(DEFAULT_PERFORMANCE_COEFFICIENT.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoutePerformance.class);
        RoutePerformance routePerformance1 = new RoutePerformance();
        routePerformance1.setId(1L);
        RoutePerformance routePerformance2 = new RoutePerformance();
        routePerformance2.setId(routePerformance1.getId());
        assertThat(routePerformance1).isEqualTo(routePerformance2);
        routePerformance2.setId(2L);
        assertThat(routePerformance1).isNotEqualTo(routePerformance2);
        routePerformance1.setId(null);
        assertThat(routePerformance1).isNotEqualTo(routePerformance2);
    }
}
