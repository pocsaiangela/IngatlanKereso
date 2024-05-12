package com.example.ingatlankereso;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText emailEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText passwordEditText;
    EditText getPasswordConfirmationEditText;

    private  static  final String LOG_TAG = RegisterActivity.class.getName();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        emailEditText = findViewById(R.id.editTextEmail);
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        passwordEditText = findViewById(R.id.editTextPassword);
        getPasswordConfirmationEditText = findViewById((R.id.editTextPasswordconfirmation));

        mAuth = FirebaseAuth.getInstance();

        //Log.i(LOG_TAG, "onCreate");??
    }

    public void register(View view) {
        String userEmail = emailEditText.getText().toString();
        String userFirstName = firstNameEditText.getText().toString();
        String userLastName = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirmation = getPasswordConfirmationEditText.getText().toString();

        if (!password.equals(passwordConfirmation)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            Snackbar.make(findViewById(R.id.main), "Nem egyenlő a jelszó és a megerősítése.",
                            Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        if (userEmail.isEmpty()) {
            Log.e(LOG_TAG, "Nem adott meg emailt.");
            Snackbar.make(findViewById(R.id.main), "Nem adott meg emailt.",
                            Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        if (userLastName.isEmpty()) {
            Log.e(LOG_TAG, "Nem adott vezeték nevet.");
            Snackbar.make(findViewById(R.id.main), "Nem adott vezeték nevet.",
                            Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        if (password.isEmpty() || passwordConfirmation.isEmpty()) {
            Log.e(LOG_TAG, "Nem adott meg jelszót.");
            Snackbar.make(findViewById(R.id.main), "Nem adott meg jelszót.",
                            Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        if (userFirstName.isEmpty()) {
            Log.e(LOG_TAG, "Nem adott keresztnevet.");
            Snackbar.make(findViewById(R.id.main), "Nem adott keresztnevet.",
                            Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + userFirstName + " " + userLastName + ", e-mail: " + userEmail);
        //TODO: a regisztrációs funkcionalitást meg kellene valósítani egyszer

        mAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG,"Profil sikeresen regisztrálva");
                    goToRealEstates();
                } else {
                    Log.d(LOG_TAG, "A profil nem regisztrált");
                    Toast.makeText(RegisterActivity.this, "A profil nem regisztrált"+
                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    private void goToRealEstates(/*registered user data*/){
        Intent intent = new Intent(this, RealEstatesActivity.class);
        startActivity(intent);
    }

}