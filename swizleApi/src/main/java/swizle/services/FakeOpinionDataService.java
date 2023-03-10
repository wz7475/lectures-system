package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.Opinion;
import swizle.services.interfaces.IOpinionDataService;
import swizle.utils.Constants;
import swizle.utils.Helpers;

import java.util.ArrayList;
import java.util.List;

@Service(Constants.FakeOpinionServiceQualifier)
public class FakeOpinionDataService implements IOpinionDataService {
    private final ArrayList<Opinion> opinions = new ArrayList<>();

    @Override
    public List<Opinion> getItems() {
        return opinions;
    }

    @Override
    public Opinion getItemById(long id) {
        for (Opinion opinion : opinions) {
            if (opinion.getId() == id) {
                return opinion;
            }
        }

        return null;
    }

    @Override
    public List<Opinion> getOpinionsByLectureId(long lectureId) {
        ArrayList<Opinion> opinionsForLecture = new ArrayList<>();
        for (Opinion opinion : opinions) {
            if (opinion.getLectureId() == lectureId) {
                opinionsForLecture.add(opinion);
            }
        }

        return opinionsForLecture;
    }

    @Override
    public List<Opinion> getOpinionsByUserId(long userId) {
        ArrayList<Opinion> opinionsForUser = new ArrayList<>();
        for (Opinion opinion : opinions) {
            if (opinion.getUserId() == userId) {
                opinionsForUser.add(opinion);
            }
        }

        return opinionsForUser;
    }

    @Override
    public Opinion addItem(Opinion opinion) {
        Opinion opinionToAdd = new Opinion(
                Helpers.getUniqueId(opinions),
                opinion.getLectureId(),
                opinion.getUserId(),
                opinion.getCreatedAt(),
                opinion.getContent()
        );

        opinions.add(opinionToAdd);
        return opinionToAdd;
    }

    @Override
    public void editItem(long id, Opinion newData) {
        Opinion opinionToEdit = getItemById(id);

        opinionToEdit.setLectureId(newData.getLectureId());
        opinionToEdit.setUserId(newData.getUserId());
        opinionToEdit.setCreatedAt(newData.getCreatedAt());
        opinionToEdit.setContent(newData.getContent());
    }

    @Override
    public void deleteItem(long id) {
        opinions.removeIf(opinion -> opinion.getId() == id);
    }
}
