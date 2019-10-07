package ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;

public class SearchDiffUtil extends DiffUtil.Callback {

    List<Course> oldList;
    List<Course> newList;

    public SearchDiffUtil(List<Course> oldList, List<Course> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        if(newList!=null)
            return newList.size();
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        String oldItemCode = oldList.get(oldItemPosition).getCode();
        String newItemCode = newList.get(newItemPosition).getCode();

        //return oldList.get(oldItemPosition).getCode().equals(newList.get(newItemPosition).getCode());
        if(oldItemCode!=null && newItemCode!=null)
        return oldItemCode.equals(newItemCode);
        else return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
        //return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
    }
}
