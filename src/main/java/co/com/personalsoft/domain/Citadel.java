package co.com.personalsoft.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Citadel.
 */
@Entity
@Table(name = "citadel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Citadel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @NotNull
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @NotNull
    @Column(name = "finish", nullable = false)
    private LocalDate finish;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citadel id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Citadel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Citadel address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public Citadel start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return this.finish;
    }

    public Citadel finish(LocalDate finish) {
        this.finish = finish;
        return this;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Citadel)) {
            return false;
        }
        return id != null && id.equals(((Citadel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Citadel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", start='" + getStart() + "'" +
            ", finish='" + getFinish() + "'" +
            "}";
    }
}
