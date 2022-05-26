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
import com.example.cuoiky.model.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    Button btnSelect, btnSave, btnSearch, btnUpdate, btnDelete, btnExit;
    ArrayList<Student> arrayList;
    EditText etId, etName, etEmail, etAddress, etIdClass;
    GridView gvStudentList;
    DBHelper dbHelper;
    ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        etId = findViewById(R.id.et_idStudent);
        etName = findViewById(R.id.et_nameStudent);
        etAddress = findViewById(R.id.et_address);
        etEmail = findViewById(R.id.et_email);
        etIdClass = findViewById(R.id.et_idclass);
        dbHelper = new DBHelper(this);
//        dbHelper.insertStudent(new Student(19533901,"Cao A Minh","TP.HCM","caoaminh@gmail.com",300));
//        dbHelper.insertStudent(new Student(19033091,"Nguyễn Văn A","TP.HCM","nguyenvana@gmail.com",100));
        gvStudentList = findViewById(R.id.gv_StudentList);
        readItem();

        btnSave = findViewById(R.id.btn_SaveS);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String id_class = etIdClass.getText().toString().trim();
                if (id.equals("")||name.equals("")||address.equals("")||email.equals("")||id_class.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin để save",Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = Integer.parseInt(id);
                    int iclass = Integer.parseInt(id_class);
                    try {
                        Student student = new Student();
                        student.setId(i);
                        student.setName(name);
                        student.setAddress(address);
                        student.setEmail(email);
                        student.setId_class(iclass);

                        if (dbHelper.insertStudent(student)>0){
                            Toast.makeText(getApplicationContext(), "Bạn đã thêm Student thành công", Toast.LENGTH_SHORT).show();
                            clearItem();
                            readItem();
                        }else {
                            Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id_class+"hoặc id = "+id+" của student đã tồn tại",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Student có id = "+id+ "đã tồn tại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnUpdate = findViewById(R.id.btn_UpdateS);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String id_class = etIdClass.getText().toString().trim();
                if (id.equals("")||name.equals("")||address.equals("")||email.equals("")||id_class.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập id cũ và thông tin mới để update",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        int i = Integer.parseInt(id);
                        int iclass = Integer.parseInt(id_class);
                        Student s = new Student(i,name,address,email,iclass);
                        dbHelper.updateStudent(i,s);
                        clearItem();
                        readItem();
                        Toast.makeText(getApplicationContext(),"Đã cập nhật student có id = "+id+" thành công",Toast.LENGTH_SHORT).show();

                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id_class+" hoặc không tìm thấy student có id = "+id,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnDelete = findViewById(R.id.btn_DeleteS);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                if (id.equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập id để xóa student",Toast.LENGTH_SHORT).show();
                }
                else {
//                    try {
                    int i = Integer.parseInt(id);
                    dbHelper.deleteStudent(i);
                    Toast.makeText(getApplicationContext(),"Bạn đã xóa Student thành công",Toast.LENGTH_SHORT).show();
                    clearItem();
                    readItem();
//                    }catch (Exception e){
//                        Toast.makeText(getApplicationContext(),"Không tìm thấy class có id = "+id,Toast.LENGTH_SHORT).show();
//                    }
                }
            }

        });
        gvStudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int vitri = i/5;
                Student student = arrayList.get(vitri);
                int id = student.getId();
                String name = student.getName();
                String address = student.getAddress();
                String email = student.getEmail();
                int id_class = student.getId_class();
                etId.setText(id+"");
                etName.setText(name);
                etAddress.setText(address);
                etEmail.setText(email);
                etIdClass.setText(id_class+"");
            }
        });
    }

    public void readItem(){
        dbHelper = new DBHelper(this);
        arrayList = dbHelper.getAllStudent();
        ArrayList<String> item = new ArrayList<>();

        for (Student student: arrayList){
            item.add(student.getId()+"");
            item.add(student.getName());
            item.add(student.getAddress());
            item.add(student.getEmail());
            ClassInfo classInfo = dbHelper.getClassById(student.getId_class());
            String className = classInfo.getName();
            if (student.getId_class()==0)
                className = "No class available";
            item.add(className+"");
            adapter = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_list_item_1,item);
        }
        gvStudentList.setAdapter(adapter);
    }
    public void clearItem(){
        ArrayList<String> item = new ArrayList<>();
        adapter = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_list_item_1,item);
        gvStudentList.setAdapter(adapter);
    }

}