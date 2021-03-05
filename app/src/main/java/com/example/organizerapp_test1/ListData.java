package com.example.organizerapp_test1;

public class ListData {

    private long rowid;
    private String item;
    private String type;
  //  private Long key_id;
    public ListData(String type, String item, long rowid) {
       // this.key_id = key_id;
        this.rowid = rowid;
        this.type = type;
        this.item = item;

    }


    public String getItem() {

        return item;
    }
    public void setItem(String item) {

        this.item = item;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRowId() {

        return rowid;
    }

    public void setRowId(long rowid) {
        this.rowid = rowid;
    }




}//end
