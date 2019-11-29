package ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Message;

public class ChatDiffUtil extends DiffUtil.Callback {

    private List<Chat> oldList;
    private List<Chat> newList;

    public ChatDiffUtil(List<Chat> oldList, List<Chat> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Chat oldItem = this.oldList.get(oldItemPosition);
        Chat newItem = this.newList.get(newItemPosition);

        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
    }
}
