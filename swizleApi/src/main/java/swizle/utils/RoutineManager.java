package swizle.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import swizle.models.Offer;
import swizle.services.OfferDataService;
import swizle.services.interfaces.IOfferDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoutineManager {
    private final IOfferDataService offerDataService;

    @Autowired
    public RoutineManager(@Qualifier(Constants.OfferServiceQualifier) IOfferDataService offerDataService) {
        this.offerDataService = offerDataService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void reportTask() {
        System.out.println("Automatically resolving complementary offers...");
        List<Long> resolvedOffers = new ArrayList<>();

        for(Offer offer1 : offerDataService.getItems()) {
            for(Offer offer2 : offerDataService.getItems()) {
                if(offer1.getId() == offer2.getId()
                || resolvedOffers.contains(offer1.getId())
                || resolvedOffers.contains(offer2.getId()))
                    continue;

                if(offer1.getOfferedLectureId() == offer2.getReturnedLectureId()
                || offer1.getReturnedLectureId() == offer2.getOfferedLectureId()) {
                    System.out.print("Swapping lectures assignments for offers of id ");
                    System.out.print(offer1.getId());
                    System.out.print(" and ");
                    System.out.println(offer2.getId());
                    resolvedOffers.add(offer1.getId());
                    resolvedOffers.add(offer2.getId());
                }
            }
        }
    }
}
