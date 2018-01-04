package com.nbkakkad.chatapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nbkakkad.chatapp.R;
import com.nbkakkad.chatapp.adapter.MessageListAdapter;
import com.nbkakkad.chatapp.model.Model;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_type_message;
    List<Model> messageList = new ArrayList<Model>();

    private RecyclerView mMessageRecycler;

    private MessageListAdapter mMessageAdapter;
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_type_message =  findViewById(R.id.edittext_chatbox);
        Button button = findViewById(R.id.button_chatbox_send);

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setHasFixedSize(true);
        mMessageRecycler.setLayoutManager(mLayoutManager);
        mMessageAdapter = new MessageListAdapter(getApplicationContext(), messageList);
        mMessageRecycler.setAdapter(mMessageAdapter);



        String openURL = getIntent().getStringExtra("openURL");

        LocalBroadcastManager.getInstance(getApplication()).registerReceiver(
                mMessageReceiver, new IntentFilter("GPSLocationUpdates"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("hai","Send button clicked");
                OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
                String userId = status.getSubscriptionStatus().getUserId();
                String pushToken = status.getSubscriptionStatus().getPushToken();
                boolean isSubscribed = status.getSubscriptionStatus().getSubscribed();

                String heading = et_type_message.getText().toString();

                Model model = new Model();
                model.setTitle(heading);
                model.setImg1(2);

                messageList.add(model);

                adapter(messageList);

                if (isSubscribed) {

                    try {
                        JSONObject notificationContent = new JSONObject("{'contents': {'en': 'The notification message or body'}," +
                                "'include_player_ids': ['" +"PHONE_PLAYER_ID"+ "'], " +
                                "'headings': {'en': '" +heading+ "'}, " +
                                "'big_picture': 'http://i.imgur.com/DKw1J2F.gif'}");
                        OneSignal.postNotification(notificationContent, null);

                        et_type_message.setText(null);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (openURL!= null){

        }


    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("Status");

            //tvNotiTitle.setText(message);

            Model noti = new Model();
            noti.setTitle(message);
            noti.setImg1(1);
            messageList.add(noti);


            adapter(messageList);

        }
    };


    private void adapter(List<Model> msgList){


        mMessageAdapter.notifyItemInserted(msgList.size() - 1);
        mLayoutManager.scrollToPosition(msgList.size() - 1);


    }

}
