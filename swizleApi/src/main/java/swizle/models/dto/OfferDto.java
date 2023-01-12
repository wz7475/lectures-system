package swizle.models.dto;

public class OfferDto {
    private long sellerId;
    private long offeredLectureId;
    private long returnedLectureId;

//    public OfferDto(long sellerId, long offeredLectureId) {
//        this.sellerId = sellerId;
//        this.offeredLectureId = offeredLectureId;
//    }

    public OfferDto(long sellerId, long offeredLectureId, long returnedLectureId) {
        this.sellerId = sellerId;
        this.offeredLectureId = offeredLectureId;
        this.returnedLectureId = returnedLectureId;
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

