package com.example.joem.updatinglistviewdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Color> data = new ArrayList<>(); //mutable, so we can change and update data as app is running
    ArrayAdapter<Color> adapter; //need reference to 'adaptor' so made global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populates arrayList
        data.add(new Color("Red"));
        data.add(new Color("Blue"));
        data.add(new Color("Green"));
        data.add(new Color("White"));
        data.add(new Color("Black"));
        data.add(new Color("Yellow"));

        ListView listView = (ListView) findViewById(R.id.listView); //(creates/instantiates?) listView

        //to use listView(s) we need to create an adaptor
        //the adaptor needs to know what kind of layout is going to be generated for each row of the listView and where it gets data from
        //have to tell it what is the type of the object of the data we're going to present (in this case an array of 'string')
        //adaptor can take in a lot of signatures of constructors; signature we use takes in:
        //context (this), layout (android...), id of text in layout(android...), string object (can be array or arrayList; colorObjects)
        //android.R.layout.simple_list_item_1 is a simple list item which contains 1 item that is a textView
        //"text1" is provided within "simple_list_item_1"
        //the adaptor does not maintain its own data set, rather it uses the data (e.g. a reference to the above arrayList to maintain the data) you provide
        adapter = new ArrayAdapter<Color>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        //one option on how to update listView includes using the following (commented out bc we're not doing that)
        //adapter.add(); //to add 'color' object
        //adapter.remove(); //to remove 'color' object
        //notifyDataSetChanged used after add (or remove?)
        //adapter.notifyDataSetChanged(); //notifies the listView that data set has changed so the listView can re-render the data

        listView.setAdapter(adapter);

        //clicking and holding onto color deletes it from list
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //could use 'data' or 'adaptor'
                Color color = adapter.getItem(position); //gets item at position
                adapter.remove(color);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        //clicking 'add' button adds info to 'listView'
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the following retrieves text entered (validation and sanitation are not represented in this example)
                EditText editText = (EditText) findViewById(R.id.editText);
                adapter.add(new Color(editText.getText().toString()));
                adapter.notifyDataSetChanged();//refreshes the list
            }
        });
    }
    //created static class in Main bc didn't want to create one outside of Main
    static class Color{
        String name;
        int hex;

        public Color(String name) { //constructor
            this.name = name;
        }
        //for every object in this simple approach, the program tries to display a toString, so we generate the below code to control what's displayed
        @Override
        public String toString() {
            return name;
        }
    }
}