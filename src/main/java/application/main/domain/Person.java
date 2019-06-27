package application.main.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * The Person entity.
 */
@ApiModel(description = "The Person entity.")
@Entity
@Table(name = "person")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "running_coefficient")
    private Double runningCoefficient;

    @Column(name = "performance_coefficient")
    private Double performanceCoefficient;

    @OneToMany(mappedBy = "person")
    private Set<RoutePerformance> routePerformances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public Person birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Double getWeight() {
        return weight;
    }

    public Person weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Person height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getRunningCoefficient() {
        return runningCoefficient;
    }

    public Person runningCoefficient(Double runningCoefficient) {
        this.runningCoefficient = runningCoefficient;
        return this;
    }

    public void setRunningCoefficient(Double runningCoefficient) {
        this.runningCoefficient = runningCoefficient;
    }

    public Double getPerformanceCoefficient() {
        return performanceCoefficient;
    }

    public Person performanceCoefficient(Double performanceCoefficient) {
        this.performanceCoefficient = performanceCoefficient;
        return this;
    }

    public void setPerformanceCoefficient(Double performanceCoefficient) {
        this.performanceCoefficient = performanceCoefficient;
    }

    public Set<RoutePerformance> getRoutePerformances() {
        return routePerformances;
    }

    public Person routePerformances(Set<RoutePerformance> routePerformances) {
        this.routePerformances = routePerformances;
        return this;
    }

    public Person addRoutePerformance(RoutePerformance routePerformance) {
        this.routePerformances.add(routePerformance);
        routePerformance.setPerson(this);
        return this;
    }

    public Person removeRoutePerformance(RoutePerformance routePerformance) {
        this.routePerformances.remove(routePerformance);
        routePerformance.setPerson(null);
        return this;
    }

    public void setRoutePerformances(Set<RoutePerformance> routePerformances) {
        this.routePerformances = routePerformances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", runningCoefficient=" + getRunningCoefficient() +
            ", performanceCoefficient=" + getPerformanceCoefficient() +
            "}";
    }
}
