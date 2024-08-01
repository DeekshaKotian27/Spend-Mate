package com.example.deeksha.spendmate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class edit extends AppCompatActivity {

    EditText date,amount,note,type;
    TextView main;
    String idate,amt,desc,itype;
    Button save;
    signupdb db;
    ImageButton ib;
    DatePickerDialog picker;
    Button edit,dlt,upd;
    Integer cust_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toast.makeText(this, "Click on edit to make any changes", Toast.LENGTH_SHORT).show();

        date=findViewById(R.id.date);
        amount=findViewById(R.id.amount);
        note=findViewById(R.id.note);
        type=findViewById(R.id.type);
        save=findViewById(R.id.savebtn);
        ib=findViewById(R.id.calbtn);
        edit=findViewById(R.id.editbtn);
        dlt=findViewById(R.id.dltbtn);
        main=findViewById(R.id.main);
        upd=findViewById(R.id.updbtn);


        date.setInputType(InputType.TYPE_NULL);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(edit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Intent intent=getIntent();
        cust_id= intent.getIntExtra("cust_id",0);
        final int tr_id=intent.getIntExtra("tr_id",0);
        final int flag=intent.getIntExtra("flag",0);

        db=new signupdb(edit.this);
        Cursor res=db.idata(cust_id,tr_id);
        Cursor res1=db.edata(cust_id,tr_id);

        if(flag==0)
        {
            while (res.moveToNext())
            {
                idate=res.getString(3);
                amt=res.getString(0);
                desc=res.getString(2);
                itype=res.getString(1);
                main.setText("Edit Income");
            }
        }
        else
        {
            while (res1.moveToNext())
            {
                idate=res1.getString(3);
                amt=res1.getString(0);
                desc=res1.getString(2);
                itype=res1.getString(1);
                main.setText("Edit Expense");
            }
        }

        date.setText(idate);
        type.setText(itype);
        note.setText(desc);
        amount.setText(amt);

        ib.setEnabled(false);
        type.setEnabled(false);
        note.setEnabled(false);
        amount.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib.setEnabled(true);
                type.setEnabled(true);
                note.setEnabled(true);
                amount.setEnabled(true);
                Toast.makeText(edit.this, "You can make changes now", Toast.LENGTH_SHORT).show();
            }
        });

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dte,des,iamt,tpe;
                dte=date.getText().toString();
                des=note.getText().toString();
                iamt=amount.getText().toString();
                tpe=type.getText().toString();
                if(idate.equals(dte) && amt.equals(iamt) && desc.equals(des) && itype.equals(tpe))
                {
                    Toast.makeText(edit.this, "Edit to make changes!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(flag==0)
                    {
                        //income update
                        db.updateIncome(cust_id,tr_id,iamt,tpe,des,dte);
                        if(!amt.equals(iamt))
                        {
                            home h=new home();//updating values in home page
                            h.incomechange(Integer.valueOf(amt),Integer.valueOf(iamt));
                        }

                    }
                    else
                    {
                        //expense update
                        db.updateExpense(cust_id,tr_id,iamt,tpe,des,dte);
                        if(!amt.equals(iamt))
                        {
                            home h=new home();//updating values in home page
                            h.expensechange(Integer.valueOf(amt),Integer.valueOf(iamt));
                        }

                    }
                    Toast.makeText(edit.this, "Changes Updated Successfully", Toast.LENGTH_SHORT).show();
                    Toast.makeText(edit.this, "Refresh to reflect the changes", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                {
                    //deleting income
                    db.delincome(tr_id);
                    home h=new home();//updating value in home
                    h.delAmt(Integer.valueOf(amt),flag);
                }
                else
                {
                    //deleting expense
                    db.delexpense(tr_id);
                    home h=new home();//updating value in home
                    h.delAmt(Integer.valueOf(amt),flag);
                }
                Toast.makeText(edit.this, "Deleted!!", Toast.LENGTH_SHORT).show();
                Toast.makeText(edit.this, "Refresh to reflect the changes", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

}