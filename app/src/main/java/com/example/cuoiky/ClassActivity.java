package com.example.cuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.cuoiky.dbhelper.DBHelper;
import com.example.cuoiky.model.ClassInfo;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    Button btnSelect, btnSave, btnSearch, btnUpdate, btnDelete, btnExit;
    ArrayList<ClassInfo> arrayList;
    EditText etId, etName;
    GridView gvClassList;
    DBHelper dbHelper;
    ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        etId = findViewById(R.id.et_idC);
        etName = findViewById(R.id.et_nameC);
        gvClassList = findViewById(R.id.gv_ClassList);
        dbHelper = new DBHelper(this);
//        dbHelper.insertClass(new ClassInfo(100,"ĐHKTPM15A"));
//        dbHelper.insertClass(new ClassInfo(200,"ĐHOT16B"));
        readItem();

        btnSave = findViewById(R.id.btnSaveC);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if (id.equals("")||name.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập id và name để save",Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = Integer.parseInt(id);
                    try {
                        ClassInfo classInfo = new ClassInfo();
                        classInfo.setId(i);
                        classInfo.setName(name);
                        if (dbHelper.insertClass(classInfo)>0){
                            Toast.makeText(getApplicationContext(), "Bạn đã thêm Class thành công", Toast.LENGTH_SHORT).show();
                            clearItem();
                            readItem();
                        }else {
                            Toast.makeText(getApplicationContext(),"id = "+id +" đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Nhập id và name để save",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnSelect = findViewById(R.id.btnSelectC);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                if (id.equals("")){
                    readItem();
                }
                else {
                    try {
                        int i = Integer.parseInt(id);
                        ClassInfo classInfo = dbHelper.getClassById(i);
                        ArrayList<String> item = new ArrayList<>();
                        item.add(classInfo.getId()+"");
                        item.add(classInfo.getName());
                        adapter = new ArrayAdapter<>(ClassActivity.this, android.R.layout.simple_list_item_1,item);
                        gvClassList.setAdapter(adapter);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id,Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnUpdate = findViewById(R.id.btnUpdateC);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if (id.equals("")||name.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập id cũ và name mới để update",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        int i = Integer.parseInt(id);
                        dbHelper.updateClass(i,name);
                        clearItem();
                        readItem();
                        Toast.makeText(getApplicationContext(),"Đã cập nhật class có id = "+id+" thành công",Toast.LENGTH_SHORT).show();

                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete = findViewById(R.id.btnDeleteC);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                if (id.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập id để xóa class",Toast.LENGTH_SHORT).show();
                }
                else {
//                    try {
                        int i = Integer.parseInt(id);
                        dbHelper.deleteClass(i);
                        Toast.makeText(getApplicationContext(),"Bạn đã xóa Class thành công",Toast.LENGTH_SHORT).show();
                        clearItem();
                        readItem();
//                    }catch (Exception e){
//                        Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id,Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        gvClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int vitri = i/2;
                ClassInfo classInfo = arrayList.get(vitri);
                int id = classInfo.getId();
                String name = classInfo.getName();
                etId.setText(id+"");
                etName.setText(name);
            }
        });
        btnExit = findViewById(R.id.btn_Exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void readItem(){
        dbHelper = new DBHelper(this);
        arrayList = dbHelper.getAllClass();
        ArrayList<String> item = new ArrayList<>();

        for (ClassInfo classInfo: arrayList){
        item.add(classInfo.getId()+"");
        item.add(classInfo.getName());
        adapter = new ArrayAdapter<>(ClassActivity.this, android.R.layout.simple_list_item_1,item);
        }
        gvClassList.setAdapter(adapter);
    }
    public void clearItem(){
        ArrayList<String> item = new ArrayList<>();
        adapter = new ArrayAdapter<>(ClassActivity.this, android.R.layout.simple_list_item_1,item);
        gvClassList.setAdapter(adapter);
    }
}