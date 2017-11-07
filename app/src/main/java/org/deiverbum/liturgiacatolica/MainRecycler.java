package org.deiverbum.liturgiacatolica;

/**
 * Created by cedano on 19/10/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainRecycler extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    RecyclerView recyclerView;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList();
        arrayList.add(new DataModel("Item 1", R.drawable.app_breviario_100x100, "#09A9FF"));
        arrayList.add(new DataModel("Item 2", R.drawable.app_misa_100x100, "#3E51B1"));
        arrayList.add(new DataModel("Item 3", R.drawable.app_santo_100x100, "#673BB7"));
        arrayList.add(new DataModel("Item 4", R.drawable.ic_home_breviario, "#4BAA50"));
        arrayList.add(new DataModel("Item 5", R.drawable.app_misa_100x100, "#F94336"));
        arrayList.add(new DataModel("Item 6", R.drawable.app_logo, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        /*AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

    }
}
