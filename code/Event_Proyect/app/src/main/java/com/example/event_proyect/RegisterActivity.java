package com.example.event_proyect;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.event_proyect.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    String TAG = "SignInLoginActivity";
    private FirebaseAuth mAuth;
    private Button btnRegister2,btnReturn;
    EditText user2,pass2;
    String stringUser2,stringPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister2 = findViewById(R.id.btnGetReg);
        user2 = findViewById(R.id.regUser);
        pass2 = findViewById(R.id.regPassword);
        btnReturn = findViewById(R.id.btnReturn);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    stringUser2 = user2.getText().toString();
                    stringPass2 = pass2.getText().toString();
                    register(stringUser2,stringPass2);
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

    }

    private void register(String email,String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent dashboardActivity = new Intent(RegisterActivity.this, MenuHome.class);
                            startActivity(dashboardActivity);
                            RegisterActivity.this.finish();
                        } else {
                            // If sign up fails, display a message to the user
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }



}