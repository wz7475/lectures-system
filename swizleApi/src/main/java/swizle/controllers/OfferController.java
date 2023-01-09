package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.Offer;
import swizle.models.dto.OfferDto;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IOfferDataService;
import swizle.utils.Constants;
import swizle.utils.Validator;
import swizle.utils.dtoConverters.OfferDtoConverter;

import java.util.List;


@RestController
@ResponseBody
public class OfferController {
    private final IOfferDataService offerDataService;
    private final Validator validator;

    private final ILectureDataService lectureDataService;

    @Autowired
    public OfferController(
            @Qualifier(Constants.OfferServiceQualifier) IOfferDataService offerDataService,
            @Qualifier(Constants.Validator) Validator validator,
            @Qualifier(Constants.LectureServiceQualifier)ILectureDataService lectureDataService){
        this.offerDataService = offerDataService;
        this.validator = validator;
        this.lectureDataService = lectureDataService;
    }

    @GetMapping("/api/offer")
    public List<Offer> getOffers(String sessionKey)
    {
        this.validator.validateSessionKey(sessionKey);
        return offerDataService.getItems();
    }

    @GetMapping("/api/offer/{id}")
    public OfferDto getOfferById(String sessionKey, @PathVariable long id) {
        this.validator.validateSessionKey(sessionKey);
        Offer offer = offerDataService.getItemById(id);
        return OfferDtoConverter.toDto(offer);
    }

    @PostMapping(value = "/api/offers", headers = {"content-type=application/json"})
    public Offer addOffer(@RequestBody OfferDto offerDto, String sessionKey) {
        this.validator.validateSessionKey(sessionKey);
        return offerDataService.addItem(OfferDtoConverter.toModel(offerDto));
    }

    @PutMapping(value = "/api/offers/{id}", headers = {"content-type=application/json"})
    public void editOffer(@PathVariable long id, @RequestBody OfferDto offerDto, String sessionKey) {
        this.validator.validateSessionKey(sessionKey);
        offerDataService.editItem(id, OfferDtoConverter.toModel(offerDto));
    }

    @DeleteMapping("/api/offers/{id}")
    public void deleteOffer(String sessionKey, @PathVariable long id) {
        this.validator.validateSessionKey(sessionKey);
        offerDataService.deleteItem(id);
    }

    @PutMapping(value = "/api/offers/accept/{offerId}", headers = { "content-type=application/json" })
    public void acceptOffer(String sessionKey, @PathVariable long offerId, @RequestBody long buyerId) {
        this.validator.validateSessionKey(sessionKey);

        Offer offer = offerDataService.getItemById(offerId);
        long sellerId = offer.getSellerId();
        long offeredLectureId = offer.getOfferedLectureId();
        long returnedLectureId = offer.getReturnedLectureId();

        lectureDataService.optOutOfLecture(offeredLectureId, sellerId);
        lectureDataService.signUpForLecture(offeredLectureId, buyerId);

        lectureDataService.optOutOfLecture(returnedLectureId, buyerId);
        lectureDataService.signUpForLecture(returnedLectureId, sellerId);

        offerDataService.deleteItem(offerId);
    }
}
