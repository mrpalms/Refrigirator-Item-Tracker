package com.example.organizerapp_test1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE = "roomInfo";
    private Button btn_view;
    public Spinner HomeSpinner;
    public TextView title_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_view = findViewById(R.id.btnviewroom);
        title_activity = findViewById(R.id.toolbar_title_home);
        title_activity.setText("HOME");

        TestDB mainDB = new TestDB(this);

        /*
        mainDB.open();
        mainDB.removeAll();
        long id = mainDB.insertType("Produce", "Cabbage");
        id = mainDB.insertType("Produce", "Apples");
        id = mainDB.insertType("Meat & Poultry", "Steak");
        id = mainDB.insertType("Drinks", "Kombucha Orange & Lemons");
        mainDB.close();

         */

      HomeSpinner = findViewById(R.id.spinner_ViewRoom);

        ArrayList<String> roomNames = new ArrayList<String>();
        mainDB.open();
        Cursor mainC = mainDB.getTypeOnly();

        if (mainC.moveToFirst())
        {
                do {
                    roomNames.add(mainC.getString(0));
                } while (mainC.moveToNext());

        }//if end
        mainDB.close();

        checkSpinner(roomNames.isEmpty());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        HomeSpinner.setAdapter(adapter);

    }//end oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }//end

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_create:
                Intent intent_create = new Intent(this, CreateNewRoom.class);
                startActivity(intent_create);
                return true;
            case R.id.navigation_addItem:
                Intent intent_addItem = new Intent(this, AddItemToContainer.class);
                startActivity(intent_addItem);
                return true;
            case R.id.navigation_remove:
                Intent intent_remove = new Intent(this, DeleteRoom.class);
                startActivity(intent_remove);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }//end

    public void checkSpinner(boolean chk)
    {
        if (chk)
        {
            btn_view.setEnabled(false);
            HomeSpinner.setEnabled(false);
            HomeSpinner.setClickable(false);
        }else
            {
                btn_view.setEnabled(true);
                HomeSpinner.setEnabled(true);
                HomeSpinner.setClickable(true);
        }

    }//



    public void viewRoomContents(View v_room){

        String roomInfo = HomeSpinner.getSelectedItem().toString();

       Intent intent_viewRoomInfo = new Intent(this, ViewRoomContents.class);
       intent_viewRoomInfo.putExtra(EXTRA_MESSAGE, roomInfo);
        startActivity(intent_viewRoomInfo);
    }//


}//end
