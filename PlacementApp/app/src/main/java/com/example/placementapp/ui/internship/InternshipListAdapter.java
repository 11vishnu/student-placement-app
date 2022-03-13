package com.example.placementapp.ui.internship;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.student.Student;
import com.example.placementapp.ui.student.StudentListAdapter;

import java.util.List;

public class InternshipListAdapter  extends RecyclerView.Adapter<InternshipListAdapter.MyViewHolder>{

    private List<InternshipDataModel> itemsList;
    private ClickListener clickListener;

    InternshipListAdapter(List<InternshipDataModel> mItemList){
        this.itemsList = mItemList;
    }

    @Override
    public InternshipListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_internship,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InternshipListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final InternshipDataModel item = itemsList.get(position);
        holder.student_name.setText(item.getStudentName());
        holder.reg_no.setText(String.valueOf(item.getRegNo()));
        holder.duration.setText(String.valueOf(item.getDuration()));
        holder.company_name.setText(String.valueOf(item.getCompanyName()));

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v,item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView reg_no,duration,student_name,company_name;
        private ConstraintLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            reg_no = itemView.findViewById(R.id.studentRegValue);
            duration = itemView.findViewById(R.id.durationValue);
            student_name = itemView.findViewById(R.id.studentNameValue);
            company_name = itemView.findViewById(R.id.companyNameValue);
            itemLayout =  itemView.findViewById(R.id.intern_item);
        }
    }


}
