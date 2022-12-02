package swizle.services;

import org.springframework.stereotype.Service;
import swizle.models.Lecture;
import swizle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Service(Constants.FakeLectureServiceQualifier)
public class FakeLectureDataService implements IModifiableDataService<Lecture> {
    private final ArrayList<Lecture> lectures = new ArrayList<>();

    @Override
    public List<Lecture> getItems() {
        return lectures;
    }

    @Override
    public Lecture getItemById(long id) {
        for(Lecture lecture : lectures) {
            if(lecture.getId() == id)
                return lecture;
        }

        return null;
    }

    @Override
    public void addItem(Lecture item) {
        lectures.add(new Lecture(lectures.size() + 1, item.getName(), item.getDayOfWeek(), item.getBeginTime(), item.getDuration()));
    }

    @Override
    public void editItem(long id, Lecture newData) {
        Lecture lectureToEdit = getItemById(id);

        lectureToEdit.setName(newData.getName());
        lectureToEdit.setBeginTime(newData.getBeginTime());
        lectureToEdit.setDayOfWeek(newData.getDayOfWeek());
        lectureToEdit.setDuration(newData.getDuration());
    }

    @Override
    public void deleteItem(long id) {
        lectures.removeIf(lecture -> lecture.getId() == id);
    }
}
