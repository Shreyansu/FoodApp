package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText name,phone,password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name= (MaterialEditText)findViewById(R.id.edtName);
        phone=(MaterialEditText)findViewById(R.id.edtPhone);
        password=(MaterialEditText)findViewById(R.id.edtPassword);
        signUp=(Button)findViewById(R.id.btnSignUp);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.child(phone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "User already exist", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User(name.getText().toString(),password.getText().toString());
                            table_user.child(phone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}