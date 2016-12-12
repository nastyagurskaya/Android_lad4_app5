package com.example.nastyagurskaya.lab4_app5;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    String[] act = { "Info", "Add record", "Edit last record"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, act);
        setListAdapter(adapter);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Info.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, Add.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Edit.class);
                        startActivity(intent2);
                        break;
                }
                Toast.makeText(getApplicationContext(), "Your choose " +
                                parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        getListView().setOnItemClickListener(itemListener);
    }



}


