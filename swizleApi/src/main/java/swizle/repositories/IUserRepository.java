package swizle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swizle.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    public User findByNameAndPassword(String name, String password);
}
