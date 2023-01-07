package swizle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swizle.models.Session;

import java.util.UUID;

@Repository
public interface ISessionRepository extends JpaRepository<Session, UUID> {
}
