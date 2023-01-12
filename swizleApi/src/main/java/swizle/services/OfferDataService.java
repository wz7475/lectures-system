package swizle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swizle.models.Offer;
import swizle.repositories.IOfferRepository;
import swizle.services.interfaces.IOfferDataService;
import swizle.utils.Constants;

import java.util.List;

@Service(Constants.OfferServiceQualifier)
public class OfferDataService implements IOfferDataService {
    private final IOfferRepository offerRepository;

    @Autowired
    public OfferDataService(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> getItems() {
        return offerRepository.findAll();
    }

    @Override
    public Offer getItemById(long id) {
        return offerRepository.getReferenceById(id);
    }

    @Override
    public Offer addItem(Offer item) {
        try {
            return offerRepository.save(item);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving offer.");
        }
    }

    @Override
    public void editItem(long id, Offer newData) {
        Offer offerToModify = offerRepository.getReferenceById(id);

        offerToModify.setSellerId(newData.getSellerId());
        offerToModify.setOfferedLectureId(newData.getOfferedLectureId());
        offerToModify.setReturnedLectureId(newData.getReturnedLectureId());

        try {
            offerRepository.save(offerToModify);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving offer.");
        }
    }

    @Override
    public void deleteItem(long id) {
        offerRepository.deleteById(id);
    }
}
