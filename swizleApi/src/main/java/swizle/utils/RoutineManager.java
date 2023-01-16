package swizle.utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import swizle.models.Offer;
import swizle.services.interfaces.ILectureDataService;
import swizle.services.interfaces.IOfferDataService;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class RoutineManager {
    private final IOfferDataService offerDataService;
    private final ILectureDataService lectureDataService;

    @Autowired
    public RoutineManager(@Qualifier(Constants.OfferServiceQualifier) IOfferDataService offerDataService,
                          @Qualifier(Constants.LectureServiceQualifier) ILectureDataService lectureDataService) {
        this.offerDataService = offerDataService;
        this.lectureDataService = lectureDataService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void reportTask() {
        List<Long> resolvedOffers = new ArrayList<>();

        for(Offer offer1 : offerDataService.getItems()) {
            for(Offer offer2 : offerDataService.getItems()) {
                if(offer1.getId() == offer2.getId()
                || resolvedOffers.contains(offer1.getId())
                || resolvedOffers.contains(offer2.getId()))
                    continue;

                if(offer1.getOfferedLectureId() == offer2.getReturnedLectureId()
                || offer1.getReturnedLectureId() == offer2.getOfferedLectureId()) {
                    String swapLog = "Swapping lectures assignments for offers of id " +
                            offer1.getId() +
                            " and " +
                            offer2.getId();

                    System.out.println(swapLog);

                    lectureDataService.signUpForLecture(offer1.getOfferedLectureId(), offer2.getSellerId());
                    lectureDataService.signUpForLecture(offer2.getOfferedLectureId(), offer1.getSellerId());

                    lectureDataService.optOutOfLecture(offer1.getOfferedLectureId(), offer1.getSellerId());
                    lectureDataService.optOutOfLecture(offer2.getOfferedLectureId(), offer2.getSellerId());

                    resolvedOffers.add(offer1.getId());
                    resolvedOffers.add(offer2.getId());
                }
            }
        }

        for(long offerId : resolvedOffers)
            offerDataService.deleteItem(offerId);
    }
}
