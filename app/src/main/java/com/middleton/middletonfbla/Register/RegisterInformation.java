package com.middleton.middletonfbla.Register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.middleton.middletonfbla.R;
import com.middleton.middletonfbla.models.AccountModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterInformation extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    TextInputEditText nameET, phoneET,idET,gpaET;
    Spinner gradeSpinner;
    CircularProgressButton registerBTN;
    ImageView profileImage;
    FirebaseFirestore db;
    StorageReference storageReference;
    CollectionReference database;
    String name;
    String phone;
    String studentID;
    String imageLink;
    String grade;
    String gpa;
    double GPA;
    int Grade;
    int StudentId;
    long Phone;
    AccountModel accountInfo;
    FirebaseAuth mAuth;
    AlertDialog.Builder alertDialog;
    private Uri filePath;
    Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = db.collection("User_Information");
        storageReference = FirebaseStorage.getInstance().getReference().child("accountImages");
        nameET = (TextInputEditText) findViewById(R.id.registerName);
        phoneET = (TextInputEditText) findViewById(R.id.registerPhone);
        idET = (TextInputEditText) findViewById(R.id.registerStudentID);
        gpaET = (TextInputEditText) findViewById(R.id.registerGPA);
        gradeSpinner = (Spinner) findViewById(R.id.registerGrade);
        profileImage = (ImageView) findViewById(R.id.registerImage);
        registerBTN = (CircularProgressButton) findViewById(R.id.registerInfoBtn);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("You need to have a 2.0 GPA or higher to be in FBLA");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameET.getText().toString().trim();
                phone = phoneET.getText().toString().trim();
                studentID = idET.getText().toString().trim();
                gpa = gpaET.getText().toString().trim();
                grade = gradeSpinner.getSelectedItem().toString().trim();
                addData(name, phone, studentID, gpa, grade, imageLink);
            }
        });

    }

    private void addData(String name, String phone, String studentID, String gpa, String grade, String imageLink){

        if(imageLink == null){
            imageLink = "default";
        }

        if(name.isEmpty()){
            nameET.setError("A name is required");
            return;
        }

        if(phone.isEmpty()){
            phoneET.setError("A phone number is required");
            return;

        }else if(phone.length() < 10 | phone.length() > 10){
            phoneET.setError("Phone number must be ten digits");
            return;
        }else{
            try{
                Phone = Long.parseLong(phone);
                System.out.println("Phone number: " + Phone);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(studentID.isEmpty()){
            idET.setError("A student ID is required");
            return;
        }else if (studentID.length() < 7 | studentID.length() > 7){
            idET.setError("Student id must be 7 digits");
            return;
        }else{
            try{
                StudentId = Integer.parseInt(studentID);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if (gpa.isEmpty()){
            gpaET.setError("Your Unweighted GPA is required");
            return;
        }else{
            try{
                GPA = Float.parseFloat(gpa);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        if(GPA > 4.0){
            gpaET.setError("Your GPA should be out of a 4.0 scale");
            return;
        }else if(GPA < 2.0){
            gpaET.setError("Must have a 2.0 GPA or higher");
            return;
        }

        if(grade.equals("Grade Level")){
            Toast.makeText(this, "Please select a grade", Toast.LENGTH_SHORT).show();
        }else if(grade.equals("9th")){
            Grade = 9;
        }else if(grade.equals("10th")){
            Grade = 10;
        }else if(grade.equals("11th")){
            Grade = 11;
        }else if(grade.equals("12th")){
            Grade = 12;
        }

        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_check_solid);

        accountInfo = new AccountModel(name, Phone, StudentId, Grade, imageLink, GPA);
        registerBTN.startAnimation();

        database.document(mAuth.getCurrentUser().getUid()).set(accountInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterInformation.this, RegisterPayment.class));
                        }
                    },2000);
                }else{
                    registerBTN.revertAnimation();
                    Toast.makeText(RegisterInformation.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.get().load(String.valueOf(bitmap)).into(profileImage);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select A Profile Image"), PICK_IMAGE_REQUEST);

        if(filePath == null){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        storageReference.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    imageLink = task.getResult().getUploadSessionUri().toString().trim();
                }else{
                    Toast.makeText(RegisterInformation.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
