package com.example.placementapp.ui.company;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.placementapp.R;
import java.lang.String;
import java.util.List;


class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder> {

    private List<Company> itemsList;
    private ClickListener clickListener;

    RecyclerviewItemAdapter(List<Company> mItemList){
        this.itemsList = mItemList;
    }

    @Override
    public RecyclerviewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerviewItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Company item = itemsList.get(position);
        holder.name.setText(item.getCompanyName());
        holder.location.setText(String.valueOf(item.getCompanyLocation()));

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

        public TextView name,location;
        private ConstraintLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.companyNameValue);
            location = itemView.findViewById(R.id.locationNameValue);
            itemLayout =  itemView.findViewById(R.id.company_item);
        }
    }


}
