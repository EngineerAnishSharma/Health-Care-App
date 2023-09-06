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

public class BuyMedicineActivity extends AppCompatActivity {

    private String[][] packages = {
            {"Aspirin", "","","","600"},
            {"Ibuprofen", "","","","500"},
            {"Paracetamol", "","","","300"},
            {"Amoxicillin", "","","","500"},
            {"Lisinopril", "","","","350"}

    };

    String[] packageDetails={
            "Widely used as a pain reliever and anti-inflammatory agent.\n"+
               "Helps reduce fever and minor aches.\n" +
               "Can be beneficial for individuals at risk of heart attack or stroke.\n" +
               "Available over-the-counter and in various forms",
            "Effective at relieving pain, reducing inflammation, and lowering fever.\n" +
                    "Versatile medication suitable for headaches, muscle aches, menstrual cramps, and arthritis.\n" +
                    "Available over-the-counter and in prescription-strength formulations.\n" +
                    "Comes in different forms, including tablets, capsules, and liquid.\n",
            "Widely used for mild to moderate pain relief and reducing fever.\n" +
                    "Generally considered safe when taken as directed.\n" +
                    "Often included in combination with other medications for cold and flu relief.\n" +
                    "Available over-the-counter in various formulations.\n",
            "A commonly prescribed antibiotic effective against a wide range of bacterial infections.\n" +
                    "Used to treat respiratory tract infections, ear infections, and urinary tract infections.\n" +
                    "Relatively well-tolerated with few side effects for most people.\n" +
                    "Available in various forms, including tablets and liquid.\n",
            "An ACE inhibitor used to manage high blood pressure and heart failure.\n" +
                    "Helps relax blood vessels, reducing strain on the heart and improving blood flow.\n" +
                    "Can be beneficial for individuals with certain kidney conditions.\n" +
                    "May lower the risk of heart attack and stroke in some cases."

    };
    ArrayList list;
    SimpleAdapter simpleAdapter;
    HashMap<String,String> items;
    Button btnBack,btnCart;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        btnCart=findViewById(R.id.buttonBMGoToCard);
        btnBack=findViewById(R.id.buttonBMBack);
        listView=findViewById(R.id.listViewBuyMedicine);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this,BuyMedicineCartActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        list=new ArrayList<>();
        for(int i=0;i<packages.length;i++){
            items=new HashMap<>();
            items.put("line1",packages[i][0]);
            items.put("line2",packages[i][1]);
            items.put("line3",packages[i][2]);
            items.put("line4",packages[i][3]);
            items.put("line5","Total cost :"+packages[i][4]+"/-");
//            items.put("line6",packages[i][5]);
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
                Intent intent=new Intent(BuyMedicineActivity.this,BuyMedicineDetailsActivity.class);
                intent.putExtra("text1",packages[i][0]);
                intent.putExtra("text2",packageDetails[i]);
                intent.putExtra("text3",packages[i][4]);
                startActivity(intent);
            }
        });
    }
}