package co.com.personalsoft.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.personalsoft.domain.BuildType} entity.
 */
public class BuildTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer duration;

    @NotNull
    @Min(value = 0)
    private Integer amountMaterial1;

    @NotNull
    @Min(value = 0)
    private Integer amountMaterial2;

    @NotNull
    @Min(value = 0)
    private Integer amountMaterial3;

    @NotNull
    @Min(value = 0)
    private Integer amountMaterial4;

    @NotNull
    @Min(value = 0)
    private Integer amountMaterial5;

    private MaterialDTO material1;

    private MaterialDTO material2;

    private MaterialDTO material3;

    private MaterialDTO material4;

    private MaterialDTO material5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAmountMaterial1() {
        return amountMaterial1;
    }

    public void setAmountMaterial1(Integer amountMaterial1) {
        this.amountMaterial1 = amountMaterial1;
    }

    public Integer getAmountMaterial2() {
        return amountMaterial2;
    }

    public void setAmountMaterial2(Integer amountMaterial2) {
        this.amountMaterial2 = amountMaterial2;
    }

    public Integer getAmountMaterial3() {
        return amountMaterial3;
    }

    public void setAmountMaterial3(Integer amountMaterial3) {
        this.amountMaterial3 = amountMaterial3;
    }

    public Integer getAmountMaterial4() {
        return amountMaterial4;
    }

    public void setAmountMaterial4(Integer amountMaterial4) {
        this.amountMaterial4 = amountMaterial4;
    }

    public Integer getAmountMaterial5() {
        return amountMaterial5;
    }

    public void setAmountMaterial5(Integer amountMaterial5) {
        this.amountMaterial5 = amountMaterial5;
    }

    public MaterialDTO getMaterial1() {
        return material1;
    }

    public void setMaterial1(MaterialDTO material1) {
        this.material1 = material1;
    }

    public MaterialDTO getMaterial2() {
        return material2;
    }

    public void setMaterial2(MaterialDTO material2) {
        this.material2 = material2;
    }

    public MaterialDTO getMaterial3() {
        return material3;
    }

    public void setMaterial3(MaterialDTO material3) {
        this.material3 = material3;
    }

    public MaterialDTO getMaterial4() {
        return material4;
    }

    public void setMaterial4(MaterialDTO material4) {
        this.material4 = material4;
    }

    public MaterialDTO getMaterial5() {
        return material5;
    }

    public void setMaterial5(MaterialDTO material5) {
        this.material5 = material5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildTypeDTO)) {
            return false;
        }

        BuildTypeDTO buildTypeDTO = (BuildTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buildTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", duration=" + getDuration() +
            ", amountMaterial1=" + getAmountMaterial1() +
            ", amountMaterial2=" + getAmountMaterial2() +
            ", amountMaterial3=" + getAmountMaterial3() +
            ", amountMaterial4=" + getAmountMaterial4() +
            ", amountMaterial5=" + getAmountMaterial5() +
            ", material1=" + getMaterial1() +
            ", material2=" + getMaterial2() +
            ", material3=" + getMaterial3() +
            ", material4=" + getMaterial4() +
            ", material5=" + getMaterial5() +
            "}";
    }
}
