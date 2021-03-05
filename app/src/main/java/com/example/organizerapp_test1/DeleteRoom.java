package com.example.organizerapp_test1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteRoom extends AppCompatActivity {

    public Spinner deleteSpinner;
    public Button btn_deleteRoom;
    public TextView title_activity;
    private Intent returnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);
        Toolbar toolbar = findViewById(R.id.toolbar_remove);
        setSupportActionBar(toolbar);
        returnToMain = new Intent(this, MainActivity.class);

        deleteSpinner = findViewById(R.id.spinner_DeleteRoom);
        title_activity = findViewById(R.id.toolbar_title_remove);
        title_activity.setText("REMOVE ROOM");

        TestDB deleteRoom_db = new TestDB(this);

        ArrayList<String> roomNames = new ArrayList<String>();
        deleteRoom_db.open();
        Cursor mainC = deleteRoom_db.getTypeOnly();
        if (mainC.moveToFirst()){
            do {
                roomNames.add(mainC.getString(0));
            }while (mainC.moveToNext());
        }//if end
        deleteRoom_db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        deleteSpinner.setAdapter(adapter);

    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_remove, menu);
        return true;
    }//end

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Intent intent_home = new Intent(this, MainActivity.class);
                startActivity(intent_home);
                return true;
            case R.id.navigation_create:
                Intent intent_create = new Intent(this, CreateNewRoom.class);
                startActivity(intent_create);
                return true;
            case R.id.navigation_addItem:
                Intent intent_remove = new Intent(this, AddItemToContainer.class);
                startActivity(intent_remove);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }//end

    public void removeRoom(final View r)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteRoom.this);

                builder.setMessage("This action will permanently remove the room. Are you sure you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TestDB rmDB = new TestDB(r.getContext());
                    rmDB.open();
                    String remove = deleteSpinner.getSelectedItem().toString();
                    rmDB.deleteRoom(remove);
                    rmDB.close();
                    Toast toast = Toast.makeText(getApplicationContext(), "succesfully removed", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    startActivity(returnToMain);
                    finish();
                }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                alert.setTitle("Remove Spinner Item");
                alert.show();


    }//remove room

    public void backBtn(View back)
    {
        super.onBackPressed();
    }

}//end