package howlingcodes.busloc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bl;
    private EditText email,password;
    private TextView bs;
    private FirebaseAuth mAuth;
    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef ;//ref to the current user info in the realtime database
    private User currentUserObject;
    private String type;
    private  String currentUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bs=findViewById(R.id.signup);
        bs.setOnClickListener(this);
        bl = findViewById(R.id.login);
        bl.setOnClickListener(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //handle the login logic
    private void loginUser() {
        String mail = email.getText().toString().trim();
        String passwd = password.getText().toString().trim();

        //check the iputs for errors

        if (mail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Email is not valid");
            email.requestFocus();
            return;
        }
        if (passwd.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (passwd.length() < 6) {
            password.setError("is it in?");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //todo check the user type and redirect it to his activity
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void updateUI(FirebaseUser u){
        if (u == null){
            return;
        }
        else{
            //get the user database from the realtime database
            currentUid = u.getUid();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    type = dataSnapshot.child("users").child(currentUid).child("type").getValue(String.class);
                    if (type == null){
                        //if the user type is null start the activity of the type form
                        startActivity(new Intent(MainActivity.this, RegTypeActivity.class));
                        finish();
                    }
                        else {
                        finish();
                        if (type.equals("admin")) {
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            finish();

                        } else if (type.equals("parent")) {
                            startActivity(new Intent(MainActivity.this, ParentActivity.class));

                        } else if (type.equals("bus")) {
                            startActivity(new Intent(MainActivity.this, BusActivity.class));
                        } else {
                            //todo error toast undefined user type
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //currentUserObject = myRef.child(currentUid).getValue(User.class);


            /* myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentUserObject = dataSnapshot.child(currentUid).getValue(User.class);

                    //get the string type and store it in the type string

                    type = currentUserObject.getType();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }

            });*/

            //chack the type of the user

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.signup){
            startActivity(new Intent(this, RegisterActivity.class));
        }
        else if (v.getId()== R.id.login){
            loginUser();
        }

    }
}
