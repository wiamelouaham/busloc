package howlingcodes.busloc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText email,password;
    private Button b;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //view id
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        b = findViewById(R.id.signup);
        b.setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        //auth
        mAuth = FirebaseAuth.getInstance();

    }

    private void registerUser(){
        String mail = email.getText().toString().trim();
        String passwd = password.getText().toString().trim();

        //check the iputs for errors

        if(mail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Email is not valid");
            email.requestFocus();
            return;
        }
        if (passwd.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (passwd.length()<6){
            password.setError("is it in?");
            password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            //startActivity(new Intent(RegisterActivity.this, RegTypeActivity.class));
                            //regIntent = new Intent(RegisterActivity.this,RegTypeActivity.class);
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    public void updateUI(FirebaseUser u){
       /* if (u == null){
            //todo go to login
            //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
            return;
        }*/
        //todo else go to login
        //startActivity(new Intent(RegisterActivity.this, RegTypeActivity.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.signup){
                registerUser();
                finish();
        }
    }
}
