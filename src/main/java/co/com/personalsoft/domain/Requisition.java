package co.com.personalsoft.domain;

import co.com.personalsoft.domain.enumeration.RequisitionState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requisition.
 */
@Entity
@Table(name = "requisition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requisition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d+,\\d+$")
    @Column(name = "coordinate", nullable = false)
    private String coordinate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private RequisitionState state;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "material1", "material2", "material3", "material4", "material5" }, allowSetters = true)
    private BuildType buildType;

    @ManyToOne(optional = false)
    @NotNull
    private Citadel citadel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Requisition id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Requisition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public Requisition coordinate(String coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public RequisitionState getState() {
        return this.state;
    }

    public Requisition state(RequisitionState state) {
        this.state = state;
        return this;
    }

    public void setState(RequisitionState state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Requisition date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BuildType getBuildType() {
        return this.buildType;
    }

    public Requisition buildType(BuildType buildType) {
        this.setBuildType(buildType);
        return this;
    }

    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }

    public Citadel getCitadel() {
        return this.citadel;
    }

    public Requisition citadel(Citadel citadel) {
        this.setCitadel(citadel);
        return this;
    }

    public void setCitadel(Citadel citadel) {
        this.citadel = citadel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requisition)) {
            return false;
        }
        return id != null && id.equals(((Requisition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requisition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coordinate='" + getCoordinate() + "'" +
            ", state='" + getState() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
