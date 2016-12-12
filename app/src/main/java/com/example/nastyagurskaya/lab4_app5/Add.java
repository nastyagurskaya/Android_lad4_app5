package com.example.nastyagurskaya.lab4_app5;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Add extends Activity {
    Button addButton;
    EditText nameText, surText, patText;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        addButton = (Button) findViewById(R.id.addBtn);
        nameText = (EditText) findViewById(R.id.namText);
        surText = (EditText) findViewById(R.id.surText);
        patText = (EditText) findViewById(R.id.patText);

    }


    public void addButtonClick(View v)
    {
        dbHelper = new DBHelper(this);
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = nameText.getText().toString();
        String surname = surText.getText().toString();
        String patronymic = patText.getText().toString();
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");

        cv.put("name", name);
        cv.put("surname", surname);
        cv.put("patronymic", patronymic);
        cv.put("data", String.valueOf(format1.format(new Date())));

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowID = db.insert("mytable", null, cv);
        Toast t = Toast.makeText(getApplicationContext(), "User with #" + String.valueOf(rowID) + " successfully added!", Toast.LENGTH_SHORT);
        t.show();

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

            if (oldVersion == 1 && newVersion == 2) {

                Cursor c = db.query("mytable", null, null, null, null, null, null);

                ArrayList<String> idArray = new ArrayList<String>();
                ArrayList<String> nameArray = new ArrayList<String>();
                ArrayList<String> name1Array = new ArrayList<String>();
                ArrayList<String> name2Array = new ArrayList<String>();
                ArrayList<String> dataArray = new ArrayList<String>();

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int name1ColIndex = c.getColumnIndex("surname");
                    int name2ColIndex = c.getColumnIndex("patronymic");
                    int dataColIndex = c.getColumnIndex("data");
                    do {
                        idArray.add(c.getString(idColIndex));
                        nameArray.add(c.getString(nameColIndex));
                        name1Array.add(c.getString(name1ColIndex));
                        name2Array.add(c.getString(name2ColIndex));
                        dataArray.add(c.getString(dataColIndex));
                    } while (c.moveToNext());

                }

                db.beginTransaction();
                try {
                    db.execSQL("DROP TABLE mytable;");
                    // создаем таблицу должностей
                    db.execSQL("create table mytable ("
                            + "id integer primary key,"
                            + "fio text, data text);");

                    ContentValues cv = new ContentValues();
                    // заполняем ее
                    for (int i = 0; i < idArray.size(); i++) {
                        cv.clear();
                        cv.put("id", idArray.get(i));
                        cv.put("fio", nameArray.get(i) + name1Array.get(i) + name2Array.get(i));
                        cv.put("data", dataArray.get(i));
                        long rowID = db.insert("mytable", null, cv);
                    }

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }
}