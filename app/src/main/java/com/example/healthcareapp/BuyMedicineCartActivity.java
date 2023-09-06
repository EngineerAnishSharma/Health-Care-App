package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineCartActivity extends AppCompatActivity {

    HashMap<String,String> items;
    ArrayList list;
    SimpleAdapter simpleAdapter;
    TextView tvTotalCost;
    ListView listView;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton,btnCheckout,btnBack;
    private String[][] packages={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_cart);

        btnCheckout=findViewById(R.id.buttonBMCCheckout);
        btnBack=findViewById(R.id.buttonBMCBack);
        dateButton=findViewById(R.id.buttonBMCDatePicker);
        listView=findViewById(R.id.listViewBMCart);
        tvTotalCost=findViewById(R.id.textViewBMCTotalCost);

        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","").toString();

        Database db=new Database(getApplicationContext(),"healthcare",null,1);
        float totalAmount=0;
        ArrayList dbData=db.getCartData(username,"medicine");
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
                startActivity(new Intent(BuyMedicineCartActivity.this,BuyMedicineActivity.class));
            }
        });

//        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                datePickerDialog.show();
                initDatePicker();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BuyMedicineCartActivity.this,BuyMedicineBookActivity.class);
                intent.putExtra("price",tvTotalCost.getText());
                intent.putExtra("date",dateButton.getText());
                startActivity(intent);
            }
        });
    }
//    private void initDatePicker(){
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
//    }
    private void initDatePicker(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(String.valueOf(day)+"."+String.valueOf(month+1)+"."+String.valueOf(year));
            }
        }, 2023, 0, 15);
        dialog.show();
    }
}