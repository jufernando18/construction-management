package co.com.personalsoft.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.personalsoft.domain.Material} entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String acronym;

    @NotNull
    private Integer amountAvailable;

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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Integer getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialDTO)) {
            return false;
        }

        MaterialDTO materialDTO = (MaterialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, materialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", amountAvailable=" + getAmountAvailable() +
            "}";
    }
}
