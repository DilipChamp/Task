package com.e.task.individual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.task.MainActivity;
import com.e.task.R;
import com.e.task.Search;
import com.e.task.SearchAdapter;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndividualActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private MenuAdapter adapter;
    private ArrayList<Menu> menuArrayList;

    private static final String URL = "https://api.documenu.com/v2/restaurant/4072702673999819?key=508ab21d1d779c46f1ac0e70d12df02e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        recyclerView = findViewById(R.id.recyclerview);
        fetchlist();
    }

    public void fetchlist() {
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
                Toast.makeText(IndividualActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        menuArrayList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(response);

            JSONObject jo = jsonObject.getJSONObject("result");
            JSONArray array = jo.getJSONArray("menus");

            JSONObject menu = array.getJSONObject(0);

            JSONArray menu1 = menu.getJSONArray("menu_sections");

            for(int i=0;i<menu1.length();i++)
            {
                Menu menulist = new Menu();
                JSONObject job=menu1.getJSONObject(i);
                menulist.setMenuname(job.getString("section_name"));
                menuArrayList.add(menulist);

            }

            adapter = new MenuAdapter(menuArrayList, IndividualActivity.this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}