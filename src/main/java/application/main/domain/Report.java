package application.main.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_victims")
    private Integer noOfVictims;

    @Column(name = "severity")
    private String severity;

    @Column(name = "day")
    private String day;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @OneToOne
    @JoinColumn(unique = true)
    private MountainRegion mountainRegion;

    @ManyToOne
    @JsonIgnoreProperties("reports")
    private RescueService author;

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

    public Report description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfVictims() {
        return noOfVictims;
    }

    public Report noOfVictims(Integer noOfVictims) {
        this.noOfVictims = noOfVictims;
        return this;
    }

    public void setNoOfVictims(Integer noOfVictims) {
        this.noOfVictims = noOfVictims;
    }

    public String getSeverity() {
        return severity;
    }

    public Report severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDay() {
        return day;
    }

    public Report day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public Report month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public Report year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MountainRegion getMountainRegion() {
        return mountainRegion;
    }

    public Report mountainRegion(MountainRegion mountainRegion) {
        this.mountainRegion = mountainRegion;
        return this;
    }

    public void setMountainRegion(MountainRegion mountainRegion) {
        this.mountainRegion = mountainRegion;
    }

    public RescueService getAuthor() {
        return author;
    }

    public Report author(RescueService rescueService) {
        this.author = rescueService;
        return this;
    }

    public void setAuthor(RescueService rescueService) {
        this.author = rescueService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", noOfVictims=" + getNoOfVictims() +
            ", severity='" + getSeverity() + "'" +
            ", day='" + getDay() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            "}";
    }
}
