package com.example.parseinstagram.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parseinstagram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private Button setsignup;
    private EditText setusername;
    private EditText setpassword;
    private EditText setemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setsignup = findViewById(R.id.setsignup_btn);
        setusername = findViewById(R.id.setusername_et);
        setpassword = findViewById(R.id.setpassword_et);
        setemail = findViewById(R.id.setemail_et);


        setsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newusername = setusername.getText().toString();
                final String newpassword = setpassword.getText().toString();
                final String newemail = setemail.getText().toString();
                setSignUp(newusername, newpassword, newemail);
                finish();

            }
        });
    }
    private void setSignUp(String username, String password, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
