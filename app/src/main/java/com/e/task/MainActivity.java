package com.e.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SearchAdapter adapter;
    private ArrayList<Search> searchArrayList;

    private static final String URL = "https://api.documenu.com/v2/restaurant/4072702673999819?key=508ab21d1d779c46f1ac0e70d12df02e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fetchSearch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        ArrayList<Search> filteredlist = new ArrayList<>();

        for (Search item : searchArrayList) {
            if (item.getCat_name().toLowerCase().contains(text.toLowerCase())||item.getCat_name().toUpperCase().contains(text.toUpperCase())) {

                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            adapter.filterList(filteredlist);
        }
    }

    public void fetchSearch() {
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
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        searchArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONObject jo = jsonObject.getJSONObject("result");

            Search search = new Search();
            search.setCat_name(jo.getString("restaurant_name"));
            search.setCat_pho(jo.getString("restaurant_phone"));

            JSONObject address=jo.getJSONObject("address");
            search.setCat_address(address.getString("city"));

            searchArrayList.add(search);

            adapter = new SearchAdapter(searchArrayList, MainActivity.this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}