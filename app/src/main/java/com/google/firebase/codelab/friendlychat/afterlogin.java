package com.google.firebase.codelab.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class afterlogin extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private String mUserID; //unique ID for this user
    private String MyChatpartnerID = "";
    private boolean mLast = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUserID = mFirebaseUser.getUid();
        mFirebaseDatabaseReference.child("users").child(mUserID).setValue(mUserID); // skapar en gren i databasem som heter user
        //final DatabaseReference userRef = mFirebaseDatabaseReference.child("users").child(mUserID);
        final DatabaseReference searchingref = mFirebaseDatabaseReference.child("searching");
        final DatabaseReference reftomyIDwhensearching = mFirebaseDatabaseReference.child("searching").child(mUserID);

        reftomyIDwhensearching.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(mUserID) && mLast)
                {
                    Log.i("Går in i mitt id",mUserID);
                    Intent intent = new Intent(afterlogin.this, MainActivity.class);
                    intent.putExtra("CHATID",mUserID);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if(mFirebaseDatabaseReference.child("searching").removeValue() == null)
                //{
                //}
                //User viktorsbajsapa = new User(mUserID);
                //mFirebaseDatabaseReference.child("searching").setValue(viktorsbajsapa);
                FriendlyMessage msg = new FriendlyMessage();
                msg.setId("fdasf");
                msg.setText("jfsdia");
                //mFirebaseDatabaseReference.child("searching").push().setValue(viktorsbajsapa);
                //mFirebaseDatabaseReference.child("searching").setValue(viktorsbajsapa);
                searchingref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() == false) {
                            User viktorsbajsapa = new User();
                            viktorsbajsapa.setId(mUserID);
                            //mFirebaseDatabaseReference.child("searching").push().setValue(viktorsbajsapa);
                            mFirebaseDatabaseReference.child("searching").child(mUserID).push().setValue(viktorsbajsapa);
                            Log.i("ID SOM SKRIVITS: ",viktorsbajsapa.getId());
                            //mFirebaseDatabaseReference.child("searching").child(mUserID).setValue(mUserID);

                            //reftomyIDwhensearching = mFirebaseDatabaseReference.child("searching").child(mUserID);
                        }
                        else
                        {
                            //MyChatpartnerID =  mFirebaseDatabaseReference.orderByKey().limitToFirst(1);
                            //MyChatpartnerID = dataSnapshot.
                            //User viktorsbajsapa = new User();
                            //viktorsbajsapa.setId(mUserID);
                            //mFirebaseDatabaseReference.child("searching").push().setValue(viktorsbajsapa);


                            Query lastQuery = mFirebaseDatabaseReference.child("searching").orderByKey().limitToLast(1);
                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot){
                                    for (DataSnapshot data : dataSnapshot.getChildren())
                                    {
                                        User myObject = data.getValue(User.class);
                                        MyChatpartnerID = myObject.getId();
                                        FriendlyMessage msg = new FriendlyMessage("HEJSVEJS",mFirebaseUser.getDisplayName(),mFirebaseUser.getPhotoUrl().toString(),mFirebaseUser.getPhotoUrl().toString());
                                        mFirebaseDatabaseReference.child("chats").child(MyChatpartnerID).child("messages").push().setValue(msg);

                                        mLast = false;

                                        data.getRef().removeValue();

                                    }

                                    Log.i("Kommer detta köras?","...");
                                    Intent intent = new Intent(afterlogin.this, MainActivity.class);
                                    intent.putExtra("CHATID",MyChatpartnerID);
                                    startActivity(intent);
                                    finish();
                                }

                                /*
                                public void onDataChange(DataSnapshot dSnapshot) {
                                    MyChatpartnerID = dSnapshot.child("id").getValue().toString();    ;
                                }*/

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //Handle possible errors.
                                }
                            });

                            //if(MyChatpartnerID == null)
                            //{
                            //    MyChatpartnerID = "fat";
                            //}
                            //searchingref.setValue(null);
                            //MyChatpartnerID = "hej";

                            //mFirebaseDatabaseReference.child("chats").child(MyChatpartnerID).setValue(MyChatpartnerID);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //if (mFirebaseDatabaseReference.child("searching").getKey() == null) {

                //}
                //else
                //{
                //    Snackbar.make(view, "There is a child here", Snackbar.LENGTH_LONG)
                //            .setAction("Action", null).show();
                //  }

            }
        });
    }
}
