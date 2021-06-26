package com.example.cashbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static int s_type = 0;
    private Button btn_add = null;
    private Button btn_find = null;
    private LinearLayout bill_list = null;
    private ListView bills_list= null;
    private TextView tv_sum = null;
    private MyMoney myMoney = new MyMoney();
    private ArrayList<HashMap<String,Object>> ListItem = new ArrayList<HashMap<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Op op = new Op(MainActivity.this);
        btn_add = findViewById(R.id.btn_add);
        btn_find = findViewById(R.id.btn_find);
        bills_list = findViewById(R.id.bills_list);
        tv_sum = findViewById(R.id.tv_sum);
        double income = 0,expenditure =0;
        for(int i = 1;i <= op.data_count();i++)
        {
            HashMap<String,Object> map = new HashMap<String,Object>();
            Bills bill = op.find(i);
            if(bill.getCostType().equals("income"))
            {
                 myMoney.setIncome(Double.parseDouble(bill.getCostMoney()));
            }
            else
            {
                myMoney.setCost(Double.parseDouble(bill.getCostMoney()));
            }
            map.put("id",i);
            map.put("Title",bill.getCostTitle());
            map.put("Date",bill.getCostDate());
            //Toast.makeText(getApplicationContext(),bill.getCostDate(),Toast.LENGTH_SHORT).show();
            map.put("Money",bill.getCostMoney());
            map.put("Type",bill.getCostType());
            ListItem.add(map);
        }
        SimpleAdapter sa = new SimpleAdapter(this,ListItem,R.layout.item,new String[]{"Title","Date","Money","Type"},
                new int[]{R.id.tv_title,R.id.tv_date,R.id.tv_money,R.id.tv_type});
        bills_list.setAdapter(sa);
        tv_sum.setText("    总支出为："+myMoney.getCost()+"元       总收入为："+myMoney.getIncome()+"元");


        bills_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , Details.class);
                intent.putExtra("id",String.valueOf(ListItem.get(position).get("id")));
                intent.putExtra("content", (Serializable) MainActivity.class);
                startActivity(intent);
            }
        });
        bills_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                //Toast.makeText(getApplicationContext(),"Action",Toast.LENGTH_SHORT).show();
                builder.setMessage("确定删除？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        op.delete(Integer.parseInt(ListItem.get(position).get("id").toString()));
                        if(String.valueOf(ListItem.get(position).get("Type")).equals("income"))
                        {
                            myMoney.setIncome(-1*Double.parseDouble(String.valueOf(ListItem.get(position).get("Money"))));
                        }
                        else
                        {
                            myMoney.setCost(-1*Double.parseDouble(String.valueOf(ListItem.get(position).get("Money"))));
                        }
                        tv_sum.setText(" 总支出为："+myMoney.getCost()+"元       总收入为："+myMoney.getIncome()+"元");
                        ListItem.remove(position);
                        sa.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create().show();
                return true;
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data, null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_d_date);
                RadioGroup group = viewDialog.findViewById(R.id.rg_type);
                group.clearCheck();
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = group.findViewById(checkedId);
                        if(rb.getText().toString().equals("收入"))
                        {
                            myMoney.setType(1);
                            s_type = 1;
                            //Toast.makeText(getApplicationContext(),"action",Toast.LENGTH_SHORT).show();
                        }
                        else if(rb.getText().toString().equals("支出"))
                        {
                            myMoney.setType(0);
                            s_type = 0;
                        }
                    }
                });
//                添加
                builder.setView(viewDialog);
                builder.setTitle("新的账单");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String costTitle= title.getText().toString();
                        String costMoney = money.getText().toString();
                        String costDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" +
                                date.getDayOfMonth();
                        String costType="";
                        if(op.CanConvert(costMoney)) {
                            if (s_type == 0) {
                                costType = "支出";
                            } else costType = "收入";
                            op.add(costTitle, costDate, costMoney, costType);
                            if (costType.equals("收入")) {
                                myMoney.setIncome(Double.parseDouble(costMoney));
                            } else {
                                myMoney.setCost(Double.parseDouble(costMoney));
                            }
                            tv_sum.setText("    总支出为：" + myMoney.getCost() + "元       总收入为：" + myMoney.getIncome() + "元");

                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("id", op.data_count());
                            map.put("Title", costTitle);
                            map.put("Date", costDate);
                            //Toast.makeText(getApplicationContext(),bill.getCostDate(),Toast.LENGTH_SHORT).show();
                            map.put("Money", costMoney);
                            map.put("Type", costType);
                            ListItem.add(map);

                            sa.notifyDataSetChanged();
                        }else{
                            money.setText("输入格式错误");
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                //Intent intent = new Intent(MainActivity.this,Add.class);
                //startActivity(intent);
            }
        });
        btn_find.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Find.class);
                startActivity(intent);
            }
        });
    }
}