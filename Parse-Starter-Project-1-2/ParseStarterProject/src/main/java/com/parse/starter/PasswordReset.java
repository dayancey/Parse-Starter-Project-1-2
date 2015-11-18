package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by nabain on 11/14/15.
 */
public class PasswordReset extends ActionBarActivity{

    Button resetBtn;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        resetBtn = (Button) findViewById(R.id.resetBtn);
        emailEditText = (EditText) findViewById(R.id.emailTxt);

        resetBtn.setOnClickListener(handler);
    }

    //onClick listener for upload left image button
    View.OnClickListener handler = new View.OnClickListener(){
        public void onClick(View v){

            String email = emailEditText.getText().toString().trim();

            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // An email was successfully sent with reset instructions.
                    } else {
                        // Something went wrong. Look at the ParseException to see what's up.
                    }
                }
            });

            //Notify user that email has been sent
            Toast.makeText(PasswordReset.this, "Email Sent", Toast.LENGTH_LONG).show();

            //Take user back to login screen
            Intent intent = new Intent(PasswordReset.this, WelcomeActivity.class);
            startActivity(intent);

        }
    };

}
