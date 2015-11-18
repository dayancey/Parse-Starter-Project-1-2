package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

import static com.parse.starter.R.drawable.user_icon;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    String email;
    byte[] profilePic;
    ParseFile file;
    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the signup form.
        usernameEditText = (EditText) findViewById(R.id.usernameSignupEdt);

        passwordEditText = (EditText) findViewById(R.id.passwordSignupEdt);
        passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);

        username = (String) getIntent().getSerializableExtra("userName");
        password = (String) getIntent().getSerializableExtra("password");
        email = (String) getIntent().getSerializableExtra("email");

        usernameEditText.setText(username);
        passwordEditText.setText(password);


                // Set up the submit button click handler
        Button mActionButton = (Button) findViewById(R.id.signupBtt);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();

        //Drawable drawable = this.getResources().getDrawable(R.drawable.user_icon);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), user_icon);


        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] arr = stream.toByteArray();
        profilePic = arr;

        // Set up a new Parse user

        file = new ParseFile("profilePicture", profilePic);
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                saveUser();
            }
        });


    }

    public void saveUser(){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("profilePicture", file);

        // Call the Parse signup method
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    // Show the error message
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.putExtra("userName", currentUser.getUsername().toString().trim());
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void haveAccount(View view){
        startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
