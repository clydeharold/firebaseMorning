package com.example.firebasemorning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button cmdAdd;
    EditText etId, etName, etCourse;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refs();

        cmdAdd.setOnClickListener(addStudentRecord);
    }

    public void refs() {
        cmdAdd = findViewById(R.id.cmdAdd);
        etId = findViewById(R.id.etStudentId);
        etName = findViewById(R.id.etStudentName);
        etCourse = findViewById(R.id.etStudentCourse);
    }

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
        }
    };

}