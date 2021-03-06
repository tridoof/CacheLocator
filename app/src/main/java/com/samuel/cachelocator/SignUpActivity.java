package com.samuel.cachelocator;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity {

    protected EditText usernameEditText;
    protected EditText passwordEditText;
    protected EditText passwordAgainEditText;
    protected Button mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = (EditText)findViewById(R.id.usernameField);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        passwordAgainEditText = (EditText)findViewById(R.id.passwordAgainField);

        mActionButton = (Button)findViewById(R.id.signupButton);
        mActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signUserUp();
            }
        });
    }

    private void signUserUp(){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()){
            //A field is empty, display message
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage(R.string.signup_error_message);
            builder.setTitle(R.string.signup_error_title);
            builder.setPositiveButton(android.R.string.ok, null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if(!password.equals(passwordAgain)){
            //Passwords don't match
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage(R.string.signup_error_message);
            builder.setTitle(R.string.signup_error_title);
            builder.setPositiveButton(android.R.string.ok, null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            //Sign up process
            ParseUser newUser = new ParseUser();
            newUser.setUsername(username);
            newUser.setPassword(password);

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        //Sign up successful
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        //Error
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage(e.getMessage())
                                .setTitle(R.string.signup_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }

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
