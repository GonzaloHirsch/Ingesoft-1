package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.utils.Pair;

public class ProfileDataAdapter extends RecyclerView.Adapter<ProfileDataAdapter.ProfileDataViewHolder> {

    private ArrayList<Pair<String, String>> dataSet=new ArrayList<>();


    public void setNewUserData(User user){
        if(dataSet.isEmpty()) {
            dataSet.add(new Pair<>("Email", user.getMail()));
            dataSet.add(new Pair<>("University", user.getUniversidad().getName()));
            dataSet.add(new Pair<>("Courses Taught", ""));
        }
        else
        {
        }
         notifyDataSetChanged();
    }

    public void setCourses(String courses){
        dataSet.get(2).second = courses;
        notifyItemChanged(2);
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


        public ProfileDataViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemProfileDataTitle);
            itemDescription = itemView.findViewById(R.id.itemProfileDataDescription);
        }


    }
}
