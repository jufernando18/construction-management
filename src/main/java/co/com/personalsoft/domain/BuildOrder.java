package co.com.personalsoft.domain;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BuildOrder.
 */
@Entity
@Table(name = "build_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BuildOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private BuildOrderState state;

    @NotNull
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @NotNull
    @Column(name = "finish", nullable = false)
    private LocalDate finish;

    @JsonIgnoreProperties(value = { "buildType", "citadel" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Requisition requisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildOrder id(Long id) {
        this.id = id;
        return this;
    }

    public BuildOrderState getState() {
        return this.state;
    }

    public BuildOrder state(BuildOrderState state) {
        this.state = state;
        return this;
    }

    public void setState(BuildOrderState state) {
        this.state = state;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public BuildOrder start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return this.finish;
    }

    public BuildOrder finish(LocalDate finish) {
        this.finish = finish;
        return this;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public Requisition getRequisition() {
        return this.requisition;
    }

    public BuildOrder requisition(Requisition requisition) {
        this.setRequisition(requisition);
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildOrder)) {
            return false;
        }
        return id != null && id.equals(((BuildOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildOrder{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", start='" + getStart() + "'" +
            ", finish='" + getFinish() + "'" +
            "}";
    }
}
