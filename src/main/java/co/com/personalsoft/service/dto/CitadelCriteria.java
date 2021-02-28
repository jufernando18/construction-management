package co.com.personalsoft.service.dto;

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
 * Criteria class for the {@link co.com.personalsoft.domain.Citadel} entity. This class is used
 * in {@link co.com.personalsoft.web.rest.CitadelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /citadels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitadelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private LocalDateFilter start;

    private LocalDateFilter finish;

    public CitadelCriteria() {}

    public CitadelCriteria(CitadelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.start = other.start == null ? null : other.start.copy();
        this.finish = other.finish == null ? null : other.finish.copy();
    }

    @Override
    public CitadelCriteria copy() {
        return new CitadelCriteria(this);
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

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitadelCriteria that = (CitadelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(start, that.start) &&
            Objects.equals(finish, that.finish)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, start, finish);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitadelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (start != null ? "start=" + start + ", " : "") +
                (finish != null ? "finish=" + finish + ", " : "") +
            "}";
    }
}
