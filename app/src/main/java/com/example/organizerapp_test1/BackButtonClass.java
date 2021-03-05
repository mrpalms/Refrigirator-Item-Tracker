package com.example.organizerapp_test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BackButtonClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void returnBack(){
        super.onBackPressed();
    }//end

}//
