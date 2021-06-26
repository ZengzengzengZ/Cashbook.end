package com.example.cashbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Find extends AppCompatActivity {

    private static int choice  = 0;
    ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        Op op = new Op(Find.this);
        MyMoney myMoney = new MyMoney();
        Button btn_back = findViewById(R.id.btn_back);
        Button btn_seatch = findViewById(R.id.btn_search);
        EditText et_find = findViewById(R.id.et_find);
        RadioGroup group = findViewById(R.id.rg_find);
        ListView lv = findViewById(R.id.lv_find);
        group.clearCheck();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (rb.getText().toString().equals("按标题")) {
                    choice = 0;
                } else if (rb.getText().toString().equals("按类型")) {
                    choice = 1;
                } else if (rb.getText().toString().equals("按日期")) {
                    choice = 2;
                }
            }
        });
        btn_seatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> ListItem = new ArrayList<HashMap<String, Object>>();
                List<Bills> bills = new ArrayList<>();
                String search_content = et_find.getText().toString();
                if(choice==0) {
                    bills = op.findbytitle(search_content);
                }
                else if(choice==1)
                {
                    bills = op.findbytype(search_content);
                }
                else if(choice==2)
                {
                    bills = op.findbydate(search_content);
                }

                for (int i = 0; i < bills.size(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    Bills bill = bills.get(i);
                    if (bill.getCostType().equals("income")) {
                        myMoney.setIncome(Double.parseDouble(bill.getCostMoney()));
                    } else {
                        myMoney.setCost(Double.parseDouble(bill.getCostMoney()));
                    }
                    map.put("id", bill.getId());
                    map.put("Title", bill.getCostTitle());
                    map.put("Date", bill.getCostDate());
                    map.put("Money", bill.getCostMoney());
                    map.put("Type", bill.getCostType());
                    ListItem.add(map);
                }
                Item = ListItem;
                SimpleAdapter sa = new SimpleAdapter(Find.this, ListItem, R.layout.item, new String[]{"Title", "Date", "Money", "Type"},
                        new int[]{R.id.tv_title, R.id.tv_date, R.id.tv_money, R.id.tv_type});
                lv.setAdapter(sa);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Find.this , Details.class);
                        intent.putExtra("id",String.valueOf(Item.get(position).get("id")));
                        intent.putExtra("content", (Serializable) Find.class);
                        startActivity(intent);
                    }
                });
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder builder= new AlertDialog.Builder(Find.this);
                        //Toast.makeText(getApplicationContext(),"Action",Toast.LENGTH_SHORT).show();
                        builder.setMessage("确定删除？");
                        builder.setTitle("提示");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                op.delete(Integer.parseInt(Item.get(position).get("id").toString()));
                                if(String.valueOf(Item.get(position).get("Type")).equals("income"))
                                {
                                    myMoney.setIncome(-1*Double.parseDouble(String.valueOf(Item.get(position).get("Money"))));
                                }
                                else
                                {
                                    myMoney.setCost(-1*Double.parseDouble(String.valueOf(Item.get(position).get("Money"))));
                                }
                                Item.remove(position);
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
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Find.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }
}