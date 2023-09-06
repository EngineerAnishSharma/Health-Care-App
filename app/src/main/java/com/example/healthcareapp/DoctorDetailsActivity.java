package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    String[][] doctorDetails1 = {
            {"Doctor Name : Dr. Emily Collins", "Hospital Address : Cityville", "Exp : 15 years", "Mobile No:(555) 123-4567", "350"},
            {"Doctor Name : Dr. Benjamin Patel", "Hospital Address : Townsville", "Exp : 12 years", "Mobile No:555) 987-6543", "420"},
            {"Doctor Name : Dr. Sophia Ramirez", "Hospital Address : Villageton", "Exp : 8 years", "Mobile No:(555) 345-6789", "130"},
            {"Doctor Name : Dr. Samuel Johnson", "Hospital Address : Cityscape", "Exp : 20 years", "Mobile No:(555) 234-5678", "180"},
            {"Doctor Name : Dr. Aisha Khan", "Hospital Address : Townville", "Exp : 10 years", "Mobile No:(555) 876-5432", "540"}
    };
    String[][] doctorDetails2 = {
            {"Doctor Name : Dr. Robert Johnson", "Hospital Address : Townsville", "Exp : 18 years", "Mobile No:(555) 567-8901", "160"},
            {"Doctor Name : Dr. Maria Garcia", "Hospital Address : Cityville", "Exp : 14 years", "Mobile No:(555) 234-5678", "140"},
            {"Doctor Name : Dr. William Smith", "Hospital Address : Villageton", "Exp : 9 years", "Mobile No:(555) 789-0123", "120"},
            {"Doctor Name : Dr. Jennifer Lee", "Hospital Address : Cityscape", "Exp : 22 years", "Mobile No:(555) 345-6789", "190"},
            {"Doctor Name : Dr. Ahmed Khan", "Hospital Address : Townville", "Exp : 11 years", "Mobile No:(555) 901-2345", "130"}
    };
    String[][] doctorDetails3 = {
            {"Doctor Name : Dr. Sarah Walker", "Hospital Address : Cityville", "Exp : 16 years", "Mobile No:(555) 890-1234", "140"},
            {"Doctor Name : Dr. Michael Anderson", "Hospital Address : Townsville", "Exp : 10 years", "Mobile No:(555) 567-8901", "120"},
            {"Doctor Name : Dr. Emily Roberts", "Hospital Address : Villageton", "Exp : 7 years", "Mobile No:(555) 234-5678", "110"},
            {"Doctor Name : Dr. James Wilson", "Hospital Address : Cityscape", "Exp : 21 years", "Mobile No:(555) 345-6789", "170"},
            {"Doctor Name : Dr. Amina Patel", "Hospital Address : Townville", "Exp : 14 years", "Mobile No:(555) 901-2345", "130"}
    };
    String[][] doctorDetails4 = {
            {"Doctor Name : Dr. Sarah Davis", "Hospital Address : Riverside", "Exp : 16 years", "Mobile No:(555) 345-6789", "150"},
            {"Doctor Name : Dr. Michael Anderson", "Hospital Address : Metropolis", "Exp : 13 years", "Mobile No:(555) 987-6543", "140"},
            {"Doctor Name : Dr. Emma Wilson", "Hospital Address : Sunnyvale", "Exp : 7 years", "Mobile No:(555) 234-5678", "130"},
            {"Doctor Name : Dr. Christopher Lee", "Hospital Address : Lakeside", "Exp : 19 years", "Mobile No:(555) 876-5432", "170"},
            {"Doctor Name : Dr. Sophia Patel", "Hospital Address : Townsville", "Exp : 12 years", "Mobile No:(555) 901-2345", "140"}
    };
    String[][] doctorDetails5 = {
            {"Doctor Name : Dr. Elizabeth Miller", "Hospital Address : Townsville", "Exp : 16 years", "Mobile No:(555) 678-9012", "155"},
            {"Doctor Name : Dr. Michael Anderson", "Hospital Address : Cityville", "Exp : 11 years", "Mobile No:(555) 123-4567", "125"},
            {"Doctor Name : Dr. Olivia Wilson", "Hospital Address : Villageton", "Exp : 7 years", "Mobile No:(555) 456-7890", "135"},
            {"Doctor Name : Dr. Christopher Brown", "Hospital Address : Cityscape", "Exp : 19 years", "Mobile No:(555) 890-1234", "175"},
            {"Doctor Name : Dr. Farah Rahman", "Hospital Address : Townville", "Exp : 13 years", "Mobile No:(555) 234-5678", "145"}
    };
    TextView tv;
    Button btn;
    String[][] doctorDetails={};
    ArrayList list;
    SimpleAdapter simpleAdapter;
    HashMap<String,String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv=findViewById(R.id.textViewDDLocation);
        btn=findViewById(R.id.buttonDDBack);
        ListView lst=findViewById(R.id.listViewDoctorDetails);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        tv.setText(title);

        if(title.compareTo("Family Physician")==0)
            doctorDetails=doctorDetails1;
        else
        if(title.compareTo("Dietician")==0)
            doctorDetails=doctorDetails2;
        else
        if(title.compareTo("Dentist")==0)
            doctorDetails=doctorDetails3;
        else
        if(title.compareTo("Surgeon")==0)
            doctorDetails=doctorDetails4;
        else
            doctorDetails=doctorDetails5;

        list=new ArrayList<>();
        for(int i=0;i<doctorDetails.length;i++){
            items=new HashMap<>();
            items.put("line1",doctorDetails[i][0]);
            items.put("line2",doctorDetails[i][1]);
            items.put("line3",doctorDetails[i][2]);
            items.put("line4",doctorDetails[i][3]);
//            items.put("line5",doctorDetails[i][4]);
            items.put("line5","Cons fees :"+doctorDetails[i][4]);
            list.add(items);
        }

        simpleAdapter=new SimpleAdapter(this,list,
                R.layout.multi_line,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );

        lst.setAdapter(simpleAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                intent.putExtra("text1",title);
                intent.putExtra("text2",doctorDetails[i][0]);
                intent.putExtra("text3",doctorDetails[i][1]);
                intent.putExtra("text4",doctorDetails[i][3]);
                intent.putExtra("text5",doctorDetails[i][4]);
//                intent.putExtra("text6",doctorDetails[i][5]);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class));
            }
        });
    }
}