package com.example.cuoiky.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cuoiky.model.ClassInfo;
import com.example.cuoiky.model.Student;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Classes(id integer primary key, name text);");
        sqLiteDatabase.execSQL("CREATE TABLE Students(id integer primary key, name text, address text, email text, id_class integer not null constraint id_class REFERENCES Classes(id) ON DELETE CASCADE ON UPDATE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Classes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Students");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    //Thêm, xóa, sửa class
    public int insertClass(ClassInfo classinfo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id",classinfo.getId()+ "");
        content.put("name",classinfo.getName());
        int res = (int) db.insert("Classes",null,content);
        db.close();
        return res;
    }

    public void deleteClass(int id){
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "DELETE FROM Classes WHERE id=" +id;
        String strSQL1 = "UPDATE Students SET id_class=0 WHERE id_class="+id;
        db.execSQL(strSQL1);
        db.delete("Classes","id="+id,null);
        db.close();
    }

    public void updateClass(int id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        String strSQL = "UPDATE Classes SET name = '" + name + "' WHERE id = " + id;
        db.execSQL(strSQL);
        db.close();
    }

    public ClassInfo getClassById(int id){
        ClassInfo classInfo = null;
        String strSQL = "SELECT * FROM Classes WHERE id = " +id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(strSQL,null);
        if (cursor!=null){
            cursor.moveToFirst();
            classInfo = new ClassInfo(cursor.getInt(0),cursor.getString(1));
            cursor.close();
            db.close();
        }
        return classInfo;
    }

    public ArrayList<ClassInfo> getAllClass(){
        ArrayList<ClassInfo> list = new ArrayList<>();
        String strSQL = "SELECT * FROM Classes";
        //Mở database để đọc
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(strSQL,null);
        if (cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                list.add(new ClassInfo(cursor.getInt(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();;
            db.close();
        }
        return list;
    }

    //Thêm, xóa, sửa Student
    public int insertStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id",student.getId() + "");
        content.put("name",student.getName() + "");
        content.put("address",student.getAddress());
        content.put("email",student.getEmail());
        content.put("id_class",student.getId_class()+"");
        int res = (int) db.insert("Students",null,content);
        db.close();
        return res;
    }
    public void deleteStudent(int id){
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "DELETE FROM Students WHERE id=" +id;
        db.delete("Students","id="+id,null);
//        db.execSQL(strSQL);
        db.close();
    }

    public void updateStudent(int id, Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        String name = student.getName();
        String address = student.getAddress();
        String email = student.getEmail();
        int id_class = student.getId_class();
        String strSQL = "UPDATE Students SET name = '" + name + "', address='"+address+"' ,email='"+email+"',id_class='"+id_class+"' WHERE id = " + id;
        db.execSQL(strSQL);
        db.close();
    }

    public ArrayList<Student> getAllStudent(){
        ArrayList<Student> list = new ArrayList<>();
        String strSQL = "SELECT * FROM Students";
        //Mở database để đọc
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(strSQL,null);
        if (cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                list.add(new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));
                cursor.moveToNext();
            }
            cursor.close();;
            db.close();
        }
        return list;
    }
}
