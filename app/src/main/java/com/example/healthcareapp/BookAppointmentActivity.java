package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4;
    TextView tvTitle;
    Button dateButton,timeButton,btnBook,btnBack;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tvTitle=findViewById(R.id.textViewBookingTitle);
        ed1=findViewById(R.id.editTextBookingFull);
//        ed2=findViewById(R.id.editTextBookingHospital);
        ed2=findViewById(R.id.editTextbookingAddress);
        ed3=findViewById(R.id.editTextbookingContact);
        ed4=findViewById(R.id.editTextbookingFees);
        dateButton=findViewById(R.id.buttonBookingDatePicker);
        timeButton=findViewById(R.id.buttonBookingTimePicker);
        btnBack=findViewById(R.id.buttonBookingBack);
        btnBook=findViewById(R.id.buttonBookingAppointment);

        // Not editable
        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);
//        ed5.setKeyListener(null);

        Intent intent=getIntent();
        String title=intent.getStringExtra("text1");
        String fullname=intent.getStringExtra("text2");
//        String hospital=intent.getStringExtra("text3");
        String address=intent.getStringExtra("text3");
        String mobile=intent.getStringExtra("text4");
        String fees=intent.getStringExtra("text5");

        tvTitle.setText(title);
        ed1.setText(fullname);
//        ed2.setText(hospital);
        ed2.setText(address);
        ed3.setText(mobile);
        ed4.setText("Cons fees :"+fees);

        //DatePicker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                datePickerDialog.show();
                initDatePicker();
            }
        });

        //TimePicker
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                timePickerDialog.show();
                initTimePicker();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this,FindDoctorActivity.class));
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database(getApplicationContext(),"healthcare",null,1);
                SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);//2
                String username=sharedPreferences.getString("username","").toString();
                if(db.checkAppointmentExists(username,title+" => "+fullname,address,mobile,dateButton.getText().toString(),timeButton.getText().toString())==1){
                    Toast.makeText(BookAppointmentActivity.this, "Appointment already added", Toast.LENGTH_SHORT).show();
                }else{
                    db.addOrder(username,title+" => "+fullname,address,mobile,0,dateButton.getText().toString(),timeButton.getText().toString(),Float.parseFloat(fees),"appointment");
                    Toast.makeText(BookAppointmentActivity.this, "Appointment is done successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                }
            }
        });
    }
    private void initDatePicker(){
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
////                i1=i1+1;
//                dateButton.setText(i2+"/"+i1+"/"+i);
//            }
//        };
//        Calendar cal=Calendar.getInstance();
//        int year=cal.get(Calendar.YEAR);
//        int month=cal.get(Calendar.MONTH);
//        int day=cal.get(Calendar.DAY_OF_MONTH);
//
//        int style= AlertDialog.BUTTON_NEUTRAL;
//        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
//        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()*86400000);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(String.valueOf(day)+"."+String.valueOf(month+1)+"."+String.valueOf(year));
            }
        }, 2023, 0, 15);
        dialog.show();

    }
    private void initTimePicker(){
//        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                timeButton.setText(i+" : "+i1);
//            }
//        };
//        Calendar cal=Calendar.getInstance();
//        int hrs=cal.get(Calendar.HOUR);
//        int mins=cal.get(Calendar.MINUTE);
//        int style= AlertDialog.BUTTON_NEUTRAL;
//        timePickerDialog=new TimePickerDialog(this,style,timeSetListener,hrs,mins,true);

            TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    timeButton.setText(String.valueOf(hour)+" : "+String.valueOf(minute));
                }
            },15,00,true);
            dialog.show();
        }
}