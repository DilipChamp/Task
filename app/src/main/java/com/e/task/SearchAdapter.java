package com.e.task;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.task.individual.IndividualActivity;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Search> search;
    private Context context;

    public SearchAdapter(ArrayList<Search> search, Context context) {
        this.search = search;
        this.context = context;
    }
    public void filterList(ArrayList<Search> filterllist) {
        search = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Search searches = search.get(position);
        holder.name.setText(searches.getCat_name());
        holder.phone.setText(searches.getCat_pho());
        holder.address.setText(searches.getCat_address());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(context, IndividualActivity.class);
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return search.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView name,phone,address;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address=itemView.findViewById(R.id.address);
            linearLayout=itemView.findViewById(R.id.linear);
        }
    }
}
