package co.com.personalsoft.service.dto;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.personalsoft.domain.BuildOrder} entity.
 */
public class BuildOrderDTO implements Serializable {

    private Long id;

    private BuildOrderState state;

    private LocalDate start;

    private LocalDate finish;

    @NotNull
    private RequisitionDTO requisition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildOrderState getState() {
        return state;
    }

    public void setState(BuildOrderState state) {
        this.state = state;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public RequisitionDTO getRequisition() {
        return requisition;
    }

    public void setRequisition(RequisitionDTO requisition) {
        this.requisition = requisition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildOrderDTO)) {
            return false;
        }

        BuildOrderDTO buildOrderDTO = (BuildOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buildOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildOrderDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", start='" + getStart() + "'" +
            ", finish='" + getFinish() + "'" +
            ", requisition=" + getRequisition() +
            "}";
    }
}
