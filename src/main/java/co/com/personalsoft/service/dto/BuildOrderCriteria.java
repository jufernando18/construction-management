package co.com.personalsoft.service.dto;

import co.com.personalsoft.domain.enumeration.BuildOrderState;
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
 * Criteria class for the {@link co.com.personalsoft.domain.BuildOrder} entity. This class is used
 * in {@link co.com.personalsoft.web.rest.BuildOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /build-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BuildOrderCriteria implements Serializable, Criteria {

    /**
     * Class for filtering BuildOrderState
     */
    public static class BuildOrderStateFilter extends Filter<BuildOrderState> {

        public BuildOrderStateFilter() {}

        public BuildOrderStateFilter(BuildOrderStateFilter filter) {
            super(filter);
        }

        @Override
        public BuildOrderStateFilter copy() {
            return new BuildOrderStateFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BuildOrderStateFilter state;

    private LocalDateFilter start;

    private LocalDateFilter finish;

    private LongFilter requisitionId;

    public BuildOrderCriteria() {}

    public BuildOrderCriteria(BuildOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.start = other.start == null ? null : other.start.copy();
        this.finish = other.finish == null ? null : other.finish.copy();
        this.requisitionId = other.requisitionId == null ? null : other.requisitionId.copy();
    }

    @Override
    public BuildOrderCriteria copy() {
        return new BuildOrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BuildOrderStateFilter getState() {
        return state;
    }

    public void setState(BuildOrderStateFilter state) {
        this.state = state;
    }

    public LocalDateFilter getStart() {
        return start;
    }

    public void setStart(LocalDateFilter start) {
        this.start = start;
    }

    public LocalDateFilter getFinish() {
        return finish;
    }

    public void setFinish(LocalDateFilter finish) {
        this.finish = finish;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BuildOrderCriteria that = (BuildOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(state, that.state) &&
            Objects.equals(start, that.start) &&
            Objects.equals(finish, that.finish) &&
            Objects.equals(requisitionId, that.requisitionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, start, finish, requisitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (start != null ? "start=" + start + ", " : "") +
                (finish != null ? "finish=" + finish + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
            "}";
    }
}
