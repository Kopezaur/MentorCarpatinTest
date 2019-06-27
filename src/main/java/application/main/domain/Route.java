package application.main.domain;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Double length;

    @Column(name = "difficulty")
    private String difficulty;

    @OneToMany(mappedBy = "route")
    private Set<RoutePerformance> routePerformances = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "route_mountain_region",
               joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "mountain_region_id", referencedColumnName = "id"))
    private Set<MountainRegion> mountainRegions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Route name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public Route length(Double length) {
        this.length = length;
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Route difficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Set<RoutePerformance> getRoutePerformances() {
        return routePerformances;
    }

    public Route routePerformances(Set<RoutePerformance> routePerformances) {
        this.routePerformances = routePerformances;
        return this;
    }

    public Route addRoutePerformance(RoutePerformance routePerformance) {
        this.routePerformances.add(routePerformance);
        routePerformance.setRoute(this);
        return this;
    }

    public Route removeRoutePerformance(RoutePerformance routePerformance) {
        this.routePerformances.remove(routePerformance);
        routePerformance.setRoute(null);
        return this;
    }

    public void setRoutePerformances(Set<RoutePerformance> routePerformances) {
        this.routePerformances = routePerformances;
    }

    public Set<MountainRegion> getMountainRegions() {
        return mountainRegions;
    }

    public Route mountainRegions(Set<MountainRegion> mountainRegions) {
        this.mountainRegions = mountainRegions;
        return this;
    }

    public Route addMountainRegion(MountainRegion mountainRegion) {
        this.mountainRegions.add(mountainRegion);
        mountainRegion.getRoutes().add(this);
        return this;
    }

    public Route removeMountainRegion(MountainRegion mountainRegion) {
        this.mountainRegions.remove(mountainRegion);
        mountainRegion.getRoutes().remove(this);
        return this;
    }

    public void setMountainRegions(Set<MountainRegion> mountainRegions) {
        this.mountainRegions = mountainRegions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }
        return id != null && id.equals(((Route) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", length=" + getLength() +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
