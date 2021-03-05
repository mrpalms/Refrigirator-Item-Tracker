package com.example.organizerapp_test1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class AddItemToContainer extends AppCompatActivity {

    public EditText in_Item;
    public AutoCompleteTextView in_Container;
    public Button btn_addItem;
    private Spinner spinner_add;
    private TestDB autoDb;
    private ArrayList<String> containers_set;


    private String msg_success = "Item Added";
    private String msg_empty = "Input field is empty";
    private String msg_failed = "Input is not Valid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additemtocontainer);
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

       // setTitle("ADD ITEM");

        spinner_add = findViewById(R.id.spinner_getRooms);
        in_Item = findViewById(R.id.in_addItem);


        //Intent intent_addItem = getIntent();
       // roomName = intent_addItem.getStringExtra(ViewRoomContents.EXTRA_MESSAGE);
        setTitle("Add Item");

        TestDB db_addItem = new TestDB(this);
        ArrayList<String> roomNames = new ArrayList<String>();
        db_addItem.open();
        Cursor mainC = db_addItem.getTypeOnly();
        if (mainC.moveToFirst()){
            do {
                roomNames.add(mainC.getString(0));
            }while (mainC.moveToNext());
        }//if end
        db_addItem.close();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_add.setAdapter(adapter);

        /*
        autoDb = new TestDB(this);
        containers_set = new ArrayList<>();
        spinner_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                containers_set.removeAll(containers_set);
                String selectedItemText = (String) parent.getItemAtPosition(i);
              //  Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT).show();

                autoDb.open();
                String select_room = spinner_add.getSelectedItem().toString();

                Cursor c = autoDb.getContainerFromRoom(selectedItemText);
                if (c.moveToFirst())
                {
                    do{
                        boolean a = containers_set.contains(c.getString(1));
                        if ((!a) || containers_set.isEmpty())
                        {
                            containers_set.add(c.getString(1));
                        } else {
                            c.moveToNext();
                        }
                    } while(c.moveToNext());

                }//if end
                autoDb.close();
                ArrayAdapter<String> autoComplete_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, containers_set);
                AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.in_addCont);
                a.setThreshold(1);
                a.setAdapter(autoComplete_adapter);
            }//on item

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(), "No selection", Toast.LENGTH_SHORT).show();
            }
        });//end

         */


    }//end

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.additem, menu);
        return true;
    }//end


public void backBtn(View back)
{
   super.onBackPressed();
}

    public void addItem(View view){

        String type = spinner_add.getSelectedItem().toString();
        String item = String.valueOf(in_Item.getText());
        //String container = String.valueOf(in_Container.getText());
        String up_item = item.toUpperCase();
       // String up_container = container.toUpperCase();
        boolean c_It = checkItem(up_item, type);
        TestDB db = new TestDB(this);
        db.open();

        if(c_It == true)
        {
            Toast.makeText(getApplicationContext(), "Item Exist", Toast.LENGTH_SHORT).show();
        }
        if ((up_item.length() < 1))
        {
            Toast toast1 = Toast.makeText(getApplicationContext(), msg_empty, Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.TOP, 0, 0);
            toast1.show();
        }
        else if ((up_item.length() > 1) && (c_It == false))
        {
            long insertItem = db.insertType(type, item);
            Toast.makeText(this, msg_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddItemToContainer.class);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
        db.close();

    }//end additem

    public boolean checkItem(String item, String type) {

        boolean itemCheck = false;
       // boolean contCheck = false;
        boolean validation = false;

        autoDb = new TestDB(this);
       ArrayList<String> getItems = new ArrayList<>();
        autoDb.open();
        getItems.removeAll(getItems);
        Cursor c = autoDb.getItemExist(type); //get room
                if (c.moveToFirst()) {
                    do {
                        getItems.add(c.getString(0).toUpperCase());
                    } while (c.moveToNext());
                }//if

         autoDb.close();
      //  boolean check = false;

        itemCheck = getItems.contains(item);
       // contCheck = getContainers.contains(container);

        if (itemCheck)
        {
            validation = true;
        }else
        {
            validation = false;
        }

        return validation;

    }//checkItem


}//end