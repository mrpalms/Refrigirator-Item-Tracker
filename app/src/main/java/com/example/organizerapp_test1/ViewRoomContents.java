package com.example.organizerapp_test1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewRoomContents extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "pass";
    public static final String EXTRA_MESSAGE1 = "";
    public Button btnAddItem;
    public TextView txtV_Title_Item;
    private TextView toolbar_title;
    public String selected_type;



    public Button btn_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_contents);
        Toolbar toolbar = findViewById(R.id.toolbar_viewroom);
        setSupportActionBar(toolbar);

        Intent intent_viewRoomName = getIntent();
       String homeRoomName = intent_viewRoomName.getStringExtra(MainActivity.EXTRA_MESSAGE);

       Intent intent_up = getIntent();
       String x = intent_up.getStringExtra(ListAdapter.EXTRA_MESSAGE);

        selected_type = homeRoomName;

        if (selected_type == null){
            selected_type = x;
        }
        else {
            selected_type = homeRoomName;
        }

        toolbar_title = findViewById(R.id.toolbar_title_viewroom);
        toolbar_title.setText(selected_type);


        TestDB db = new TestDB(this);

        List<ListData> dbItems = new ArrayList<>();
        dbItems = getData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ListAdapter adapter = new ListAdapter(this, dbItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }//oncreate


        public List<ListData> getData()
        {
            List<ListData> list = new ArrayList<>();
            TestDB db1 = new TestDB(this);
            db1.open();

            Cursor c = db1.getAllInfo();
            if (c.moveToFirst()) {
                do {
                    if (c.getString(1).contentEquals(selected_type)) {
                        list.add(new ListData(c.getString(1), c.getString(2), c.getLong(0)));
                    }

                } while (c.moveToNext());
            }
           db1.close();

            return list;
        }//getdata

    public void returnHome(View v)
    {
        super.onBackPressed();

    }//getroom

    public void checkFinish(boolean x){

        if (x){
            getFinish();
        }else {
            Toast.makeText(getApplicationContext(), "Bad Error", Toast.LENGTH_SHORT).show();
        }

    }//

    public void getFinish()
    {
       finish();
    }//


}//end