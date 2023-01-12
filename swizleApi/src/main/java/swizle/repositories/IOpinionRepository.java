package swizle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swizle.models.Opinion;

@Repository
public interface IOpinionRepository extends JpaRepository<Opinion, Long> {
}
