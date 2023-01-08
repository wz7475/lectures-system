package swizle.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Offers")
public class Offer implements IModel {
    @Id
    @SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    private long id;

    @Column(nullable = false)
    private long sellerId;

    @Column(nullable = false)
    private long offeredLectureId;

    @Column(nullable = true)
    private long returnedLectureId;

    public Offer() {}

    public Offer(long sellerId, long offeredLectureId) {
        this.sellerId = sellerId;
        this.offeredLectureId = offeredLectureId;
    }

    public Offer(long id, long sellerId, long offeredLectureId, long returnedLectureId) {
        this.id = id;
        this.sellerId = sellerId;
        this.offeredLectureId = offeredLectureId;
        this.returnedLectureId = returnedLectureId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getOfferedLectureId() {
        return offeredLectureId;
    }

    public void setOfferedLectureId(long offeredLectureId) {
        this.offeredLectureId = offeredLectureId;
    }

    public long getReturnedLectureId() {
        return returnedLectureId;
    }

    public void setReturnedLectureId(long returnedLectureId) {
        this.returnedLectureId = returnedLectureId;
    }
}
