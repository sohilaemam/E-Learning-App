package com.example.elearningversion2.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elearningversion2.databinding.ItemGradeBinding;
import com.example.elearningversion2.models.ModelMember;
import java.util.ArrayList;

public class CourseGradesAdapter extends RecyclerView.Adapter<CourseGradesAdapter.Holder>{

    ArrayList<ModelMember> list = new ArrayList<>();
    public void setList(ArrayList<ModelMember> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGradeBinding binding = ItemGradeBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        ItemGradeBinding binding ;
        public Holder(ItemGradeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ModelMember m)
        {
            binding.quizGrade.setText(m.getQuizGrade()+"");
            binding.attendGrade.setText(m.getAttendanceGrade()+"");
            binding.studentId.setText(m.getStudentId());
        }
    }
}
