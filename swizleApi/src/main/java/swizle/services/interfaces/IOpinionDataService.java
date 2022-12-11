package swizle.services.interfaces;

import swizle.models.Opinion;

import java.util.List;

public interface IOpinionDataService extends IModifiableDataService<Opinion> {
    public Opinion getOpinionById(int id);
    public List<Opinion> getOpinionsByLectureId(int lectureId);
    public List<Opinion> getOpinionsByUserId(int lectureId);
}
