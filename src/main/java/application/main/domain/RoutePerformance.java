package application.main.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A RoutePerformance.
 */
@Entity
@Table(name = "route_performance")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "routeperformance")
public class RoutePerformance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "time")
    private Integer time;

    @Column(name = "pace")
    private Double pace;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "performance_coefficient")
    private Double performanceCoefficient;

    @ManyToOne
    @JsonIgnoreProperties("routePerformances")
    private Route route;

    @ManyToOne
    @JsonIgnoreProperties("routePerformances")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public RoutePerformance time(Integer time) {
        this.time = time;
        return this;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getPace() {
        return pace;
    }

    public RoutePerformance pace(Double pace) {
        this.pace = pace;
        return this;
    }

    public void setPace(Double pace) {
        this.pace = pace;
    }

    public Double getSpeed() {
        return speed;
    }

    public RoutePerformance speed(Double speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getPerformanceCoefficient() {
        return performanceCoefficient;
    }

    public RoutePerformance performanceCoefficient(Double performanceCoefficient) {
        this.performanceCoefficient = performanceCoefficient;
        return this;
    }

    public void setPerformanceCoefficient(Double performanceCoefficient) {
        this.performanceCoefficient = performanceCoefficient;
    }

    public Route getRoute() {
        return route;
    }

    public RoutePerformance route(Route route) {
        this.route = route;
        return this;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Person getPerson() {
        return person;
    }

    public RoutePerformance person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoutePerformance)) {
            return false;
        }
        return id != null && id.equals(((RoutePerformance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RoutePerformance{" +
            "id=" + getId() +
            ", time=" + getTime() +
            ", pace=" + getPace() +
            ", speed=" + getSpeed() +
            ", performanceCoefficient=" + getPerformanceCoefficient() +
            "}";
    }
}
