package com.middleton.middletonfbla.Register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.middleton.middletonfbla.Paypal_Config.PaypalConfig;
import com.middleton.middletonfbla.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class RegisterPayment extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalConfig.PAYPAL_CLIENT_ID);

    CheckBox membership, dlc, shirt;
    Spinner shirtSize, paymentType;
    TextView shirtText;
    FloatingActionButton paymentBtn;
    FirebaseFirestore db;
    DocumentReference database;
    FirebaseAuth auth;
    String size, type;
    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        database = db.collection("User_Information").document(auth.getCurrentUser().getUid());

        membership = (CheckBox) findViewById(R.id.paymentRegistration);
        dlc = (CheckBox) findViewById(R.id.paymentDLC);
        shirt = (CheckBox) findViewById(R.id.paymentShirt);
        shirtSize = (Spinner) findViewById(R.id.shirtSize);
        paymentType = (Spinner) findViewById(R.id.paymentType);
        paymentBtn = (FloatingActionButton) findViewById(R.id.registerPayment);
        shirtText = (TextView) findViewById(R.id.shirtText);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You are required to pay the membership fee in order to join FBLA");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                size = shirtSize.getSelectedItem().toString().trim();
                type = paymentType.getSelectedItem().toString().trim();

                if(membership.isChecked()){
                    amount += 30;
                }else{
                    builder.show();
                }
                if(dlc.isChecked()){
                    amount += 20;

                }
                if(shirt.isChecked()){
                    amount += 10;

                    if(size.equals("Select")){
                        Toast.makeText(RegisterPayment.this, "Please select a shirt size", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if(type.equals("Select")){
                    Toast.makeText(RegisterPayment.this, "Please select a type of payment", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(type.equals("Online Payment")){
                    builder.setMessage("Online Payments will be available on the next update");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

                database.update("shirtSize", size, "paymentType", type).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(RegisterPayment.this, null));
                        }else{
                            Toast.makeText(RegisterPayment.this, "An unexpected error has occurred. Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null){
                    try{
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);
                        System.out.println(paymentDetails);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void processPayment(){
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),
                "USD",
                "Middleton FBLA",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}
