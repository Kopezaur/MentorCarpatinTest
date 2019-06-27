package application.main.web.rest;

import application.main.MentorCarpatinApp;
import application.main.domain.RescueService;
import application.main.repository.RescueServiceRepository;
import application.main.repository.search.RescueServiceSearchRepository;
import application.main.service.RescueServiceService;
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
 * Integration tests for the {@Link RescueServiceResource} REST controller.
 */
@SpringBootTest(classes = MentorCarpatinApp.class)
public class RescueServiceResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_EMPLOYEES = 1;
    private static final Integer UPDATED_NO_OF_EMPLOYEES = 2;

    @Autowired
    private RescueServiceRepository rescueServiceRepository;

    @Autowired
    private RescueServiceService rescueServiceService;

    /**
     * This repository is mocked in the application.main.repository.search test package.
     *
     * @see application.main.repository.search.RescueServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private RescueServiceSearchRepository mockRescueServiceSearchRepository;

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

    private MockMvc restRescueServiceMockMvc;

    private RescueService rescueService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RescueServiceResource rescueServiceResource = new RescueServiceResource(rescueServiceService);
        this.restRescueServiceMockMvc = MockMvcBuilders.standaloneSetup(rescueServiceResource)
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
    public static RescueService createEntity(EntityManager em) {
        RescueService rescueService = new RescueService()
            .description(DEFAULT_DESCRIPTION)
            .noOfEmployees(DEFAULT_NO_OF_EMPLOYEES);
        return rescueService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RescueService createUpdatedEntity(EntityManager em) {
        RescueService rescueService = new RescueService()
            .description(UPDATED_DESCRIPTION)
            .noOfEmployees(UPDATED_NO_OF_EMPLOYEES);
        return rescueService;
    }

    @BeforeEach
    public void initTest() {
        rescueService = createEntity(em);
    }

    @Test
    @Transactional
    public void createRescueService() throws Exception {
        int databaseSizeBeforeCreate = rescueServiceRepository.findAll().size();

        // Create the RescueService
        restRescueServiceMockMvc.perform(post("/api/rescue-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rescueService)))
            .andExpect(status().isCreated());

        // Validate the RescueService in the database
        List<RescueService> rescueServiceList = rescueServiceRepository.findAll();
        assertThat(rescueServiceList).hasSize(databaseSizeBeforeCreate + 1);
        RescueService testRescueService = rescueServiceList.get(rescueServiceList.size() - 1);
        assertThat(testRescueService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRescueService.getNoOfEmployees()).isEqualTo(DEFAULT_NO_OF_EMPLOYEES);

        // Validate the RescueService in Elasticsearch
        verify(mockRescueServiceSearchRepository, times(1)).save(testRescueService);
    }

    @Test
    @Transactional
    public void createRescueServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rescueServiceRepository.findAll().size();

        // Create the RescueService with an existing ID
        rescueService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRescueServiceMockMvc.perform(post("/api/rescue-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rescueService)))
            .andExpect(status().isBadRequest());

        // Validate the RescueService in the database
        List<RescueService> rescueServiceList = rescueServiceRepository.findAll();
        assertThat(rescueServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the RescueService in Elasticsearch
        verify(mockRescueServiceSearchRepository, times(0)).save(rescueService);
    }


    @Test
    @Transactional
    public void getAllRescueServices() throws Exception {
        // Initialize the database
        rescueServiceRepository.saveAndFlush(rescueService);

        // Get all the rescueServiceList
        restRescueServiceMockMvc.perform(get("/api/rescue-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rescueService.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].noOfEmployees").value(hasItem(DEFAULT_NO_OF_EMPLOYEES)));
    }
    
    @Test
    @Transactional
    public void getRescueService() throws Exception {
        // Initialize the database
        rescueServiceRepository.saveAndFlush(rescueService);

        // Get the rescueService
        restRescueServiceMockMvc.perform(get("/api/rescue-services/{id}", rescueService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rescueService.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.noOfEmployees").value(DEFAULT_NO_OF_EMPLOYEES));
    }

    @Test
    @Transactional
    public void getNonExistingRescueService() throws Exception {
        // Get the rescueService
        restRescueServiceMockMvc.perform(get("/api/rescue-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRescueService() throws Exception {
        // Initialize the database
        rescueServiceService.save(rescueService);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRescueServiceSearchRepository);

        int databaseSizeBeforeUpdate = rescueServiceRepository.findAll().size();

        // Update the rescueService
        RescueService updatedRescueService = rescueServiceRepository.findById(rescueService.getId()).get();
        // Disconnect from session so that the updates on updatedRescueService are not directly saved in db
        em.detach(updatedRescueService);
        updatedRescueService
            .description(UPDATED_DESCRIPTION)
            .noOfEmployees(UPDATED_NO_OF_EMPLOYEES);

        restRescueServiceMockMvc.perform(put("/api/rescue-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRescueService)))
            .andExpect(status().isOk());

        // Validate the RescueService in the database
        List<RescueService> rescueServiceList = rescueServiceRepository.findAll();
        assertThat(rescueServiceList).hasSize(databaseSizeBeforeUpdate);
        RescueService testRescueService = rescueServiceList.get(rescueServiceList.size() - 1);
        assertThat(testRescueService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRescueService.getNoOfEmployees()).isEqualTo(UPDATED_NO_OF_EMPLOYEES);

        // Validate the RescueService in Elasticsearch
        verify(mockRescueServiceSearchRepository, times(1)).save(testRescueService);
    }

    @Test
    @Transactional
    public void updateNonExistingRescueService() throws Exception {
        int databaseSizeBeforeUpdate = rescueServiceRepository.findAll().size();

        // Create the RescueService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRescueServiceMockMvc.perform(put("/api/rescue-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rescueService)))
            .andExpect(status().isBadRequest());

        // Validate the RescueService in the database
        List<RescueService> rescueServiceList = rescueServiceRepository.findAll();
        assertThat(rescueServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RescueService in Elasticsearch
        verify(mockRescueServiceSearchRepository, times(0)).save(rescueService);
    }

    @Test
    @Transactional
    public void deleteRescueService() throws Exception {
        // Initialize the database
        rescueServiceService.save(rescueService);

        int databaseSizeBeforeDelete = rescueServiceRepository.findAll().size();

        // Delete the rescueService
        restRescueServiceMockMvc.perform(delete("/api/rescue-services/{id}", rescueService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RescueService> rescueServiceList = rescueServiceRepository.findAll();
        assertThat(rescueServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RescueService in Elasticsearch
        verify(mockRescueServiceSearchRepository, times(1)).deleteById(rescueService.getId());
    }

    @Test
    @Transactional
    public void searchRescueService() throws Exception {
        // Initialize the database
        rescueServiceService.save(rescueService);
        when(mockRescueServiceSearchRepository.search(queryStringQuery("id:" + rescueService.getId())))
            .thenReturn(Collections.singletonList(rescueService));
        // Search the rescueService
        restRescueServiceMockMvc.perform(get("/api/_search/rescue-services?query=id:" + rescueService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rescueService.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].noOfEmployees").value(hasItem(DEFAULT_NO_OF_EMPLOYEES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RescueService.class);
        RescueService rescueService1 = new RescueService();
        rescueService1.setId(1L);
        RescueService rescueService2 = new RescueService();
        rescueService2.setId(rescueService1.getId());
        assertThat(rescueService1).isEqualTo(rescueService2);
        rescueService2.setId(2L);
        assertThat(rescueService1).isNotEqualTo(rescueService2);
        rescueService1.setId(null);
        assertThat(rescueService1).isNotEqualTo(rescueService2);
    }
}
