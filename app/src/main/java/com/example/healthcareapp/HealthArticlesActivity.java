package com.example.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    private String[][] health_Details={
            {"Walking Daily","","","","More Details"},
            {"Home care of COVID -19","","","","More Details"},
            {"Stop Smoking","","","","More Details"},
            {"Menstrual Cramps","","","","More Details"},
            {"Healthy Guts","","","","More Details"}
    };
    private int[] images={
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };
    Button btnBack;
    ListView listView;
    HashMap<String,String> items;
    ArrayList list;
    SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);
        btnBack=findViewById(R.id.buttonHABack);
        listView=findViewById(R.id.listViewHA);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticlesActivity.this,HomeActivity.class));
            }
        });

        list=new ArrayList<>();
        for(int i=0;i<health_Details.length;i++){
            items=new HashMap<>();
            items.put("line1",health_Details[i][0]);
            items.put("line2",health_Details[i][1]);
            items.put("line3",health_Details[i][2]);
            items.put("line4",health_Details[i][3]);
            items.put("line5",health_Details[i][4]);
//            items.put("line6",health_Details[i][5]);
            list.add(items);
        }
        simpleAdapter=new SimpleAdapter(this,list,
                R.layout.multi_line,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(HealthArticlesActivity.this, HealthArticlesDetailsActivity.class);
                intent.putExtra("text1",health_Details[i][0]);
                intent.putExtra("text2",images[i]);
                startActivity(intent);
            }
        });
    }
}