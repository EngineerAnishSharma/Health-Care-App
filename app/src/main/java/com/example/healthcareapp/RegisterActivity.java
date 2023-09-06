package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername,edEmail,edPassword1,edPassword2;
    Button registerBtn;
    TextView existingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUsername=findViewById(R.id.editTextRegisterUsername);
        edEmail=findViewById(R.id.editTextRegisterEmail);
        edPassword1=findViewById(R.id.editTextRegisterPassword);
        edPassword2=findViewById(R.id.editTextRegisterConfirmPassword);
        registerBtn=findViewById(R.id.buttonRegisterLogin);
        existingUser=findViewById(R.id.textViewExistingAccount);

        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edUsername.getText().toString();
                String email=edEmail.getText().toString();
                String password=edPassword1.getText().toString();
                String confirm=edPassword2.getText().toString();

                Database db=new Database(getApplicationContext(),"healthcare",null,1);
                if(username.length()==0 || email.length()==0 || password.length()==0 || confirm.length()==0){
                    Toast.makeText(RegisterActivity.this, "Fill all required details", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.compareTo(confirm)==0){
                        if(isValid(password)){
                            db.register(username,email,password);
                            Toast.makeText(RegisterActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Password must contain at least 8 characters, having letters,digits and special symbols.", Toast.LENGTH_SHORT).show();
//                            edPassword1.setText("");
//                            edPassword2.setText("");
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Password and confirm password didn't match", Toast.LENGTH_SHORT).show();
//                        edPassword1.setText("");
//                        edPassword2.setText("");
                    }
                }
            }
        });
    }
    public static boolean isValid(String password){
        int f1=0,f2=0,f3=0;
        if(password.length()<8)
            return false;
        else{
            for(int i=0;i<password.length();i++){
                if(Character.isLetter(password.charAt(i)))
                    f1=1;
            }
            for(int i=0;i<password.length();i++){
                if(Character.isDigit(password.charAt(i)))
                    f2=1;
            }
            for(int i=0;i<password.length();i++){
                char c=password.charAt(i);
                if(c>=33 && c<=64)
                    f3=1;
            }
            if(f1==1 && f2==1 && f3==1)
                return true;
            return false;
        }
    }
}