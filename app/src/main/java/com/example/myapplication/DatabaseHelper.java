package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName="Signup.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context,"Signup.db", null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS allusers (email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS todo_items (id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT, completed INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop TABLE if exists allusers");
        MyDatabase.execSQL("DROP TABLE IF EXISTS todo_items");
        onCreate(MyDatabase);

    }
    public Boolean insertData(String email,String password) {
        SQLiteDatabase MyDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result =MyDatabase.insert("allusers",null,contentValues);

        MyDatabase.close();

        return result != -1;


        }
        public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase=this.getReadableDatabase();
        Cursor cursor=MyDatabase.rawQuery("Select * from allusers where email=?",new String[]{email});

            boolean exists = cursor.getCount() > 0;
            cursor.close();
            MyDatabase.close();

            return exists;


        }
        public Boolean checkEmailPassword(String email,String password){
        SQLiteDatabase MyDatabase=this.getReadableDatabase();
        Cursor cursor =MyDatabase.rawQuery("Select * from allusers where email=? and password=?",new String[]{email,password});

            boolean exists = cursor.getCount() > 0;
            cursor.close();
            MyDatabase.close();

            return exists;

        }


    public List<TodoItem> getAllTodoItems() {

        List<TodoItem> todoItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, text, completed FROM todo_items", null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int textIndex = cursor.getColumnIndex("text");
                int completedIndex = cursor.getColumnIndex("completed");

                int id = cursor.getInt(idIndex);
                String text = cursor.getString(textIndex);
                boolean completed = cursor.getInt(completedIndex) == 1;

                TodoItem todoItem = new TodoItem(text);
                todoItem.setId(id);
                todoItem.setCompleted(completed);

                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return todoItems;

    }

    public void insertTodoItem(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", todoItem.getText());
        values.put("completed", todoItem.isCompleted() ? 1 : 0);

        /*db.insert("todo_items", null, values);
        db.close(); */

        long id = db.insert("todo_items", null, values);
        todoItem.setId((int) id); // Eklenen öğenin otomatik artan id'sini ayarlıyoruz

        db.close();
    }


}

