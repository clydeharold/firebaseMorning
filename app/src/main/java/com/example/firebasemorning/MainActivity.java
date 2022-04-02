package com.example.firebasemorning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button cmdAdd, cmdSearch;
    EditText etId, etName, etCourse;
    ArrayList<Student> students = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refs();

        cmdAdd.setOnClickListener(addStudentRecord);
        cmdSearch.setOnClickListener(searchStudentId);
        addValuEventListener();

    }


    public void addValuEventListener() {
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot ds: task.getResult().getChildren()){
                        students.add(ds.getValue(Student.class));
                    }
                }
            }
        });
    }
    public void refs() {
        cmdAdd = findViewById(R.id.cmdAdd);
        cmdSearch = findViewById(R.id.cmdSearch);
        etId = findViewById(R.id.etStudentId);
        etName = findViewById(R.id.etStudentName);
        etCourse = findViewById(R.id.etStudentCourse);
    }

    View.OnClickListener searchStudentId = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(),"Size: " + students.size(),Toast.LENGTH_SHORT).show();

            int id = Integer.parseInt(etId.getText().toString()), flag = 0;
            for(Student s: students){
                if(id == s.getId()) {
                    etName.setText(s.getName());
                    etCourse.setText(s.getCourse());
                    flag = 1;
                    break;
                }
            }

            if(flag == 0) {
                Toast.makeText(getApplicationContext(),"Record not Found.",Toast.LENGTH_SHORT).show();
                etName.setText("");
                etCourse.setText("");
            }

        }
    };

    View.OnClickListener addStudentRecord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id;
            String name, course;

            id = Integer.parseInt(etId.getText().toString());
            name = etName.getText().toString();
            course = etCourse.getText().toString();

            Student stud = new Student(id, name, course);
            myRef.child(etId.getText().toString()).setValue(stud);
            addValuEventListener();
        }
    };

}