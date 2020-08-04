package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Common.Common;

public class Login extends AppCompatActivity {

    EditText phone,password;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone =(EditText) findViewById(R.id.edtPhone);
        password = (EditText) findViewById(R.id.edtPassword);
        signIn = (Button)findViewById(R.id.btnSignIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Please Wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.child(phone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(password.getText().toString()))
                            {
//                                Toast.makeText(Login.this, "SignedIn successfully", Toast.LENGTH_SHORT).show();
                                Intent homIntent=new Intent(Login.this,Hom.class);
                                Common.currentUser = user;
                                startActivity(homIntent);
                                finish();
                            }
                            else
                                Toast.makeText(Login.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(Login.this, "User not Exist", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}