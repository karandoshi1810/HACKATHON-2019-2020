package net.smallacademy.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Delete extends AppCompatActivity {
    Button mDelete,mtnBake,mtnLogout;
    EditText mID;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__delete);
        mID = (EditText) findViewById(R.id.id_et);
        mDelete = (Button)findViewById(R.id.btdelete);
        mtnBake = (Button)findViewById(R.id.Backkn);
        mtnLogout= (Button)findViewById(R.id.btlogoutn);
        databaseReference= FirebaseDatabase.getInstance().getReference("Peoples");

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = mID.getText().toString().trim();
                databaseReference.child("Peoples").child(ID).addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        databaseReference.child(ID).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        });
        mtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mtnBake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Choosing.class));
                finish();
            }
        });
    }
}