package ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import ar.edu.itba.ingesoft.Classes.Message;

public class ChatMessagesDiffUtil extends DiffUtil.Callback {

    private List<Message> oldList;
    private List<Message> newList;

    public ChatMessagesDiffUtil(List<Message> oldList, List<Message> newList){
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
        Message oldItem = this.oldList.get(oldItemPosition);
        Message newItem = this.newList.get(newItemPosition);

        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
    }
}
