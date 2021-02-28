package co.com.personalsoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.personalsoft.IntegrationTest;
import co.com.personalsoft.domain.BuildType;
import co.com.personalsoft.domain.Citadel;
import co.com.personalsoft.domain.Requisition;
import co.com.personalsoft.domain.enumeration.RequisitionState;
import co.com.personalsoft.repository.RequisitionRepository;
import co.com.personalsoft.service.RequisitionQueryService;
import co.com.personalsoft.service.dto.RequisitionCriteria;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.service.mapper.RequisitionMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RequisitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequisitionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATE = "1,7";
    private static final String UPDATED_COORDINATE = "77,8";

    private static final RequisitionState DEFAULT_STATE = RequisitionState.FAILED;
    private static final RequisitionState UPDATED_STATE = RequisitionState.APPROVED;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private RequisitionRepository requisitionRepository;

    @Autowired
    private RequisitionMapper requisitionMapper;

    @Autowired
    private RequisitionQueryService requisitionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequisitionMockMvc;

    private Requisition requisition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisition createEntity(EntityManager em) {
        Requisition requisition = new Requisition()
            .name(DEFAULT_NAME)
            .coordinate(DEFAULT_COORDINATE)
            .state(DEFAULT_STATE)
            .date(DEFAULT_DATE);
        // Add required entity
        BuildType buildType;
        if (TestUtil.findAll(em, BuildType.class).isEmpty()) {
            buildType = BuildTypeResourceIT.createEntity(em);
            em.persist(buildType);
            em.flush();
        } else {
            buildType = TestUtil.findAll(em, BuildType.class).get(0);
        }
        requisition.setBuildType(buildType);
        // Add required entity
        Citadel citadel;
        if (TestUtil.findAll(em, Citadel.class).isEmpty()) {
            citadel = CitadelResourceIT.createEntity(em);
            em.persist(citadel);
            em.flush();
        } else {
            citadel = TestUtil.findAll(em, Citadel.class).get(0);
        }
        requisition.setCitadel(citadel);
        return requisition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisition createUpdatedEntity(EntityManager em) {
        Requisition requisition = new Requisition()
            .name(UPDATED_NAME)
            .coordinate(UPDATED_COORDINATE)
            .state(UPDATED_STATE)
            .date(UPDATED_DATE);
        // Add required entity
        BuildType buildType;
        if (TestUtil.findAll(em, BuildType.class).isEmpty()) {
            buildType = BuildTypeResourceIT.createUpdatedEntity(em);
            em.persist(buildType);
            em.flush();
        } else {
            buildType = TestUtil.findAll(em, BuildType.class).get(0);
        }
        requisition.setBuildType(buildType);
        // Add required entity
        Citadel citadel;
        if (TestUtil.findAll(em, Citadel.class).isEmpty()) {
            citadel = CitadelResourceIT.createUpdatedEntity(em);
            em.persist(citadel);
            em.flush();
        } else {
            citadel = TestUtil.findAll(em, Citadel.class).get(0);
        }
        requisition.setCitadel(citadel);
        return requisition;
    }

    @BeforeEach
    public void initTest() {
        requisition = createEntity(em);
    }

    @Test
    @Transactional
    void createRequisition() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();
        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);
        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate + 1);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequisition.getCoordinate()).isEqualTo(DEFAULT_COORDINATE);
        assertThat(testRequisition.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testRequisition.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createRequisitionWithExistingId() throws Exception {
        // Create the Requisition with an existing ID
        requisition.setId(1L);
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setName(null);

        // Create the Requisition, which fails.
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoordinateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setCoordinate(null);

        // Create the Requisition, which fails.
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setState(null);

        // Create the Requisition, which fails.
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setDate(null);

        // Create the Requisition, which fails.
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        restRequisitionMockMvc
            .perform(
                post("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequisitions() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList
        restRequisitionMockMvc
            .perform(get("/api/requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].coordinate").value(hasItem(DEFAULT_COORDINATE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get the requisition
        restRequisitionMockMvc
            .perform(get("/api/requisitions/{id}", requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requisition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.coordinate").value(DEFAULT_COORDINATE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getRequisitionsByIdFiltering() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        Long id = requisition.getId();

        defaultRequisitionShouldBeFound("id.equals=" + id);
        defaultRequisitionShouldNotBeFound("id.notEquals=" + id);

        defaultRequisitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRequisitionShouldNotBeFound("id.greaterThan=" + id);

        defaultRequisitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRequisitionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name equals to DEFAULT_NAME
        defaultRequisitionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the requisitionList where name equals to UPDATED_NAME
        defaultRequisitionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name not equals to DEFAULT_NAME
        defaultRequisitionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the requisitionList where name not equals to UPDATED_NAME
        defaultRequisitionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRequisitionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the requisitionList where name equals to UPDATED_NAME
        defaultRequisitionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name is not null
        defaultRequisitionShouldBeFound("name.specified=true");

        // Get all the requisitionList where name is null
        defaultRequisitionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameContainsSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name contains DEFAULT_NAME
        defaultRequisitionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the requisitionList where name contains UPDATED_NAME
        defaultRequisitionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRequisitionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where name does not contain DEFAULT_NAME
        defaultRequisitionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the requisitionList where name does not contain UPDATED_NAME
        defaultRequisitionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate equals to DEFAULT_COORDINATE
        defaultRequisitionShouldBeFound("coordinate.equals=" + DEFAULT_COORDINATE);

        // Get all the requisitionList where coordinate equals to UPDATED_COORDINATE
        defaultRequisitionShouldNotBeFound("coordinate.equals=" + UPDATED_COORDINATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate not equals to DEFAULT_COORDINATE
        defaultRequisitionShouldNotBeFound("coordinate.notEquals=" + DEFAULT_COORDINATE);

        // Get all the requisitionList where coordinate not equals to UPDATED_COORDINATE
        defaultRequisitionShouldBeFound("coordinate.notEquals=" + UPDATED_COORDINATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate in DEFAULT_COORDINATE or UPDATED_COORDINATE
        defaultRequisitionShouldBeFound("coordinate.in=" + DEFAULT_COORDINATE + "," + UPDATED_COORDINATE);

        // Get all the requisitionList where coordinate equals to UPDATED_COORDINATE
        defaultRequisitionShouldNotBeFound("coordinate.in=" + UPDATED_COORDINATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate is not null
        defaultRequisitionShouldBeFound("coordinate.specified=true");

        // Get all the requisitionList where coordinate is null
        defaultRequisitionShouldNotBeFound("coordinate.specified=false");
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateContainsSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate contains DEFAULT_COORDINATE
        defaultRequisitionShouldBeFound("coordinate.contains=" + DEFAULT_COORDINATE);

        // Get all the requisitionList where coordinate contains UPDATED_COORDINATE
        defaultRequisitionShouldNotBeFound("coordinate.contains=" + UPDATED_COORDINATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByCoordinateNotContainsSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where coordinate does not contain DEFAULT_COORDINATE
        defaultRequisitionShouldNotBeFound("coordinate.doesNotContain=" + DEFAULT_COORDINATE);

        // Get all the requisitionList where coordinate does not contain UPDATED_COORDINATE
        defaultRequisitionShouldBeFound("coordinate.doesNotContain=" + UPDATED_COORDINATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where state equals to DEFAULT_STATE
        defaultRequisitionShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the requisitionList where state equals to UPDATED_STATE
        defaultRequisitionShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where state not equals to DEFAULT_STATE
        defaultRequisitionShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the requisitionList where state not equals to UPDATED_STATE
        defaultRequisitionShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where state in DEFAULT_STATE or UPDATED_STATE
        defaultRequisitionShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the requisitionList where state equals to UPDATED_STATE
        defaultRequisitionShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where state is not null
        defaultRequisitionShouldBeFound("state.specified=true");

        // Get all the requisitionList where state is null
        defaultRequisitionShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date equals to DEFAULT_DATE
        defaultRequisitionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the requisitionList where date equals to UPDATED_DATE
        defaultRequisitionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date not equals to DEFAULT_DATE
        defaultRequisitionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the requisitionList where date not equals to UPDATED_DATE
        defaultRequisitionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultRequisitionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the requisitionList where date equals to UPDATED_DATE
        defaultRequisitionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date is not null
        defaultRequisitionShouldBeFound("date.specified=true");

        // Get all the requisitionList where date is null
        defaultRequisitionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date is greater than or equal to DEFAULT_DATE
        defaultRequisitionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the requisitionList where date is greater than or equal to UPDATED_DATE
        defaultRequisitionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date is less than or equal to DEFAULT_DATE
        defaultRequisitionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the requisitionList where date is less than or equal to SMALLER_DATE
        defaultRequisitionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date is less than DEFAULT_DATE
        defaultRequisitionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the requisitionList where date is less than UPDATED_DATE
        defaultRequisitionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where date is greater than DEFAULT_DATE
        defaultRequisitionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the requisitionList where date is greater than SMALLER_DATE
        defaultRequisitionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRequisitionsByBuildTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        BuildType buildType = BuildTypeResourceIT.createEntity(em);
        em.persist(buildType);
        em.flush();
        requisition.setBuildType(buildType);
        requisitionRepository.saveAndFlush(requisition);
        Long buildTypeId = buildType.getId();

        // Get all the requisitionList where buildType equals to buildTypeId
        defaultRequisitionShouldBeFound("buildTypeId.equals=" + buildTypeId);

        // Get all the requisitionList where buildType equals to buildTypeId + 1
        defaultRequisitionShouldNotBeFound("buildTypeId.equals=" + (buildTypeId + 1));
    }

    @Test
    @Transactional
    void getAllRequisitionsByCitadelIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        Citadel citadel = CitadelResourceIT.createEntity(em);
        em.persist(citadel);
        em.flush();
        requisition.setCitadel(citadel);
        requisitionRepository.saveAndFlush(requisition);
        Long citadelId = citadel.getId();

        // Get all the requisitionList where citadel equals to citadelId
        defaultRequisitionShouldBeFound("citadelId.equals=" + citadelId);

        // Get all the requisitionList where citadel equals to citadelId + 1
        defaultRequisitionShouldNotBeFound("citadelId.equals=" + (citadelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRequisitionShouldBeFound(String filter) throws Exception {
        restRequisitionMockMvc
            .perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].coordinate").value(hasItem(DEFAULT_COORDINATE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restRequisitionMockMvc
            .perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRequisitionShouldNotBeFound(String filter) throws Exception {
        restRequisitionMockMvc
            .perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionMockMvc
            .perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRequisition() throws Exception {
        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition
        Requisition updatedRequisition = requisitionRepository.findById(requisition.getId()).get();
        // Disconnect from session so that the updates on updatedRequisition are not directly saved in db
        em.detach(updatedRequisition);
        updatedRequisition.name(UPDATED_NAME).coordinate(UPDATED_COORDINATE).state(UPDATED_STATE).date(UPDATED_DATE);
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(updatedRequisition);

        restRequisitionMockMvc
            .perform(
                put("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequisition.getCoordinate()).isEqualTo(UPDATED_COORDINATE);
        assertThat(testRequisition.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testRequisition.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void updateNonExistingRequisition() throws Exception {
        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionMockMvc
            .perform(
                put("/api/requisitions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequisitionWithPatch() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition using partial update
        Requisition partialUpdatedRequisition = new Requisition();
        partialUpdatedRequisition.setId(requisition.getId());

        partialUpdatedRequisition.state(UPDATED_STATE).date(UPDATED_DATE);

        restRequisitionMockMvc
            .perform(
                patch("/api/requisitions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequisition))
            )
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRequisition.getCoordinate()).isEqualTo(DEFAULT_COORDINATE);
        assertThat(testRequisition.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testRequisition.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRequisitionWithPatch() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition using partial update
        Requisition partialUpdatedRequisition = new Requisition();
        partialUpdatedRequisition.setId(requisition.getId());

        partialUpdatedRequisition.name(UPDATED_NAME).coordinate(UPDATED_COORDINATE).state(UPDATED_STATE).date(UPDATED_DATE);

        restRequisitionMockMvc
            .perform(
                patch("/api/requisitions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequisition))
            )
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRequisition.getCoordinate()).isEqualTo(UPDATED_COORDINATE);
        assertThat(testRequisition.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testRequisition.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void partialUpdateRequisitionShouldThrown() throws Exception {
        // Update the requisition without id should throw
        Requisition partialUpdatedRequisition = new Requisition();

        restRequisitionMockMvc
            .perform(
                patch("/api/requisitions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequisition))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeDelete = requisitionRepository.findAll().size();

        // Delete the requisition
        restRequisitionMockMvc
            .perform(delete("/api/requisitions/{id}", requisition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
