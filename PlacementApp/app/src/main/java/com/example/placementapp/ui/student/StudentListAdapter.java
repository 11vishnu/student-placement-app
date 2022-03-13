package com.example.placementapp.ui.student;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.staff.Staff;

import java.util.List;

public class StudentListAdapter  extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    private List<Student> itemsList;
    private ClickListener clickListener;

    StudentListAdapter(List<Student> mItemList){
        this.itemsList = mItemList;
    }

    @Override
    public com.example.placementapp.ui.student.StudentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new StudentListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.placementapp.ui.student.StudentListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Student item = itemsList.get(position);
        holder.name.setText(item.getStudentName());
        holder.department.setText(String.valueOf(item.getStudentDepartment()));
        holder.email.setText(String.valueOf(item.getStudentMail()));

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

        public TextView name,department,email;
        private ConstraintLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentNameValue);
            department = itemView.findViewById(R.id.studentDepartmentNameValue);
            email =  itemView.findViewById(R.id.studentEmailNameValue);
            itemLayout =  itemView.findViewById(R.id.student_item);
        }
    }


}

