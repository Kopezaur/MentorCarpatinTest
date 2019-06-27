package application.main.repository;

import application.main.domain.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Route entity.
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(value = "select distinct route from Route route left join fetch route.mountainRegions",
        countQuery = "select count(distinct route) from Route route")
    Page<Route> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct route from Route route left join fetch route.mountainRegions")
    List<Route> findAllWithEagerRelationships();

    @Query("select route from Route route left join fetch route.mountainRegions where route.id =:id")
    Optional<Route> findOneWithEagerRelationships(@Param("id") Long id);

}
