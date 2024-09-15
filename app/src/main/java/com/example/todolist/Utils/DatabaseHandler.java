package com.example.todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + STATUS + " Integer)";

    private SQLiteDatabase db;
    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }
    public void openDatabase() {
        db = this.getWritableDatabase();
    }
    public void insertTask(ToDoModel task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, task.getTask());
        contentValues.put(STATUS, 0);
        db.insert(TODO_TABLE,null,contentValues);
    }
    public List<ToDoModel> getAllTask() {
        List<ToDoModel> list = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE,null,null,null,null,null,null,null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();

                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt( cur.getColumnIndexOrThrow(STATUS)));
                        list.add(task);
                    } while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return list;
    }
    public void updateStatus(int id, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS,status);
        db.update(TODO_TABLE,contentValues,ID + "=?", new String[] {String.valueOf(id)});
    }
    public void updateTask(int id, String task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK,task);
        db.update(TODO_TABLE,contentValues,ID + "=?", new String[] {String.valueOf(id)});
    }
    public void deleteTask(int id) {
        db.delete(TODO_TABLE,ID + "= ?", new String[] {String.valueOf(id)});
    }

}
