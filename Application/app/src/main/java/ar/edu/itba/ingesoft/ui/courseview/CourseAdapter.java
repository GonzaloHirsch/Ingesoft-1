package ar.edu.itba.ingesoft.ui.courseview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context context;
    private Course course;


    public CourseAdapter(Context context, Course course){
        this.context = context;

    }

    public void changeDataSet(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.text.setText("");

    }

    @Override
    public int getItemCount() { return 0;}

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView text;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            text = (TextView) itemView.findViewById(R.id.course_text);

        }
    }
}
