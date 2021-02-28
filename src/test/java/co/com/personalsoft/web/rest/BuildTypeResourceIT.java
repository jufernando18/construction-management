package co.com.personalsoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.personalsoft.IntegrationTest;
import co.com.personalsoft.domain.BuildType;
import co.com.personalsoft.domain.Material;
import co.com.personalsoft.repository.BuildTypeRepository;
import co.com.personalsoft.service.BuildTypeQueryService;
import co.com.personalsoft.service.dto.BuildTypeCriteria;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.mapper.BuildTypeMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuildTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuildTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final Integer DEFAULT_AMOUNT_MATERIAL_1 = 0;
    private static final Integer UPDATED_AMOUNT_MATERIAL_1 = 1;
    private static final Integer SMALLER_AMOUNT_MATERIAL_1 = 0 - 1;

    private static final Integer DEFAULT_AMOUNT_MATERIAL_2 = 0;
    private static final Integer UPDATED_AMOUNT_MATERIAL_2 = 1;
    private static final Integer SMALLER_AMOUNT_MATERIAL_2 = 0 - 1;

    private static final Integer DEFAULT_AMOUNT_MATERIAL_3 = 0;
    private static final Integer UPDATED_AMOUNT_MATERIAL_3 = 1;
    private static final Integer SMALLER_AMOUNT_MATERIAL_3 = 0 - 1;

    private static final Integer DEFAULT_AMOUNT_MATERIAL_4 = 0;
    private static final Integer UPDATED_AMOUNT_MATERIAL_4 = 1;
    private static final Integer SMALLER_AMOUNT_MATERIAL_4 = 0 - 1;

    private static final Integer DEFAULT_AMOUNT_MATERIAL_5 = 0;
    private static final Integer UPDATED_AMOUNT_MATERIAL_5 = 1;
    private static final Integer SMALLER_AMOUNT_MATERIAL_5 = 0 - 1;

    @Autowired
    private BuildTypeRepository buildTypeRepository;

    @Autowired
    private BuildTypeMapper buildTypeMapper;

    @Autowired
    private BuildTypeQueryService buildTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildTypeMockMvc;

    private BuildType buildType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildType createEntity(EntityManager em) {
        BuildType buildType = new BuildType()
            .name(DEFAULT_NAME)
            .duration(DEFAULT_DURATION)
            .amountMaterial1(DEFAULT_AMOUNT_MATERIAL_1)
            .amountMaterial2(DEFAULT_AMOUNT_MATERIAL_2)
            .amountMaterial3(DEFAULT_AMOUNT_MATERIAL_3)
            .amountMaterial4(DEFAULT_AMOUNT_MATERIAL_4)
            .amountMaterial5(DEFAULT_AMOUNT_MATERIAL_5);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        buildType.setMaterial1(material);
        return buildType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildType createUpdatedEntity(EntityManager em) {
        BuildType buildType = new BuildType()
            .name(UPDATED_NAME)
            .duration(UPDATED_DURATION)
            .amountMaterial1(UPDATED_AMOUNT_MATERIAL_1)
            .amountMaterial2(UPDATED_AMOUNT_MATERIAL_2)
            .amountMaterial3(UPDATED_AMOUNT_MATERIAL_3)
            .amountMaterial4(UPDATED_AMOUNT_MATERIAL_4)
            .amountMaterial5(UPDATED_AMOUNT_MATERIAL_5);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        buildType.setMaterial1(material);
        return buildType;
    }

    @BeforeEach
    public void initTest() {
        buildType = createEntity(em);
    }

    @Test
    @Transactional
    void createBuildType() throws Exception {
        int databaseSizeBeforeCreate = buildTypeRepository.findAll().size();
        // Create the BuildType
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);
        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BuildType testBuildType = buildTypeList.get(buildTypeList.size() - 1);
        assertThat(testBuildType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuildType.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testBuildType.getAmountMaterial1()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_1);
        assertThat(testBuildType.getAmountMaterial2()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_2);
        assertThat(testBuildType.getAmountMaterial3()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_3);
        assertThat(testBuildType.getAmountMaterial4()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_4);
        assertThat(testBuildType.getAmountMaterial5()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void createBuildTypeWithExistingId() throws Exception {
        // Create the BuildType with an existing ID
        buildType.setId(1L);
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        int databaseSizeBeforeCreate = buildTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setName(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setDuration(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountMaterial1IsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setAmountMaterial1(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountMaterial2IsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setAmountMaterial2(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountMaterial3IsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setAmountMaterial3(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountMaterial4IsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setAmountMaterial4(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountMaterial5IsRequired() throws Exception {
        int databaseSizeBeforeTest = buildTypeRepository.findAll().size();
        // set the field null
        buildType.setAmountMaterial5(null);

        // Create the BuildType, which fails.
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        restBuildTypeMockMvc
            .perform(
                post("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuildTypes() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList
        restBuildTypeMockMvc
            .perform(get("/api/build-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].amountMaterial1").value(hasItem(DEFAULT_AMOUNT_MATERIAL_1)))
            .andExpect(jsonPath("$.[*].amountMaterial2").value(hasItem(DEFAULT_AMOUNT_MATERIAL_2)))
            .andExpect(jsonPath("$.[*].amountMaterial3").value(hasItem(DEFAULT_AMOUNT_MATERIAL_3)))
            .andExpect(jsonPath("$.[*].amountMaterial4").value(hasItem(DEFAULT_AMOUNT_MATERIAL_4)))
            .andExpect(jsonPath("$.[*].amountMaterial5").value(hasItem(DEFAULT_AMOUNT_MATERIAL_5)));
    }

    @Test
    @Transactional
    void getBuildType() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get the buildType
        restBuildTypeMockMvc
            .perform(get("/api/build-types/{id}", buildType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buildType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.amountMaterial1").value(DEFAULT_AMOUNT_MATERIAL_1))
            .andExpect(jsonPath("$.amountMaterial2").value(DEFAULT_AMOUNT_MATERIAL_2))
            .andExpect(jsonPath("$.amountMaterial3").value(DEFAULT_AMOUNT_MATERIAL_3))
            .andExpect(jsonPath("$.amountMaterial4").value(DEFAULT_AMOUNT_MATERIAL_4))
            .andExpect(jsonPath("$.amountMaterial5").value(DEFAULT_AMOUNT_MATERIAL_5));
    }

    @Test
    @Transactional
    void getBuildTypesByIdFiltering() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        Long id = buildType.getId();

        defaultBuildTypeShouldBeFound("id.equals=" + id);
        defaultBuildTypeShouldNotBeFound("id.notEquals=" + id);

        defaultBuildTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBuildTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultBuildTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBuildTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name equals to DEFAULT_NAME
        defaultBuildTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the buildTypeList where name equals to UPDATED_NAME
        defaultBuildTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name not equals to DEFAULT_NAME
        defaultBuildTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the buildTypeList where name not equals to UPDATED_NAME
        defaultBuildTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBuildTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the buildTypeList where name equals to UPDATED_NAME
        defaultBuildTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name is not null
        defaultBuildTypeShouldBeFound("name.specified=true");

        // Get all the buildTypeList where name is null
        defaultBuildTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name contains DEFAULT_NAME
        defaultBuildTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the buildTypeList where name contains UPDATED_NAME
        defaultBuildTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where name does not contain DEFAULT_NAME
        defaultBuildTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the buildTypeList where name does not contain UPDATED_NAME
        defaultBuildTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration equals to DEFAULT_DURATION
        defaultBuildTypeShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration equals to UPDATED_DURATION
        defaultBuildTypeShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration not equals to DEFAULT_DURATION
        defaultBuildTypeShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration not equals to UPDATED_DURATION
        defaultBuildTypeShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultBuildTypeShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the buildTypeList where duration equals to UPDATED_DURATION
        defaultBuildTypeShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration is not null
        defaultBuildTypeShouldBeFound("duration.specified=true");

        // Get all the buildTypeList where duration is null
        defaultBuildTypeShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration is greater than or equal to DEFAULT_DURATION
        defaultBuildTypeShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration is greater than or equal to UPDATED_DURATION
        defaultBuildTypeShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration is less than or equal to DEFAULT_DURATION
        defaultBuildTypeShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration is less than or equal to SMALLER_DURATION
        defaultBuildTypeShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration is less than DEFAULT_DURATION
        defaultBuildTypeShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration is less than UPDATED_DURATION
        defaultBuildTypeShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where duration is greater than DEFAULT_DURATION
        defaultBuildTypeShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the buildTypeList where duration is greater than SMALLER_DURATION
        defaultBuildTypeShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 equals to DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.equals=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 equals to UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.equals=" + UPDATED_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 not equals to DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.notEquals=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 not equals to UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.notEquals=" + UPDATED_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 in DEFAULT_AMOUNT_MATERIAL_1 or UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.in=" + DEFAULT_AMOUNT_MATERIAL_1 + "," + UPDATED_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 equals to UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.in=" + UPDATED_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 is not null
        defaultBuildTypeShouldBeFound("amountMaterial1.specified=true");

        // Get all the buildTypeList where amountMaterial1 is null
        defaultBuildTypeShouldNotBeFound("amountMaterial1.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 is greater than or equal to DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.greaterThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 is greater than or equal to UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.greaterThanOrEqual=" + UPDATED_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 is less than or equal to DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.lessThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 is less than or equal to SMALLER_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.lessThanOrEqual=" + SMALLER_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 is less than DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.lessThan=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 is less than UPDATED_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.lessThan=" + UPDATED_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial1 is greater than DEFAULT_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldNotBeFound("amountMaterial1.greaterThan=" + DEFAULT_AMOUNT_MATERIAL_1);

        // Get all the buildTypeList where amountMaterial1 is greater than SMALLER_AMOUNT_MATERIAL_1
        defaultBuildTypeShouldBeFound("amountMaterial1.greaterThan=" + SMALLER_AMOUNT_MATERIAL_1);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 equals to DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.equals=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 equals to UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.equals=" + UPDATED_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 not equals to DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.notEquals=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 not equals to UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.notEquals=" + UPDATED_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 in DEFAULT_AMOUNT_MATERIAL_2 or UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.in=" + DEFAULT_AMOUNT_MATERIAL_2 + "," + UPDATED_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 equals to UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.in=" + UPDATED_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 is not null
        defaultBuildTypeShouldBeFound("amountMaterial2.specified=true");

        // Get all the buildTypeList where amountMaterial2 is null
        defaultBuildTypeShouldNotBeFound("amountMaterial2.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 is greater than or equal to DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.greaterThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 is greater than or equal to UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.greaterThanOrEqual=" + UPDATED_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 is less than or equal to DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.lessThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 is less than or equal to SMALLER_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.lessThanOrEqual=" + SMALLER_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 is less than DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.lessThan=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 is less than UPDATED_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.lessThan=" + UPDATED_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial2 is greater than DEFAULT_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldNotBeFound("amountMaterial2.greaterThan=" + DEFAULT_AMOUNT_MATERIAL_2);

        // Get all the buildTypeList where amountMaterial2 is greater than SMALLER_AMOUNT_MATERIAL_2
        defaultBuildTypeShouldBeFound("amountMaterial2.greaterThan=" + SMALLER_AMOUNT_MATERIAL_2);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 equals to DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.equals=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 equals to UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.equals=" + UPDATED_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 not equals to DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.notEquals=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 not equals to UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.notEquals=" + UPDATED_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 in DEFAULT_AMOUNT_MATERIAL_3 or UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.in=" + DEFAULT_AMOUNT_MATERIAL_3 + "," + UPDATED_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 equals to UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.in=" + UPDATED_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 is not null
        defaultBuildTypeShouldBeFound("amountMaterial3.specified=true");

        // Get all the buildTypeList where amountMaterial3 is null
        defaultBuildTypeShouldNotBeFound("amountMaterial3.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 is greater than or equal to DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.greaterThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 is greater than or equal to UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.greaterThanOrEqual=" + UPDATED_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 is less than or equal to DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.lessThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 is less than or equal to SMALLER_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.lessThanOrEqual=" + SMALLER_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 is less than DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.lessThan=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 is less than UPDATED_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.lessThan=" + UPDATED_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial3 is greater than DEFAULT_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldNotBeFound("amountMaterial3.greaterThan=" + DEFAULT_AMOUNT_MATERIAL_3);

        // Get all the buildTypeList where amountMaterial3 is greater than SMALLER_AMOUNT_MATERIAL_3
        defaultBuildTypeShouldBeFound("amountMaterial3.greaterThan=" + SMALLER_AMOUNT_MATERIAL_3);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 equals to DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.equals=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 equals to UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.equals=" + UPDATED_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 not equals to DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.notEquals=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 not equals to UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.notEquals=" + UPDATED_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 in DEFAULT_AMOUNT_MATERIAL_4 or UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.in=" + DEFAULT_AMOUNT_MATERIAL_4 + "," + UPDATED_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 equals to UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.in=" + UPDATED_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 is not null
        defaultBuildTypeShouldBeFound("amountMaterial4.specified=true");

        // Get all the buildTypeList where amountMaterial4 is null
        defaultBuildTypeShouldNotBeFound("amountMaterial4.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 is greater than or equal to DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.greaterThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 is greater than or equal to UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.greaterThanOrEqual=" + UPDATED_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 is less than or equal to DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.lessThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 is less than or equal to SMALLER_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.lessThanOrEqual=" + SMALLER_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 is less than DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.lessThan=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 is less than UPDATED_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.lessThan=" + UPDATED_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial4 is greater than DEFAULT_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldNotBeFound("amountMaterial4.greaterThan=" + DEFAULT_AMOUNT_MATERIAL_4);

        // Get all the buildTypeList where amountMaterial4 is greater than SMALLER_AMOUNT_MATERIAL_4
        defaultBuildTypeShouldBeFound("amountMaterial4.greaterThan=" + SMALLER_AMOUNT_MATERIAL_4);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 equals to DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.equals=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 equals to UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.equals=" + UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 not equals to DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.notEquals=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 not equals to UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.notEquals=" + UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsInShouldWork() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 in DEFAULT_AMOUNT_MATERIAL_5 or UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.in=" + DEFAULT_AMOUNT_MATERIAL_5 + "," + UPDATED_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 equals to UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.in=" + UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsNullOrNotNull() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 is not null
        defaultBuildTypeShouldBeFound("amountMaterial5.specified=true");

        // Get all the buildTypeList where amountMaterial5 is null
        defaultBuildTypeShouldNotBeFound("amountMaterial5.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 is greater than or equal to DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.greaterThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 is greater than or equal to UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.greaterThanOrEqual=" + UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 is less than or equal to DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.lessThanOrEqual=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 is less than or equal to SMALLER_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.lessThanOrEqual=" + SMALLER_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsLessThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 is less than DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.lessThan=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 is less than UPDATED_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.lessThan=" + UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByAmountMaterial5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        // Get all the buildTypeList where amountMaterial5 is greater than DEFAULT_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldNotBeFound("amountMaterial5.greaterThan=" + DEFAULT_AMOUNT_MATERIAL_5);

        // Get all the buildTypeList where amountMaterial5 is greater than SMALLER_AMOUNT_MATERIAL_5
        defaultBuildTypeShouldBeFound("amountMaterial5.greaterThan=" + SMALLER_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void getAllBuildTypesByMaterial1IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);
        Material material1 = MaterialResourceIT.createEntity(em);
        em.persist(material1);
        em.flush();
        buildType.setMaterial1(material1);
        buildTypeRepository.saveAndFlush(buildType);
        Long material1Id = material1.getId();

        // Get all the buildTypeList where material1 equals to material1Id
        defaultBuildTypeShouldBeFound("material1Id.equals=" + material1Id);

        // Get all the buildTypeList where material1 equals to material1Id + 1
        defaultBuildTypeShouldNotBeFound("material1Id.equals=" + (material1Id + 1));
    }

    @Test
    @Transactional
    void getAllBuildTypesByMaterial2IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);
        Material material2 = MaterialResourceIT.createEntity(em);
        em.persist(material2);
        em.flush();
        buildType.setMaterial2(material2);
        buildTypeRepository.saveAndFlush(buildType);
        Long material2Id = material2.getId();

        // Get all the buildTypeList where material2 equals to material2Id
        defaultBuildTypeShouldBeFound("material2Id.equals=" + material2Id);

        // Get all the buildTypeList where material2 equals to material2Id + 1
        defaultBuildTypeShouldNotBeFound("material2Id.equals=" + (material2Id + 1));
    }

    @Test
    @Transactional
    void getAllBuildTypesByMaterial3IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);
        Material material3 = MaterialResourceIT.createEntity(em);
        em.persist(material3);
        em.flush();
        buildType.setMaterial3(material3);
        buildTypeRepository.saveAndFlush(buildType);
        Long material3Id = material3.getId();

        // Get all the buildTypeList where material3 equals to material3Id
        defaultBuildTypeShouldBeFound("material3Id.equals=" + material3Id);

        // Get all the buildTypeList where material3 equals to material3Id + 1
        defaultBuildTypeShouldNotBeFound("material3Id.equals=" + (material3Id + 1));
    }

    @Test
    @Transactional
    void getAllBuildTypesByMaterial4IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);
        Material material4 = MaterialResourceIT.createEntity(em);
        em.persist(material4);
        em.flush();
        buildType.setMaterial4(material4);
        buildTypeRepository.saveAndFlush(buildType);
        Long material4Id = material4.getId();

        // Get all the buildTypeList where material4 equals to material4Id
        defaultBuildTypeShouldBeFound("material4Id.equals=" + material4Id);

        // Get all the buildTypeList where material4 equals to material4Id + 1
        defaultBuildTypeShouldNotBeFound("material4Id.equals=" + (material4Id + 1));
    }

    @Test
    @Transactional
    void getAllBuildTypesByMaterial5IsEqualToSomething() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);
        Material material5 = MaterialResourceIT.createEntity(em);
        em.persist(material5);
        em.flush();
        buildType.setMaterial5(material5);
        buildTypeRepository.saveAndFlush(buildType);
        Long material5Id = material5.getId();

        // Get all the buildTypeList where material5 equals to material5Id
        defaultBuildTypeShouldBeFound("material5Id.equals=" + material5Id);

        // Get all the buildTypeList where material5 equals to material5Id + 1
        defaultBuildTypeShouldNotBeFound("material5Id.equals=" + (material5Id + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuildTypeShouldBeFound(String filter) throws Exception {
        restBuildTypeMockMvc
            .perform(get("/api/build-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].amountMaterial1").value(hasItem(DEFAULT_AMOUNT_MATERIAL_1)))
            .andExpect(jsonPath("$.[*].amountMaterial2").value(hasItem(DEFAULT_AMOUNT_MATERIAL_2)))
            .andExpect(jsonPath("$.[*].amountMaterial3").value(hasItem(DEFAULT_AMOUNT_MATERIAL_3)))
            .andExpect(jsonPath("$.[*].amountMaterial4").value(hasItem(DEFAULT_AMOUNT_MATERIAL_4)))
            .andExpect(jsonPath("$.[*].amountMaterial5").value(hasItem(DEFAULT_AMOUNT_MATERIAL_5)));

        // Check, that the count call also returns 1
        restBuildTypeMockMvc
            .perform(get("/api/build-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuildTypeShouldNotBeFound(String filter) throws Exception {
        restBuildTypeMockMvc
            .perform(get("/api/build-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuildTypeMockMvc
            .perform(get("/api/build-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBuildType() throws Exception {
        // Get the buildType
        restBuildTypeMockMvc.perform(get("/api/build-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateBuildType() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        int databaseSizeBeforeUpdate = buildTypeRepository.findAll().size();

        // Update the buildType
        BuildType updatedBuildType = buildTypeRepository.findById(buildType.getId()).get();
        // Disconnect from session so that the updates on updatedBuildType are not directly saved in db
        em.detach(updatedBuildType);
        updatedBuildType
            .name(UPDATED_NAME)
            .duration(UPDATED_DURATION)
            .amountMaterial1(UPDATED_AMOUNT_MATERIAL_1)
            .amountMaterial2(UPDATED_AMOUNT_MATERIAL_2)
            .amountMaterial3(UPDATED_AMOUNT_MATERIAL_3)
            .amountMaterial4(UPDATED_AMOUNT_MATERIAL_4)
            .amountMaterial5(UPDATED_AMOUNT_MATERIAL_5);
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(updatedBuildType);

        restBuildTypeMockMvc
            .perform(
                put("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildType testBuildType = buildTypeList.get(buildTypeList.size() - 1);
        assertThat(testBuildType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuildType.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testBuildType.getAmountMaterial1()).isEqualTo(UPDATED_AMOUNT_MATERIAL_1);
        assertThat(testBuildType.getAmountMaterial2()).isEqualTo(UPDATED_AMOUNT_MATERIAL_2);
        assertThat(testBuildType.getAmountMaterial3()).isEqualTo(UPDATED_AMOUNT_MATERIAL_3);
        assertThat(testBuildType.getAmountMaterial4()).isEqualTo(UPDATED_AMOUNT_MATERIAL_4);
        assertThat(testBuildType.getAmountMaterial5()).isEqualTo(UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void updateNonExistingBuildType() throws Exception {
        int databaseSizeBeforeUpdate = buildTypeRepository.findAll().size();

        // Create the BuildType
        BuildTypeDTO buildTypeDTO = buildTypeMapper.toDto(buildType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildTypeMockMvc
            .perform(
                put("/api/build-types").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuildTypeWithPatch() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        int databaseSizeBeforeUpdate = buildTypeRepository.findAll().size();

        // Update the buildType using partial update
        BuildType partialUpdatedBuildType = new BuildType();
        partialUpdatedBuildType.setId(buildType.getId());

        partialUpdatedBuildType
            .name(UPDATED_NAME)
            .duration(UPDATED_DURATION)
            .amountMaterial2(UPDATED_AMOUNT_MATERIAL_2)
            .amountMaterial3(UPDATED_AMOUNT_MATERIAL_3)
            .amountMaterial4(UPDATED_AMOUNT_MATERIAL_4);

        restBuildTypeMockMvc
            .perform(
                patch("/api/build-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildType))
            )
            .andExpect(status().isOk());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildType testBuildType = buildTypeList.get(buildTypeList.size() - 1);
        assertThat(testBuildType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuildType.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testBuildType.getAmountMaterial1()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_1);
        assertThat(testBuildType.getAmountMaterial2()).isEqualTo(UPDATED_AMOUNT_MATERIAL_2);
        assertThat(testBuildType.getAmountMaterial3()).isEqualTo(UPDATED_AMOUNT_MATERIAL_3);
        assertThat(testBuildType.getAmountMaterial4()).isEqualTo(UPDATED_AMOUNT_MATERIAL_4);
        assertThat(testBuildType.getAmountMaterial5()).isEqualTo(DEFAULT_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void fullUpdateBuildTypeWithPatch() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        int databaseSizeBeforeUpdate = buildTypeRepository.findAll().size();

        // Update the buildType using partial update
        BuildType partialUpdatedBuildType = new BuildType();
        partialUpdatedBuildType.setId(buildType.getId());

        partialUpdatedBuildType
            .name(UPDATED_NAME)
            .duration(UPDATED_DURATION)
            .amountMaterial1(UPDATED_AMOUNT_MATERIAL_1)
            .amountMaterial2(UPDATED_AMOUNT_MATERIAL_2)
            .amountMaterial3(UPDATED_AMOUNT_MATERIAL_3)
            .amountMaterial4(UPDATED_AMOUNT_MATERIAL_4)
            .amountMaterial5(UPDATED_AMOUNT_MATERIAL_5);

        restBuildTypeMockMvc
            .perform(
                patch("/api/build-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildType))
            )
            .andExpect(status().isOk());

        // Validate the BuildType in the database
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildType testBuildType = buildTypeList.get(buildTypeList.size() - 1);
        assertThat(testBuildType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuildType.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testBuildType.getAmountMaterial1()).isEqualTo(UPDATED_AMOUNT_MATERIAL_1);
        assertThat(testBuildType.getAmountMaterial2()).isEqualTo(UPDATED_AMOUNT_MATERIAL_2);
        assertThat(testBuildType.getAmountMaterial3()).isEqualTo(UPDATED_AMOUNT_MATERIAL_3);
        assertThat(testBuildType.getAmountMaterial4()).isEqualTo(UPDATED_AMOUNT_MATERIAL_4);
        assertThat(testBuildType.getAmountMaterial5()).isEqualTo(UPDATED_AMOUNT_MATERIAL_5);
    }

    @Test
    @Transactional
    void partialUpdateBuildTypeShouldThrown() throws Exception {
        // Update the buildType without id should throw
        BuildType partialUpdatedBuildType = new BuildType();

        restBuildTypeMockMvc
            .perform(
                patch("/api/build-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildType))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteBuildType() throws Exception {
        // Initialize the database
        buildTypeRepository.saveAndFlush(buildType);

        int databaseSizeBeforeDelete = buildTypeRepository.findAll().size();

        // Delete the buildType
        restBuildTypeMockMvc
            .perform(delete("/api/build-types/{id}", buildType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuildType> buildTypeList = buildTypeRepository.findAll();
        assertThat(buildTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
