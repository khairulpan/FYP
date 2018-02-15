package com.ashrafazmi.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AzFar on 11/19/2017.
 */

public class MainActivity extends AppCompatActivity {

    public static final String STUDENT_NAME = "com.ashrafazmi.umotion.fullnameet";
    public static final String STUDENT_ID = "com.ashrafazmi.umotion.studidet";

    private TextView usee, full, maill ,time;
    private EditText message;
    private FirebaseAuth auth;

    DatabaseReference databaseStudent;
    ListView listViewTracks;

    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String mail = preferences.getString("email", "");
        String code = preferences.getString("code","");
        String fullname = preferences.getString("fullname","");
        String use = preferences.getString("user","");

        //Retrieve from database------------------------------------------------------------------------
        databaseStudent = FirebaseDatabase.getInstance().getReference("cartuser");

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //email = (TextView) findViewById(R.id.useremail);
        listViewTracks =(ListView) findViewById(R.id.listViewTracks);
        studentList = new ArrayList<>();

        //get current user
        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


        //---------------------------------IniButton----------------------------------------------------
        FloatingActionButton listact = (FloatingActionButton ) findViewById(R.id.bdaylist);
        FloatingActionButton  useract = (FloatingActionButton ) findViewById(R.id.buser);
        final FloatingActionButton feedbackact = (FloatingActionButton ) findViewById(R.id.bfeedback);
        FloatingActionButton signoutact = (FloatingActionButton ) findViewById(R.id.imageButtonLogout);
        maill = (TextView) findViewById(R.id.mail);
        //---------------------------------Buttonaction--------------------------------------------------
        listact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sum Of Daily Record", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Still in progress...", Toast.LENGTH_SHORT).show();
            }
        });

        useract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Edit User", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Still in progress...", Toast.LENGTH_SHORT).show();
                //willaddaction
            }
        });

        feedbackact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "News/Feedback", Toast.LENGTH_LONG).show();
                Intent feedback = new Intent(MainActivity.this, Feedback.class);
                MainActivity.this.startActivity(feedback);

            }
        });

        signoutact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                Intent signout = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(signout);

            }
        });

    }

    @Override
    public void onStart() {

        final TextView countuse = (TextView) findViewById(R.id.countuse);
        final TextView resultt = (TextView) findViewById(R.id.resultinc);

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

                StudentListone adapter = new StudentListone(MainActivity.this, studentList);
                listViewTracks.setAdapter(adapter);
                countuse.setText(String.valueOf(listViewTracks.getAdapter().getCount()));

                int a = Integer.parseInt(countuse.getText().toString());

                a = a * 3;

                resultt.setText("MYR "+String.valueOf(a));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

  /**  public void displayData() {
        TextView dailycount = (TextView) findViewById(R.id.textView5);
        dailycount.setText(String.valueOf(listViewTracks.getCount()));
    } **/

    //@SuppressLint("SetTextI18n")
    //private void setDataToView(FirebaseUser user) {

   //     maill.setText(user.getEmail());


 //   }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                ;

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
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}