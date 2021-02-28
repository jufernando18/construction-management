package co.com.personalsoft.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BuildType.
 */
@Entity
@Table(name = "build_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BuildType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount_material_1", nullable = false)
    private Integer amountMaterial1;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount_material_2", nullable = false)
    private Integer amountMaterial2;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount_material_3", nullable = false)
    private Integer amountMaterial3;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount_material_4", nullable = false)
    private Integer amountMaterial4;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount_material_5", nullable = false)
    private Integer amountMaterial5;

    @ManyToOne(optional = false)
    @NotNull
    private Material material1;

    @ManyToOne
    private Material material2;

    @ManyToOne
    private Material material3;

    @ManyToOne
    private Material material4;

    @ManyToOne
    private Material material5;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public BuildType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public BuildType duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAmountMaterial1() {
        return this.amountMaterial1;
    }

    public BuildType amountMaterial1(Integer amountMaterial1) {
        this.amountMaterial1 = amountMaterial1;
        return this;
    }

    public void setAmountMaterial1(Integer amountMaterial1) {
        this.amountMaterial1 = amountMaterial1;
    }

    public Integer getAmountMaterial2() {
        return this.amountMaterial2;
    }

    public BuildType amountMaterial2(Integer amountMaterial2) {
        this.amountMaterial2 = amountMaterial2;
        return this;
    }

    public void setAmountMaterial2(Integer amountMaterial2) {
        this.amountMaterial2 = amountMaterial2;
    }

    public Integer getAmountMaterial3() {
        return this.amountMaterial3;
    }

    public BuildType amountMaterial3(Integer amountMaterial3) {
        this.amountMaterial3 = amountMaterial3;
        return this;
    }

    public void setAmountMaterial3(Integer amountMaterial3) {
        this.amountMaterial3 = amountMaterial3;
    }

    public Integer getAmountMaterial4() {
        return this.amountMaterial4;
    }

    public BuildType amountMaterial4(Integer amountMaterial4) {
        this.amountMaterial4 = amountMaterial4;
        return this;
    }

    public void setAmountMaterial4(Integer amountMaterial4) {
        this.amountMaterial4 = amountMaterial4;
    }

    public Integer getAmountMaterial5() {
        return this.amountMaterial5;
    }

    public BuildType amountMaterial5(Integer amountMaterial5) {
        this.amountMaterial5 = amountMaterial5;
        return this;
    }

    public void setAmountMaterial5(Integer amountMaterial5) {
        this.amountMaterial5 = amountMaterial5;
    }

    public Material getMaterial1() {
        return this.material1;
    }

    public BuildType material1(Material material) {
        this.setMaterial1(material);
        return this;
    }

    public void setMaterial1(Material material) {
        this.material1 = material;
    }

    public Material getMaterial2() {
        return this.material2;
    }

    public BuildType material2(Material material) {
        this.setMaterial2(material);
        return this;
    }

    public void setMaterial2(Material material) {
        this.material2 = material;
    }

    public Material getMaterial3() {
        return this.material3;
    }

    public BuildType material3(Material material) {
        this.setMaterial3(material);
        return this;
    }

    public void setMaterial3(Material material) {
        this.material3 = material;
    }

    public Material getMaterial4() {
        return this.material4;
    }

    public BuildType material4(Material material) {
        this.setMaterial4(material);
        return this;
    }

    public void setMaterial4(Material material) {
        this.material4 = material;
    }

    public Material getMaterial5() {
        return this.material5;
    }

    public BuildType material5(Material material) {
        this.setMaterial5(material);
        return this;
    }

    public void setMaterial5(Material material) {
        this.material5 = material;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildType)) {
            return false;
        }
        return id != null && id.equals(((BuildType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", duration=" + getDuration() +
            ", amountMaterial1=" + getAmountMaterial1() +
            ", amountMaterial2=" + getAmountMaterial2() +
            ", amountMaterial3=" + getAmountMaterial3() +
            ", amountMaterial4=" + getAmountMaterial4() +
            ", amountMaterial5=" + getAmountMaterial5() +
            "}";
    }
}
