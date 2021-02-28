package co.com.personalsoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.personalsoft.IntegrationTest;
import co.com.personalsoft.domain.Material;
import co.com.personalsoft.repository.MaterialRepository;
import co.com.personalsoft.service.MaterialQueryService;
import co.com.personalsoft.service.dto.MaterialCriteria;
import co.com.personalsoft.service.dto.MaterialDTO;
import co.com.personalsoft.service.mapper.MaterialMapper;
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
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT_AVAILABLE = 1;
    private static final Integer UPDATED_AMOUNT_AVAILABLE = 2;
    private static final Integer SMALLER_AMOUNT_AVAILABLE = 1 - 1;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialQueryService materialQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material().name(DEFAULT_NAME).acronym(DEFAULT_ACRONYM).amountAvailable(DEFAULT_AMOUNT_AVAILABLE);
        return material;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material().name(UPDATED_NAME).acronym(UPDATED_ACRONYM).amountAvailable(UPDATED_AMOUNT_AVAILABLE);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();
        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);
        restMaterialMockMvc
            .perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMaterial.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testMaterial.getAmountAvailable()).isEqualTo(DEFAULT_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void createMaterialWithExistingId() throws Exception {
        // Create the Material with an existing ID
        material.setId(1L);
        MaterialDTO materialDTO = materialMapper.toDto(material);

        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc
            .perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setName(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);

        restMaterialMockMvc
            .perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setAcronym(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);

        restMaterialMockMvc
            .perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setAmountAvailable(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);

        restMaterialMockMvc
            .perform(post("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc
            .perform(get("/api/materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].amountAvailable").value(hasItem(DEFAULT_AMOUNT_AVAILABLE)));
    }

    @Test
    @Transactional
    void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc
            .perform(get("/api/materials/{id}", material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.amountAvailable").value(DEFAULT_AMOUNT_AVAILABLE));
    }

    @Test
    @Transactional
    void getMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        Long id = material.getId();

        defaultMaterialShouldBeFound("id.equals=" + id);
        defaultMaterialShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaterialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name equals to DEFAULT_NAME
        defaultMaterialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the materialList where name equals to UPDATED_NAME
        defaultMaterialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name not equals to DEFAULT_NAME
        defaultMaterialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the materialList where name not equals to UPDATED_NAME
        defaultMaterialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMaterialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the materialList where name equals to UPDATED_NAME
        defaultMaterialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name is not null
        defaultMaterialShouldBeFound("name.specified=true");

        // Get all the materialList where name is null
        defaultMaterialShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByNameContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name contains DEFAULT_NAME
        defaultMaterialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the materialList where name contains UPDATED_NAME
        defaultMaterialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where name does not contain DEFAULT_NAME
        defaultMaterialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the materialList where name does not contain UPDATED_NAME
        defaultMaterialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym equals to DEFAULT_ACRONYM
        defaultMaterialShouldBeFound("acronym.equals=" + DEFAULT_ACRONYM);

        // Get all the materialList where acronym equals to UPDATED_ACRONYM
        defaultMaterialShouldNotBeFound("acronym.equals=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym not equals to DEFAULT_ACRONYM
        defaultMaterialShouldNotBeFound("acronym.notEquals=" + DEFAULT_ACRONYM);

        // Get all the materialList where acronym not equals to UPDATED_ACRONYM
        defaultMaterialShouldBeFound("acronym.notEquals=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym in DEFAULT_ACRONYM or UPDATED_ACRONYM
        defaultMaterialShouldBeFound("acronym.in=" + DEFAULT_ACRONYM + "," + UPDATED_ACRONYM);

        // Get all the materialList where acronym equals to UPDATED_ACRONYM
        defaultMaterialShouldNotBeFound("acronym.in=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym is not null
        defaultMaterialShouldBeFound("acronym.specified=true");

        // Get all the materialList where acronym is null
        defaultMaterialShouldNotBeFound("acronym.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym contains DEFAULT_ACRONYM
        defaultMaterialShouldBeFound("acronym.contains=" + DEFAULT_ACRONYM);

        // Get all the materialList where acronym contains UPDATED_ACRONYM
        defaultMaterialShouldNotBeFound("acronym.contains=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    void getAllMaterialsByAcronymNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where acronym does not contain DEFAULT_ACRONYM
        defaultMaterialShouldNotBeFound("acronym.doesNotContain=" + DEFAULT_ACRONYM);

        // Get all the materialList where acronym does not contain UPDATED_ACRONYM
        defaultMaterialShouldBeFound("acronym.doesNotContain=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable equals to DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.equals=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable equals to UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.equals=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable not equals to DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.notEquals=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable not equals to UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.notEquals=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable in DEFAULT_AMOUNT_AVAILABLE or UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.in=" + DEFAULT_AMOUNT_AVAILABLE + "," + UPDATED_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable equals to UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.in=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable is not null
        defaultMaterialShouldBeFound("amountAvailable.specified=true");

        // Get all the materialList where amountAvailable is null
        defaultMaterialShouldNotBeFound("amountAvailable.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable is greater than or equal to DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.greaterThanOrEqual=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable is greater than or equal to UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.greaterThanOrEqual=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable is less than or equal to DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.lessThanOrEqual=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable is less than or equal to SMALLER_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.lessThanOrEqual=" + SMALLER_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable is less than DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.lessThan=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable is less than UPDATED_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.lessThan=" + UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAmountAvailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where amountAvailable is greater than DEFAULT_AMOUNT_AVAILABLE
        defaultMaterialShouldNotBeFound("amountAvailable.greaterThan=" + DEFAULT_AMOUNT_AVAILABLE);

        // Get all the materialList where amountAvailable is greater than SMALLER_AMOUNT_AVAILABLE
        defaultMaterialShouldBeFound("amountAvailable.greaterThan=" + SMALLER_AMOUNT_AVAILABLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialShouldBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].amountAvailable").value(hasItem(DEFAULT_AMOUNT_AVAILABLE)));

        // Check, that the count call also returns 1
        restMaterialMockMvc
            .perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialShouldNotBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialMockMvc
            .perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).get();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial.name(UPDATED_NAME).acronym(UPDATED_ACRONYM).amountAvailable(UPDATED_AMOUNT_AVAILABLE);
        MaterialDTO materialDTO = materialMapper.toDto(updatedMaterial);

        restMaterialMockMvc
            .perform(put("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterial.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testMaterial.getAmountAvailable()).isEqualTo(UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void updateNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(put("/api/materials").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial.name(UPDATED_NAME);

        restMaterialMockMvc
            .perform(
                patch("/api/materials")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterial.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testMaterial.getAmountAvailable()).isEqualTo(DEFAULT_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void fullUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial.name(UPDATED_NAME).acronym(UPDATED_ACRONYM).amountAvailable(UPDATED_AMOUNT_AVAILABLE);

        restMaterialMockMvc
            .perform(
                patch("/api/materials")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterial.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testMaterial.getAmountAvailable()).isEqualTo(UPDATED_AMOUNT_AVAILABLE);
    }

    @Test
    @Transactional
    void partialUpdateMaterialShouldThrown() throws Exception {
        // Update the material without id should throw
        Material partialUpdatedMaterial = new Material();

        restMaterialMockMvc
            .perform(
                patch("/api/materials")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc
            .perform(delete("/api/materials/{id}", material.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
