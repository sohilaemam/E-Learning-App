package com.example.elearningversion2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elearningversion2.R;
import com.example.elearningversion2.databinding.ItemusersBinding;
import com.example.elearningversion2.models.ModelCourse;

import java.util.ArrayList;

public class AdapterRecyclerCourses extends RecyclerView.Adapter<AdapterRecyclerCourses.Holder> {

    private ArrayList<ModelCourse> list;


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick ;

    public void setList(ArrayList<ModelCourse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemusersBinding binding =
                ItemusersBinding.inflate(LayoutInflater.from(parent.getContext())
                        ,parent,false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder{
        ItemusersBinding binding ;
        public Holder(ItemusersBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick !=null){
                        onItemClick.onClick(list.get(getLayoutPosition()));
                    }
                }
            });
        }
        public void bind(ModelCourse course){
            binding.textName.setText(course.getCourseName());
        }
    }

    public interface OnItemClick{
        void onClick(ModelCourse id);
    }


}
