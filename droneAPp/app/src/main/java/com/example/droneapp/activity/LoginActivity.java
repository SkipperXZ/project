package com.example.droneapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.droneapp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText textUser,textPass;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textUser = (EditText)findViewById(R.id.userName);
        textPass = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify(textUser.getText().toString(),textPass.getText().toString())){
                    newHomeActivity();
                }else{
                    newHomeActivity();
                }
            }
        });

    }

    private boolean verify(String user,String pass){
        if(user.equalsIgnoreCase("jopark123") && pass.equals(pass)){
            return true;
        }else {
            return false;
        }
    }

    private void newHomeActivity(){
        Intent homeIntent = new Intent(this,HomeActivity.class);
        startActivity(homeIntent);
    }
}
