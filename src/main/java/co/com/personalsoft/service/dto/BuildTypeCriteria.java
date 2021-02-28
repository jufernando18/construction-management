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
 * Criteria class for the {@link co.com.personalsoft.domain.BuildType} entity. This class is used
 * in {@link co.com.personalsoft.web.rest.BuildTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /build-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BuildTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter duration;

    private IntegerFilter amountMaterial1;

    private IntegerFilter amountMaterial2;

    private IntegerFilter amountMaterial3;

    private IntegerFilter amountMaterial4;

    private IntegerFilter amountMaterial5;

    private LongFilter material1Id;

    private LongFilter material2Id;

    private LongFilter material3Id;

    private LongFilter material4Id;

    private LongFilter material5Id;

    public BuildTypeCriteria() {}

    public BuildTypeCriteria(BuildTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.amountMaterial1 = other.amountMaterial1 == null ? null : other.amountMaterial1.copy();
        this.amountMaterial2 = other.amountMaterial2 == null ? null : other.amountMaterial2.copy();
        this.amountMaterial3 = other.amountMaterial3 == null ? null : other.amountMaterial3.copy();
        this.amountMaterial4 = other.amountMaterial4 == null ? null : other.amountMaterial4.copy();
        this.amountMaterial5 = other.amountMaterial5 == null ? null : other.amountMaterial5.copy();
        this.material1Id = other.material1Id == null ? null : other.material1Id.copy();
        this.material2Id = other.material2Id == null ? null : other.material2Id.copy();
        this.material3Id = other.material3Id == null ? null : other.material3Id.copy();
        this.material4Id = other.material4Id == null ? null : other.material4Id.copy();
        this.material5Id = other.material5Id == null ? null : other.material5Id.copy();
    }

    @Override
    public BuildTypeCriteria copy() {
        return new BuildTypeCriteria(this);
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

    public IntegerFilter getDuration() {
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public IntegerFilter getAmountMaterial1() {
        return amountMaterial1;
    }

    public void setAmountMaterial1(IntegerFilter amountMaterial1) {
        this.amountMaterial1 = amountMaterial1;
    }

    public IntegerFilter getAmountMaterial2() {
        return amountMaterial2;
    }

    public void setAmountMaterial2(IntegerFilter amountMaterial2) {
        this.amountMaterial2 = amountMaterial2;
    }

    public IntegerFilter getAmountMaterial3() {
        return amountMaterial3;
    }

    public void setAmountMaterial3(IntegerFilter amountMaterial3) {
        this.amountMaterial3 = amountMaterial3;
    }

    public IntegerFilter getAmountMaterial4() {
        return amountMaterial4;
    }

    public void setAmountMaterial4(IntegerFilter amountMaterial4) {
        this.amountMaterial4 = amountMaterial4;
    }

    public IntegerFilter getAmountMaterial5() {
        return amountMaterial5;
    }

    public void setAmountMaterial5(IntegerFilter amountMaterial5) {
        this.amountMaterial5 = amountMaterial5;
    }

    public LongFilter getMaterial1Id() {
        return material1Id;
    }

    public void setMaterial1Id(LongFilter material1Id) {
        this.material1Id = material1Id;
    }

    public LongFilter getMaterial2Id() {
        return material2Id;
    }

    public void setMaterial2Id(LongFilter material2Id) {
        this.material2Id = material2Id;
    }

    public LongFilter getMaterial3Id() {
        return material3Id;
    }

    public void setMaterial3Id(LongFilter material3Id) {
        this.material3Id = material3Id;
    }

    public LongFilter getMaterial4Id() {
        return material4Id;
    }

    public void setMaterial4Id(LongFilter material4Id) {
        this.material4Id = material4Id;
    }

    public LongFilter getMaterial5Id() {
        return material5Id;
    }

    public void setMaterial5Id(LongFilter material5Id) {
        this.material5Id = material5Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BuildTypeCriteria that = (BuildTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(amountMaterial1, that.amountMaterial1) &&
            Objects.equals(amountMaterial2, that.amountMaterial2) &&
            Objects.equals(amountMaterial3, that.amountMaterial3) &&
            Objects.equals(amountMaterial4, that.amountMaterial4) &&
            Objects.equals(amountMaterial5, that.amountMaterial5) &&
            Objects.equals(material1Id, that.material1Id) &&
            Objects.equals(material2Id, that.material2Id) &&
            Objects.equals(material3Id, that.material3Id) &&
            Objects.equals(material4Id, that.material4Id) &&
            Objects.equals(material5Id, that.material5Id)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            duration,
            amountMaterial1,
            amountMaterial2,
            amountMaterial3,
            amountMaterial4,
            amountMaterial5,
            material1Id,
            material2Id,
            material3Id,
            material4Id,
            material5Id
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (amountMaterial1 != null ? "amountMaterial1=" + amountMaterial1 + ", " : "") +
                (amountMaterial2 != null ? "amountMaterial2=" + amountMaterial2 + ", " : "") +
                (amountMaterial3 != null ? "amountMaterial3=" + amountMaterial3 + ", " : "") +
                (amountMaterial4 != null ? "amountMaterial4=" + amountMaterial4 + ", " : "") +
                (amountMaterial5 != null ? "amountMaterial5=" + amountMaterial5 + ", " : "") +
                (material1Id != null ? "material1Id=" + material1Id + ", " : "") +
                (material2Id != null ? "material2Id=" + material2Id + ", " : "") +
                (material3Id != null ? "material3Id=" + material3Id + ", " : "") +
                (material4Id != null ? "material4Id=" + material4Id + ", " : "") +
                (material5Id != null ? "material5Id=" + material5Id + ", " : "") +
            "}";
    }
}
