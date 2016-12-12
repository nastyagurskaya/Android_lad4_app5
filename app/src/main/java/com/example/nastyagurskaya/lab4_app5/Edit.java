package com.example.nastyagurskaya.lab4_app5;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;


public class Edit extends Activity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        dbHelper = new DBHelper(this);
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", "Ivanov");
        cv.put("surname", "Ivan");
        cv.put("patronymic", "Ivanovich");

        db.update("mytable", cv, "id=(SELECT MAX(id) FROM mytable)", null);
        Toast t = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG);
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