package com.example.deeksha.spendmate;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class dashboard extends AppCompatActivity {

    signupdb db=new signupdb(dashboard.this);
    RecyclerView recyclerView;
    ArrayList<incomedata> data;
    String plus="+",minus="-";
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i=getIntent();
        final int cust_id= i.getIntExtra("id",0);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor res=db.incomegetdata(cust_id);
        Cursor res1=db.expensegetdata(cust_id);
        data=new ArrayList<>();

        while(res.moveToNext())
        {
            incomedata obj=new incomedata(res.getString(3),res.getString(1),res.getString(0),res.getString(4),cust_id,plus,0);
            data.add(obj);
        }
        while(res1.moveToNext())
        {
            incomedata obj=new incomedata(res1.getString(3),res1.getString(1),res1.getString(0),res1.getString(4),cust_id,minus,1);
            data.add(obj);
        }

        Collections.sort(data, new Comparator<incomedata>() {
            @Override
            public int compare(incomedata lhs, incomedata rhs) {
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                Date d1=null,d2=null;
                try {
                    d1=sdf.parse(rhs.getTrdate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2=sdf.parse(lhs.getTrdate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1.compareTo(d2);
            }
        });


        Myadapter adapter=new Myadapter(dashboard.this,data);//income adapter
        recyclerView.setAdapter(adapter);

        swipe=findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Cursor res=db.incomegetdata(cust_id);
                Cursor res1=db.expensegetdata(cust_id);
                data=new ArrayList<>();

                while(res.moveToNext())
                {
                    incomedata obj=new incomedata(res.getString(3),res.getString(1),res.getString(0),res.getString(4),cust_id,plus,0);
                    data.add(obj);
                }
                while(res1.moveToNext())
                {
                    incomedata obj=new incomedata(res1.getString(3),res1.getString(1),res1.getString(0),res1.getString(4),cust_id,minus,1);
                    data.add(obj);
                }

                Collections.sort(data, new Comparator<incomedata>() {
                    @Override
                    public int compare(incomedata lhs, incomedata rhs) {
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                        Date d1=null,d2=null;
                        try {
                            d1=sdf.parse(rhs.getTrdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            d2=sdf.parse(lhs.getTrdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return d1.compareTo(d2);
                    }
                });

                Myadapter adapter=new Myadapter(dashboard.this,data);//income adapter
                recyclerView.setAdapter(adapter);
                swipe.setRefreshing(false);
            }
        });

    }

}
