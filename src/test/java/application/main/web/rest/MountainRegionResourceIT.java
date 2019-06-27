package application.main.web.rest;

import application.main.MentorCarpatinApp;
import application.main.domain.MountainRegion;
import application.main.repository.MountainRegionRepository;
import application.main.repository.search.MountainRegionSearchRepository;
import application.main.service.MountainRegionService;
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
 * Integration tests for the {@Link MountainRegionResource} REST controller.
 */
@SpringBootTest(classes = MentorCarpatinApp.class)
public class MountainRegionResourceIT {

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SURFACE = 1;
    private static final Integer UPDATED_SURFACE = 2;

    private static final String DEFAULT_TOP_PEAK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOP_PEAK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOP_PEAK_HEIGHT = 1;
    private static final Integer UPDATED_TOP_PEAK_HEIGHT = 2;

    @Autowired
    private MountainRegionRepository mountainRegionRepository;

    @Autowired
    private MountainRegionService mountainRegionService;

    /**
     * This repository is mocked in the application.main.repository.search test package.
     *
     * @see application.main.repository.search.MountainRegionSearchRepositoryMockConfiguration
     */
    @Autowired
    private MountainRegionSearchRepository mockMountainRegionSearchRepository;

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

    private MockMvc restMountainRegionMockMvc;

