package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.Offer;
import swizle.models.dto.OfferDto;
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

    @Autowired
    public OfferController(
            @Qualifier(Constants.OfferServiceQualifier) IOfferDataService offerDataService,
            @Qualifier(Constants.Validator) Validator validator) {
        this.offerDataService = offerDataService;
        this.validator = validator;
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



//    private void validateLecture(Lecture lecture) {
//        if(lecture == null)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested lecture does not exist.");
//    }
//
//    private void validateOpinion(Opinion opinion) {
//        if(opinion == null)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested opinion does not exist.");
//    }

//    @PutMapping(value = "/api/offers/accept", headers = { "content-type=application/json" })
//    public void acceptOffer(@RequestBody long buyerId) {
//        return buyerId;
//    }
}
