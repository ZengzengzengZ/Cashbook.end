package com.example.cashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add extends AppCompatActivity {
    private String title = null;
    private String date = null;
    private String money = null;
    private String type = null;
    private EditText et_title = null;
    private EditText et_date = null;
    private EditText et_money = null;
    private EditText et_type = null;
    private Button btn_sure = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Op op = new Op(Add.this);
        et_title = findViewById(R.id.et_title);
        et_date = findViewById(R.id.et_date);
        et_money = findViewById(R.id.et_money);
        et_type = findViewById(R.id.et_type);
        btn_sure = findViewById(R.id.btn_sure);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_title.getText().toString();
                date = et_date.getText().toString();
                money = et_money.getText().toString();
                type = et_type.getText().toString();
                op.add(title,date,money,type);

                Intent intent = new Intent(Add.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}