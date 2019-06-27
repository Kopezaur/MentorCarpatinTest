package application.main.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MountainRegion.
 */
@Entity
@Table(name = "mountain_region")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mountainregion")
public class MountainRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "surface")
    private Integer surface;

    @Column(name = "top_peak_name")
    private String topPeakName;

    @Column(name = "top_peak_height")
    private Integer topPeakHeight;

    @OneToOne
    @JoinColumn(unique = true)
    private Country country;

    @OneToOne
    @JoinColumn(unique = true)
    private RescueService rescueService;

    @ManyToMany(mappedBy = "mountainRegions")
    @JsonIgnore
    private Set<Route> routes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public MountainRegion regionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getSurface() {
        return surface;
    }

    public MountainRegion surface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public String getTopPeakName() {
        return topPeakName;
    }

    public MountainRegion topPeakName(String topPeakName) {
        this.topPeakName = topPeakName;
        return this;
    }

    public void setTopPeakName(String topPeakName) {
        this.topPeakName = topPeakName;
    }

    public Integer getTopPeakHeight() {
        return topPeakHeight;
    }

    public MountainRegion topPeakHeight(Integer topPeakHeight) {
        this.topPeakHeight = topPeakHeight;
        return this;
    }

    public void setTopPeakHeight(Integer topPeakHeight) {
        this.topPeakHeight = topPeakHeight;
    }

    public Country getCountry() {
        return country;
    }

    public MountainRegion country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public RescueService getRescueService() {
        return rescueService;
    }

    public MountainRegion rescueService(RescueService rescueService) {
        this.rescueService = rescueService;
        return this;
    }

    public void setRescueService(RescueService rescueService) {
        this.rescueService = rescueService;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public MountainRegion routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public MountainRegion addRoute(Route route) {
        this.routes.add(route);
        route.getMountainRegions().add(this);
        return this;
    }

    public MountainRegion removeRoute(Route route) {
        this.routes.remove(route);
        route.getMountainRegions().remove(this);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MountainRegion)) {
            return false;
        }
        return id != null && id.equals(((MountainRegion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MountainRegion{" +
            "id=" + getId() +
            ", regionName='" + getRegionName() + "'" +
            ", surface=" + getSurface() +
            ", topPeakName='" + getTopPeakName() + "'" +
            ", topPeakHeight=" + getTopPeakHeight() +
            "}";
    }
}
