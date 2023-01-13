package swizle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swizle.models.Offer;
@Repository
public interface IOfferRepository extends JpaRepository<Offer, Long> {
    public Offer findBySellerIdAndOfferedLectureId(long sellerId, long offeredLectureId);
}
