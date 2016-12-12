package com.example.nastyagurskaya.lab4_app5;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Info extends Activity {
    DBHelper dbHelper;
    TextView tex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        tex = (TextView) findViewById(R.id.textView2);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        ListView listV = (ListView)findViewById(R.id.listV);

        ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int name1ColIndex = c.getColumnIndex("surname");
            int name2ColIndex = c.getColumnIndex("patronymic");
            int dataColIndex = c.getColumnIndex("data");
            do {
                map = new HashMap<String, String>();
                map.put("ID + Name","#"+c.getString(idColIndex)+ " "+ c.getString(nameColIndex) + " " + c.getString(name1ColIndex) + " " + c.getString(name2ColIndex));
                map.put("Time", c.getString(dataColIndex));
                myArrList.add(map);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());

        }
        else
        {
            tex.setText("0 rows in DB");
        }


        SimpleAdapter adapter = new SimpleAdapter(this, myArrList, android.R.layout.simple_list_item_2,
                new String[] {"ID + Name", "Time"},
                new int[] {android.R.id.text1, android.R.id.text2});
        listV.setAdapter(adapter);

    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "surname text,"
                    + "patronymic text,"
                    + "data text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}