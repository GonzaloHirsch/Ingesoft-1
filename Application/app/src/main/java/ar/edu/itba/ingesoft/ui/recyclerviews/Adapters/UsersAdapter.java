package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.UserDiffutil;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> users = new ArrayList<>();

    public UsersAdapter(String courseId){
        //todo ver la fucking race condition
        users = CoursesTeachersCache.getCourseTeachers().get(courseId);
    }

    public void update(List<User> newList){

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new UserDiffutil(this.users, newList));
        Log.v("UsersAdapter", "new List");

        if(newList!=null) {
            users.clear();
            users.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new UsersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        User u = users.get(position);
        holder.title.setText(u.getName());
        holder.subtitle.setText(u.getUniversidad().getName());
        //todo holder.btn.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;
        Button btn;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemUserTitle);
            subtitle = itemView.findViewById(R.id.itemUserSubtitle);
            btn = itemView.findViewById(R.id.itemUserButton);
        }
    }
}
