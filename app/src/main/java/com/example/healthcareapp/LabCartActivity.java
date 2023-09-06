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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LabCartActivity extends AppCompatActivity {

    HashMap<String,String> items;
    ArrayList list;
    SimpleAdapter simpleAdapter;
    TextView tvTotalCost;
    ListView listView;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton,timeButton,btnCheckout,btnBack;
    private String packages[][]={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_cart);

        btnCheckout=findViewById(R.id.buttonLCCheckout);
        btnBack=findViewById(R.id.buttonLCBack);
        dateButton=findViewById(R.id.buttonLCDatePicker);
        timeButton=findViewById(R.id.buttonLCTimePicker);
        listView=findViewById(R.id.listViewLabCart);
        tvTotalCost=findViewById(R.id.textViewLCTotalCost);

        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","").toString();

        Database db=new Database(getApplicationContext(),"healthcare",null,1);
        float totalAmount=0;
        ArrayList dbData=db.getCartData(username,"Lab");
//        Toast.makeText(this, ""+dbData, Toast.LENGTH_SHORT).show();
        packages=new String[dbData.size()][];
        for(int i=0;i<packages.length;i++){
            packages[i]=new String[5];
        }
        for(int i=0;i<dbData.size();i++){
            String arrData=dbData.get(i).toString();
            String strData[]=arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0]=strData[0];
            packages[i][4]="Cost :"+strData[1]+"/-";
            totalAmount=totalAmount+Float.parseFloat(strData[1]);
        }

        tvTotalCost.setText("Total Cost :"+totalAmount);

        list=new ArrayList<>();
        for(int i=0;i<packages.length;i++){
            items=new HashMap<String,String>();
            items.put("line1",packages[i][0]);
            items.put("line2",packages[i][1]);
            items.put("line3",packages[i][2]);
            items.put("line4",packages[i][3]);
            items.put("line5",packages[i][4]);
//            items.put("line6",packages[i][5]);
            list.add(items);
        }
        simpleAdapter=new SimpleAdapter(this,list,
                R.layout.multi_line,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        listView.setAdapter(simpleAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabCartActivity.this,LabTestActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LabCartActivity.this,LabTestBookActivity.class);
                intent.putExtra("price",tvTotalCost.getText());
                intent.putExtra("date",dateButton.getText());
                intent.putExtra("time",timeButton.getText());
                startActivity(intent);
            }
        });

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
//        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                timePickerDialog.show();
                initTimePicker();
            }
        });

    }
    private void initDatePicker() {
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener(this,) {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
////                i1=i1+1;
//                dateButton.setText(i2+"/"+i1+"/"+i);
//            }
//        };
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(String.valueOf(day)+"."+String.valueOf(month+1)+"."+String.valueOf(year));
            }
        }, 2023, 0, 15);
        dialog.show();
    }

//        Calendar cal=Calendar.getInstance();
//        int year=cal.get(Calendar.YEAR);
//        int month=cal.get(Calendar.MONTH);
//        int day=cal.get(Calendar.DAY_OF_MONTH);
//
//        int style= AlertDialog.BUTTON_NEUTRAL;
//        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
//        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()*86400000);
//    }
//    private void initTimePicker(){
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
//    }
    private void initTimePicker(){
        TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timeButton.setText(String.valueOf(hour)+" : "+String.valueOf(minute));
            }
        },15,00,true);
        dialog.show();
    }
}