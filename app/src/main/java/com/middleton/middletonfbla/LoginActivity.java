package com.middleton.middletonfbla;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.middleton.middletonfbla.Register.RegisterActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextInputEditText emailET, passET;
    CircularProgressButton loginBtn;
    Button registerBtn;
    String email, password;
    Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
        }else{

        }

        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_check_solid);

        emailET = (TextInputEditText) findViewById(R.id.loginEmail);
        passET = (TextInputEditText) findViewById(R.id.loginPass);
        loginBtn = (CircularProgressButton) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.startAnimation();
                email = emailET.getText().toString().trim();
                password = passET.getText().toString().trim();

                if (email.isEmpty()) {
                    emailET.setError("Need an Email to continue");
                    return;
                }else if (password.length()<6){
                    passET.setError("Password needs to be 6 characters or longer");
                    return;
                }else if (password.isEmpty()){
                    passET.setError("Need a password to continue");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        },100);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        loginBtn.revertAnimation();
                    }
                });
                
            }
        });
        
        
    }
}
