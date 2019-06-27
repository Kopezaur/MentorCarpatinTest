package application.main.repository;

import application.main.domain.RescueService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RescueService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RescueServiceRepository extends JpaRepository<RescueService, Long> {

}
