package com.example.cashbook;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = this.getIntent();
        final String id = intent.getStringExtra("id");
        final Context context = (Context) intent.getSerializableExtra("context");

        Op op = new Op(Details.this);
        MyMoney myMoney = new MyMoney();
        Bills bill = op.find(Integer.parseInt(id));
        String costTitle = bill.getCostTitle();
        String costDate= bill.getCostDate();
        String costMoney = bill.getCostMoney();
        String costType = bill.getCostType();
        RadioButton rb1 = findViewById(R.id.rb_btn1);
        RadioButton rb2 = findViewById(R.id.rb_btn2);
        if(costType.equals("支出"))
        {
            rb1.setChecked(true);
            rb2.setChecked(false);
        }
        else{
            rb2.setChecked(true);
            rb1.setChecked(false);
        }
        RadioGroup group = findViewById(R.id.rg_d_type);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(),"group-action",Toast.LENGTH_SHORT).show();
                RadioButton rb = group.findViewById(checkedId);
                if (rb.getText().toString().equals("支出")) {
                    myMoney.setType(0);
                } else if (rb.getText().toString().equals("收入")) {
                    Toast.makeText(getApplicationContext(),"action",Toast.LENGTH_SHORT).show();
                    myMoney.setType(1);
                }
            }
        });
        EditText title = (EditText) findViewById(R.id.et_d_title);
        EditText money = (EditText) findViewById(R.id.et_d_money);
        DatePicker date = (DatePicker) findViewById(R.id.dp_d_date);

        title.setText(costTitle);
        money.setText(costMoney);
        String a[] = costDate.split("-");

        Button btn_change = findViewById(R.id.btn_d_change);
        btn_change.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String t_date = date.getYear() + "-" + (date.getMonth() + 1) + "-" +
                        date.getDayOfMonth();
                String Type  ="";
                if(myMoney.getType()==0)
                {
                    Type = "支出";
                }
                else Type = "收入";
                Toast.makeText(getApplicationContext(),Type,Toast.LENGTH_SHORT).show();
                if(op.CanConvert(money.getText().toString())) {
                    op.updata(Integer.parseInt(id), title.getText().toString(), t_date, money.getText().toString(), Type);
                    Intent intent = new Intent(Details.this , context.getClass());
                    startActivity(intent);
                }else
                {
                    money.setText("输入格式错误");
                }
            }
        });
    }
}

