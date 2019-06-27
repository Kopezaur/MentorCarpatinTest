package application.main.repository;

import application.main.domain.RoutePerformance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoutePerformance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoutePerformanceRepository extends JpaRepository<RoutePerformance, Long> {

}
