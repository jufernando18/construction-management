package co.com.personalsoft.service.dto;

import co.com.personalsoft.domain.enumeration.RequisitionState;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.com.personalsoft.domain.Requisition} entity. This class is used
 * in {@link co.com.personalsoft.web.rest.RequisitionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /requisitions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionCriteria implements Serializable, Criteria {

    /**
     * Class for filtering RequisitionState
     */
    public static class RequisitionStateFilter extends Filter<RequisitionState> {

        public RequisitionStateFilter() {}

        public RequisitionStateFilter(RequisitionStateFilter filter) {
            super(filter);
        }

        @Override
        public RequisitionStateFilter copy() {
            return new RequisitionStateFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter coordinate;

    private RequisitionStateFilter state;

    private LocalDateFilter date;

    private LongFilter buildTypeId;

    private LongFilter citadelId;

    public RequisitionCriteria() {}

    public RequisitionCriteria(RequisitionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.coordinate = other.coordinate == null ? null : other.coordinate.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.buildTypeId = other.buildTypeId == null ? null : other.buildTypeId.copy();
        this.citadelId = other.citadelId == null ? null : other.citadelId.copy();
    }

    @Override
    public RequisitionCriteria copy() {
        return new RequisitionCriteria(this);
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

    public StringFilter getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(StringFilter coordinate) {
        this.coordinate = coordinate;
    }

    public RequisitionStateFilter getState() {
        return state;
    }

    public void setState(RequisitionStateFilter state) {
        this.state = state;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getBuildTypeId() {
        return buildTypeId;
    }

    public void setBuildTypeId(LongFilter buildTypeId) {
        this.buildTypeId = buildTypeId;
    }

    public LongFilter getCitadelId() {
        return citadelId;
    }

    public void setCitadelId(LongFilter citadelId) {
        this.citadelId = citadelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequisitionCriteria that = (RequisitionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(coordinate, that.coordinate) &&
            Objects.equals(state, that.state) &&
            Objects.equals(date, that.date) &&
            Objects.equals(buildTypeId, that.buildTypeId) &&
            Objects.equals(citadelId, that.citadelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinate, state, date, buildTypeId, citadelId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequisitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (coordinate != null ? "coordinate=" + coordinate + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (buildTypeId != null ? "buildTypeId=" + buildTypeId + ", " : "") +
                (citadelId != null ? "citadelId=" + citadelId + ", " : "") +
            "}";
    }
}
