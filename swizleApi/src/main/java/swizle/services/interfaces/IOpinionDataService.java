package swizle.services.interfaces;

import swizle.models.Opinion;

import java.util.List;

public interface IOpinionDataService extends IModifiableDataService<Opinion> {
    public List<Opinion> getOpinionsByLectureId(long lectureId);
    public List<Opinion> getOpinionsByUserId(long lectureId);
}
