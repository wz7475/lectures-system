package swizle.utils;

import swizle.models.IModel;

import java.util.List;

public class Helpers {
    public static long getUniqueId(List<? extends IModel> collection) {
        long maxId = 0;

        for(IModel element : collection) {
            if(element.getId() > maxId)
                maxId = element.getId();
        }

        return maxId + 1;
    }
}
