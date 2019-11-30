package ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.User;

public class UserDiffutil extends DiffUtil.Callback {

    private List<User> oldList;
    private List<User> newList;

    public UserDiffutil(List<User> oldList, List<User> newList){
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
        return areContentsTheSame(oldItemPosition, newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //todo hacer esta comparacion mas compleja...
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
    }
}
