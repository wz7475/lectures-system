package swizle.utils.dtoConverters;

import swizle.models.Offer;
import swizle.models.dto.OfferDto;

public class OfferDtoConverter {
    public static Offer toModel(OfferDto offerDto) {
        return new Offer(offerDto.getSellerId(), offerDto.getOfferedLectureId(), offerDto.getReturnedLectureId());
    }

    public static OfferDto toDto(Offer offer) {
        return new OfferDto(offer.getSellerId(), offer.getOfferedLectureId(), offer.getReturnedLectureId());
    }
}
