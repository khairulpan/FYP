package com.ashrafazmi.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

       /** if (auth.getCurrentUser() != null) {
            startActivity(new Intent(com.ashrafazmi.admin.LoginActivity.this, com.ashrafazmi.admin.Logsecondlayer.class));
            finish();
        } **/

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = etUsername.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String email = jsonResponse.getString("email");
                                String code = jsonResponse.getString("code");
                                String fullname = jsonResponse.getString("fullname");
                               // String secret = jsonResponse.getString("secret");

                                /**Intent intent = new Intent(com.ashrafazmi.admin.LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("accountbalance", accountbalance);
                                intent.putExtra("username", username);
                                com.ashrafazmi.admin.LoginActivity.this.startActivity(intent);**/

                                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("email",email);
                                editor.putString("fullname",fullname);
                                editor.putString("user",user);
                                editor.putString("code",code);
                              //  editor.putString("secret",secret);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, Logsecondlayer.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user , responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

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
