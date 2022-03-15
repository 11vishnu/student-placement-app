package com.example.placementapp.ui.staff;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.ui.company.ClickListener;

import java.util.List;

public class StaffListAdapter  extends RecyclerView.Adapter<StaffListAdapter.MyViewHolder> {

private List<Staff> itemsList;
private ClickListener clickListener;

    StaffListAdapter(List<Staff> mItemList){
        this.itemsList = mItemList;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff,parent,false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
final Staff item = itemsList.get(position);
        holder.name.setText(item.getStaffName());
        holder.department.setText(String.valueOf(item.getStaffDepartment()));
    holder.email.setText(String.valueOf(item.getStaffMail()));

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
        name = itemView.findViewById(R.id.staffNameValue);
        department = itemView.findViewById(R.id.staffDepartmentNameValue);
        email =  itemView.findViewById(R.id.staffEmailNameValue);
        itemLayout =  itemView.findViewById(R.id.staff_item);
    }
}


}

