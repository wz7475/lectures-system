package swizle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swizle.models.Lecture;

public interface ILectureRepository extends JpaRepository<Lecture, Long> {
}
