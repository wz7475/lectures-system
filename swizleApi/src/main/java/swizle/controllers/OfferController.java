package swizle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import swizle.models.Offer;
import swizle.models.dto.OfferDto;
import swizle.services.interfaces.IOfferDataService;
import swizle.utils.Constants;
import swizle.utils.dtoConverters.OfferDtoConverter;

import java.util.List;


@RestController
@ResponseBody
public class OfferController {
    private final IOfferDataService offerDataService;

    @Autowired
    public OfferController(@Qualifier(Constants.OfferServiceQualifier) IOfferDataService offerDataService) {
        this.offerDataService = offerDataService;
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
    public Offer addOffer(@RequestBody OfferDto offerDto) {
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
}