    private MountainRegion mountainRegion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MountainRegionResource mountainRegionResource = new MountainRegionResource(mountainRegionService);
        this.restMountainRegionMockMvc = MockMvcBuilders.standaloneSetup(mountainRegionResource)
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
    public static MountainRegion createEntity(EntityManager em) {
        MountainRegion mountainRegion = new MountainRegion()
            .regionName(DEFAULT_REGION_NAME)
            .surface(DEFAULT_SURFACE)
            .topPeakName(DEFAULT_TOP_PEAK_NAME)
            .topPeakHeight(DEFAULT_TOP_PEAK_HEIGHT);
        return mountainRegion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MountainRegion createUpdatedEntity(EntityManager em) {
        MountainRegion mountainRegion = new MountainRegion()
            .regionName(UPDATED_REGION_NAME)
            .surface(UPDATED_SURFACE)
            .topPeakName(UPDATED_TOP_PEAK_NAME)
            .topPeakHeight(UPDATED_TOP_PEAK_HEIGHT);
        return mountainRegion;
    }

    @BeforeEach
    public void initTest() {
        mountainRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createMountainRegion() throws Exception {
        int databaseSizeBeforeCreate = mountainRegionRepository.findAll().size();

        // Create the MountainRegion
        restMountainRegionMockMvc.perform(post("/api/mountain-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mountainRegion)))
            .andExpect(status().isCreated());

        // Validate the MountainRegion in the database
        List<MountainRegion> mountainRegionList = mountainRegionRepository.findAll();
        assertThat(mountainRegionList).hasSize(databaseSizeBeforeCreate + 1);
        MountainRegion testMountainRegion = mountainRegionList.get(mountainRegionList.size() - 1);
        assertThat(testMountainRegion.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testMountainRegion.getSurface()).isEqualTo(DEFAULT_SURFACE);
        assertThat(testMountainRegion.getTopPeakName()).isEqualTo(DEFAULT_TOP_PEAK_NAME);
        assertThat(testMountainRegion.getTopPeakHeight()).isEqualTo(DEFAULT_TOP_PEAK_HEIGHT);

        // Validate the MountainRegion in Elasticsearch
        verify(mockMountainRegionSearchRepository, times(1)).save(testMountainRegion);
    }

    @Test
    @Transactional
    public void createMountainRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mountainRegionRepository.findAll().size();

        // Create the MountainRegion with an existing ID
        mountainRegion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMountainRegionMockMvc.perform(post("/api/mountain-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mountainRegion)))
            .andExpect(status().isBadRequest());

        // Validate the MountainRegion in the database
        List<MountainRegion> mountainRegionList = mountainRegionRepository.findAll();
        assertThat(mountainRegionList).hasSize(databaseSizeBeforeCreate);

        // Validate the MountainRegion in Elasticsearch
        verify(mockMountainRegionSearchRepository, times(0)).save(mountainRegion);
    }


    @Test
    @Transactional
    public void getAllMountainRegions() throws Exception {
        // Initialize the database
        mountainRegionRepository.saveAndFlush(mountainRegion);

        // Get all the mountainRegionList
        restMountainRegionMockMvc.perform(get("/api/mountain-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mountainRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME.toString())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE)))
            .andExpect(jsonPath("$.[*].topPeakName").value(hasItem(DEFAULT_TOP_PEAK_NAME.toString())))
            .andExpect(jsonPath("$.[*].topPeakHeight").value(hasItem(DEFAULT_TOP_PEAK_HEIGHT)));
    }
    
    @Test
    @Transactional
    public void getMountainRegion() throws Exception {
        // Initialize the database
        mountainRegionRepository.saveAndFlush(mountainRegion);

        // Get the mountainRegion
        restMountainRegionMockMvc.perform(get("/api/mountain-regions/{id}", mountainRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mountainRegion.getId().intValue()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME.toString()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE))
            .andExpect(jsonPath("$.topPeakName").value(DEFAULT_TOP_PEAK_NAME.toString()))
            .andExpect(jsonPath("$.topPeakHeight").value(DEFAULT_TOP_PEAK_HEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingMountainRegion() throws Exception {
        // Get the mountainRegion
        restMountainRegionMockMvc.perform(get("/api/mountain-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMountainRegion() throws Exception {
        // Initialize the database
        mountainRegionService.save(mountainRegion);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMountainRegionSearchRepository);

        int databaseSizeBeforeUpdate = mountainRegionRepository.findAll().size();

        // Update the mountainRegion
        MountainRegion updatedMountainRegion = mountainRegionRepository.findById(mountainRegion.getId()).get();
        // Disconnect from session so that the updates on updatedMountainRegion are not directly saved in db
        em.detach(updatedMountainRegion);
        updatedMountainRegion
            .regionName(UPDATED_REGION_NAME)
            .surface(UPDATED_SURFACE)
            .topPeakName(UPDATED_TOP_PEAK_NAME)
            .topPeakHeight(UPDATED_TOP_PEAK_HEIGHT);

        restMountainRegionMockMvc.perform(put("/api/mountain-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMountainRegion)))
            .andExpect(status().isOk());

        // Validate the MountainRegion in the database
        List<MountainRegion> mountainRegionList = mountainRegionRepository.findAll();
        assertThat(mountainRegionList).hasSize(databaseSizeBeforeUpdate);
        MountainRegion testMountainRegion = mountainRegionList.get(mountainRegionList.size() - 1);
        assertThat(testMountainRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testMountainRegion.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testMountainRegion.getTopPeakName()).isEqualTo(UPDATED_TOP_PEAK_NAME);
        assertThat(testMountainRegion.getTopPeakHeight()).isEqualTo(UPDATED_TOP_PEAK_HEIGHT);

        // Validate the MountainRegion in Elasticsearch
        verify(mockMountainRegionSearchRepository, times(1)).save(testMountainRegion);
    }

    @Test
    @Transactional
    public void updateNonExistingMountainRegion() throws Exception {
        int databaseSizeBeforeUpdate = mountainRegionRepository.findAll().size();

        // Create the MountainRegion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMountainRegionMockMvc.perform(put("/api/mountain-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mountainRegion)))
            .andExpect(status().isBadRequest());

        // Validate the MountainRegion in the database
        List<MountainRegion> mountainRegionList = mountainRegionRepository.findAll();
        assertThat(mountainRegionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MountainRegion in Elasticsearch
        verify(mockMountainRegionSearchRepository, times(0)).save(mountainRegion);
    }

    @Test
    @Transactional
    public void deleteMountainRegion() throws Exception {
        // Initialize the database
        mountainRegionService.save(mountainRegion);

        int databaseSizeBeforeDelete = mountainRegionRepository.findAll().size();

        // Delete the mountainRegion
        restMountainRegionMockMvc.perform(delete("/api/mountain-regions/{id}", mountainRegion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MountainRegion> mountainRegionList = mountainRegionRepository.findAll();
        assertThat(mountainRegionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MountainRegion in Elasticsearch
        verify(mockMountainRegionSearchRepository, times(1)).deleteById(mountainRegion.getId());
    }

    @Test
    @Transactional
    public void searchMountainRegion() throws Exception {
        // Initialize the database
        mountainRegionService.save(mountainRegion);
        when(mockMountainRegionSearchRepository.search(queryStringQuery("id:" + mountainRegion.getId())))
            .thenReturn(Collections.singletonList(mountainRegion));
        // Search the mountainRegion
        restMountainRegionMockMvc.perform(get("/api/_search/mountain-regions?query=id:" + mountainRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mountainRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE)))
            .andExpect(jsonPath("$.[*].topPeakName").value(hasItem(DEFAULT_TOP_PEAK_NAME)))
            .andExpect(jsonPath("$.[*].topPeakHeight").value(hasItem(DEFAULT_TOP_PEAK_HEIGHT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MountainRegion.class);
        MountainRegion mountainRegion1 = new MountainRegion();
        mountainRegion1.setId(1L);
        MountainRegion mountainRegion2 = new MountainRegion();
        mountainRegion2.setId(mountainRegion1.getId());
        assertThat(mountainRegion1).isEqualTo(mountainRegion2);
        mountainRegion2.setId(2L);
        assertThat(mountainRegion1).isNotEqualTo(mountainRegion2);
        mountainRegion1.setId(null);
        assertThat(mountainRegion1).isNotEqualTo(mountainRegion2);
    }
}
