package co.com.personalsoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.personalsoft.IntegrationTest;
import co.com.personalsoft.domain.Citadel;
import co.com.personalsoft.repository.CitadelRepository;
import co.com.personalsoft.service.CitadelQueryService;
import co.com.personalsoft.service.dto.CitadelCriteria;
import co.com.personalsoft.service.dto.CitadelDTO;
import co.com.personalsoft.service.mapper.CitadelMapper;
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
 * Integration tests for the {@link CitadelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitadelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FINISH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINISH = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CitadelRepository citadelRepository;

    @Autowired
    private CitadelMapper citadelMapper;

    @Autowired
    private CitadelQueryService citadelQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitadelMockMvc;

    private Citadel citadel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citadel createEntity(EntityManager em) {
        Citadel citadel = new Citadel().name(DEFAULT_NAME).address(DEFAULT_ADDRESS).start(DEFAULT_START).finish(DEFAULT_FINISH);
        return citadel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citadel createUpdatedEntity(EntityManager em) {
        Citadel citadel = new Citadel().name(UPDATED_NAME).address(UPDATED_ADDRESS).start(UPDATED_START).finish(UPDATED_FINISH);
        return citadel;
    }

    @BeforeEach
    public void initTest() {
        citadel = createEntity(em);
    }

    @Test
    @Transactional
    void createCitadel() throws Exception {
        int databaseSizeBeforeCreate = citadelRepository.findAll().size();
        // Create the Citadel
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);
        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isCreated());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeCreate + 1);
        Citadel testCitadel = citadelList.get(citadelList.size() - 1);
        assertThat(testCitadel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCitadel.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCitadel.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCitadel.getFinish()).isEqualTo(DEFAULT_FINISH);
    }

    @Test
    @Transactional
    void createCitadelWithExistingId() throws Exception {
        // Create the Citadel with an existing ID
        citadel.setId(1L);
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        int databaseSizeBeforeCreate = citadelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citadelRepository.findAll().size();
        // set the field null
        citadel.setName(null);

        // Create the Citadel, which fails.
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = citadelRepository.findAll().size();
        // set the field null
        citadel.setAddress(null);

        // Create the Citadel, which fails.
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = citadelRepository.findAll().size();
        // set the field null
        citadel.setStart(null);

        // Create the Citadel, which fails.
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFinishIsRequired() throws Exception {
        int databaseSizeBeforeTest = citadelRepository.findAll().size();
        // set the field null
        citadel.setFinish(null);

        // Create the Citadel, which fails.
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        restCitadelMockMvc
            .perform(post("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitadels() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList
        restCitadelMockMvc
            .perform(get("/api/citadels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citadel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH.toString())));
    }

    @Test
    @Transactional
    void getCitadel() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get the citadel
        restCitadelMockMvc
            .perform(get("/api/citadels/{id}", citadel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citadel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.finish").value(DEFAULT_FINISH.toString()));
    }

    @Test
    @Transactional
    void getCitadelsByIdFiltering() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        Long id = citadel.getId();

        defaultCitadelShouldBeFound("id.equals=" + id);
        defaultCitadelShouldNotBeFound("id.notEquals=" + id);

        defaultCitadelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitadelShouldNotBeFound("id.greaterThan=" + id);

        defaultCitadelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitadelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitadelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name equals to DEFAULT_NAME
        defaultCitadelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citadelList where name equals to UPDATED_NAME
        defaultCitadelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitadelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name not equals to DEFAULT_NAME
        defaultCitadelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citadelList where name not equals to UPDATED_NAME
        defaultCitadelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitadelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitadelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citadelList where name equals to UPDATED_NAME
        defaultCitadelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitadelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name is not null
        defaultCitadelShouldBeFound("name.specified=true");

        // Get all the citadelList where name is null
        defaultCitadelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCitadelsByNameContainsSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name contains DEFAULT_NAME
        defaultCitadelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citadelList where name contains UPDATED_NAME
        defaultCitadelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitadelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where name does not contain DEFAULT_NAME
        defaultCitadelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citadelList where name does not contain UPDATED_NAME
        defaultCitadelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address equals to DEFAULT_ADDRESS
        defaultCitadelShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the citadelList where address equals to UPDATED_ADDRESS
        defaultCitadelShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address not equals to DEFAULT_ADDRESS
        defaultCitadelShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the citadelList where address not equals to UPDATED_ADDRESS
        defaultCitadelShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCitadelShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the citadelList where address equals to UPDATED_ADDRESS
        defaultCitadelShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address is not null
        defaultCitadelShouldBeFound("address.specified=true");

        // Get all the citadelList where address is null
        defaultCitadelShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressContainsSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address contains DEFAULT_ADDRESS
        defaultCitadelShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the citadelList where address contains UPDATED_ADDRESS
        defaultCitadelShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitadelsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where address does not contain DEFAULT_ADDRESS
        defaultCitadelShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the citadelList where address does not contain UPDATED_ADDRESS
        defaultCitadelShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start equals to DEFAULT_START
        defaultCitadelShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the citadelList where start equals to UPDATED_START
        defaultCitadelShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start not equals to DEFAULT_START
        defaultCitadelShouldNotBeFound("start.notEquals=" + DEFAULT_START);

        // Get all the citadelList where start not equals to UPDATED_START
        defaultCitadelShouldBeFound("start.notEquals=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsInShouldWork() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start in DEFAULT_START or UPDATED_START
        defaultCitadelShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the citadelList where start equals to UPDATED_START
        defaultCitadelShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start is not null
        defaultCitadelShouldBeFound("start.specified=true");

        // Get all the citadelList where start is null
        defaultCitadelShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start is greater than or equal to DEFAULT_START
        defaultCitadelShouldBeFound("start.greaterThanOrEqual=" + DEFAULT_START);

        // Get all the citadelList where start is greater than or equal to UPDATED_START
        defaultCitadelShouldNotBeFound("start.greaterThanOrEqual=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start is less than or equal to DEFAULT_START
        defaultCitadelShouldBeFound("start.lessThanOrEqual=" + DEFAULT_START);

        // Get all the citadelList where start is less than or equal to SMALLER_START
        defaultCitadelShouldNotBeFound("start.lessThanOrEqual=" + SMALLER_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsLessThanSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start is less than DEFAULT_START
        defaultCitadelShouldNotBeFound("start.lessThan=" + DEFAULT_START);

        // Get all the citadelList where start is less than UPDATED_START
        defaultCitadelShouldBeFound("start.lessThan=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where start is greater than DEFAULT_START
        defaultCitadelShouldNotBeFound("start.greaterThan=" + DEFAULT_START);

        // Get all the citadelList where start is greater than SMALLER_START
        defaultCitadelShouldBeFound("start.greaterThan=" + SMALLER_START);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish equals to DEFAULT_FINISH
        defaultCitadelShouldBeFound("finish.equals=" + DEFAULT_FINISH);

        // Get all the citadelList where finish equals to UPDATED_FINISH
        defaultCitadelShouldNotBeFound("finish.equals=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish not equals to DEFAULT_FINISH
        defaultCitadelShouldNotBeFound("finish.notEquals=" + DEFAULT_FINISH);

        // Get all the citadelList where finish not equals to UPDATED_FINISH
        defaultCitadelShouldBeFound("finish.notEquals=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsInShouldWork() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish in DEFAULT_FINISH or UPDATED_FINISH
        defaultCitadelShouldBeFound("finish.in=" + DEFAULT_FINISH + "," + UPDATED_FINISH);

        // Get all the citadelList where finish equals to UPDATED_FINISH
        defaultCitadelShouldNotBeFound("finish.in=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsNullOrNotNull() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish is not null
        defaultCitadelShouldBeFound("finish.specified=true");

        // Get all the citadelList where finish is null
        defaultCitadelShouldNotBeFound("finish.specified=false");
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish is greater than or equal to DEFAULT_FINISH
        defaultCitadelShouldBeFound("finish.greaterThanOrEqual=" + DEFAULT_FINISH);

        // Get all the citadelList where finish is greater than or equal to UPDATED_FINISH
        defaultCitadelShouldNotBeFound("finish.greaterThanOrEqual=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish is less than or equal to DEFAULT_FINISH
        defaultCitadelShouldBeFound("finish.lessThanOrEqual=" + DEFAULT_FINISH);

        // Get all the citadelList where finish is less than or equal to SMALLER_FINISH
        defaultCitadelShouldNotBeFound("finish.lessThanOrEqual=" + SMALLER_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsLessThanSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish is less than DEFAULT_FINISH
        defaultCitadelShouldNotBeFound("finish.lessThan=" + DEFAULT_FINISH);

        // Get all the citadelList where finish is less than UPDATED_FINISH
        defaultCitadelShouldBeFound("finish.lessThan=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllCitadelsByFinishIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        // Get all the citadelList where finish is greater than DEFAULT_FINISH
        defaultCitadelShouldNotBeFound("finish.greaterThan=" + DEFAULT_FINISH);

        // Get all the citadelList where finish is greater than SMALLER_FINISH
        defaultCitadelShouldBeFound("finish.greaterThan=" + SMALLER_FINISH);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitadelShouldBeFound(String filter) throws Exception {
        restCitadelMockMvc
            .perform(get("/api/citadels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citadel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH.toString())));

        // Check, that the count call also returns 1
        restCitadelMockMvc
            .perform(get("/api/citadels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitadelShouldNotBeFound(String filter) throws Exception {
        restCitadelMockMvc
            .perform(get("/api/citadels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitadelMockMvc
            .perform(get("/api/citadels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitadel() throws Exception {
        // Get the citadel
        restCitadelMockMvc.perform(get("/api/citadels/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateCitadel() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        int databaseSizeBeforeUpdate = citadelRepository.findAll().size();

        // Update the citadel
        Citadel updatedCitadel = citadelRepository.findById(citadel.getId()).get();
        // Disconnect from session so that the updates on updatedCitadel are not directly saved in db
        em.detach(updatedCitadel);
        updatedCitadel.name(UPDATED_NAME).address(UPDATED_ADDRESS).start(UPDATED_START).finish(UPDATED_FINISH);
        CitadelDTO citadelDTO = citadelMapper.toDto(updatedCitadel);

        restCitadelMockMvc
            .perform(put("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isOk());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeUpdate);
        Citadel testCitadel = citadelList.get(citadelList.size() - 1);
        assertThat(testCitadel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitadel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitadel.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCitadel.getFinish()).isEqualTo(UPDATED_FINISH);
    }

    @Test
    @Transactional
    void updateNonExistingCitadel() throws Exception {
        int databaseSizeBeforeUpdate = citadelRepository.findAll().size();

        // Create the Citadel
        CitadelDTO citadelDTO = citadelMapper.toDto(citadel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitadelMockMvc
            .perform(put("/api/citadels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citadelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitadelWithPatch() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        int databaseSizeBeforeUpdate = citadelRepository.findAll().size();

        // Update the citadel using partial update
        Citadel partialUpdatedCitadel = new Citadel();
        partialUpdatedCitadel.setId(citadel.getId());

        partialUpdatedCitadel.address(UPDATED_ADDRESS).finish(UPDATED_FINISH);

        restCitadelMockMvc
            .perform(
                patch("/api/citadels")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitadel))
            )
            .andExpect(status().isOk());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeUpdate);
        Citadel testCitadel = citadelList.get(citadelList.size() - 1);
        assertThat(testCitadel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCitadel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitadel.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCitadel.getFinish()).isEqualTo(UPDATED_FINISH);
    }

    @Test
    @Transactional
    void fullUpdateCitadelWithPatch() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        int databaseSizeBeforeUpdate = citadelRepository.findAll().size();

        // Update the citadel using partial update
        Citadel partialUpdatedCitadel = new Citadel();
        partialUpdatedCitadel.setId(citadel.getId());

        partialUpdatedCitadel.name(UPDATED_NAME).address(UPDATED_ADDRESS).start(UPDATED_START).finish(UPDATED_FINISH);

        restCitadelMockMvc
            .perform(
                patch("/api/citadels")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitadel))
            )
            .andExpect(status().isOk());

        // Validate the Citadel in the database
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeUpdate);
        Citadel testCitadel = citadelList.get(citadelList.size() - 1);
        assertThat(testCitadel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitadel.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCitadel.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCitadel.getFinish()).isEqualTo(UPDATED_FINISH);
    }

    @Test
    @Transactional
    void partialUpdateCitadelShouldThrown() throws Exception {
        // Update the citadel without id should throw
        Citadel partialUpdatedCitadel = new Citadel();

        restCitadelMockMvc
            .perform(
                patch("/api/citadels")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitadel))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteCitadel() throws Exception {
        // Initialize the database
        citadelRepository.saveAndFlush(citadel);

        int databaseSizeBeforeDelete = citadelRepository.findAll().size();

        // Delete the citadel
        restCitadelMockMvc
            .perform(delete("/api/citadels/{id}", citadel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Citadel> citadelList = citadelRepository.findAll();
        assertThat(citadelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
