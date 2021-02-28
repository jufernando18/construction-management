package co.com.personalsoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.personalsoft.IntegrationTest;
import co.com.personalsoft.domain.BuildOrder;
import co.com.personalsoft.domain.Requisition;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.repository.BuildOrderRepository;
import co.com.personalsoft.service.BuildOrderQueryService;
import co.com.personalsoft.service.dto.BuildOrderCriteria;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.mapper.BuildOrderMapper;
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
 * Integration tests for the {@link BuildOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuildOrderResourceIT {

    private static final BuildOrderState DEFAULT_STATE = BuildOrderState.PENDING;
    private static final BuildOrderState UPDATED_STATE = BuildOrderState.BUILDING;

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FINISH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINISH = LocalDate.ofEpochDay(-1L);

    @Autowired
    private BuildOrderRepository buildOrderRepository;

    @Autowired
    private BuildOrderMapper buildOrderMapper;

    @Autowired
    private BuildOrderQueryService buildOrderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildOrderMockMvc;

    private BuildOrder buildOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildOrder createEntity(EntityManager em) {
        BuildOrder buildOrder = new BuildOrder().state(DEFAULT_STATE).start(DEFAULT_START).finish(DEFAULT_FINISH);
        // Add required entity
        Requisition requisition;
        if (TestUtil.findAll(em, Requisition.class).isEmpty()) {
            requisition = RequisitionResourceIT.createEntity(em);
            em.persist(requisition);
            em.flush();
        } else {
            requisition = TestUtil.findAll(em, Requisition.class).get(0);
        }
        buildOrder.setRequisition(requisition);
        return buildOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildOrder createUpdatedEntity(EntityManager em) {
        BuildOrder buildOrder = new BuildOrder().state(UPDATED_STATE).start(UPDATED_START).finish(UPDATED_FINISH);
        // Add required entity
        Requisition requisition;
        if (TestUtil.findAll(em, Requisition.class).isEmpty()) {
            requisition = RequisitionResourceIT.createUpdatedEntity(em);
            em.persist(requisition);
            em.flush();
        } else {
            requisition = TestUtil.findAll(em, Requisition.class).get(0);
        }
        buildOrder.setRequisition(requisition);
        return buildOrder;
    }

    @BeforeEach
    public void initTest() {
        buildOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createBuildOrder() throws Exception {
        int databaseSizeBeforeCreate = buildOrderRepository.findAll().size();
        // Create the BuildOrder
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);
        restBuildOrderMockMvc
            .perform(
                post("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeCreate + 1);
        BuildOrder testBuildOrder = buildOrderList.get(buildOrderList.size() - 1);
        assertThat(testBuildOrder.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBuildOrder.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testBuildOrder.getFinish()).isEqualTo(DEFAULT_FINISH);
    }

    @Test
    @Transactional
    void createBuildOrderWithExistingId() throws Exception {
        // Create the BuildOrder with an existing ID
        buildOrder.setId(1L);
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);

        int databaseSizeBeforeCreate = buildOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildOrderMockMvc
            .perform(
                post("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildOrderRepository.findAll().size();
        // set the field null
        buildOrder.setState(null);

        // Create the BuildOrder, which fails.
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);

        restBuildOrderMockMvc
            .perform(
                post("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildOrderRepository.findAll().size();
        // set the field null
        buildOrder.setStart(null);

        // Create the BuildOrder, which fails.
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);

        restBuildOrderMockMvc
            .perform(
                post("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFinishIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildOrderRepository.findAll().size();
        // set the field null
        buildOrder.setFinish(null);

        // Create the BuildOrder, which fails.
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);

        restBuildOrderMockMvc
            .perform(
                post("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isBadRequest());

        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuildOrders() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList
        restBuildOrderMockMvc
            .perform(get("/api/build-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH.toString())));
    }

    @Test
    @Transactional
    void getBuildOrder() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get the buildOrder
        restBuildOrderMockMvc
            .perform(get("/api/build-orders/{id}", buildOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buildOrder.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.finish").value(DEFAULT_FINISH.toString()));
    }

    @Test
    @Transactional
    void getBuildOrdersByIdFiltering() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        Long id = buildOrder.getId();

        defaultBuildOrderShouldBeFound("id.equals=" + id);
        defaultBuildOrderShouldNotBeFound("id.notEquals=" + id);

        defaultBuildOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBuildOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultBuildOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBuildOrderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where state equals to DEFAULT_STATE
        defaultBuildOrderShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the buildOrderList where state equals to UPDATED_STATE
        defaultBuildOrderShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where state not equals to DEFAULT_STATE
        defaultBuildOrderShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the buildOrderList where state not equals to UPDATED_STATE
        defaultBuildOrderShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStateIsInShouldWork() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where state in DEFAULT_STATE or UPDATED_STATE
        defaultBuildOrderShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the buildOrderList where state equals to UPDATED_STATE
        defaultBuildOrderShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where state is not null
        defaultBuildOrderShouldBeFound("state.specified=true");

        // Get all the buildOrderList where state is null
        defaultBuildOrderShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start equals to DEFAULT_START
        defaultBuildOrderShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the buildOrderList where start equals to UPDATED_START
        defaultBuildOrderShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start not equals to DEFAULT_START
        defaultBuildOrderShouldNotBeFound("start.notEquals=" + DEFAULT_START);

        // Get all the buildOrderList where start not equals to UPDATED_START
        defaultBuildOrderShouldBeFound("start.notEquals=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsInShouldWork() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start in DEFAULT_START or UPDATED_START
        defaultBuildOrderShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the buildOrderList where start equals to UPDATED_START
        defaultBuildOrderShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start is not null
        defaultBuildOrderShouldBeFound("start.specified=true");

        // Get all the buildOrderList where start is null
        defaultBuildOrderShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start is greater than or equal to DEFAULT_START
        defaultBuildOrderShouldBeFound("start.greaterThanOrEqual=" + DEFAULT_START);

        // Get all the buildOrderList where start is greater than or equal to UPDATED_START
        defaultBuildOrderShouldNotBeFound("start.greaterThanOrEqual=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start is less than or equal to DEFAULT_START
        defaultBuildOrderShouldBeFound("start.lessThanOrEqual=" + DEFAULT_START);

        // Get all the buildOrderList where start is less than or equal to SMALLER_START
        defaultBuildOrderShouldNotBeFound("start.lessThanOrEqual=" + SMALLER_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsLessThanSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start is less than DEFAULT_START
        defaultBuildOrderShouldNotBeFound("start.lessThan=" + DEFAULT_START);

        // Get all the buildOrderList where start is less than UPDATED_START
        defaultBuildOrderShouldBeFound("start.lessThan=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where start is greater than DEFAULT_START
        defaultBuildOrderShouldNotBeFound("start.greaterThan=" + DEFAULT_START);

        // Get all the buildOrderList where start is greater than SMALLER_START
        defaultBuildOrderShouldBeFound("start.greaterThan=" + SMALLER_START);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish equals to DEFAULT_FINISH
        defaultBuildOrderShouldBeFound("finish.equals=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish equals to UPDATED_FINISH
        defaultBuildOrderShouldNotBeFound("finish.equals=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish not equals to DEFAULT_FINISH
        defaultBuildOrderShouldNotBeFound("finish.notEquals=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish not equals to UPDATED_FINISH
        defaultBuildOrderShouldBeFound("finish.notEquals=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsInShouldWork() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish in DEFAULT_FINISH or UPDATED_FINISH
        defaultBuildOrderShouldBeFound("finish.in=" + DEFAULT_FINISH + "," + UPDATED_FINISH);

        // Get all the buildOrderList where finish equals to UPDATED_FINISH
        defaultBuildOrderShouldNotBeFound("finish.in=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish is not null
        defaultBuildOrderShouldBeFound("finish.specified=true");

        // Get all the buildOrderList where finish is null
        defaultBuildOrderShouldNotBeFound("finish.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish is greater than or equal to DEFAULT_FINISH
        defaultBuildOrderShouldBeFound("finish.greaterThanOrEqual=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish is greater than or equal to UPDATED_FINISH
        defaultBuildOrderShouldNotBeFound("finish.greaterThanOrEqual=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish is less than or equal to DEFAULT_FINISH
        defaultBuildOrderShouldBeFound("finish.lessThanOrEqual=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish is less than or equal to SMALLER_FINISH
        defaultBuildOrderShouldNotBeFound("finish.lessThanOrEqual=" + SMALLER_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsLessThanSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish is less than DEFAULT_FINISH
        defaultBuildOrderShouldNotBeFound("finish.lessThan=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish is less than UPDATED_FINISH
        defaultBuildOrderShouldBeFound("finish.lessThan=" + UPDATED_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByFinishIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        // Get all the buildOrderList where finish is greater than DEFAULT_FINISH
        defaultBuildOrderShouldNotBeFound("finish.greaterThan=" + DEFAULT_FINISH);

        // Get all the buildOrderList where finish is greater than SMALLER_FINISH
        defaultBuildOrderShouldBeFound("finish.greaterThan=" + SMALLER_FINISH);
    }

    @Test
    @Transactional
    void getAllBuildOrdersByRequisitionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Requisition requisition = buildOrder.getRequisition();
        buildOrderRepository.saveAndFlush(buildOrder);
        Long requisitionId = requisition.getId();

        // Get all the buildOrderList where requisition equals to requisitionId
        defaultBuildOrderShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the buildOrderList where requisition equals to requisitionId + 1
        defaultBuildOrderShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuildOrderShouldBeFound(String filter) throws Exception {
        restBuildOrderMockMvc
            .perform(get("/api/build-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH.toString())));

        // Check, that the count call also returns 1
        restBuildOrderMockMvc
            .perform(get("/api/build-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuildOrderShouldNotBeFound(String filter) throws Exception {
        restBuildOrderMockMvc
            .perform(get("/api/build-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuildOrderMockMvc
            .perform(get("/api/build-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBuildOrder() throws Exception {
        // Get the buildOrder
        restBuildOrderMockMvc.perform(get("/api/build-orders/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateBuildOrder() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        int databaseSizeBeforeUpdate = buildOrderRepository.findAll().size();

        // Update the buildOrder
        BuildOrder updatedBuildOrder = buildOrderRepository.findById(buildOrder.getId()).get();
        // Disconnect from session so that the updates on updatedBuildOrder are not directly saved in db
        em.detach(updatedBuildOrder);
        updatedBuildOrder.state(UPDATED_STATE).start(UPDATED_START).finish(UPDATED_FINISH);
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(updatedBuildOrder);

        restBuildOrderMockMvc
            .perform(
                put("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeUpdate);
        BuildOrder testBuildOrder = buildOrderList.get(buildOrderList.size() - 1);
        assertThat(testBuildOrder.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBuildOrder.getStart()).isEqualTo(UPDATED_START);
        assertThat(testBuildOrder.getFinish()).isEqualTo(UPDATED_FINISH);
    }

    @Test
    @Transactional
    void updateNonExistingBuildOrder() throws Exception {
        int databaseSizeBeforeUpdate = buildOrderRepository.findAll().size();

        // Create the BuildOrder
        BuildOrderDTO buildOrderDTO = buildOrderMapper.toDto(buildOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildOrderMockMvc
            .perform(
                put("/api/build-orders").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuildOrderWithPatch() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        int databaseSizeBeforeUpdate = buildOrderRepository.findAll().size();

        // Update the buildOrder using partial update
        BuildOrder partialUpdatedBuildOrder = new BuildOrder();
        partialUpdatedBuildOrder.setId(buildOrder.getId());

        partialUpdatedBuildOrder.start(UPDATED_START);

        restBuildOrderMockMvc
            .perform(
                patch("/api/build-orders")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildOrder))
            )
            .andExpect(status().isOk());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeUpdate);
        BuildOrder testBuildOrder = buildOrderList.get(buildOrderList.size() - 1);
        assertThat(testBuildOrder.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBuildOrder.getStart()).isEqualTo(UPDATED_START);
        assertThat(testBuildOrder.getFinish()).isEqualTo(DEFAULT_FINISH);
    }

    @Test
    @Transactional
    void fullUpdateBuildOrderWithPatch() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        int databaseSizeBeforeUpdate = buildOrderRepository.findAll().size();

        // Update the buildOrder using partial update
        BuildOrder partialUpdatedBuildOrder = new BuildOrder();
        partialUpdatedBuildOrder.setId(buildOrder.getId());

        partialUpdatedBuildOrder.state(UPDATED_STATE).start(UPDATED_START).finish(UPDATED_FINISH);

        restBuildOrderMockMvc
            .perform(
                patch("/api/build-orders")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildOrder))
            )
            .andExpect(status().isOk());

        // Validate the BuildOrder in the database
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeUpdate);
        BuildOrder testBuildOrder = buildOrderList.get(buildOrderList.size() - 1);
        assertThat(testBuildOrder.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBuildOrder.getStart()).isEqualTo(UPDATED_START);
        assertThat(testBuildOrder.getFinish()).isEqualTo(UPDATED_FINISH);
    }

    @Test
    @Transactional
    void partialUpdateBuildOrderShouldThrown() throws Exception {
        // Update the buildOrder without id should throw
        BuildOrder partialUpdatedBuildOrder = new BuildOrder();

        restBuildOrderMockMvc
            .perform(
                patch("/api/build-orders")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildOrder))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteBuildOrder() throws Exception {
        // Initialize the database
        buildOrderRepository.saveAndFlush(buildOrder);

        int databaseSizeBeforeDelete = buildOrderRepository.findAll().size();

        // Delete the buildOrder
        restBuildOrderMockMvc
            .perform(delete("/api/build-orders/{id}", buildOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuildOrder> buildOrderList = buildOrderRepository.findAll();
        assertThat(buildOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
