package com.example.elearningversion2.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elearningversion2.databinding.ItemMaterialBinding;
import com.example.elearningversion2.models.ModelMaterial;
import java.util.ArrayList;

public class StudentMaterialAdapter extends RecyclerView.Adapter<StudentMaterialAdapter.Holder>{

    OnItemClick onClick ;
    ArrayList<ModelMaterial> list = new ArrayList<>();
    public void setList(ArrayList<ModelMaterial> list) {
        this.list = list;
    }
    public void setOnItemClick(OnItemClick onItemClick){
        this.onClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMaterialBinding binding = ItemMaterialBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
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
        ItemMaterialBinding binding ;
        public Holder(ItemMaterialBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClick!= null)
                        onClick.onClick(list.get(getLayoutPosition()));
                }
            });
        }
        public void bind (ModelMaterial m)
        {
            binding.textName.setText(m.getFileName());
            binding.link.setText(m.getFileLink());
        }
    }
    public interface OnItemClick{
        void onClick(ModelMaterial m);
    }
}
