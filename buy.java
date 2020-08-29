package net.smallacademy.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class buy extends AppCompatActivity {
     EditText mSearchField;
     Button mSearchBtn;
    RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Peoples");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (Button) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });
    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(buy.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("book").startAt(searchText).endAt(searchText + "\uf8ff");



        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getbook(), model.getrate(), model.getimageURL(),model.getphone(),model.getuserNo());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userbook, String userrate, String userimageURL, String userphone,String useruserNo){

            TextView user_book = (TextView) mView.findViewById(R.id.name_text);
            TextView user_rate = (TextView) mView.findViewById(R.id.status_text);
            TextView user_phone = (TextView) mView.findViewById(R.id.phone_text);
            TextView user_userNo = (TextView)  mView.findViewById(R.id.userno_text);
            ImageView user_imageURL = (ImageView) mView.findViewById(R.id.profile_image);



            user_book.setText(userbook);
            user_rate.setText(userrate);
            user_phone.setText(userphone);
            user_userNo.setText(useruserNo);
            Glide.with(ctx).load(userimageURL).into(user_imageURL);


        }




    }


}
