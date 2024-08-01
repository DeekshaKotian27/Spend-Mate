package com.example.deeksha.spendmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class home extends AppCompatActivity {

    private static TextView welcome,tincome,texpense,tbalance;
    Button income,expense,dashboard,logout;
    signupdb db;
    private static Integer income_amt,expense_amt,finalamt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        welcome=findViewById(R.id.welcome);
        income=findViewById(R.id.btnIncome);
        expense=findViewById(R.id.btnExpense);
        dashboard=findViewById(R.id.btnDash);
        texpense=findViewById(R.id.exp);
        tincome=findViewById(R.id.income);
        logout=findViewById(R.id.logoutbtn);
        tbalance=findViewById(R.id.balance);


        Intent intent=getIntent();
        String addname=intent.getStringExtra("name");
        final int cust_id= intent.getIntExtra("id",0);

        db=new signupdb(home.this);
        Cursor res = db.incomegetdata(cust_id);
        Cursor res1 = db.expensegetdata(cust_id);

        income_amt=0;
        expense_amt=0;
        finalamt=0;
        while(res.moveToNext())
        {
            income_amt= income_amt+Integer.valueOf(res.getString(0));
        }
        while(res1.moveToNext())
        {
            expense_amt= expense_amt+Integer.valueOf(res1.getString(0));

        }
        finalamt=income_amt-expense_amt;

        welcome.setText(welcome.getText()+" "+addname.toUpperCase());
        tincome.setText(tincome.getText()+Integer.toString(income_amt));
        texpense.setText(texpense.getText()+Integer.toString(expense_amt));
        tbalance.setText(tbalance.getText()+Integer.toString(finalamt));

//        if(finalamt<=200)
//        {
//            GradientDrawable shape =  new GradientDrawable();
//            shape.setCornerRadius( 50 );
//            shape.setPadding(20,30,1,1);
//            shape.setColor(Color.argb(255,255,0,0));
//            tbalance.setBackground(shape);
//        }

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(home.this, income.class);
                intent1.putExtra("id",cust_id);
                startActivity(intent1);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(home.this, expense.class);
                intent1.putExtra("id",cust_id);
                startActivity(intent1);
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(home.this, dashboard.class);
                intent1.putExtra("id",cust_id);
                startActivity(intent1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setTitle("Confirm!").
                        setMessage("You sure, that you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent1=new Intent(home.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });


    }

    public void updateincome(String amt) {
        Integer oldincome= income_amt;
        Integer newincome=Integer.valueOf(amt);
        Integer income=oldincome+newincome;
        income_amt=income;
        tincome.setText("INCOME-"+Integer.toString(income));

        Integer oldbalance= finalamt;
        Integer newbalance=oldbalance+newincome;
        finalamt=newbalance;
        tbalance.setText("BALANCE-"+Integer.toString(newbalance));
    }

    //@TargetApi(29)
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void updateexpense(String amt) {
        Integer oldexpense= expense_amt;
        Integer newexpense=Integer.valueOf(amt);
        Integer expense=oldexpense+newexpense;
        expense_amt=expense;
        texpense.setText("EXPENSE-"+Integer.toString(expense));

        Integer oldincome= finalamt;
        Integer newincome=oldincome-newexpense;
        finalamt=newincome;
        tbalance.setText("BALANCE-"+Integer.toString(newincome));

//        if(finalamt<=200)
//        {
//            GradientDrawable shape =  new GradientDrawable();
//            shape.setCornerRadius( 50);
//            shape.setPadding(20,30,1,1);
//            shape.setColor(Color.argb(255,255,0,0));
//            tbalance.setBackground(shape);
//        }
    }

    //update income after changing values
    public void incomechange(Integer oldincome,Integer newincome)
    {
        if(oldincome>newincome)
        {
            //when the old income is greater than the updated one, we have minus it from income and balance
            Integer diff=oldincome-newincome;//changing income
            Integer f=income_amt-diff;
            income_amt=f;
            tincome.setText("INCOME-"+Integer.toString(f));

            Integer b=finalamt-diff;//changing balance
            finalamt=b;
            tbalance.setText("BALANCE-"+Integer.toString(b));
        }
        else{
            //when the old income is less than the updated one, we have add it to income and balance
            Integer diff=newincome-oldincome;//changing income
            Integer f=income_amt+diff;
            income_amt=f;
            tincome.setText("INCOME-"+Integer.toString(f));

            Integer b=finalamt+diff;
            finalamt=b;
            tbalance.setText("BALANCE-"+Integer.toString(b));
        }
    }

    //update income after changing values
    public void expensechange(Integer oldamt,Integer newamt)
    {
        if(oldamt>newamt)
        {
            //the differnce amt,we have to subtract it from expense and add it to balance
            Integer diff=oldamt-newamt;//changing expense
            Integer f=expense_amt-diff;
            expense_amt=f;
            texpense.setText("EXPENSE-"+Integer.toString(f));

            Integer b=finalamt+diff;//changing balance
            finalamt=b;
            tbalance.setText("BALANCE-"+Integer.toString(b));
        }
        else
        {
            //the differnce amt,we have to subtract it from balance and add it to expense
            Integer diff=newamt-oldamt;//changing expense
            Integer f=expense_amt+diff;
            expense_amt=f;
            texpense.setText("EXPENSE-"+Integer.toString(f));

            Integer b=finalamt-diff;//changing balance
            finalamt=b;
            tbalance.setText("BALANCE-"+Integer.toString(b));
        }

    }

    //updating when deleted
    public void delAmt(Integer amt,Integer flag)
    {
        if(flag==0)
        {
            Integer f=income_amt-amt;
            income_amt=f;
            tincome.setText("INCOME-"+Integer.toString(f));

            Integer d=finalamt-amt;
            finalamt=d;
            tbalance.setText("BALANCE-"+Integer.toString(d));
        }
        else
        {
            Integer f=expense_amt-amt;
            expense_amt=f;
            texpense.setText("EXPENSE-"+Integer.toString(f));

            Integer b=finalamt+amt;//changing balance
            finalamt=b;
            tbalance.setText("BALANCE-"+Integer.toString(b));
        }
    }

    @Override
    public void onBackPressed() {
        //since the below code is commented,i cannot go back unless i logout
        //super.onBackPressed();
    }
}