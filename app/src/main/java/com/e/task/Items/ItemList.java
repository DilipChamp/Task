package com.e.task.Items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.task.R;
import com.e.task.individual.IndividualActivity;
import com.e.task.individual.Menu;
import com.e.task.individual.MenuAdapter;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {
    String menuname;
    private RecyclerView recyclerView;

    private ItemAdapter adapter;
    private ArrayList<Item> itemArrayList;

    private static final String URL = "https://api.documenu.com/v2/restaurant/4072702673999819?key=508ab21d1d779c46f1ac0e70d12df02e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        recyclerView = findViewById(R.id.recycleview);
        Intent intent = getIntent();
        menuname = intent.getStringExtra("sectionname");
        fetchItem();
    }

    public void fetchItem() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim() != null) {

                    showJSON(response);

                } else {

                    showJSON(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemList.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        itemArrayList = new ArrayList<>();
        try {
            Item item = new Item();
            JSONObject jsonObject = new JSONObject(response);

            JSONObject jo = jsonObject.getJSONObject("result");
            JSONArray array = jo.getJSONArray("menus");
            item.setWebsite(jo.getString("restaurant_website"));
            JSONObject menu = array.getJSONObject(0);

            JSONArray menu1 = menu.getJSONArray("menu_sections");

            for (int i = 0; i < menu1.length(); i++) {
                JSONObject job = menu1.getJSONObject(i);
                if (job.getString("section_name").equals(menuname)) {
                    JSONArray item1 = job.getJSONArray("menu_items");
                    for (int j = 0; j < item1.length(); j++) {

                        JSONObject job1 = item1.getJSONObject(j);
                        item.setItemname(job1.getString("name"));
                        item.setItemprice(job1.getString("price"));
                        System.out.println(job1.getString("price"));
                        itemArrayList.add(item);
                    }


                }

            }

            adapter = new ItemAdapter(itemArrayList, ItemList.this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}