package com.example.push;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    String token = "";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        final EditText titleEditText = (EditText)findViewById(R.id.et_title);
        final EditText authorEditText = (EditText)findViewById(R.id.et_author);
        Button submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = titleEditText.getText().toString();
                final String pass =  authorEditText.getText().toString();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                return;
                                            }
                                            database.getReference().child("User").child(auth.getCurrentUser().getUid()).setValue(new User(auth.getCurrentUser().getUid(),email,task.getResult().getToken()));

                                            Log.d("token>>>.", task.getResult().getToken());
                                            startActivity(new Intent(MainActivity.this,Main2Activity.class));

                                        }
                                    });



                        }
                    }
                });







//
//                FirebaseInstanceId.getInstance().getInstanceId()
//                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                if (!task.isSuccessful()) {
//                                    return;
//                                }
//                                token = task.getResult().getToken();
//                                Log.d("kiemtrammm", token);
//                                FirebaseMessaging.getInstance().subscribeToTopic("android");
//                                DatabaseReference myRef = database.getReference("token").push();
//                                Article article = new Article(email,pass);
//                                myRef.setValue(article);
//                            }
//                        });

            }
        });
    }
}
