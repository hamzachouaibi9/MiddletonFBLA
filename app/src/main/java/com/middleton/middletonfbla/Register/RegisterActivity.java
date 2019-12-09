package com.middleton.middletonfbla.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.middleton.middletonfbla.R;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText emailET, passwordET;
    FloatingActionButton registerBtn;
    FirebaseAuth firebaseAuth;
    String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        emailET = (TextInputEditText) findViewById(R.id.registerEmail);
        passwordET = (TextInputEditText) findViewById(R.id.registerPassword);
        registerBtn = (FloatingActionButton) findViewById(R.id.registerNext);

        final Animation rotation = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.rotate);
        rotation.setFillAfter(true);


        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("This email is already in use. Please log in");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotateFab(registerBtn, true);

                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();

                if(email.isEmpty()){
                    emailET.setError("Need an Email");
                    registerBtn.clearAnimation();
                    return;
                }else if (password.isEmpty()) {
                    passwordET.setError("Need a Password");
                    registerBtn.clearAnimation();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(RegisterActivity.this, RegisterInformation.class));
                        }else if(!task.isSuccessful()){
                            try{
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                passwordET.setError("Password needs to be 6 characters or longer.");
                                registerBtn.clearAnimation();
                            }catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                emailET.setError("Email is in incorrect format");
                                registerBtn.clearAnimation();
                            } catch (FirebaseAuthUserCollisionException existEmail){
                                builder.show();
                                registerBtn.clearAnimation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            rotateFab(registerBtn, false);
                        }
                    }
                });
            }
        });


    }

    public static boolean rotateFab(final View v, final boolean rotate) {
        do {
            v.animate().setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rotateFab(v,rotate);
                        }
                    })
                    .rotation(rotate ? 360f: 0f);
            return rotate;
        }while (true);

    }
}
