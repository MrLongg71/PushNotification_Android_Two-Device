package com.example.push;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView lv;
    EditText messs,mess1;
    TextView txtEmail;
    Button btnsend;
    ArrayList<User> arrayList = new ArrayList<>();
    DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv = findViewById(R.id.lv);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();
                for(DataSnapshot value : dataSnapshot.getChildren()){
                    User user = value.getValue(User.class);
                    arrayList.add(user);
                }

                ArrayList<String> userlis = new ArrayList<>();

                for(User user : arrayList){
                    userlis.add(user.email);
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,userlis);
                    lv.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dataUser.child("User").addValueEventListener(valueEventListener);





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog dialog = new Dialog(Main2Activity.this);
                        dialog.setContentView(R.layout.dialog_input);
                txtEmail = dialog.findViewById(R.id.txtEmail);
                btnsend = dialog.findViewById(R.id.btnSend);
                messs = dialog.findViewById(R.id.mess);
                mess1 = dialog.findViewById(R.id.mess1);


                txtEmail.setText(arrayList.get(position).email);

                Toast.makeText(Main2Activity.this, "" +auth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

                btnsend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseMessaging.getInstance().subscribeToTopic("android");
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("article").push();
                        Article article = new Article(messs.getText().toString(),mess1.getText().toString(),arrayList.get(position).key);


                        myRef.setValue(article);
                    }
                });





                dialog.show();
//
            }
        });
    }
}
