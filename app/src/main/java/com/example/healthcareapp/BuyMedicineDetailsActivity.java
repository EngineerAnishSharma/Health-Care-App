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

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvMedicine,tvCost;
    EditText medicineDetails;
    Button btnBack,btnAddToCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details);
        tvMedicine=findViewById(R.id.textViewBMDTitle);
        tvCost=findViewById(R.id.textViewBMDCost);
        medicineDetails=findViewById(R.id.editTextBMDMultiLine);
        btnBack=findViewById(R.id.buttonBMDBack);
        btnAddToCard=findViewById(R.id.buttonBMDAddToCard);

        Intent intent=getIntent();
        tvMedicine.setText(intent.getStringExtra("text1"));
        medicineDetails.setText(intent.getStringExtra("text2"));
        tvCost.setText("Total Cost :"+intent.getStringExtra("text3")+"/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineDetailsActivity.this,BuyMedicineActivity.class));
            }
        });

        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username=sharedPreferences.getString("username","").toString();
                String product=tvMedicine.getText().toString();
                float price=Float.parseFloat(intent.getStringExtra("text3").toString());

                Database db=new Database(getApplicationContext(),"healthcare",null,1);
                if(db.checkCart(username,product)==1){
                    Toast.makeText(BuyMedicineDetailsActivity.this, "Product already added", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.addCart(username,product,price,"medicine");
                    Toast.makeText(BuyMedicineDetailsActivity.this, "Record inserted to cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuyMedicineDetailsActivity.this,BuyMedicineActivity.class));
                }
            }
        });
    }
}