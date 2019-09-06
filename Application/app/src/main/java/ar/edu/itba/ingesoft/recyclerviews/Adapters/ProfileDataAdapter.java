package ar.edu.itba.ingesoft.recyclerviews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.ingesoft.R;

public class ProfileDataAdapter extends RecyclerView.Adapter<ProfileDataAdapter.ProfileDataViewHolder> {

    private ArrayList<Pair<String, String>> dataSet = new ArrayList<>();

    public ProfileDataAdapter(){
        this.dataSet.add(new Pair<>("Name", "Pablo"));
        this.dataSet.add(new Pair<>("University", "Itba"));
        this.dataSet.add(new Pair<>("Likes", "Pizza"));
    }

    @NonNull
    @Override
    public ProfileDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_data, parent, false);

        ProfileDataViewHolder dataViewHolder = new ProfileDataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDataViewHolder holder, int position) {
        holder.itemTitle.setText(dataSet.get(position).first);
        holder.itemDescription.setText(dataSet.get(position).second);
        //Change status when selected, inform tracker
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public long getItemId(int position){
        return (long) position;
    }

    public static class ProfileDataViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;
        public TextView itemDescription;

        public void bind(){
            itemView.setActivated(false);
        }

        public void bind(boolean isActivated){
            itemView.setActivated(isActivated);
        }

        public ProfileDataViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemProfileDataTitle);
            itemDescription = itemView.findViewById(R.id.itemProfileDataDescription);
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails(){
            return new ItemDetailsLookup.ItemDetails<Long>(){

                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }
}
