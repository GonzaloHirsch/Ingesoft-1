package ar.edu.itba.ingesoft.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;

public class SearchDiffUtil extends DiffUtil.Callback {

    List<Map.Entry<Course,List<User>>> oldList;
    List<Map.Entry<Course,List<User>>> newList;

    public SearchDiffUtil(List<Map.Entry<Course,List<User>>> oldList, List<Map.Entry<Course,List<User>>> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getKey().getCode().equals(newList.get(newItemPosition).getKey().getCode());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //todo compare courses list
        return false;
    }
}
