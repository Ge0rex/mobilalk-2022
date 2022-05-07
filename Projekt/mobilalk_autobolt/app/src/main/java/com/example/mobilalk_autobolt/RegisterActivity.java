package com.example.mobilalk_autobolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;


    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bundle bundle = getIntent().getExtras();
        // int secret_key = bundle.getInt("SECRET_KEY");
        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 99) {
            finish();
        }


        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordAgainEditText);


        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userEmailEditText.setText(userName);
        passwordEditText.setText(password);
        passwordConfirmEditText.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {

        String email = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();


        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            return;
        }




        Log.i(LOG_TAG, "Regisztrált , e-mail: " + email);
        startShopping();
        if((userEmailEditText.getText().toString()==null || userEmailEditText.getText().toString().equals("")) || (passwordEditText.getText().toString()==null || passwordEditText.getText().toString().equals("")) || (passwordConfirmEditText.getText().toString()==null || passwordConfirmEditText.getText().toString().equals("")))
        {Log.d(LOG_TAG, "One or more of the inputs is/are empty");}
        else{
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");
                    startShopping();
                } else {
                    Log.d(LOG_TAG, "User creation failed:", task.getException());
                    Toast.makeText(RegisterActivity.this, "User creation failed:", Toast.LENGTH_LONG).show();
                }
            }
        });}

    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping(/* registered used class */) {
        Intent intent = new Intent(this, ShopListActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }
}