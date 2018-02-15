package com.ashrafazmi.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Logsecondlayer extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView welcometext, inputEmailview;
    private FirebaseAuth auth;
    private Button btnLogin, btnreset;
    private CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_second_layer);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        final String mail = preferences.getString("email", "");
        String code = preferences.getString("code","");
        final String fullname = preferences.getString("fullname","");
        //String secretcode = preferences.getString("secret", "");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

       /** if (auth.getCurrentUser() != null) {
            startActivity(new Intent(com.ashrafazmi.admin.Logsecondlayer.this, ImageListActivity.class));
            finish();
        } **/

        setContentView(R.layout.log_second_layer);
        inputEmail = (EditText) findViewById(R.id.email);
        inputEmailview = (TextView) findViewById(R.id.emailview);
        inputPassword = (EditText) findViewById(R.id.password);
        welcometext = (TextView) findViewById(R.id.welcometv);
        check = (CheckBox) findViewById(R.id.checkBox);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnreset = (Button) findViewById(R.id.resettbtn);


        //  welcometext.setText(secretcode);
        inputEmailview.setText(mail);
        inputEmail.setText(mail);
        welcometext.setText("["+code+"]");

       // welcometext.setText("Welcome To UniKL UniMotion\n"+name+"("+studentid+")");

        //checkbox

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked())
                    inputPassword.setVisibility(View.VISIBLE);
                else
                    inputPassword.setVisibility(View.GONE);

            }
        });


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmailview.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

               // progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Logsecondlayer.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                              //  progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(" too short");
                                    } else {
                                        Toast.makeText(Logsecondlayer.this, "Failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Logsecondlayer.this, MainActivity.class);
                                    Toast.makeText(Logsecondlayer.this, "Welcome "+fullname, Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Logsecondlayer.this, "Reset your password here", Toast.LENGTH_LONG).show();
                Intent reset = new Intent(Logsecondlayer.this, ResetPasswordActivity.class);
                Logsecondlayer.this.startActivity(reset);

            }
        });

    }





    }

