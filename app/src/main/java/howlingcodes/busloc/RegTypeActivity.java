package howlingcodes.busloc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fname,lname;
    private RadioGroup type;
    private FirebaseAuth mAuth;
    private User userObject;
    private Button cont;
    private FirebaseDatabase db;
    private FirebaseUser currentUser;
    private String userType;
    private DatabaseReference ref;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         currentUser = mAuth.getCurrentUser();
         if (currentUser==null){
             startActivity(new Intent(RegTypeActivity.this,MainActivity.class));
             finish();
         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_type);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        type = findViewById(R.id.type);
        cont = findViewById(R.id.updateprofile);
        cont.setOnClickListener(this);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();


        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.admin:
                        //todo go to admin reg continue
                        userType = "admin";
                        break;
                    case R.id.parent:
                        //todo go to parent reg continue
                        userType="parent";
                        break;
                    case R.id.bus:
                        //todo go to bus reg continue
                        userType ="bus";
                        break;
                }
            }
        });


    }

    public void checkFields(){
        //todo check fields logic
    }

    public void pushUser(){
        //todo check if the fields are okay
        //todo how are you fields? are you okay?
        checkFields();
        //todo if okay , contunue and create a user object and the push it to the database

        String id = currentUser.getUid();
        String mail = currentUser.getEmail().trim();
        String firstname = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        userObject = new User(mail,firstname,lastname,userType);
        ref.child("users").child(id).setValue(userObject);
        if (currentUser == null){
            return;
        }
        else{
            //get the user database from the realtime database
            final String currentUid = currentUser.getUid();
            ref = db.getReference();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userType = dataSnapshot.child(currentUid).child("type").getValue(String.class);
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
            if (type == null){
                //if the user type is null start the activity of the type form
                startActivity(new Intent(RegTypeActivity.this, RegTypeActivity.class));
                finish();
            }

            if (userType == "admin"){
                //todo redirect to admin intent
                startActivity(new Intent(RegTypeActivity.this,AdminActivity.class));

            }
            else if (userType == "parent"){
                //todo redirect to parent intent
                startActivity(new Intent(RegTypeActivity.this,ParentActivity.class));

            }
            else if (userType == "bus"){
                //todo redirect to bus intent
                startActivity(new Intent(RegTypeActivity.this,BusActivity.class));

            }
            else{
                //todo error toast undefined user type
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.updateprofile){
            pushUser();
        }
        //todo then check the user type and send him to his appropriate activity
    }
}

