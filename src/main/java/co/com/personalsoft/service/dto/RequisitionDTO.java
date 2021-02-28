package co.com.personalsoft.service.dto;

import co.com.personalsoft.domain.enumeration.RequisitionState;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.personalsoft.domain.Requisition} entity.
 */
public class RequisitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d+,\\d+$")
    private String coordinate;

    @NotNull
    private RequisitionState state;

    @NotNull
    private LocalDate date;

    private BuildTypeDTO buildType;

    private CitadelDTO citadel;

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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public RequisitionState getState() {
        return state;
    }

    public void setState(RequisitionState state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BuildTypeDTO getBuildType() {
        return buildType;
    }

    public void setBuildType(BuildTypeDTO buildType) {
        this.buildType = buildType;
    }

    public CitadelDTO getCitadel() {
        return citadel;
    }

    public void setCitadel(CitadelDTO citadel) {
        this.citadel = citadel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequisitionDTO)) {
            return false;
        }

        RequisitionDTO requisitionDTO = (RequisitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requisitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequisitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coordinate='" + getCoordinate() + "'" +
            ", state='" + getState() + "'" +
            ", date='" + getDate() + "'" +
            ", buildType=" + getBuildType() +
            ", citadel=" + getCitadel() +
            "}";
    }
}
