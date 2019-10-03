package ar.edu.itba.ingesoft.recyclerviews.diffutil_callbacks;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Chat;

public class ChatDiffUtil extends DiffUtil.Callback {

    List<Chat> oldList;
    List<Chat> newList;


    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
