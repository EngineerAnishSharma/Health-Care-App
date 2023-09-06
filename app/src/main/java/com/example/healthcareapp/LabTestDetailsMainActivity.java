package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailsMainActivity extends AppCompatActivity {

    TextView tvPackage,tvTotalCost;
    EditText edDetails;
    Button btnAddCard,btnGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details_main);

        tvPackage=findViewById(R.id.textViewLTDPackages);
        tvTotalCost=findViewById(R.id.textViewLTDCost);
        edDetails=findViewById(R.id.editTextLTDMultiLine);
        btnAddCard=findViewById(R.id.buttonLTDAddToCard);
        btnGoBack=findViewById(R.id.buttonLTDBack);
        edDetails.setKeyListener(null);

        Intent intent=getIntent();
        tvPackage.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost :"+intent.getStringExtra("text3")+"/-");

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetailsMainActivity.this,LabTestActivity.class));
            }
        });
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);//3
                String username=sharedPreferences.getString("username","").toString();
                String product=tvPackage.getText().toString();
                float price=Float.parseFloat(intent.getStringExtra("text3").toString());
                Database db=new Database(getApplicationContext(),"healthcare",null,1);
                if(db.checkCart(username,product)==1){
                    Toast.makeText(LabTestDetailsMainActivity.this, "Product already added", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.addCart(username,product,price,"Lab");
                    Toast.makeText(LabTestDetailsMainActivity.this, "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsMainActivity.this,LabTestActivity.class));
                }
            }
        });
    }
}