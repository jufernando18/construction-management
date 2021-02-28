package co.com.personalsoft.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.personalsoft.domain.Citadel} entity.
 */
public class CitadelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate finish;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitadelDTO)) {
            return false;
        }

        CitadelDTO citadelDTO = (CitadelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citadelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitadelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", start='" + getStart() + "'" +
            ", finish='" + getFinish() + "'" +
            "}";
    }
}
