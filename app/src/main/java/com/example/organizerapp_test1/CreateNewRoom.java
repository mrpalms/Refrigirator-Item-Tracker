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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewRoom extends AppCompatActivity {

    private static boolean pass;
    public Button addButton;
    public EditText addText;
    public TextView tv_success;
    public TextView title_activity;
    private String msg_success = "Room Created";
    private String msg_failed = "Room Already Exists";
    private String msg_empty = "empty field";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_room);
        Toolbar toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);
        //setTitle("CREATE");

        title_activity = findViewById(R.id.toolbar_title_create);
        title_activity.setText("CREATE ROOM");
        addButton = findViewById(R.id.btnCreate);
        addText = findViewById(R.id.input_createRoom);



    }//main end

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_create, menu);
        return true;
    }//end

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.navigation_home:
                Intent intent_home = new Intent(this, MainActivity.class);
                startActivity(intent_home);
                return true;
            case R.id.navigation_addItem:
                Intent intent_create = new Intent(this, AddItemToContainer.class);
                startActivity(intent_create);
                return true;
            case R.id.navigation_remove:
                Intent intent_remove = new Intent(this, DeleteRoom.class);
                startActivity(intent_remove);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }//end

    public boolean input_check(String input_type){

        TestDB in_db = new TestDB(this);
        ArrayList<String> dbList = new ArrayList<String>();
        int x = 0;

        in_db.open();
        Cursor mainC = in_db.getTypeOnly();
        if (mainC.moveToFirst()){
            do {
                   dbList.add(mainC.getString(0));
            }while (mainC.moveToNext());
        }//if end
        in_db.close();

        pass = true;
      while (x < dbList.size() && pass == true){

          String compare_input = dbList.get(x).toUpperCase();

            if (compare_input.equals(input_type)){
                pass = false;
            }else{
                pass = true;
            }
            x++;
        }//while

        return pass;

    }//input checker

    public void onClick(View v){
       TestDB db = new TestDB(this);
        String input = String.valueOf(addText.getText());
        String up_input = input.toUpperCase();

        db.open();

                    if ((!input_check(up_input) == false) && (up_input.length() > 1))
                    {
                        long insertRoom = db.insertType(input, "");
                        Toast toast = Toast.makeText(getApplicationContext(), msg_success, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                        Intent intent_CreateRoom = new Intent(this, CreateNewRoom.class);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent_CreateRoom);
                        overridePendingTransition(0,0);

                    } else if (up_input.length() < 1)
                    {
                        Toast toast1 = Toast.makeText(getApplicationContext(), msg_empty, Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();
                    }
                    else
                        {
                        Toast toast1 = Toast.makeText(getApplicationContext(), msg_failed, Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();
                    }
            db.close();



    }//onclick

    public void btnBack(View b)
    {
        super.onBackPressed();
    }


} //end
