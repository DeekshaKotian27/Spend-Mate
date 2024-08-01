package com.example.deeksha.spendmate;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    EditText uname,email,pass,pass2,phone;
    Button signup;
    String name,mail,password,phoneno,Cpassword;
    signupdb su;
    private static final String PASSWORD_PATTERN= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    Pattern psPattern= Pattern.compile(PASSWORD_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        uname=findViewById(R.id.uname);
        email=findViewById(R.id.umail);
        pass=findViewById(R.id.upass);
        pass2=findViewById(R.id.upass2);
        phone=findViewById(R.id.uphone);
        signup=findViewById(R.id.signupbtn);

        su=new signupdb(signup.this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this,MainActivity.class);
                name=uname.getText().toString();
                mail=email.getText().toString();
                password=pass.getText().toString();
                Cpassword=pass2.getText().toString();
                phoneno=phone.getText().toString();

                if(name.isEmpty()|| mail.isEmpty()||password.isEmpty()||Cpassword.isEmpty()||phoneno.isEmpty())
                {
                    Toast.makeText(signup.this, "Fill in all the details", Toast.LENGTH_SHORT).show();
                }
                else if(isvalid())
                {
                    //if password does not match the pattern
                    pass.setText("");
                    pass2.setText("");
                }
                else if(password.compareTo(Cpassword)!=0)
                {
                    Toast.makeText(signup.this, "Password are not equal", Toast.LENGTH_SHORT).show();
                    pass.setText("");
                    pass2.setText("");
                }
                else  //adding to database
                {

                    Boolean check=su.signupinsert(name,mail,password,phoneno);
                    if(check)
                    {
                        Toast.makeText(signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(signup.this, "sign up failed", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
    private boolean isvalid(){
        if(!psPattern.matcher(password).matches()){
            Toast.makeText(signup.this,"Password should contain at least 1 uppercase,1 lowercase.\n"
                    +"Password should contain letters,numbers and special character.\n"+"Minimum length of password id 8.",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            return false;
        }
    }
}

