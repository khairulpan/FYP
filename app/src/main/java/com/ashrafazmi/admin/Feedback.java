package com.ashrafazmi.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Feedback extends AppCompatActivity {

    public static final String STUDENT_NAME = "com.ashrafazmi.umotion.fullnameet";
    public static final String STUDENT_ID = "com.ashrafazmi.umotion.studidet";

    private ImageButton buttonPay, buttonfeedback,imageButtonprooff;
    private TextView usee, full, maill ,time;
    private EditText message;
    private FirebaseAuth auth;
    private ImageButton btnadd;

    DatabaseReference databaseStudent;
    ListView listViewTracks;

    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String mail = preferences.getString("email", "");
        String code = preferences.getString("code","");
        String fullname = preferences.getString("fullname","");
        String use = preferences.getString("user","");

        //inidec

        btnadd = (ImageButton) findViewById(R.id.btnadd);
        usee = (TextView) findViewById(R.id.use);
        full = (TextView) findViewById(R.id.full);
        maill = (TextView) findViewById(R.id.mail);
        time =(TextView)findViewById(R.id.time);
        message= (EditText) findViewById(R.id.message);

        //GetDateAndTime
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

       time.setText(currentDateTimeString);
        full.setText("ADMIN");
        usee.setText("ADMIN");
        maill.setText(mail);



        //Retrieve from database
        databaseStudent = FirebaseDatabase.getInstance().getReference("feedback");


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //email = (TextView) findViewById(R.id.useremail);
        listViewTracks =(ListView) findViewById(R.id.listViewTracks);
        studentList = new ArrayList<>();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Feedback.this, LoginActivity.class));
                    finish();
                }
            }
        };

/**      buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent vlemoodle = new Intent(com.ashrafazmi.admin.Feedback.this, PayGateway.class);
                com.ashrafazmi.admin.Feedback.this.startActivity(vlemoodle);

            }
        }); **/

     /**   ImageButton imageButtonprooff = (ImageButton) findViewById(R.id.imageButtonprooff);

        imageButtonprooff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent promo = new Intent(com.ashrafazmi.admin.Feedback.this, Promo.class);
                com.ashrafazmi.admin.Feedback.this.startActivity(promo);

            }
        }); **/

        //addrecord function
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });

    }

    //add_feedback

    private void addStudent() {
        //getting the values to save
        String  namesavee  = usee.getText().toString().trim();
        String  idsavee  = full.getText().toString().trim();
        String  coursesavee  = maill.getText().toString().trim();
        String  kelaslecturesavee  = time.getText().toString().trim();
        String  kelastimesave  = message.getText().toString().trim();



        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our Artist


        //creating an Artist Object
        String id = databaseStudent.push().getKey();
        Student student = new Student(namesavee, idsavee, coursesavee,  kelaslecturesavee, kelastimesave);

        //Saving the Artist
        databaseStudent.child(id).setValue(student);

        //setting edittext to blank again
        message.setText("");

        /**setting edittext to blank again
         editTextName.setText("");
         fullnameet.setText("");
         studidet.setText("");
         courseet.setText("");
         lcet.setText("");
         ctet.setText("");
         clet.setText("");
         lghocet.setText("");
         ghoctimet.setText(""); **/

    }

    //addfeedbackscripttend
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                studentList.clear();
                //iterating through all the nodes
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Student student = studentSnapshot.getValue(Student.class);

                    studentList.add(student);
                }

                StudentList adapter = new StudentList(Feedback.this, studentList);
                listViewTracks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        maill.setText(user.getEmail());


    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(Feedback.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Feedback.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

   /** @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    } **/

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }



}







