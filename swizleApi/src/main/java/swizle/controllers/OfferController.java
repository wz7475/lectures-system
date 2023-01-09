package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swizle.models.Lecture;
import swizle.models.Offer;
import swizle.models.Opinion;
import swizle.models.User;
import swizle.models.dto.OfferDto;
import swizle.services.UserDataService;
import swizle.services.interfaces.IOfferDataService;
import swizle.services.interfaces.IUserDataService;
import swizle.utils.Constants;
import swizle.utils.Validator;
import swizle.utils.dtoConverters.OfferDtoConverter;

import java.util.List;
import java.util.UUID;


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
    public List<Offer> getOffers() {
        return offerDataService.getItems();
    }

    @GetMapping("/api/offer/{id}")
    public OfferDto getOfferById(@PathVariable long id) {
        Offer offer = offerDataService.getItemById(id);
        return OfferDtoConverter.toDto(offer);
    }

    @PostMapping(value = "/api/offers", headers = {"content-type=application/json"})
    public Offer addOffer(@RequestBody OfferDto offerDto, String sessionKey) {
        this.validator.validateSessionKey(sessionKey);
        return offerDataService.addItem(OfferDtoConverter.toModel(offerDto));
    }

    @PutMapping(value = "/api/offers/{id}", headers = {"content-type=application/json"})
    public void editOffer(@PathVariable long id, @RequestBody OfferDto offerDto) {
        offerDataService.editItem(id, OfferDtoConverter.toModel(offerDto));
    }

    @DeleteMapping("/api/offers/{id}")
    public void deleteOffer(@PathVariable long id) {
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
