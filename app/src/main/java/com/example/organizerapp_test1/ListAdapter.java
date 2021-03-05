package com.example.organizerapp_test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    Intent intent_EditItem;
    Intent intent_up;
    private ListAdapter adapter;

    List<ListData> listdata = Collections.emptyList();
    public static final String EXTRA_MESSAGE = "";
    public static final String EXTRA_BOOLEAN = "";

    // RecyclerView recyclerView;
    public ListAdapter(Context context, List<ListData> listdata) {
        this.listdata = listdata;
       this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int myListData = holder.getAdapterPosition();
        holder.txtV_Item.setText(listdata.get(position).getItem());


        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Deleting this item is permanent." +
                        "Do you still want to proceed?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            String roomDlt = listdata.get(myListData).getType();
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TestDB db = new TestDB(context);
                                db.open();
                                db.deleteItem(listdata.get(myListData).getItem(), listdata.get(myListData).getType());
                                db.close();

                              intent_up = new Intent(context, ViewRoomContents.class);
                              intent_up.putExtra(EXTRA_MESSAGE, roomDlt);
                              context.startActivity(intent_up);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Remove Item");
                alert.show();

            }
        });

    }//onBind


    @Override
    public int getItemCount() {

        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtV_Item;
        public ImageButton btnRemoveItem;
        public LinearLayout parentLayout;
       // public EditText edit;


        //public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txtV_Item = (TextView) itemView.findViewById(R.id.txtV_Item);
          this.btnRemoveItem = (ImageButton) itemView.findViewById(R.id.btn_deleteItem);
            this.parentLayout = itemView.findViewById(R.id.parentlayout);
            //this.edit = itemView.findViewById(R.id.test_editItem);
            //edit.setVisibility(itemView.GONE);

        }


    }//ViewHolder

}//end
