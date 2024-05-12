package com.example.ingatlankereso;

import android.content.Intent;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = LoginActivity.class.getName();
    private static final int RC_SIGN_IN = 123;

    EditText emailET;
    EditText passwordET;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.editTextEmail);
        passwordET = findViewById(R.id.editTextPassword);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
                .requestIdToken("715708576180-pvr18gbgr4vnhem7poagj92vkpou1fru.apps.googleusercontent.com")
                .requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void login(View view) {
        String userEmail = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (userEmail.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Hiányos adatok!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Bejelentkezett: " + userEmail + ", jelszó: " + password);
                    goToRealEstates();
                } else {
                    Log.e(LOG_TAG, "Sikertelen bejelentkezés");
                    Toast.makeText(LoginActivity.this, "Sikertelen bejelentkezés: "+
                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       if(requestCode == RC_SIGN_IN) {
           Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

           try{
               GoogleSignInAccount account = task.getResult(ApiException.class);
               Log.d(LOG_TAG, "firebaseAuthWithGoogle: " + account.getId());
               firebaseAuthWithGoogle(account.getIdToken());
           } catch (ApiException e) {
               Log.w(LOG_TAG, "Nem sikerült a Google fiók bejelentkezés.", e);
           }
       }
   }

   private void firebaseAuthWithGoogle(String idToken) {
       AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
       mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Log.i(LOG_TAG, "signInWithCredential:success");
                   goToRealEstates();
               } else {
                   Log.e(LOG_TAG, "signInWithCredential:failure", task.getException());
               }
           }
       });
   }

    private void goToRealEstates() {
        Intent intent = new Intent(this, RealEstatesActivity.class);
        startActivity(intent);
    }

    public void login_google(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void back(View view) {
        finish();
    }
}