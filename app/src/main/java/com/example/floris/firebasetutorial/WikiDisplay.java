package com.example.floris.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Map;

public class WikiDisplay extends AppCompatActivity {
    private TextView DisplayText;
    private Firebase mRef;
    private FirebaseAuth mAuth;
    private Button DeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_display);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mRef = new Firebase("https://fir-tutorial-a2a99.firebaseio.com/Users/").child(user_id);
        final String value = getIntent().getStringExtra("pagename");
        DisplayText = (TextView) findViewById(R.id.wikidisplayTV);
        DeleteButton = (Button) findViewById(R.id.deletebutton);

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child(value).removeValue();
                Intent dbIntent = new Intent(WikiDisplay.this,ListviewActivity.class);
                startActivity(dbIntent);
                            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String texttodisplay = map.get(value);
                DisplayText.setText(texttodisplay);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {




            }
        });


    }

}
