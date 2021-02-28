package co.com.personalsoft.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.com.personalsoft.domain.Material} entity. This class is used
 * in {@link co.com.personalsoft.web.rest.MaterialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MaterialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter acronym;

    private IntegerFilter amountAvailable;

    public MaterialCriteria() {}

    public MaterialCriteria(MaterialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.acronym = other.acronym == null ? null : other.acronym.copy();
        this.amountAvailable = other.amountAvailable == null ? null : other.amountAvailable.copy();
    }

    @Override
    public MaterialCriteria copy() {
        return new MaterialCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAcronym() {
        return acronym;
    }

    public void setAcronym(StringFilter acronym) {
        this.acronym = acronym;
    }

    public IntegerFilter getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(IntegerFilter amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MaterialCriteria that = (MaterialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(acronym, that.acronym) &&
            Objects.equals(amountAvailable, that.amountAvailable)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, acronym, amountAvailable);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (acronym != null ? "acronym=" + acronym + ", " : "") +
                (amountAvailable != null ? "amountAvailable=" + amountAvailable + ", " : "") +
            "}";
    }
}
