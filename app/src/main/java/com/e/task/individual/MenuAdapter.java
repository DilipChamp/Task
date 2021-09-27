package com.e.task.individual;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.task.Items.ItemList;
import com.e.task.R;
import com.e.task.Search;
import com.e.task.SearchAdapter;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private ArrayList<Menu> menulist;
    private Context context;

    public MenuAdapter(ArrayList<Menu> menulist, Context context) {
        this.menulist = menulist;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_listitem, parent, false);
        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        Menu searches = menulist.get(position);
        holder.name.setText(searches.getMenuname());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ItemList.class);
                intent.putExtra("sectionname",searches.getMenuname());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menulist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView name;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            name = itemView.findViewById(R.id.menuname);
            cardView=itemView.findViewById(R.id.cardlinear);
        }
    }
}
