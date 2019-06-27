package application.main.domain;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RescueService.
 */
@Entity
@Table(name = "rescue_service")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rescueservice")
public class RescueService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_employees")
    private Integer noOfEmployees;

    @OneToMany(mappedBy = "author")
    private Set<Report> reports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public RescueService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfEmployees() {
        return noOfEmployees;
    }

    public RescueService noOfEmployees(Integer noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
        return this;
    }

    public void setNoOfEmployees(Integer noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public RescueService reports(Set<Report> reports) {
        this.reports = reports;
        return this;
    }

    public RescueService addReport(Report report) {
        this.reports.add(report);
        report.setAuthor(this);
        return this;
    }

    public RescueService removeReport(Report report) {
        this.reports.remove(report);
        report.setAuthor(null);
        return this;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RescueService)) {
            return false;
        }
        return id != null && id.equals(((RescueService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RescueService{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", noOfEmployees=" + getNoOfEmployees() +
            "}";
    }
}
