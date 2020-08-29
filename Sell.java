package net.smallacademy.authenticatorapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sell extends AppCompatActivity {
    EditText bookName,phoneNo,Price;
    Button ok,Back,Logout;
    DatabaseReference reff;
    Peoples peoples;

    Button btnbrowse;
    Button btnupload;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;






    //Button ch,up;
    //ImageView img;
    //StorageReference mStorageRef;
    //private StorageTask uploadTask;
    //public Uri imguri;

    long maxid=0;
    long maxxid=1;
    String bookNames;
    String urls;
    private StorageReference Folder;

    private static final int ImageBack =  1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Peoples");
       btnbrowse = (Button)findViewById(R.id.buttons);
        btnupload= (Button)findViewById(R.id.button);
        imgview = (ImageView)findViewById(R.id.imageView);
        Logout= (Button)findViewById(R.id.btlogout);
        progressDialog = new ProgressDialog(Sell.this);
        //  mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        //   ch=(Button)findViewById(R.id.buttons);
        //up=(Button)findViewById(R.id.button2);
        //    img=(ImageView)findViewById(R.id.imageView);


        //Toast.makeText(Sell.this,"Firebase connection Success",Toast.LENGTH_LONG).show();
        //setContentView(R.layout.activity_main);
        bookName=(EditText)findViewById(R.id.editText2);
        phoneNo=(EditText)findViewById(R.id.editText3);
        Price=(EditText)findViewById(R.id.editText4);
        ok=(Button)findViewById(R.id.button);
        Back=(Button)findViewById(R.id.back);


        reff= FirebaseDatabase.getInstance().getReference().child("Peoples");
        peoples = new Peoples();


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bookN= bookName.getText().toString();
                final String phoneN =phoneNo.getText().toString();
                final String pricee=Price.getText().toString();

                int flag1=0,flag2=0,temp,len1,len2;
                len1=phoneN.length();
                len2=pricee.length();

                for(int i=0;i<len1;i++)
                {

                    temp=phoneN.charAt(i);
                    if(temp<48 || temp>57){
                        flag1=1;
                        break;
                    }
                }

                for(int i=0;i<len2;i++)
                {

                    temp=pricee.charAt(i);
                    if(temp<48 || temp>57){
                        flag2=1;
                        break;
                    }
                }



            /*bookNames = bookName.getText().toString();
                peoples.setBook(bookName.getText().toString().trim());
                peoples.setPhone(phoneNo.getText().toString().trim());
                peoples.setRate(Price.getText().toString().trim());
                reff.child(bookNames).setValue(peoples);*/
                //reff.child(String.valueOf(maxid+1)).setValue(peoples);
                //reff.push().setValue(peoples);

                if(TextUtils.isEmpty(bookN)){
                    bookName.setError("Name is Required.");
                    return;
                }
                if((phoneN.length()!=10) || flag1==1){
                    phoneNo.setError("Phone number not valid");
                    return;
                }
                if(flag2==1){
                    Price.setError("Price not valid");
                    return;
                }
                Toast.makeText(Sell.this,"Data Inserted Successfully",Toast.LENGTH_LONG).show();

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Choosing.class));
                finish();
            }
        });

    }


    public void UploadData(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ImageBack);

        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageBack){
            if(resultCode == RESULT_OK){
                Uri ImageData = data.getData();
                final StorageReference Imagename = Folder.child("image"+ImageData.getLastPathSegment());
                Imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String userNo = Long.toString(maxid+maxxid);
                                bookNames = bookName.getText().toString();
                                peoples.setBook(bookName.getText().toString().trim());
                                peoples.setPhone(phoneNo.getText().toString().trim());
                                peoples.setRate(Price.getText().toString().trim());
                                peoples.setUserNo(userNo);
                                //reff.child(bookNames).setValue(peoples);
                                DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference("Peoples").child(String.valueOf(maxid+1));
                              // DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference("Peoples").child(bookNames);
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("imageurl", String.valueOf(uri));

                                imagestore.child(bookNames).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Sell.this,"Finally completed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                peoples.setImageURL(String.valueOf(uri));
                                //reff.child(bookNames).setValue(peoples);
                                reff.child(String.valueOf(maxid+1)).setValue(peoples);
                            }
                        });
                    }
                });
            }
        }
    }





}