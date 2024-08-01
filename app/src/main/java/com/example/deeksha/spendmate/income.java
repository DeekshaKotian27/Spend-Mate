package com.example.deeksha.spendmate;

import android.app.AlertDialog;
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
import android.widget.Toast;

import java.util.Calendar;

public class income extends AppCompatActivity {

    EditText date,amount,note,type;
    String idate,amt,desc,itype;
    Button save;
    signupdb db;
    ImageButton ib;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        date=findViewById(R.id.date);
        amount=findViewById(R.id.amount);
        note=findViewById(R.id.note);
        type=findViewById(R.id.type);
        save=findViewById(R.id.savebtn);
        ib=findViewById(R.id.calbtn);

        date.setInputType(InputType.TYPE_NULL);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(income.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idate=date.getText().toString();
                amt=amount.getText().toString();
                desc=note.getText().toString();
                itype=type.getText().toString();

                Intent in=getIntent();
                final int cust_id= in.getIntExtra("id",0);

                db=new signupdb(income.this);

                if(idate.isEmpty()||amt.isEmpty()||itype.isEmpty())
                {
                    Toast.makeText(income.this, "fill all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Boolean check=db.incomeinsert(cust_id,amt,itype,desc,idate);
                    if(check)
                    {
                        home h=new home();//updating values in home page
                        h.updateincome(amt);

                        Toast.makeText(income.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                        date.setText("");
                        amount.setText("");
                        note.setText("");
                        type.setText("");
                    }
                    else
                        Toast.makeText(income.this, "FAILED!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}


