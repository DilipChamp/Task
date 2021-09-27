package com.e.task.Items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.task.R;


import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private ArrayList<Item> Itemlist;
    private Context context;

    public ItemAdapter(ArrayList<Item> itemlist, Context context) {
        Itemlist = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new ItemAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = Itemlist.get(position);
        holder.itname.setText(item.getItemname());
        holder.itprice.setText(item.getItemprice());
        holder.website.setText(item.getWebsite());
    }

    @Override
    public int getItemCount() {
        return Itemlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView itname,itprice,website;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            itname = itemView.findViewById(R.id.itname);
            itprice=itemView.findViewById(R.id.itprice);
            website=itemView.findViewById(R.id.website);
        }
    }
}
