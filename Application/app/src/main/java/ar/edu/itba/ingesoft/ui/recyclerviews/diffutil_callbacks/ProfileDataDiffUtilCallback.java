package ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ar.edu.itba.ingesoft.utils.Pair;

public class ProfileDataDiffUtilCallback extends DiffUtil.Callback {

    List<Pair<String, String>> oldList;
    List<Pair<String, String>> newList;

    public ProfileDataDiffUtilCallback(List<Pair<String ,String>> newList, List<Pair<String, String>> oldList){
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
        return oldList.get(oldItemPosition).first.equals(newList.get(newItemPosition).first);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).second.equals(newList.get(newItemPosition).second);
    }
}
