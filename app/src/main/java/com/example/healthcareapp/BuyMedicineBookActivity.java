package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuyMedicineBookActivity extends AppCompatActivity {

    Button btnBook;
    EditText name,address,contact,pinCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        btnBook=findViewById(R.id.buttonBMBook);
        name=findViewById(R.id.editTextBMBookFullname);
        address=findViewById(R.id.editTextBMBookAddress);
        contact=findViewById(R.id.editTextBMBookContact);
        pinCode=findViewById(R.id.editTextBMBookPincode);

        Intent intent=getIntent();
        String[] price=intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date=intent.getStringExtra("date").toString();
//        String time=intent.getStringExtra("time").toString();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.length()==0 || address.length()==0 || contact.length()==0 || pinCode.length()==0){
                    Toast.makeText(BuyMedicineBookActivity.this, "Fill all required details", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);//2
                    String username=sharedPreferences.getString("username","").toString();

                    Database db=new Database(getApplicationContext(),"healthcare",null,1);
                    db.addOrder(username,name.getText().toString(),address.getText().toString(),contact.getText().toString(),Integer.parseInt(pinCode.getText().toString()),date.toString(),"",Float.parseFloat(price[1].toString()),"medicine");
                    db.removeCart(username,"medicine");
                    Toast.makeText(getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
                }



            }
        });
    }
}