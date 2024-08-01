package com.example.deeksha.spendmate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText mail,pass;
    Button login,signup;
    String umail,password;
    signupdb db;
    String loginmail,loginpass,loginname;
    int cust_id;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.btnsignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,signup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,home.class);
                umail=mail.getText().toString();
                password=pass.getText().toString();

                db=new signupdb(MainActivity.this);

                Cursor res = db.signupgetdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "sign up to begin", Toast.LENGTH_SHORT).show();

                }

                if(umail.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    while(res.moveToNext())
                    {
                        loginmail=res.getString(0);
                        loginpass=res.getString(1);
                        loginname=res.getString(2);
                        cust_id= Integer.parseInt(res.getString(3));
                        if(loginmail.compareTo(umail)==0 &&loginpass.compareTo(password)==0)
                        {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            mail.setText("");
                            pass.setText("");
                            i.putExtra("name",loginname);
                            i.putExtra("id",cust_id);
                            startActivity(i);
                            return;
                        }
                        else{
                            flag=1;
                        }
                    }
                }

                if(flag==1)
                {
                    Toast.makeText(MainActivity.this, "Invalid mail and password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}


