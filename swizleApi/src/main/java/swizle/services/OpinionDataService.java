package swizle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swizle.models.Opinion;
import swizle.repositories.IOpinionRepository;
import swizle.services.interfaces.IOpinionDataService;
import swizle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Service(Constants.OpinionServiceQualifier)
public class OpinionDataService implements IOpinionDataService {
    private final IOpinionRepository opinionRepository;

    @Autowired
    public OpinionDataService(IOpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    @Override
    public List<Opinion> getItems() {
        return opinionRepository.findAll();
    }

    @Override
    public Opinion getItemById(long id) {
        if(!opinionRepository.existsById(id))
            return null;

        return opinionRepository.getReferenceById(id);
    }

    @Override
    public Opinion addItem(Opinion item) {
        return opinionRepository.save(item);
    }

    @Override
    public void editItem(long id, Opinion newData) {
        if(!opinionRepository.existsById(id))
            throw new IllegalArgumentException("Requested opinion does not exist.");

        Opinion requestedOpinion = opinionRepository.getReferenceById(id);

        requestedOpinion.setContent(newData.getContent());
        requestedOpinion.setCreatedAt(newData.getCreatedAt());
        requestedOpinion.setUser(newData.getUser());
        requestedOpinion.setLecture(newData.getLecture());

        opinionRepository.save(requestedOpinion);
    }

    @Override
    public void deleteItem(long id) {
        if(!opinionRepository.existsById(id))
            throw new IllegalArgumentException("Requested opinion does not exist.");

        opinionRepository.deleteById(id);
    }

    @Override
    public List<Opinion> getOpinionsByLectureId(long lectureId) {
        List<Opinion> result = new ArrayList<>();
        opinionRepository.findAll().forEach(opinion -> {
            if(opinion.getLecture().getId() == lectureId)
                result.add(opinion);
        });

        return result;
    }

    @Override
    public List<Opinion> getOpinionsByUserId(long userId) {
        List<Opinion> result = new ArrayList<>();
        opinionRepository.findAll().forEach(opinion -> {
            if(opinion.getUser().getId() == userId)
                result.add(opinion);
        });

        return result;
    }
}
